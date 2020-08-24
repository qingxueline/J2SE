package com.somnus.annotation.audit;

import java.lang.annotation.*;

/**
 * ${DESCRIPTION}
 *
 * @author lyl
 * @version 2019/3/18 0018 14:58:22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Apple {
    String name();
    String sex() default "男";
    String phone() default "15931787945";
    int age() default 2;
}
