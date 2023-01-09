package org.geekbang.thinking.in.spring.dependency.injection;

import org.geekbang.thinking.in.spring.dependency.injection.annotation.UserGroup;
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
public class RefactorQualifierAnnotationDependencyInjectionDemo {

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
    // TODO: qualifiedUsers;// 2 Beans = user1 +　user2 ---->>>>>> 由于扩展了@Qualifier注解 它的范围变大了，增加了两个Bean user3 user4 四个Bean组成，因为UserGroup 也是Qualifier，就是说这种方式是类似于像继承一样的关系
    private Collection<User> qualifiedUsers;// 2 Beans = user1 +　user2 ---->>>>>> 由于扩展了@Qualifier注解 它的范围变大了，增加了两个Bean user3 user4 四个Bean组成，因为UserGroup 也是Qualifier，就是说这种方式是类似于像继承一样的关系

    // TODO: 不过呢特殊的组就只有两个
    // TODO: 千万注意一个特点，就是一旦有@Qualifier之后，它就会进行分组，那么以前的@Autowired，请大家注意以前@Autowired的allBeans
    // TODO: 里面并不会包含这些数据，那么相当于说有@Qualifier和没有@Qualifier做了一个分组，这点就是我们大致上的一个理解


    @Autowired
    @UserGroup
    private Collection<User> groupUsers;// 2 Beans = user3 +　user4

    @Bean
    @Qualifier
    public User user1() {// 进行逻辑分组
        return createUser(7L);
    }
    @Bean
    @Qualifier
    public User user2() {// 进行逻辑分组
        return createUser(8L);
    }
    @Bean
    @UserGroup
    public User user3() {// 进行逻辑分组
        return createUser(9L);
    }
    @Bean
    @UserGroup
    public User user4() {// 进行逻辑分组
        return createUser(10L);
    }

    private static User createUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    public static void main(String[] args) {

        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类） -> spring bean
        applicationContext.register(RefactorQualifierAnnotationDependencyInjectionDemo.class);

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlSourceLocation = "META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源 ， 解析并生成 BeanDefinition
        xmlBeanDefinitionReader.loadBeanDefinitions(xmlSourceLocation);

        // 启动应用上下文
        applicationContext.refresh();

        //依赖查找 AnnotationDependencyFieldInjectionDemo  Bean
        RefactorQualifierAnnotationDependencyInjectionDemo demo = applicationContext.getBean(RefactorQualifierAnnotationDependencyInjectionDemo.class);

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
        //期待输出 user3 user4
        System.out.println("demo.groupUsers = " + demo.groupUsers);
        System.out.println();





        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }
}
