package com.somnus.hashcode;

import java.util.Collection;
import java.util.HashSet;

/** 
 * @Description: TODO
 * @author Somnus
 * @date 2016年2月26日 下午2:59:54 
 * @version V1.0 
 */
public class HashCodeDemo {  
    public static void main(String[] args) {  
        Name n1 = new Name("01");  
        Name n2 = new Name("01");  
          
        Collection c = new HashSet();  
        c.add(n1);  
        System.out.println("------------");  
        c.add(n2);  
        System.out.println("------------");  
        System.out.println(n1.equals(n2));  
        System.out.println("------------");  
        System.out.println(n1.hashCode());  
        System.out.println(n2.hashCode());  
        System.out.println(c);
    }  
}  
  
class Name {  
    private String id;  
    public Name(String id) {  
        this.id = id;   
    }  
      
    public String toString(){  
        return this.id;  
    }  
    public boolean equals(Object obj) {  
        if (obj instanceof Name) {  
            Name name = (Name) obj;  
            System.out.println("equals"+ name.id);  
            return (id.equals(name.id));  
        }  
        return super.equals(obj);  
    }  
          
    public int hashCode() {  
        Name name = (Name) this;  
        System.out.println("Hash" + name.id);  
        return id.hashCode();  
    }
}  
