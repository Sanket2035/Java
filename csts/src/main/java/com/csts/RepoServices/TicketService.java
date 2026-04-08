package com.csts.RepoServices;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csts.Models.Staff;
import com.csts.Models.Ticket;

@Service
public class TicketService {

	@Autowired
	TicketRepo tr;
	@Autowired
	StaffRepo sr;

	public boolean newTicket(Ticket t) {
		t.setDateCreated(LocalDateTime.now());
		tr.save(t);
		return true;
	}

	public List<Ticket> getAllTickets() {
		return tr.findAll();
	}

	public List<Ticket> getTicketByStatus(String Status) {
		return tr.findByStatusOrderByIdAsc(Status);
	}

	public boolean assignStaff(Ticket ticket, Staff staff) {
		if (tr.existsById(ticket.getId()) && sr.existsById(staff.getId())) {
			ticket.setStaff(staff);
			List<Ticket> tickets = staff.getAssignedTickets();
			tickets.add(ticket);
			staff.setAssignedTickets(tickets);

			tr.save(ticket);
			sr.save(staff);
			return true;
		} else {
			throw new RuntimeException("Ticket or Staff not found");
		}
	}

	public Ticket changeStatus(Ticket ticket, String status) {
		if (tr.existsById(ticket.getId())) {
			ticket.setStatus(status);
			return tr.save(ticket);
		} else {
			throw new RuntimeException("Ticket Not Found");
		}
	}

	public Ticket addRemark(Ticket ticket, String remark) {
		if (tr.existsById(ticket.getId())) {
			List<String> remarks = ticket.getRemarks();
			remarks.add(remark);
			ticket.setRemarks(remarks);
			return tr.save(ticket);
		} else {
			throw new RuntimeException("Ticket Not Found");
		}
	}

	public Ticket getTicketById(Long id) {
		if (tr.existsById(id)) {
			return tr.getById(id);
		} else {
			throw new RuntimeException("Ticket not found");
		}
	}
}
