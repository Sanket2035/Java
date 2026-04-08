package oops;

class Animal{
	int age;
	String name;
	String color;
	
	void eating() {
		System.out.println("Eating....");
	}
	void running() {
		System.out.println("Running....");
	}
}

public class Demo {
	public static void main(String[] args) {
		Animal cat=new Animal();
		cat.name="Rosy";
		cat.age=2;
		cat.color="Orange";
		
		cat.eating();
		cat.running();
	}
}
