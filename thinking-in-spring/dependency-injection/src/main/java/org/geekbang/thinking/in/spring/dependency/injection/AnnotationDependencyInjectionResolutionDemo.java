package org.geekbang.thinking.in.spring.dependency.injection;

import org.geekbang.thinking.in.spring.dependency.injection.annotation.InjectedUser;
import org.geekbang.thinking.in.spring.dependency.injection.annotation.MyAutowired;
import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.*;

import static org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME;

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

    @MyAutowired
    private Optional<User> userOptional;

    @Inject
    private User injectUser;

    @InjectedUser
    private User myInjectedUser;

    // TODO: 新老API 兼容 同时注入 原因是同时注入两个 AutowiredAnnotationBeanPostProcessor 对象，换言之在当前上下文中会有两个 AutowiredAnnotationBeanPostProcessor 来进行同时处理 自定义注解，以及新的注解
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 3)
    public static AutowiredAnnotationBeanPostProcessor annotationBeanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        autowiredAnnotationBeanPostProcessor.setAutowiredAnnotationType(InjectedUser.class);
        return autowiredAnnotationBeanPostProcessor;
    }

    // TODO: 可以查询出所有的信息 bean的名称并不一定要叫这个名字
    /*@Bean(name = AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
    public static AutowiredAnnotationBeanPostProcessor annotationBeanPostProcessor(){
        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        // @Autowired + @Inject  + 新注解 @InjectedUser
        Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>(
                Arrays.asList(
                        Autowired.class,
                        Inject.class,
                        InjectedUser.class
                )
        );
        autowiredAnnotationBeanPostProcessor.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
        return autowiredAnnotationBeanPostProcessor;
    }*/

    //TODO：添加自定义注解 注册到 AutoWiredAnnotationBeanPostProcessor 可以查询出所有信息
//    @Bean(name = "myInjectedAnnotationBeanPostProcessor")
//    public static AutowiredAnnotationBeanPostProcessor annotationBeanPostProcessor(){
//        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
//        // 替换原有注解处理，使用新注解 @InjectedUser
//        autowiredAnnotationBeanPostProcessor.setAutowiredAnnotationType(InjectedUser.class);
//        return autowiredAnnotationBeanPostProcessor;
//    }

    // 此方法全部为空
    /*@Bean(name = "myInjectedAnnotationBeanPostProcessor")
    public AutowiredAnnotationBeanPostProcessor annotationBeanPostProcessor(){
        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        // 替换原有注解处理，使用新注解 @InjectedUser
        autowiredAnnotationBeanPostProcessor.setAutowiredAnnotationType(InjectedUser.class);
        return autowiredAnnotationBeanPostProcessor;
    }*/


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

        //期待输出 superUser user  Bean
        System.out.println("demo.myInjectedUser = " + demo.myInjectedUser);
        System.out.println();



        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }
}
