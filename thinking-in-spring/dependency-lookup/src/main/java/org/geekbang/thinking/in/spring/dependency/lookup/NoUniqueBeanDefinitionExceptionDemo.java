package org.geekbang.thinking.in.spring.dependency.lookup;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * {@link NoUniqueBeanDefinitionException}
 */
public class NoUniqueBeanDefinitionExceptionDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();

        annotationConfigApplicationContext.register(NoUniqueBeanDefinitionExceptionDemo.class);

        annotationConfigApplicationContext.refresh();

        try {
            annotationConfigApplicationContext.getBean(String.class);
        } catch (NoUniqueBeanDefinitionException e) {
            System.err.printf("Spring 应用上下文存在 %d个 %s 类型的 Bean , 具体原因 : %s%n",
                    e.getNumberOfBeansFound(),
                    String.class.getName(),
                    e.getMessage());
        }

        annotationConfigApplicationContext.close();
    }

    @Bean
    public String bean2() {
        return "bean22";
    }
    @Bean
    public String bean3() {
        return "bean33";
    }
    @Bean
    public String bean4() {
        return "bean44";
    }
}
