package org.geekbang.thinking.in.spring.dependency.injection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 基于 API 实现依赖 constructor 注入示例
 */
public class ApiDependencyConstructorInjectionDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 UserHolder 的 BeanDefinition

        BeanDefinition userHolderBeanDefinition = createUserHolderBeanDefinition();
        applicationContext.registerBeanDefinition("userHolder", userHolderBeanDefinition);
        // 加载XML配置
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlSourceLocation = "META-INF/dependency-lookup-context.xml";
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
     * 为 {@link UserHolder} 生成 {@link BeanDefinition}
     *
     * @return
     */
    private static BeanDefinition createUserHolderBeanDefinition() {
        return BeanDefinitionBuilder
                .genericBeanDefinition(UserHolder.class)
                .addConstructorArgReference("superUser")
                .getBeanDefinition();
    }

    /**
     * 使用的事 Setter 方式
     */
//    @Bean
//    public UserHolder userHolder(User user) { // superUser -> primary = true
//        UserHolder userHolder = new UserHolder();
//        userHolder.setUser(user);
//        return userHolder;
//    }
}
