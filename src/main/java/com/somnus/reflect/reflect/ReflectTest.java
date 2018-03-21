package com.somnus.reflect.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 
 * @Title: ReflectTest.java 
 * @Package com.somnus.reflect.reflect 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月10日 上午9:31:16 
 * @version V1.0
 */
public class ReflectTest {
	
	public Object copy(Object object) throws Exception{
		Class<?> clazz = object.getClass();
		
		Object objectCopy = clazz.getConstructor(new Class[]{}).newInstance(new Object[]{});
	    /*以上这行代码等于下面*/
	    /*Object objectCopy2 = clazz.newInstance();*/
		
		Field[] fields = clazz.getDeclaredFields();
		
		for(int i=0;i<fields.length;i++){
			Field field = fields[i];
			String fieldName = field.getName();
			String firstLetter = fieldName.substring(0,1).toUpperCase();
			
			String getMethodName = "get"+firstLetter+fieldName.substring(1);
			String setMethodName = "set"+firstLetter+fieldName.substring(1);
			
			Method getMethod = clazz.getMethod(getMethodName, new Class[]{});
			Method setMethod = clazz.getMethod(setMethodName, new Class[]{field.getType()});
			
			Object value = getMethod.invoke(object, new Object[]{});
			
			setMethod.invoke(objectCopy, new Object[]{value});
		}
		return objectCopy;
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Customer customer = new Customer();
		customer.setAge(22);
		customer.setId(99L);
		customer.setName("hello");
		Customer customerCopy = (Customer)new ReflectTest().copy(customer);
		System.out.println(customerCopy.getId()+","+customerCopy.getAge()+","+customerCopy.getName());

	}
	
}
class Customer{
	private Long id;
	private String name;
	public int age;
	
	public Customer() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
