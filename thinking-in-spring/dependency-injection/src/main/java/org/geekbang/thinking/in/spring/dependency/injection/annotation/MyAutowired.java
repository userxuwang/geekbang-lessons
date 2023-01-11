package org.geekbang.thinking.in.spring.dependency.injection.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
/**
 * 自定义注解 （元标注 @Autowired）
 */
@Target(value = {CONSTRUCTOR, METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Autowired
public @interface MyAutowired {

    /**
     * Declares whether the annotated dependency is required.
     * <p>Defaults to {@code true}.
     */
    boolean required() default true;

}
