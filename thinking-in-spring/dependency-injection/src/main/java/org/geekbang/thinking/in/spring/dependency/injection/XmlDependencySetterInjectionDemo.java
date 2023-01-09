package org.geekbang.thinking.in.spring.dependency.injection;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 基于 XML 资源的依赖 Setter 方法注入示例
 */
public class XmlDependencySetterInjectionDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //加载XML
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String xmlResource = "classpath*:/META-INF/dependency-setter-injection.xml";
        xmlBeanDefinitionReader.loadBeanDefinitions(xmlResource);
        UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        System.out.println("userHolder = " + userHolder);
    }
}
