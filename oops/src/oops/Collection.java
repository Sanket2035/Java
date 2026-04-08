package oops;

import java.util.ArrayList;
import java.util.Iterator;

public class Collection {
	
	public static void main(String[] args) {
		ArrayList a = new ArrayList();
		a.add(25);
		a.add(65);
		a.add(23);
		a.add(86);
		a.add(85);
		// Iterator , 
		Iterator i = a.iterator();
		while(i.hasNext()) {
			System.out.println(i.next());		
			
//			int s =(int) i.next();	// noSuchElementException
//			System.out.println(s);
//			if(s==86) {vjkpppp=p
//				i.remove();					// illegalStateException
//			}
		}
		
		Iterator i2 = a.iterator();
		i2.forEachRemaining(c->System.out.println("Remaining :"+c.toString()));
		
//		Iterator i3 = a.iterator();
//		i3.forEachRemaining(c->System.out.println("Remaining :"+c.toString()));
		
	}
}
