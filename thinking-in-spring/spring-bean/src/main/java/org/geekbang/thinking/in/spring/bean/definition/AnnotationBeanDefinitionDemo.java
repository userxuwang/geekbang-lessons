package org.geekbang.thinking.in.spring.bean.definition;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;

// 3.通过@Import 来进行导入
@Import(AnnotationBeanDefinitionDemo.Config.class)

public class AnnotationBeanDefinitionDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class 配置类
        applicationContext.register(AnnotationBeanDefinitionDemo.class);

        // 通过 BeanDefinition 注册 API 方式
        // 1.命名 Bean 的注册方式     mingming-user=User{id=1, name='虚妄'}
        registryUserBeanDefinition(applicationContext, "mingming-user");
        // 2.非命名 Bean 的注册方式   org.geekbang.thinking.in.spring.ioc.overview.domain.User#0=User{id=1, name='虚妄'}
        registryUserBeanDefinition(applicationContext);

        // 启动应用上下文
        applicationContext.refresh();

        // 按照类型依赖查找
        System.out.println("Config Bean 的所有 Beans ： " + applicationContext.getBeansOfType(Config.class));
        System.out.println("User Bean 的所有 Beans ： " + applicationContext.getBeansOfType(User.class));


        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

    public static void registryUserBeanDefinition(BeanDefinitionRegistry registry, String beanName){
        BeanDefinitionBuilder beanDefinitionBuilder = genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id", 1L)
                .addPropertyValue("name", "虚妄");
        // 判断 如果 BeanName 参数存在时
        if (StringUtils.hasText(beanName)) {
         // 注册 BeanDefinition
        registry.registerBeanDefinition(beanName,beanDefinitionBuilder.getBeanDefinition());
        }else {
            // 非命名 Bean 注册方法
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
        }
    }

    public static void registryUserBeanDefinition(BeanDefinitionRegistry registry){
        registryUserBeanDefinition(registry, null);
    }


    // 2.通过 @Component 方式
    @Component
    public static class Config {

        // 1.通过 @Bean 方式定义
        /**
         * 通过 java 注解的方式，定义了一个 Bean
         */
        @Bean(name = {"user", "wang-user"})
        public User user() {
            User user = new User();
            user.setId(1L);
            user.setName("xuwang");
            return user;
        }
    }
}
