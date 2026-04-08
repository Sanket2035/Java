package com.csts.RepoServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csts.Models.Admin;

@Service
public class AdminServices {

	@Autowired
	AdminRepo ar;

	public Admin login(String email, String password) {
		List<Admin> admin = ar.findAll();
		for (Admin a : admin) {
			if (a.getEmail().equals(email) && a.getPassword().equals(password)) {
				return a;
			}
		}
		return null;
	}

}
