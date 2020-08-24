package com.somnus.annotation;

import com.somnus.annotation.audit.MethodInfo;

import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Somnus
 * @version V1.0
 * @Title: AnnotationDemo.java
 * @Package com.somnus.annotation
 * @Description: TODO
 * @date 2015年6月12日 上午9:48:54
 */
public class AnnotationDemo {

    @Override
    @MethodInfo(author = "Somnus", comments = "Main method", date = "June 12 2015", revision = 1)
    public String toString() {
        return "Overriden toString method";
    }

    @Deprecated
    @MethodInfo(comments = "deprecated method", date = "June 12 2015")
    public static void outdatedMethod() {
        System.out.println("outdated method, don't use it.");
    }

    @SuppressWarnings({"unchecked"})
    @MethodInfo(author = "admin", comments = "Main method", date = "June 12 2015", revision = 10)
    public static void genericsTest() throws FileNotFoundException {
        List l = new ArrayList();
        l.add("abc");
        outdatedMethod();
    }


    public static void main(String[] args) {
        for (Method method : AnnotationDemo.class.getMethods()) {
            // checks if MethodInfo annotation is present for the method
            if (method.isAnnotationPresent(MethodInfo.class)) {
                try {
                    // iterates all the annotations available in the method
                    for (Annotation anno : method.getDeclaredAnnotations()) {
                        System.out.println("Annotation in Method \n'" + method.getName() + "' : " + anno);
                    }
                    MethodInfo methodInfo = method.getAnnotation(MethodInfo.class);
                    if (methodInfo.revision() == 1) {
                        System.out.println("=======comments:" + methodInfo.comments());
                        System.out.println("=======author:" + methodInfo.author());
                        System.out.println("=======date:" + methodInfo.date());
                        System.out.println("=======revision:" + methodInfo.revision());
                    }
                    System.out.println("*******************************************");
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
