package org.geektimes.context;

import org.geektimes.function.ThrowableAction;
import org.geektimes.function.ThrowableFunction;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Old Java Component Context based on JNDI
 */
public class ClassicComponentContext implements ComponentContext {
    public static final String CONTEXT_NAME = ClassicComponentContext.class.getName();

    private static final String COMPONENT_ENV_CONTEXT_NAME = "java:comp/env";

    private static final Logger logger = Logger.getLogger(CONTEXT_NAME);

    private static ServletContext servletContext;

    private Context envContext; // Component Env Context

    private ClassLoader classLoader;

    private Map<String, Object> componentsCache = new LinkedHashMap<>();

    /**
     * @PreDestroy Cache method, key is method, value is object
     */
    private Map<Method, Object> preDestroyMethodCache = new LinkedHashMap<>();

    public static ClassicComponentContext getInstance() {
        return (ClassicComponentContext) servletContext.getAttribute(CONTEXT_NAME);
    }

    public void init(ServletContext servletContext) throws RuntimeException {
        ClassicComponentContext.servletContext = servletContext;
        servletContext.setAttribute(CONTEXT_NAME, this);
        this.init();
    }

    protected void instantiateComponents() {
        List<String> componentNames = listAllComponentNames();
        componentNames.forEach(name -> componentsCache.put(name, lookupComponent(name)));
    }

    protected void initializeComponents() {
        componentsCache.values().forEach(this::initializeComponent);
    }

    public void initializeComponent(Object component) {
        Class<?> componentClass = component.getClass();
        injectComponent(component, componentClass);
        List<Method> candidateMethods = findCandidateMethods(componentClass);
        processPostConstruct(component, candidateMethods);
        processPreDestroyMetadata(component, candidateMethods);
    }

    private List<Method> findCandidateMethods(Class<?> componentClass) {
        return Stream.of(componentClass.getMethods())
                .filter(method ->
                        !Modifier.isStatic(method.getModifiers()) &&
                                method.getParameterCount() == 0)
                .collect(Collectors.toList());
    }

    private void registerShutdownHook() {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            processPreDestroy();

        }));
    }

    public void injectComponent(Object component) {
        injectComponent(component, component.getClass());
    }

    protected void injectComponent(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getDeclaredFields())
                .filter(field -> {
                    int mods = field.getModifiers();
                    return !Modifier.isStatic(mods) &&
                            field.isAnnotationPresent(Resource.class);
                }).forEach(field -> {
            Resource resource = field.getAnnotation(Resource.class);
            String resourceName = resource.name();
            Object injectedObject = lookupComponent(resourceName);
            field.setAccessible(true);
            try {
                field.set(component, injectedObject);
            } catch (IllegalAccessException e) {
            }
        });
    }

    private void processPostConstruct(Object component, List<Method> candidateMethods) {
        candidateMethods
                .stream()
                .filter(method -> method.isAnnotationPresent(PostConstruct.class))
                .forEach(method -> {
                    ThrowableAction.execute(() -> method.invoke(component));
                });
    }


    private void processPreDestroyMetadata(Object component, List<Method> candidateMethods) {
        candidateMethods.stream()
                .filter(method -> method.isAnnotationPresent(PreDestroy.class))
                .forEach(method -> {
                    preDestroyMethodCache.put(method, component);
                });
    }

    private void processPreDestroy() {
        for (Method preDestroyMethod : preDestroyMethodCache.keySet()) {
            Object component = preDestroyMethodCache.remove(preDestroyMethod);
            ThrowableAction.execute(() -> preDestroyMethod.invoke(component));
        }
    }

    protected <R> R executeInContext(ThrowableFunction<Context, R> function) {
        return executeInContext(function, false);
    }

    protected <R> R executeInContext(ThrowableFunction<Context, R> function, boolean ignoredException) {
        return executeInContext(this.envContext, function, ignoredException);
    }

    private <R> R executeInContext(Context context, ThrowableFunction<Context, R> function,
                                   boolean ignoredException) {
        R result = null;
        try {
            result = ThrowableFunction.execute(context, function);
        } catch (Throwable e) {
            if (ignoredException) {
                logger.warning(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public <C> C lookupComponent(String name) {
        return executeInContext(context -> (C) context.lookup(name));
    }


    public <C> C getComponent(String name) {
        return (C) componentsCache.get(name);
    }

    public List<String> getComponentNames() {
        return new ArrayList<>(componentsCache.keySet());
    }

    private List<String> listAllComponentNames() {
        return listComponentNames("/");
    }

    protected List<String> listComponentNames(String name) {
        return executeInContext(context -> {
            NamingEnumeration<NameClassPair> e = executeInContext(context, ctx -> ctx.list(name), true);

            if (e == null) {
                return Collections.emptyList();
            }

            List<String> fullNames = new LinkedList<>();
            while (e.hasMoreElements()) {
                NameClassPair element = e.nextElement();
                String className = element.getClassName();
                Class<?> targetClass = classLoader.loadClass(className);
                if (Context.class.isAssignableFrom(targetClass)) {
                    fullNames.addAll(listComponentNames(element.getName()));
                } else {
                    String fullName = name.startsWith("/") ?
                            element.getName() : name + "/" + element.getName();
                    fullNames.add(fullName);
                }
            }
            return fullNames;
        });
    }

    @Override
    public void init() {
        initClassLoader();
        initEnvContext();
        instantiateComponents();
        initializeComponents();
        registerShutdownHook();
    }

    private void initClassLoader() {
        this.classLoader = servletContext.getClassLoader();
    }

    @Override
    public void destroy() throws RuntimeException {
        processPreDestroy();
        clearCache();
        closeEnvContext();
    }

    private void closeEnvContext() {
        close(this.envContext);
    }

    private void clearCache() {
        componentsCache.clear();
        preDestroyMethodCache.clear();
    }

    private void initEnvContext() throws RuntimeException {
        if (this.envContext != null) {
            return;
        }
        Context context = null;
        try {
            context = new InitialContext();
            this.envContext = (Context) context.lookup(COMPONENT_ENV_CONTEXT_NAME);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            close(context);
        }
    }

    private static void close(Context context) {
        if (context != null) {
            ThrowableAction.execute(context::close);
        }
    }

}