package com.csts.Models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Staff {
	@Id
	private Long id;
	private String name;
	@Column(unique = true)
	private String email;
	private String password;
	private String status;
	@OneToMany(mappedBy = "staff")
	List<Ticket> assignedTickets = new ArrayList<Ticket>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Ticket> getAssignedTickets() {
		return assignedTickets;
	}

	public void setAssignedTickets(List<Ticket> assignedTickets) {
		this.assignedTickets = assignedTickets;
	}

}
