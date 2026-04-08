package Demo;

import java.util.List;

public class Student {
	private int id;
	private String name;
	private List<String> skills;
	 
	public Student(int id) {
		this.id = id;
	}
	public Student(String name) {
		this.name = name;
	}
	
	public Student(List<String> skills) {
		this.skills = skills;
	}
	public Student(String name, int id,List<String> skills) {
		this.name = name;
		this.id = id;
		this.skills=skills;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<String> getSkills() {
		return skills;
	}
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
	public void show() {
		System.out.println("Student [Id=" + id + ", name=" + name + ", skills=" + skills + "]");
	}
	
}
