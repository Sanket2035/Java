package oops;

import java.util.HashMap;

public class mapdemo {
	public static void main(String[] args) {
		HashMap h = new HashMap();
		h.put(24, "Chaitanya");
		h.put(43, "Avadhut");
		h.put(46, "Rushilesh");
		h.put(null, "Vaibhav");
		
		System.out.println(h);
		System.out.println(h.get(24));
		System.out.println(h.get(null));
		
	}
}
