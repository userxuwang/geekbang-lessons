package org.geekbang.thinking.in.spring.dependency.injection;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 注解驱动的依赖注入处理过程
 *
 * todo 需要结合 org.springframework.beans.factory.support.DefaultListableBeanFactory#resolveDependency
 * todo (org.springframework.beans.factory.config.DependencyDescriptor, java.lang.String, java.util.Set,
 * todo org.springframework.beans.TypeConverter)
 * todo 来看
 */
public class AnnotationDependencyInjectionResolutionDemo {

    @Autowired
    @Lazy
    private User lazyUser;

    /**
     * 依赖查找（处理）
     * DependencyDescriptor --->>>>
     * 必须（required=true）
     * 实时注入（eager=true）
     * 通过类型（User.class）
     * 字段名称 （“user")
     * 是否首要 （primary=true)
     */
    @Autowired // 赖查找（处理）
    private User user; //

    @Autowired
    private Map<String, User> users;

    @Autowired
    private Optional<User> userOptional;

    @Inject
    private User injectUser;

    public static void main(String[] args) {

        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类） -> spring bean
        applicationContext.register(AnnotationDependencyInjectionResolutionDemo.class);

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlSourceLocation = "META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源 ， 解析并生成 BeanDefinition
        xmlBeanDefinitionReader.loadBeanDefinitions(xmlSourceLocation);

        // 启动应用上下文
        applicationContext.refresh();

        //依赖查找 AnnotationDependencyFieldInjectionDemo  Bean
        AnnotationDependencyInjectionResolutionDemo demo = applicationContext.getBean(AnnotationDependencyInjectionResolutionDemo.class);

        //期待输出 superUser Bean
        System.out.println("demo.user = " + demo.user);
        System.out.println();

        System.out.println("demo.injectUser = " + demo.injectUser);
        System.out.println();

        //期待输出 superUser user  Bean
        System.out.println("demo.users = " + demo.users);
        System.out.println();

        //期待输出 superUser user  Bean
        System.out.println("demo.userOptional = " + demo.userOptional);
        System.out.println();



        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }
}
