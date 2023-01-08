package org.geekbang.thinking.in.spring.dependency.lookup;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@link org.springframework.beans.BeanInstantiationException}
 */
public class BeanInstantiationExceptionDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();

        annotationConfigApplicationContext.register(NoUniqueBeanDefinitionExceptionDemo.class);


        //注册 BeanDefinition
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(CharSequence.class);
        annotationConfigApplicationContext.registerBeanDefinition("errorBean", beanDefinitionBuilder.getBeanDefinition());
        annotationConfigApplicationContext.refresh();

        annotationConfigApplicationContext.close();
    }
}
