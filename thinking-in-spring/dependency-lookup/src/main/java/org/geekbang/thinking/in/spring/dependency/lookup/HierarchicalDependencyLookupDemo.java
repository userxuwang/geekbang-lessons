package org.geekbang.thinking.in.spring.dependency.lookup;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

public class HierarchicalDependencyLookupDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();

        annotationConfigApplicationContext.register(HierarchicalDependencyLookupDemo.class);

        // 1. 获取 HierarchicalBeanFactory <- ConfigurableBeanFactory <- ConfigurableListableBeanFactory

        ConfigurableListableBeanFactory beanFactory = annotationConfigApplicationContext.getBeanFactory();

//        System.out.println("当前 BeanFactory 的 Parent BeanFactory : " + beanFactory.getParentBeanFactory());

        // 2.设置 Parent BeanFactory
        HierarchicalBeanFactory parentBeanFactory = createParentBeanFactory();
        beanFactory.setParentBeanFactory(parentBeanFactory);
//        System.out.println("当前 BeanFactory 的 Parent BeanFactory : " + beanFactory.getParentBeanFactory());

        displayLocalBean(beanFactory, "user");
        displayLocalBean(parentBeanFactory, "user");

        displayContainsBean(beanFactory, "user");
        displayContainsBean(parentBeanFactory, "user");


        annotationConfigApplicationContext.refresh();

        annotationConfigApplicationContext.close();
    }

    private static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("当前 BeanFactory[%s] 是否包含 bean[name : %s] : %s\n", beanFactory, beanName, containsBean(beanFactory, beanName));
    }

    /**
     * 实现双亲委派
     *
     * @param beanFactory
     * @param beanName
     * @return
     */
    public static boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if (parentBeanFactory instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory parentHierarchicalBeanFactory = (HierarchicalBeanFactory) parentBeanFactory;
//            HierarchicalBeanFactory parentHierarchicalBeanFactory = HierarchicalBeanFactory.class.cast(parentBeanFactory);
            if (containsBean(parentHierarchicalBeanFactory, beanName)) {
                return true;
            }
        }
        return beanFactory.containsLocalBean(beanName);
    }


    /**
     * 演示或者是展示 什么是 localBean
     *
     * @param beanFactory
     * @param beanName
     */
    public static void displayLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("当前 BeanFactory[%s] 是否包含 Local bean[name : %s] : %s\n", beanFactory, beanName, beanFactory.containsLocalBean(beanName));
    }

    /**
     * 两者皆可
     */
    private static ConfigurableListableBeanFactory createParentBeanFactory() {
//    private static HierarchicalBeanFactory createParentBeanFactory() {
        // 创建 BeanFactory 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // XML 配置文件 ClassPath 路径
        String location = "META-INF/dependency-lookup-context.xml";
        // 加载配置
        reader.loadBeanDefinitions(location);
        return beanFactory;
    }


    @Deprecated
    public static <T> Map<String, T> beansOfTypeIncludingAncestors(ListableBeanFactory lbf, Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {

        Assert.notNull(lbf, "ListableBeanFactory must not be null");
        Map<String, T> result = new LinkedHashMap<>(4);
        result.putAll(lbf.getBeansOfType(type, includeNonSingletons, allowEagerInit));
        if (lbf instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory hbf = (HierarchicalBeanFactory) lbf;
            if (hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
                Map<String, T> parentResult = beansOfTypeIncludingAncestors((ListableBeanFactory) hbf.getParentBeanFactory(), type, includeNonSingletons, allowEagerInit);
                parentResult.forEach((beanName, beanInstance) -> {
                    if (!result.containsKey(beanName) && !hbf.containsLocalBean(beanName)) {
                        result.put(beanName, beanInstance);
                    }
                });
            }
        }
        return result;
    }
}
