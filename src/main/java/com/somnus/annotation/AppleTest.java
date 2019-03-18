package com.somnus.annotation;

/**
 * ${DESCRIPTION}
 *
 * @author lyl
 * @version 2019/3/18 0018 14:52:50
 */
public class AppleTest {
    public static void main(String[] args) throws NoSuchMethodException {
        Class<AppleTest> appleTestClass = AppleTest.class;
        Apple show = appleTestClass.getMethod("show").getAnnotation(Apple.class);
        System.out.println(show.name());
        System.out.println(show.sex());
        System.out.println(show.phone());
        System.out.println(show.age());
    }

    @Apple(name = "张朵朵")
    public void show() {

    }
}
