package com.somnus.accessModifier;

import org.junit.Test;

public class StringTest {
	
    @Test
	public void strTest() {
		String str = "abcd";
		String str2 = "cd";
		System.out.println(str == "ab" + "cd");
		System.out.println(str == "ab" + str2);
	}
	
    @Test
    public void strTest2() {
        String str = "abcd"; 
        final String a = "ab";
        String b = "ab";
        System.out.println(str == a + "cd");
        System.out.println(str == b + "cd");
    }
    
    @Test
    public void strTest3() {
        String str = "abcd"; 
        final String a = "cd";
        String b = "cd";
        System.out.println(str == "ab" + a);
        System.out.println(str == "ab" + b);
    }
}