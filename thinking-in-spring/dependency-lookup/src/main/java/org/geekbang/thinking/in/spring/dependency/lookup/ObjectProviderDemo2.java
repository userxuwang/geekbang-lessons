package org.geekbang.thinking.in.spring.dependency.lookup;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 通过 {@link ObjectProvider} 进行依赖查找
 * 使用这个 AnnotationConfigApplicationContext 类注册以后 就不需要打上@Configuration 了
 */
public class ObjectProviderDemo2 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        //注册当前类为配置类
        annotationConfigApplicationContext.register(ObjectProviderDemo2.class);
        // 启动应用上下文
        annotationConfigApplicationContext.refresh();

        // 依赖查找集合对象
        lookUpByObjectProvider(annotationConfigApplicationContext);
        //当我类不存在的时候 我应该怎么去做
        //如果存在的话

        lookupIfAvaiable(annotationConfigApplicationContext);
        // 做一个Stream 的操作
        lookupByStreamOps(annotationConfigApplicationContext);

        // 关闭应用上下文
        annotationConfigApplicationContext.close();

    }

    private static void lookupByStreamOps(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        ObjectProvider<String> objectProvider = annotationConfigApplicationContext.getBeanProvider(String.class);
        Iterable<String> stringIterable = objectProvider;
        //相当于输出我们当前上下文里面所有的 String 类型的集合,那么和我们集合的查找其实是异曲同工的
        // 区别在什么地方呢? 区别在于说它目前实现方案它是一个延迟加载的
//        for (String string : stringIterable) {
//            System.out.println(string);
//        }
        objectProvider.stream().forEach(System.out::println);
    }

    private static void lookupIfAvaiable(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        ObjectProvider<User> userObjectProvider = annotationConfigApplicationContext.getBeanProvider(User.class);
        // 第一种方式 一步到位 当我这个上下文里面如果没有User的话, 那么我做了一个兜底,如果不存在 我就创建一个默认对象并返回,否在的话也不会有什么事情
        User user = userObjectProvider.getIfAvailable(User::createUser);
        System.out.println("当前 user 对象 : " + user);
    }

    @Bean
    @Primary
    public String helloWorld() {
        return "Hello,World";
    }

    @Bean
    public String message() {
        return "Message";
    }

    private static void lookUpByObjectProvider(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        ObjectProvider<String> beanProvider = annotationConfigApplicationContext.getBeanProvider(String.class);
        System.out.println("beanProvider = " + beanProvider);
        System.out.println("beanProvider.getObject() = " + beanProvider.getObject());
    }
}
