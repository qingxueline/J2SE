package com.somnus.annotation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/** 
 * @Title: AnnotationDemo.java 
 * @Package com.somnus.annotation 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月12日 上午9:48:54 
 * @version V1.0 
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
    @MethodInfo(author = "Somnus", comments = "Main method", date = "June 12 2015", revision = 10)
    public static void genericsTest() throws FileNotFoundException {
        List l = new ArrayList();
        l.add("abc");
        outdatedMethod();
    }
}
