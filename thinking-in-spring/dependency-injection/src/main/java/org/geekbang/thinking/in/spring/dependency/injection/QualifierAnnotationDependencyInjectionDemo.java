package org.geekbang.thinking.in.spring.dependency.injection;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

/**
 * {@link Qualifier} 注解依赖注入
 *
 * @see Qualifier
 */
public class QualifierAnnotationDependencyInjectionDemo {

    @Autowired
    private User user; // superUser -> primary = true

    @Autowired
    @Qualifier("user")
    private User namedUser;

    /**
     * 整体应用上下文存在 4 个 User 类型的Bean
     * superUser
     * user
     * user1 -> @Qualifier
     * user2 -> @Qualifier
     */

    @Autowired
    private Collection<User> allUsers; // 期待2 个bean 全部输出   user + superUser


    @Autowired
    @Qualifier
    private Collection<User> qualifiedUsers;// 2 Beans = user1 +　user2

    @Bean
    @Qualifier
    public User user1() {// 进行逻辑分组
        User user = new User();
        user.setId(7L);
        return user;
    }
    @Bean
    @Qualifier
    public User user2() {// 进行逻辑分组
        User user = new User();
        user.setId(8L);
        return user;
    }

    public static void main(String[] args) {

        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类） -> spring bean
        applicationContext.register(QualifierAnnotationDependencyInjectionDemo.class);

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlSourceLocation = "META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源 ， 解析并生成 BeanDefinition
        xmlBeanDefinitionReader.loadBeanDefinitions(xmlSourceLocation);

        // 启动应用上下文
        applicationContext.refresh();

        //依赖查找 AnnotationDependencyFieldInjectionDemo  Bean
        QualifierAnnotationDependencyInjectionDemo demo = applicationContext.getBean(QualifierAnnotationDependencyInjectionDemo.class);

        //期待输出 superUser Bean
        System.out.println("demo.user = " + demo.user);
        System.out.println();
        //期待输出 user Bean
        System.out.println("demo.namedUser = " + demo.namedUser);
        System.out.println();
        //期待输出 superUser user user1 user2
        System.out.println("demo.allUsers = " + demo.allUsers);
        System.out.println();
        //期待输出 user1 user2
        System.out.println("demo.qualifiedUsers = " + demo.qualifiedUsers);
        System.out.println();





        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }
}
