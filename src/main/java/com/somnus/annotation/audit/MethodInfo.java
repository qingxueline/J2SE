package com.somnus.annotation.audit;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/** 
 * 注解方法不能有参数
 * 注解方法的返回类型局限于原始类型，字符串，枚举，注解，或以上类型构成的数组
 * 注解方法可以包含默认值
 * 注解可以包含与其绑定的元注解，元注解为注解提供信息，有四种元注解类型：@Documented 、@Target、@Inherited、@Retention
 *
 * 1.CONSTRUCTOR:用于描述构造器
 * 2.FIELD:用于描述域
 * 3.LOCAL_VARIABLE:用于描述局部变量
 * 4.METHOD:用于描述方法
 * 5.PACKAGE:用于描述包
 * 6.PARAMETER:用于描述参数
 * 7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 */

@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodInfo {
    String author() default "Somnus";
    String date();
    int revision() default 1;
    String comments();
}
