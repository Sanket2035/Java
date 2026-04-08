package Models;

import Enums.UserRole;

public class Users {
	private int id;
	private String username;
	private String email;
	private String pass;
	private UserRole role;
	
	public Users() {
	}
	public Users(int id, String username, String email, String pass, String role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.pass = pass;
		this.role = UserRole.valueOf(role);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String password) {
		this.pass = password;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = UserRole.valueOf(role);
	}
}
