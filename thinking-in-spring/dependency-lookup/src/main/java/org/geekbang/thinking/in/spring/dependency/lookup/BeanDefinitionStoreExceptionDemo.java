package org.geekbang.thinking.in.spring.dependency.lookup;

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * {@link org.springframework.beans.factory.BeanDefinitionStoreException}
 * XML配置资源路径无法找到时 会报这个错误
 */

public class BeanDefinitionStoreExceptionDemo {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("META-INF/dependddency-lookup-context.xml");
    }
}
