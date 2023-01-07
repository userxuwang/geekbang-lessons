package org.geekbang.thinking.in.spring.bean.definition;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * {@link org.springframework.beans.factory.config.BeanDefinition}
 *
 */
public class definitionBeanDefinitionCreationDemo {
    public static void main(String[] args) {

        //1 通过 BeanDefinitionBuilder
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // 通过属性设置
        beanDefinitionBuilder.addPropertyValue("id", 1);
        beanDefinitionBuilder.addPropertyValue("name", "wang");
        //获取 BeanDefinition 实例
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // BeanDefinition 并非 Bean 的终态，可以自定义修改

        //2.通过 AbstractBeanDefinition 以及派生类
        AbstractBeanDefinition abstractBeanDefinition = new GenericBeanDefinition();
        // 设置 Bean 的类型
        abstractBeanDefinition.setBeanClass(User.class);

        // 通过 MutablePropertyValues 批量操作属性
        MutablePropertyValues propertyValues = new MutablePropertyValues();
//        propertyValues.addPropertyValue("id", 1);
//        propertyValues.addPropertyValue("name", "wang");
        propertyValues
                .add("id", 1)
                .add("name", "wang");
        // 通过 set MutablePropertyValues 批量操作属性
        abstractBeanDefinition.setPropertyValues(propertyValues);


    }
}
