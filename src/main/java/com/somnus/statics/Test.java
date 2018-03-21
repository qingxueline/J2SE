package com.somnus.statics;

/** 
 * @Description: TODO
 * @author Somnus
 * @date 2015年8月17日 下午5:21:37 
 * @version V1.0 
 */
class A {
    protected static int num = 2;

    public static void a() {
        System.out.println("a");
    }
}

class B extends A {
    protected static int num = 4;

    public static void a() {
        System.out.println("b");
    }
}

public class Test {
    public static void main(String[] args) {
        A a = new B();
        B.a();
        a.a();                          // 输出结果是a，说明静态方法不能再子类之中被覆盖。
        System.out.println(B.num);      // 通过类名调用被覆盖的静态变量可以显示子类的被覆盖的静态变量。
        System.out.println(a.num);      // 输出2，说明静态变量是不能被在子类中被覆盖的。
    }
}