package com.pune;

import java.util.List;

public class Student {
	private int id;
	private String name;
	private String address;
	private List<Integer> marks;
	public Student() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Integer> getMarks() {
		return marks;
	}
	public void setMarks(List<Integer> marks) {
		this.marks = marks;
	}
	public void display() {
		System.out.println("id: "+id);
		System.out.println("name: "+name);
		System.out.println("address: "+address);
		System.out.println("marks: "+marks);
	}
}
