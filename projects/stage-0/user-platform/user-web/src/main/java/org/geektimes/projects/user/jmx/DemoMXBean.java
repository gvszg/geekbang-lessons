package org.geektimes.projects.user.jmx;

import javax.management.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态结构，无固定接口类型（运行时确定）
 */
public class DemoMXBean implements DynamicMBean {
    private Map<String, Object> attributes = new HashMap<>();

    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        if (!attributes.containsKey(attribute)) {
            throw new AttributeNotFoundException("...");
        }
        return attributes.get(attribute);
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        attributes.put(attribute.getName(), attribute.getValue());
    }

    @Override
    public AttributeList getAttributes(String[] attributes) {
        AttributeList attributeList = new AttributeList();
        for (String attribute : attributes) {
            try {
                Object attributeValue = getAttribute(attribute);
                attributeList.add(new Attribute(attribute, attributeValue));
            } catch (AttributeNotFoundException | MBeanException | ReflectionException e) {
            }
        }
        return attributeList;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        return null;
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        // 方法被调用时
        System.out.println(actionName);
        Arrays.stream(signature).forEach(System.out::println);
        return null;
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        MBeanInfo mBeanInfo = new MBeanInfo(DemoMXBean.class.getName(), "DemoMXBean", null, null, null, null);
        return mBeanInfo;
    }
}
