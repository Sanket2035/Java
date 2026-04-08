package Demo;

public class A {
	
	B b;
	
	public B getB() {
		return b;
	}
	public void setB(B b) {
		this.b = b;
	}
	A(){
		System.out.println("A Constructor");
	}
	public void display() {
		System.out.println("display method A class");
		b.print();
	}
}
