package org.geekbang.thinking.in.spring.dependency.lookup;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 安全依赖查找
 */
public class TypeSafetyDependencyLookupDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        //注册当前类为配置类
        annotationConfigApplicationContext.register(TypeSafetyDependencyLookupDemo.class);
        // 启动应用上下文
        annotationConfigApplicationContext.refresh();

        // 演示 BeanFactory#getBean 方法的安全性
        displayBeanFactoryGetBean(annotationConfigApplicationContext);
        // 演示 ObjectFactory#getObject 方法的安全性
        displayObjectFactoryGetObject(annotationConfigApplicationContext);
        // 演示 ObjectFactory#getIfAvailable 方法的安全性
        displayObjectProviderIfAvailable(annotationConfigApplicationContext);
        // 演示 displayListableBeanFactoryGetBeansOfType#getBeansOfType 方法的安全性
        displayListableBeanFactoryGetBeansOfType(annotationConfigApplicationContext);
        // 演示 displayObjectProviderStreamOps# Stream 方法的安全性
        displayObjectProviderStreamOps(annotationConfigApplicationContext);


        // 关闭应用上下文
        annotationConfigApplicationContext.close();

    }

    private static void displayObjectProviderStreamOps(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        ObjectProvider<User> beanProvider = annotationConfigApplicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectProviderStreamOps", () -> beanProvider.stream().forEach(System.out::println));
    }


    /**
     * <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException;
     * 这个方法也会抛出异常,这个异常就比较单一了,就当你的Bean 创建的时候本身就有问题
     * 比如说你把一个Bean 定义成了一个抽象类,那么这时候本来初始化就不成功,因为我们知道在Java 里面必须是具体类才能实例化,因此这个地方会有这么一个区别
     */
    private static void displayListableBeanFactoryGetBeansOfType(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        // TODO: AnnotationConfigApplicationContext 是可以和 ListableBeanFactory 进行互相转化的
        printBeansException("displayListableBeanFactoryGetBeansOfType", () -> {
            annotationConfigApplicationContext.getBeansOfType(User.class);
        });
    }

    private static void displayObjectProviderIfAvailable(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        ObjectProvider<User> beanProvider = annotationConfigApplicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectProviderIfAvailable", () -> beanProvider.getIfAvailable());
    }

    private static void displayObjectFactoryGetObject(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        // TODO: ObjectProvider is ObjectFactory
        ObjectFactory<User> userObjectFactory = annotationConfigApplicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectFactoryGetObject", () -> userObjectFactory.getObject());
    }

    /**
     * 其实我们以前也知道,如果我们一个Bean 的类型有多种的时候,比如说,这个时候有两个以上的实例,
     * 这时候你 getBean 通过某个类型会报错
     */
    public static void displayBeanFactoryGetBean(BeanFactory beanFactory) {
        printBeansException("displayBeanFactoryGetBean", () -> {
            beanFactory.getBean(User.class);
        });
    }

    private static void printBeansException(String source, Runnable runnable) {
        System.err.println("===============================");
        System.err.println("Source from : " + source);
        try {
            runnable.run();
        } catch (BeansException e) {
            //这个方法是线程安全的, 不要再生产环境中使用, 会发生阻塞
            e.printStackTrace();
        }
    }
}
