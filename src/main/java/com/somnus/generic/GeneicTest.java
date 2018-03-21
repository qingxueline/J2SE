package com.somnus.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GeneicTest{
	
	private static Class<?> getGenericSuperClass(Class<?> clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            Type[] params = ParameterizedType.class.cast(genType).getActualTypeArguments();
            if ((params != null) && (params.length >= (index - 1))) {
                return (Class<?>) params[index];
            }
        }
        return null;
    }
	
	private static Class<?> getGenericInterfaceClass(Class<?> clazz, int index) {
        Type[] genTypes = clazz.getGenericInterfaces();
        for (Type genType : genTypes) {
        	if (genType instanceof ParameterizedType) {
                Type[] params = ParameterizedType.class.cast(genType).getActualTypeArguments();
                if ((params != null) && (params.length >= (index - 1))) {
                    return (Class<?>) params[index];
                }
            }
        }
        return null;
    }
	
	public static void main(String[] args) {
		System.out.println(getGenericSuperClass(MybatisDao.class,0).getSimpleName());
		System.out.println(getGenericInterfaceClass(MybatisDao.class,0).getSimpleName());
		
		System.out.println(getGenericSuperClass(MybatisDao.class,1).getSimpleName());
		System.out.println(getGenericInterfaceClass(MybatisDao.class,1).getSimpleName());
		
	}
}
interface BaseInterface<T,R>{
	R say(T t);
}
class BaseDao<T,R> {
	
}
class MybatisDao extends BaseDao<Flower,Animal> implements BaseInterface<Flower,Animal>{
	@Override
	public Animal say(Flower f) {
		return new Cat();
	}
}