package com.somnus.statics;

/** 
 * @Description: 静态变量并不会被继承
 * @author Somnus
 * @date 2016年1月5日 上午10:25:06 
 * @version V1.0 
 */
public class StaticTest2 {
    
    public static void main(String[] args) {
        Child c1 = new Child(10);
        Child c2 = new Child(30);
        c1.value++;
        c1.addValue(3);
        c2.value++;
        c2.addValue(5);
        
        System.out.println(c1.value);
        System.out.println(c1.getValue());
        System.out.println(c2.value);
        System.out.println(Super.value);
    }
    
}
class Super{
    public static int value =100;
    Super(int v){
        value = v;
    }
    protected int getValue(){
        return value;
    }
    protected void addValue(int v){
        value += v;
    }
}
class Child extends Super{
    public int value;
    Child(int v){
        super(v);
        this.value=v;
    }
}