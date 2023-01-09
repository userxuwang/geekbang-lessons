package org.geekbang.thinking.in.spring.dependency.injection;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * "byName" Auto-wiring 依赖 Setter 方法注入示例
 *
 * byName 就是 XML 中的 autowire="byName"
 * byType 就是 XML 中的 autowire="byType"
 * byType 会优先查找 XML 中 primary=true的 bean
 * byName 会优先查找 与 bean 名称相同的
 */
public class AutoWiringByNameDependcySetterInjectionDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //加载XML
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String xmlResource = "classpath*:/META-INF/autowiring-dependency-setter-injection.xml";
        // 加载 XML 资源，解析并且生成 BeanDefinition
        xmlBeanDefinitionReader.loadBeanDefinitions(xmlResource);
        // 依赖查找并且创建 Bean
        UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        System.out.println("userHolder = " + userHolder);
    }
}
