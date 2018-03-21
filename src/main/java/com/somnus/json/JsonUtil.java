package com.somnus.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * Json与javaBean之间的转换工具类
 *
 * @author
 *
 *         {@code   现使用json-lib组件实现
 *          需要
 *              json-lib-2.4-jdk15.jar
 *              ezmorph-1.0.6.jar
 *              commons-collections-3.1.jar
 *              commons-lang-2.0.jar
 *              commons-beanutils-1.9.1.jar
 *          支持
 * }
 */

public class JsonUtil {
    // *************************************以下对象转换为JSON***************************************************************
    private static JsonConfig configJson(String[] excludes) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(Date.class, new JsDateJsonValueProcessor());
        return jsonConfig;
    }

    /**
     * 将java对象转换成json字符串
     *
     * @param bean
     * @return
     */
    public static String obj2JsonString(Object obj) {
        JSONObject json = JSONObject.fromObject(obj);
        return json.toString();
    }

    /**
     * 将java对象指定的属性转换成json字符串
     *
     * @param bean
     * @param include
     *            包含的属性字段值
     * @param nory
     * @return
     */
    public static String obj2JsonString(Object obj, String[] include, boolean nory) {
        JSONObject json = null;
        // 转换include里的属性
        if (nory){
            Field[] fields = obj.getClass().getDeclaredFields();
            String str = "";
            for (Field field : fields) {
                str += ("#" + field.getName());
            }
            fields = obj.getClass().getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                str += ("#" + field.getName());
            }
            str += "#";
            for (String s : include) {
                str = str.replace("#" + s + "#", "#");
            }
            json = JSONObject.fromObject(obj, configJson(str.split(":")));
        } 
        // 转换除了include里的属性
        else {
            json = JSONObject.fromObject(obj, configJson(include));
        }
        return json.toString();
    }

    /**
     * 将java对象List集合转换成json字符串
     * 
     * @param beans
     * @return
     */
    public static <E> String list2JsonString(List<E> list) {
        StringBuffer temp = new StringBuffer();
        temp.append("[");
        int size = list.size();
        for (int i = 0; i < size; i++) {
            temp.append(obj2JsonString(list.get(i)) + ((i < size - 1) ? "," : ""));
        }
        temp.append("]");
        return temp.toString();
    }
    
    public static <E> String list2JsonString_(List<E> list) {
        JSONArray jsonArray = JSONArray.fromObject(list);
        return jsonArray.toString();
    }


    /**
     * 将List中所含对象指定的属性转换成json字符串
     *
     * @param bean
     * @param include
     *            包含的属性字段值
     * @param nory
     * @return
     */
    public static <E> String list2JsonString(List<E> beans, String[] include, boolean nory) {
        StringBuffer rest = new StringBuffer();
        rest.append("[");
        int size = beans.size();
        for (int i = 0; i < size; i++) {
            rest.append(obj2JsonString(beans.get(i), include, nory) + ((i < size - 1) ? "," : ""));
        }
        rest.append("]");
        return rest.toString();
    }
   

    /**
     * map集合转换成json格式数据
     * 
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <E, V> String map2JsonString(Map<E, V> map, String[] include, boolean nory) {
        String s_json = "{";
        for (Iterator<E> it = map.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            if (map.get(key) == null) {
            } else if (map.get(key) instanceof List) {
                s_json += ("\"" + key + "\":" + JsonUtil.list2JsonString((List<E>) map.get(key), include, nory));
            } else {
                JSONObject json = JSONObject.fromObject(map.get(key));
                s_json += ("\"" + key + "\":" + json.toString());
            }
            if (it.hasNext()) {
                s_json += ",";
            }
        }
        s_json += "}";
        return s_json;
    }

    // *************************************以下JSON转换为对象***************************************************************

    /**
     * 从一个JSON 对象字符格式中得到一个java对象
     * 
     * @param jsonString
     * @param beanCalss
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonString2Object(String jsonString, Class<T> clazz) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        T bean = (T) JSONObject.toBean(jsonObject, clazz);
        return bean;
    }

    /**
     * 从json HASH表达式中获取一个map，改map支持嵌套功能
     *
     * @param jsonString
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    public static<E, V> Map<E, V> jsonString2Map(String jsonString) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Iterator<E> keyIter = jsonObject.keys();
        E key;
        V value;
        Map<E, V> valueMap = new HashMap<E, V>();
        while (keyIter.hasNext()) {
            key = (E)keyIter.next();
            value = (V)jsonObject.get(key);
            valueMap.put(key, value);
        }
        return valueMap;
    }

    /**
     * 从json对象集合表达式中得到一个java对象列表
     *
     * @param jsonString
     * @param beanClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> json2ObjectList(String jsonString, Class<T> beanClass) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        JSONObject jsonObject;
        T bean;
        int size = jsonArray.size();
        List<T> list = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
            jsonObject = jsonArray.getJSONObject(i);
            bean = (T) JSONObject.toBean(jsonObject, beanClass);
            list.add(bean);
        }
        return list;
    }
    
    /**
     * 从json数组中得到相应java数组
     *
     * @param jsonString
     * @return
     */
    public static Object[] jsonToObjectArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        return jsonArray.toArray();
    }

    /**
     * 从json数组中解析出java字符串数组
     *
     * @param jsonString
     * @return
     */
    public static String[] jsonToStringArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        String[] stringArray = new String[jsonArray.size()];
        int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            stringArray[i] = jsonArray.getString(i);
        }
        return stringArray;
    }

    /**
     * 从json数组中解析出javaLong型对象数组
     *
     * @param jsonString
     * @return
     */
    public static Long[] jsonToLongArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        Long[] longArray = new Long[size];
        for (int i = 0; i < size; i++) {
            longArray[i] = jsonArray.getLong(i);
        }
        return longArray;
    }

    /**
     * 从json数组中解析出java Integer型对象数组
     *
     * @param jsonString
     * @return
     */
    public static Integer[] jsonToIntegerArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        Integer[] integerArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            integerArray[i] = jsonArray.getInt(i);
        }
        return integerArray;
    }

    /**
     * 从json数组中解析出java Double型对象数组
     * 
     * @param jsonString
     * @return
     */
    public static Double[] jsonToDoubleArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        Double[] doubleArray = new Double[size];
        for (int i = 0; i < size; i++) {
            doubleArray[i] = jsonArray.getDouble(i);
        }
        return doubleArray;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123");
        user.setAge(20);
        User user2 = new User();
        user2.setUsername("admin2");
        user2.setPassword("123456");
        user2.setAge(24);
        System.out.println("一：" + JsonUtil.obj2JsonString(user));

        Map<String, String> map11 = new HashMap<String, String>();
        map11.put("1", "a");
        map11.put("2", "b");
        System.out.println("二：" + JsonUtil.obj2JsonString(map11));

        System.out.println("****************************************************************");
        List<User> list = new ArrayList<User>();
        list.add(user);
        list.add(user2);
        System.out.println("三：" + JsonUtil.list2JsonString(list));
        System.out.println("四：" + JsonUtil.list2JsonString_(list));

        System.out.println("****************************************************************");
        // 只包含数组里面的属性
        System.out.println("五：" + JsonUtil.obj2JsonString(user, new String[] { "username" }, true));
        // 除数组之外的所有属性
        System.out.println("六：" + JsonUtil.obj2JsonString(user, new String[] { "username" }, false));

        System.out.println("七：" + JsonUtil.list2JsonString(list, new String[] { "username", "password" }, true));

        System.out.println("****************************************************************");
        Map<String, User> map1 = new HashMap<String, User>();
        map1.put("1", user);
        map1.put("2", user2);
        System.out.println("八：" + JsonUtil.map2JsonString(map1, new String[] { "username", "password" }, true));

        Map<String, List<User>> map2 = new HashMap<String, List<User>>();
        map2.put("1", list);
        System.out.println("九：" + JsonUtil.map2JsonString(map2, new String[] { "username", "password" }, true));

    }

}