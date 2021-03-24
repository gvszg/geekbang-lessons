package org.geektimes.context;

import java.util.List;

/**
 * 组件上下文（Web 应用全局使用）
 */
public interface ComponentContext {
    void init();

    /**
     * Context Destroy Method
     */
    void destroy();

    /**
     * Look up component object by name
     *
     * @param name
     * @param <C>
     * @return not found return <code>null</code>
     */
    <C> C getComponent(String name);

    /**
     * Get all component names
     * @return
     */
    List<String> getComponentNames();
}
