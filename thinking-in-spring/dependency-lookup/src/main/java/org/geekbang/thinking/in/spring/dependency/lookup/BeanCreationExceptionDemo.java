package org.geekbang.thinking.in.spring.dependency.lookup;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * {@link org.springframework.beans.factory.BeanCreationException}
 */

public class BeanCreationExceptionDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();

        annotationConfigApplicationContext.register(BeanCreationExceptionDemo.class);

        annotationConfigApplicationContext.registerBeanDefinition("errorBean", BeanDefinitionBuilder.genericBeanDefinition(POJO.class).getBeanDefinition());

        annotationConfigApplicationContext.refresh();

        annotationConfigApplicationContext.close();
    }

    static class POJO implements InitializingBean{
        /**
         * 它先执行
         * @throws Throwable
         */
        @PostConstruct
        public static void init() throws Throwable {
            throw new Exception("init() For purposes ...");
        }

        /**
         * 由于 init() 先执行 之后就不会轮到它来执行了 注意顺序
         */
        @Override
        public void afterPropertiesSet() throws Exception {
            throw new Exception("afterPropertiesSet() For purposes ...");
        }
    }
}
