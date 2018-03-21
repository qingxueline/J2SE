package com.somnus;

/**
 * 
 * @Title: QueryNumber.java 
 * @Package com.somnus 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月8日 上午11:34:43 
 * @version V1.0
 */
public class QueryNumber{
	/*
	 * 5*4*3*2*1
	 */
	public int multiplication(int num){
		if (1 == num){
			return 1;
		}
		else{
			return num * multiplication(num - 1);
		}
	}
	/*
	 * 1+2+3+4+5+6+7+8+9+10
	 */
	public int sum(int num){
		if (1 == num){
			return 1;
		}
		else{
			return num + sum(num - 1);
		}
	}
	/*
	 * 1,1,2,3,5,8,13,21....
	 */
	public int compute(int n){
		if (1 == n || 2 == n){
			return 1;
		}
		else{
			return compute(n - 1) + compute(n - 2);
		}
	}
	/*
     * 1,1,2,3,5,8,13,21....
     */
    public int compute2(int n){
        int num1 = 1;
        int num2 = 1;
        for(int i=1;i<=n;i++){
            if (1 != i && 2 != i){
                num2 = num1 + num2;
                num1 = num2 - num1;
            }
        }
        return num2;
    }

	public static void main(String[] args){
		QueryNumber query = new QueryNumber();
		System.out.println(query.sum(100));
		System.out.println(query.multiplication(5));
		System.out.println(query.compute(9));
		System.out.println(query.compute2(9));
	}
}
