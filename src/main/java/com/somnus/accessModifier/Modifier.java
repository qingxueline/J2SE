package com.somnus.accessModifier;

public class Modifier{
	/**1
	 * private(私有的)
	 * 被private所修饰的属性和方法
	 * A只能在该类内部被使用
	 */
	private void c()
	{
		
	}
	/**2
	 * 默认的(不加任何访问修饰符)
	 * A在类内部
	 * B相同包下面的类
	 */
	 void d()
	{
		
	}
	 
	/**3
	 * protected(受保护的)
	 * A在类内部
	 * B相同包下面的类
	 * C子类(可以是不同的包)
	 */
	protected void b()
	{
			
	}
		
	/**4
	 * public(公共的)
	 * 被public所修饰的属性和方法
	 * 可以被所有类访问
	 */
	public void a()
	{
			
	}
}
