package org.geekbang.thinking.in.spring.bean.definition;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * {@link BeanDefinition}
 *
 */
public class BeanAliasDemo {
    public static void main(String[] args) {
        //配置 XML 配置文件
        //启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/MATA-INF/bean-definition-context.xml");
        //通过别名 lean-user 获取曾用名 user 的 bean
        //
        User leanUser = beanFactory.getBean("lean-user", User.class);
        User user = beanFactory.getBean("user", User.class);
        System.out.println("lean-user == user : " + (leanUser == user));
    }
}
