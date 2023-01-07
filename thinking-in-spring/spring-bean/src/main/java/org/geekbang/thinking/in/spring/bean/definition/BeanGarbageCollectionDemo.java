package org.geekbang.thinking.in.spring.bean.definition;

import org.geekbang.thinking.in.spring.bean.factory.DefaultUserFactory;
import org.geekbang.thinking.in.spring.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Bean 垃圾回收 (GC) 示例
 */
@Configuration //配置类
public class BeanGarbageCollectionDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类）
        applicationContext.register(BeanGarbageCollectionDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        //关闭应用上下文
        applicationContext.close();
        System.out.println("Spring 应用上下文已关闭");

        // 强制触发 GC
        // TODO: 默认情况下可用,如果把FullGC的参数关闭了,那就失效了
        System.gc();
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
