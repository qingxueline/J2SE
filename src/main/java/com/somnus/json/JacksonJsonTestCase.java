package com.somnus.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/** 
 * @Description: TODO
 * @author Somnus
 * @date 2015年11月20日 下午2:10:04 
 * @version V1.0 
 */
public class JacksonJsonTestCase {

    /**
     * 简单序列化
     */
    @Test
    public void simpleTest() throws JsonProcessingException{
    	User user = new User();
        user.setUsername("admin");
        user.setPassword("123");
        user.setAge(20);
        User user2 = new User();
        user2.setUsername("admin2");
        user2.setPassword("123456");
        user2.setAge(24);
        List<Plot> list = Arrays.asList(new Plot("diudiu"),new Plot("dudu") );
        user.setList(list);
        /*Map<String,List<String>> map = new HashMap<String, List<String>>();
        map.put("somnus",Arrays.asList("1","2"));
        user.setMap(map);*/
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);  
        objectMapper.setSerializationInclusion(Include.NON_EMPTY);
        String jsonStr = objectMapper.writeValueAsString(user);
        System.out.println(jsonStr);
    }
    
    /**
     * 复杂序列化
     */
	@Test
    public void complexTest() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", "owen");
        map.put("age", 25);
        map.put("sex", "男");

        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("name", "jack");
        temp.put("age", 18);
        
        map.put("girinfo", temp);

        List<String> list = new ArrayList<String>();
        list.add("爬山");
        list.add("电影");
        list.add("旅游");
        
        map.put("hobby", list);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);  
        objectMapper.setSerializationInclusion(Include.NON_EMPTY);
        
        String jsonStr = objectMapper.writeValueAsString(map);
        System.out.println(jsonStr);
    }
    
	/**
     * 简单反序列化
     */
	@Test
    public void simpleDeserializeTest() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
        String jsonStr = "{\"username\":\"owen\",\"password\":\"passw0rd\", \"age\":24}";
        
        User user = objectMapper.readValue(jsonStr, User.class);
        System.out.println(user);
    }
	
	/**
     * 泛型反序列化
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
     */
	@Test
    public void genericTypeDeserializeTest() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
        String jsonStr = "{\"user\":{\"username\":\"owen\",\"password\":\"passw0rd\", \"age\":24}}";
        Map<String, User> map = objectMapper.readValue(jsonStr, new TypeReference<Map<String, User>>(){});
        System.out.println(map.get("user")+"\n");

        String jsonStr2 = "{\"user\":[{\"username\":\"owen\", \"age\":24}, {\"username\":\"jack\", \"age\":18}]}";
        Map<String, List<User>> users = objectMapper.readValue(jsonStr2, new TypeReference<Map<String, List<User>>>(){});
        for (User user : users.get("user")) {
        	System.out.println(user);
            System.out.println("------------------");
        }
        
        System.out.println("\n");
        
        String jsonStr3 = "[{\"username\":\"owen\", \"age\":24}, {\"username\":\"jack\", \"age\":18}]";
        List<User> list = objectMapper.readValue(jsonStr3, new TypeReference<List<User>>(){});
        for (User user : list) {
        	System.out.println(user);
            System.out.println("------------------");
        }
    }
    
    
}
