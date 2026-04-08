package com.pune;

public class employee {
	private int id;
	private String name;
	private String address;
	
	public employee() {
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
	public void display() {
		System.out.println("id: "+id);
		System.out.println("name: "+name);
		System.out.println("address: "+address);
	}
}
