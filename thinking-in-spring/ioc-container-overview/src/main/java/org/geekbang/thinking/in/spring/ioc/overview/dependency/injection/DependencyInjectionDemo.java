package org.geekbang.thinking.in.spring.ioc.overview.dependency.injection;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.geekbang.thinking.in.spring.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 依赖注入 示例
 */
public class DependencyInjectionDemo {
    public static void main(String[] args) {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
//        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

        // 依赖来源一 自定义 Bean
        UserRepository userRepository = applicationContext.getBean("userRepository", UserRepository.class);

        //        System.out.println("userRepository.getUsers() = " + userRepository.getUsers());

        //依赖注入 通过 XML 方式 注入
        //依赖来源二 依赖注入 （内建依赖）
        System.out.println("userRepository.getBeanFactory() = " + userRepository.getBeanFactory());
        //System.out.println(userRepository.getBeanFactory() == beanFactory);

//        ObjectFactory<User> userObjectFactory = userRepository.getUserObjectFactory();
//        System.out.println("userObjectFactory.getObject() = " + userObjectFactory.getObject());

        /**
         * org.springframework.context.support.ClassPathXmlApplicationContext@146ba0ac
         * objectFactory.getObject() 这个方法变成了当前的ApplicationContext对象，
         * 疑惑：那么这个对象是否和当前的BeanFactory对象是否一致呢
         *
         */
        ObjectFactory<ApplicationContext> objectFactory = userRepository.getObjectFactory();
//        System.out.println("objectFactory.getObject() = " + objectFactory.getObject());
        /**
         * 结果为 true
         * 结果是一样的，就说明我们刚才ObjectFactory autowire 的时候帮我们注入了一个ApplicationContext 对象，
         * 那这里的ApplicationContext又是BeanFactory呢，后面揭晓
         */
        System.out.println("objectFactory.getObject() == beanFactory = " + (objectFactory.getObject() == applicationContext));

        //依赖查找 beanFactory.getBean(BeanFactory.class); {错误}
        /**
         * 如果BeanFactory 这个对象是个bean的话，按道理来讲，应该就是一个对象或者是定义的bean
         * No qualifying bean of type 'org.springframework.beans.factory.BeanFactory' available
         * 控制台没有这个bean 的定义
         * 问题：这个beanFactory 到底是怎么来的呢？
         * 说明依赖查找和一拉注入不一样
         * beanFactory.getBean() 这是依赖查找，而是用userRepository.getBeanFactory() 是依赖注入。
         * 依赖查找和依赖注入都属于依赖，那么这个依赖是不是来自于同一个地方，或者说是不是来自于同源，答案是否定的，
         * 这个BeanFactory并不是一个普通的Bean，如果不是一个普通的bean 那又是什么呢？（暂且存疑）
         */
//        System.out.println(beanFactory.getBean(BeanFactory.class));

        // 依赖来源三 是内部的一些初始化的Bean 容器内建 Bean
        Environment environment = applicationContext.getBean(Environment.class);
    }

    static void whoIsIOCContainer(UserRepository userRepository, ApplicationContext applicationContext){

        // ConfigurableApplicationContext <- ApplicationContext <- BeanFactory

        // ConfigurableApplicationContext#getBeanFacotory();
        /**
         * 
         */

        //答案很简单 因为它是一种设计模式
        //这个表达式为什么不会成立
        System.out.println(userRepository.getBeanFactory() == applicationContext);

    }
}
