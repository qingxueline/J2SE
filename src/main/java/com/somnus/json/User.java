package com.somnus.json;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class User {
	private String username;
	
	private String password;
	
	private int age;
	
	private List<Plot> list;
	
	private Map<String,List<String>> map;
	
	public User() {
		super();
	}
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<Plot> getList() {
		return list;
	}
	public void setList(List<Plot> list) {
		this.list = list;
	}
	public Map<String, List<String>> getMap() {
		return map;
	}
	public void setMap(Map<String, List<String>> map) {
		this.map = map;
	}
	
	public String toString() {  
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);   
    } 
}
class Plot{
	
	public Plot() {
		super();
	}
	public Plot(String name){
		this.name = name;
	}
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}