package com.somnus.generic;

import java.util.List;

/** 
 * @Description: TODO
 * @author Somnus
 * @date 2016年1月11日 下午4:54:51 
 * @version V1.0 
 */
public class GenericSample<E extends Flower> {
    
    private List<E> result;
    
    public <F extends Flower> void add(F r){
        
    }
    /*public <R super Rose> void add(R r){
        
    }*/
    public <T> void add(List<T> list){
        
    }
    
    public <F extends Flower> F save(List<F> list){
        if(list != null && list.size()>0){
            F f = (F) list.get(0);
            return f;
        }
		return null;
    }
    /*public <K super Rose> K save2(List<K> list){
        if(list != null && list.size()>0){
            K k = (K) list.get(0);
            return k;
        }
		return null;
    }*/
	public List<E> getResult() {
		return result;
	}
	public void setResult(List<E> result) {
		this.result = result;
	}
    
}
class Flower{
    
}
class Rose extends Flower{
    
}
