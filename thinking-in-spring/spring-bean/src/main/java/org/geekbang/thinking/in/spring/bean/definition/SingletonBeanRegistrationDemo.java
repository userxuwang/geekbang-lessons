package org.geekbang.thinking.in.spring.bean.definition;

import org.geekbang.thinking.in.spring.bean.factory.DefaultUserFactory;
import org.geekbang.thinking.in.spring.bean.factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 单体 Bean 注册实例
 */
public class SingletonBeanRegistrationDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 创建一个外部 UserFactory 对象
        UserFactory userFactory = new DefaultUserFactory();
        ConfigurableListableBeanFactory configurableListableBeanFactory = applicationContext.getBeanFactory();
        // 注册外部单例对象
        configurableListableBeanFactory.registerSingleton("userFactory", userFactory);
        // 启动应用上下文
        applicationContext.refresh();
        // 通过依赖查找的方式来获取 UserFactory
        UserFactory userFactoryByLookUp = configurableListableBeanFactory.getBean("userFactory", UserFactory.class);
        System.out.println("userFactory == userFactoryByLookUp : " + (userFactoryByLookUp == userFactory));

        //关闭应用上下文
        applicationContext.close();
    }
}
