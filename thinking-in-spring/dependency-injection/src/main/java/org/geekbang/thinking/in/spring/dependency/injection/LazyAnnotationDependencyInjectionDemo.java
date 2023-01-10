package org.geekbang.thinking.in.spring.dependency.injection;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Set;

/**
 * {@link ObjectProvider} 延迟依赖注入
 */
public class LazyAnnotationDependencyInjectionDemo {

    @Autowired
    // 实时注入
    private User user;

    @Autowired
    //TODO: 延迟注入 | 间接注入 还需要通过第 2 次操作的方式来进行操作
    // TODO: 单一类型 集合类型 皆可操作
    private ObjectProvider<User> userObjectProvider;

    @Autowired
    private ObjectFactory<Set<User>> usersObjectFactory;

    public static void main(String[] args) {

        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类） -> spring bean
        applicationContext.register(LazyAnnotationDependencyInjectionDemo.class);

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlSourceLocation = "META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源 ， 解析并生成 BeanDefinition
        xmlBeanDefinitionReader.loadBeanDefinitions(xmlSourceLocation);

        // 启动应用上下文
        applicationContext.refresh();

        //依赖查找 AnnotationDependencyFieldInjectionDemo  Bean
        LazyAnnotationDependencyInjectionDemo demo = applicationContext.getBean(LazyAnnotationDependencyInjectionDemo.class);

        //期待输出 superUser Bean
        System.out.println("demo.user = " + demo.user);
        System.out.println();
        //期待输出 user Bean
        // userObjectProvider 继承 ObjectFactory
        System.out.println("demo.userObjectProvider = " + demo.userObjectProvider.getObject());
        System.out.println();
        // 会查找初所有的 User 对象
        demo.userObjectProvider.forEach(System.out::println);

        System.out.println();
        System.out.println("demo.usersObjectFactory = " + demo.usersObjectFactory.getObject());
        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }
}
