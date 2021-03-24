# 作業提交
## 第一週
加了一個 controller，可以顯示註冊頁面。

但是實際跑起來會報錯...

JNDI 設定還搞不懂...

## 第二週
把 User domain 加上一些 validator。

`ComponentContext` 執行會報錯，還不明白錯誤原因...

用依賴注入與依賴查找實現用戶註冊功能，還沒有頭緒...

## 第三週
依照 [官方 reference](https://jolokia.org/reference/html/agents.html#agents-war) 設定了依賴與 web.xml
，還是有點怪怪的。


錯誤訊息
```bash
java.lang.RuntimeException: java.lang.reflect.InvocationTargetException
        at org.geektimes.context.ComponentContext.lambda$processPostConstruct$5(ComponentContext.java:129)
        at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        at java.base/java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:177)
        at java.base/java.util.Spliterators$ArraySpliterator.forEachRemaining(Spliterators.java:948)
        at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:484)
        at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
        at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
        at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
        at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
        at org.geektimes.context.ComponentContext.processPostConstruct(ComponentContext.java:124)
        at org.geektimes.context.ComponentContext.lambda$initializeComponents$1(ComponentContext.java:93)
        at java.base/java.util.LinkedHashMap$LinkedValues.forEach(LinkedHashMap.java:608)
        at org.geektimes.context.ComponentContext.initializeComponents(ComponentContext.java:88)
        at org.geektimes.context.ComponentContext.init(ComponentContext.java:66)
        at org.geektimes.projects.user.web.listener.ComponentContextInitializerListener.contextInitialized(ComponentContextInitializerListener.java:21)
        at org.apache.catalina.core.StandardContext.listenerStart(StandardContext.java:4797)
        at org.apache.catalina.core.StandardContext.startInternal(StandardContext.java:5291)
        at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
        at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1559)
        at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1549)
        at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)                                                                                                                                                at java.base/java.lang.Thread.run(Thread.java:834)
Caused by: java.lang.reflect.InvocationTargetException
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:566)                                                                                                                                                                               at org.geektimes.context.ComponentContext.lambda$processPostConstruct$5(ComponentContext.java:127)
        ... 24 more
Caused by: javax.persistence.PersistenceException: [PersistenceUnit: emf] Unable to build Hibernate SessionFactory                                                                                                                                  at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.persistenceException(EntityManagerFactoryBuilderImpl.java:1336)
        at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1262)
        at org.hibernate.jpa.HibernatePersistenceProvider.createEntityManagerFactory(HibernatePersistenceProvider.java:56)
        at javax.persistence.Persistence.createEntityManagerFactory(Persistence.java:79)
        at org.geektimes.projects.user.orm.jpa.DelegatingEntityManager.init(DelegatingEntityManager.java:35)
        ... 29 more
Caused by: org.hibernate.cfg.beanvalidation.IntegrationException: Error activating Bean Validation integration
        at org.hibernate.cfg.beanvalidation.BeanValidationIntegrator.integrate(BeanValidationIntegrator.java:137)
        at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:283)
        at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:468)
        at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1259)
        ... 32 more
Caused by: java.lang.NoClassDefFoundError: javax/el/ELManager
        at org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator.buildExpressionFactory(ResourceBundleMessageInterpolator.java:171)
        at org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator.<init>(ResourceBundleMessageInterpolator.java:94)
        at org.hibernate.validator.internal.engine.AbstractConfigurationImpl.getDefaultMessageInterpolator(AbstractConfigurationImpl.java:570)
        at org.hibernate.validator.internal.engine.AbstractConfigurationImpl.getDefaultMessageInterpolatorConfiguredWithClassLoader(AbstractConfigurationImpl.java:790)
        at org.hibernate.validator.internal.engine.AbstractConfigurationImpl.getMessageInterpolator(AbstractConfigurationImpl.java:480)
        at org.hibernate.validator.internal.engine.ValidatorFactoryImpl.<init>(ValidatorFactoryImpl.java:151)
        at org.hibernate.validator.HibernateValidator.buildValidatorFactory(HibernateValidator.java:38)
        at org.hibernate.validator.internal.engine.AbstractConfigurationImpl.buildValidatorFactory(AbstractConfigurationImpl.java:448)
        at javax.validation.Validation.buildDefaultValidatorFactory(Validation.java:103)
        at org.hibernate.cfg.beanvalidation.TypeSafeActivator.getValidatorFactory(TypeSafeActivator.java:502)
        at org.hibernate.cfg.beanvalidation.TypeSafeActivator.activate(TypeSafeActivator.java:84)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:566)
        at org.hibernate.cfg.beanvalidation.BeanValidationIntegrator.integrate(BeanValidationIntegrator.java:131)
        ... 35 more
Caused by: java.lang.ClassNotFoundException: javax.el.ELManager
        at org.apache.catalina.loader.WebappClassLoader.loadClass(WebappClassLoader.java:1713)
        at org.apache.catalina.loader.WebappClassLoader.loadClass(WebappClassLoader.java:1558)
        ... 51 more        
```

## 第四週
仿照小馬哥的實現 ComponentContext 自動初始化