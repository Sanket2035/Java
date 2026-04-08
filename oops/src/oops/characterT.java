package oops;

import java.util.Scanner;
public class characterT {
	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);

	        System.out.print("Enter a string: ");
	        String str = sc.nextLine();

	        boolean found = false;

	        for(int i = 0; i < str.length(); i++) {
	            if(str.charAt(i) == 't') {
	                found = true;
	                break;
	            }
	        }

	        if(found)
	            System.out.println("Character 't' found in string");
	        else
	            System.out.println("Character 't' not found");
	    }

}
