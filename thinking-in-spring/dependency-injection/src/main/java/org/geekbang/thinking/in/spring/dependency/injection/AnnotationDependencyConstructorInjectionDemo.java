package org.geekbang.thinking.in.spring.dependency.injection;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 基于 Java 注解的依赖 constructor 注入示例（构造器注入 、 Setter 注入）
 * 说明：
 * 在Annotation的场景中就是在注解的场景，也能够用XML的读取的方式来做，只要能确认到，这里我只关心两点：
 * 一个是资源在哪里（XML文件，也包括其他）
 * 我的注册中心谁来进行承担
 */
public class AnnotationDependencyConstructorInjectionDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class 配置类
        applicationContext.register(AnnotationDependencyConstructorInjectionDemo.class);

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlSourceLocation = "META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源 ， 解析并生成 BeanDefinition
        xmlBeanDefinitionReader.loadBeanDefinitions(xmlSourceLocation);

        // 启动应用上下文
        applicationContext.refresh();

        /**
         * 因为 superUser bean中设置了 primary="true"
         */
        UserHolder userHolder = applicationContext.getBean(UserHolder.class);
        System.out.println("userHolder = " + userHolder);
        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

    /**
     * 使用的是 constructor
     */
    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }

    //使用的事 构造器的方式
//    @Bean
//    public UserHolder userHolder(User user) {
//        return new UserHolder(user);
//    }
}
