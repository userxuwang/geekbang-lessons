package org.geekbang.thinking.in.spring.ioc.overview.dependency.lookup;

import org.geekbang.thinking.in.spring.ioc.overview.annotation.Super;
import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 依赖查找示例
 * 1. 通过名称的方式来查找
 */
public class DependencyLookupDemo {

    public static void main(String[] args) {
        //配置 XML 配置文件
        //启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INF/dependency-injection-context.xml");

        //按照类型查找
        lookupByType(beanFactory);
        //按照类型查找集合对象
        lookupCollectionByType(beanFactory);
        //通过注解查找对象
        lookupAnnotationByType(beanFactory);

//        lookupRealTie(beanFactory);
//
//        lookupInLazy(beanFactory);
    }

    private static void lookupAnnotationByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, Object> beansWithAnnotation = listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找标注 @Super 所有的User 对象 " + beansWithAnnotation);
        }
    }

    /**
     * 查找多个实例 集合对象
     * @param beanFactory
     */
    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查到的所有的 User 集合对象：" + beansOfType);
        }
    }

    /**
     * 查找单个实例
     * TODO: available: expected single matching bean but found 2: user,superUser 错误
     * TODO: 常用解决办法 在 xml 配置文件中 bean 里面添加 primary=true
     * @param beanFactory
     */
    private static void lookupByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("实时查找 = " + user);
    }

    /**
     * 根据Bean 名称查找 - 延迟查找
     * @param beanFactory
     */
    private static void lookupInLazy(BeanFactory beanFactory) {
       ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User object = objectFactory.getObject();
        System.out.println("延迟查找 = " + object);
    }

    /**
     * 根据Bean 名称查找 - 实时查找
     * @param beanFactory
     */
    private static void lookupRealTie(BeanFactory beanFactory) {
        User user = beanFactory.getBean("user", User.class);
        System.out.println("实时查找 = " + user);
    }
}
