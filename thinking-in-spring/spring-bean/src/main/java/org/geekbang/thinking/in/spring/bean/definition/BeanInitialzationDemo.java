package org.geekbang.thinking.in.spring.bean.definition;

import org.geekbang.thinking.in.spring.bean.factory.DefaultUserFactory;
import org.geekbang.thinking.in.spring.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Bean 初始化 demo
 * 1. java 代码实现
 * 2。 @Bean(initMethod = "initUserFactory")
 * 3.
 */
@Configuration //配置类
public class BeanInitialzationDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类）
        applicationContext.register(BeanInitialzationDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 非延迟初始化在 Spring 应用上下文启动完成后, 被初始化.
        System.out.println("Spring 应用上下文已启动");


        // 依赖查找 UserFactory 根据类型查找
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        System.out.println("userFactory = " + userFactory);

        System.out.println("Spring 应用上下文准备关闭");
        //关闭应用上下文
        applicationContext.close();
        System.out.println("Spring 应用上下文已关闭");

    }

    /**
     * 将 UserFactory 声明 Bean
     *
     * @return
     *
     */
    @Bean(initMethod = "initUserFactory", destroyMethod = "doDestroy")
    @Lazy(value = false)
    public DefaultUserFactory userFactory() {
        return new DefaultUserFactory();
    }
}
