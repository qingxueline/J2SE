package com.somnus.instanceoff;

/**
 * instanceof关键字和isInstance方法的区别
 * @Title: Test.java 
 * @Package com.somnus.instanceofTest 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午5:05:22 
 * @version V1.0
 */
public class Test{
    
	@org.junit.Test
	public void test0(){
		System.out.println(Animal.class.isAssignableFrom(Animal.class));
		System.out.println(Animal.class.isAssignableFrom(Dog.class));
		System.out.println(Animal.class.isAssignableFrom(Cat.class));
	}
	
	@org.junit.Test
	public void test1(){
		Animal animal = new Dog();
		System.out.println(animal instanceof Dog);
		System.out.println(animal instanceof Cat);
	}
	
	@org.junit.Test
	public void test2(){
		Animal animal = new Dog();
		System.out.println(Dog.class.isInstance(animal));
		System.out.println(Cat.class.isInstance(animal));
	}
	
	@org.junit.Test
	public void test3(){
		Animal animal = new Animal();
		System.out.println(Animal.class.cast(animal));
		
		Animal dog = new Dog();
		System.out.println(Dog.class.cast(dog));
		
		Animal cat = new Cat();
		System.out.println(Cat.class.cast(cat));
	}
	

	public static void main(String[] args) throws Exception{
		Dog dog = new Dog();
		Organism organism = dog.unwrap(Organism.class);
		organism.defined();
	}

}
interface Organism{
	void defined();
}
class Animal{
	public Animal(){
		System.out.println("Animal is instance");
	}
}
class Dog extends Animal implements Organism{
	public Dog(){
		System.out.println("Dog is instance");
	}
	public final <T> T unwrap(Class<T> type) {
		if ( type.isAssignableFrom( getClass() ) ) {
			return type.cast( this );
		}
		return null;
	}
	@Override
	public void defined() {
		System.out.println("I am dog");
	}
}
class Cat extends Animal implements Organism{
	public Cat(){
		System.out.println("Cat is instance");
	}
	public final <T> T unwrap(Class<T> type) {
		if ( type.isAssignableFrom( getClass() ) ) {
			return type.cast( this );
		}
		return null;
	}
	@Override
	public void defined() {
		System.out.println("I am cat");
	}
}