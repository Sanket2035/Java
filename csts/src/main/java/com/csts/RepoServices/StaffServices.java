package com.csts.RepoServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csts.Models.Staff;

@Service
public class StaffServices {
	@Autowired
	StaffRepo sr;

	public boolean StaffRegistration(Staff s) {
		sr.save(s);
		return true;
	}

	public List<Staff> getAllstaff() {
		return sr.findAll();
	}

	public void delete(Long id) {
		if (sr.existsById(id)) {
			sr.deleteById(id);
		} else {
			System.out.println("Id Does Not Exist!");
		}
	}

	public Staff update(Staff s) {
		if (sr.existsById(s.getId())) {
			return sr.save(s);
		} else {
			throw new RuntimeException("Staff Not Found!");
		}
	}

	public Staff getStaffById(Long id) {
		return sr.getById(id);
	}

	public Staff toggleStatus(Long id) {
		if (sr.existsById(id)) {
			Staff s = sr.getById(id);
			String status = (s.getStatus().equals("Active")) ? "Deactive" : "Active";
			s.setStatus(status);
			return sr.save(s);
		} else {
			throw new RuntimeException("Staff Not Found");
		}
	}
}
