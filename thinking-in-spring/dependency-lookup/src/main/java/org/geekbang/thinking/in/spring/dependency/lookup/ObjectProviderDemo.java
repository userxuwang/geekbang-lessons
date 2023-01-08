package org.geekbang.thinking.in.spring.dependency.lookup;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 通过 {@link org.springframework.beans.factory.ObjectProvider} 进行依赖查找
 * 使用这个 AnnotationConfigApplicationContext 类注册以后 就不需要打上@Configuration 了
 */
public class ObjectProviderDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        //注册当前类为配置类
        annotationConfigApplicationContext.register(ObjectProviderDemo.class);
        // 启动应用上下文
        annotationConfigApplicationContext.refresh();

        // 依赖查找集合对象
        lookUpByOjbectProvider(annotationConfigApplicationContext);

        // 关闭应用上下文
        annotationConfigApplicationContext.close();

    }

    @Bean
    String helloWorld(){
        return "Hello,World";
    }

    private static void lookUpByOjbectProvider(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        ObjectProvider<String> beanProvider = annotationConfigApplicationContext.getBeanProvider(String.class);
        System.out.println("beanProvider = " + beanProvider);
        System.out.println("beanProvider.getObject() = " + beanProvider.getObject());
    }
}
