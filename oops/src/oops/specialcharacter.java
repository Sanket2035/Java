package oops;

import java.util.Scanner;
public class specialcharacter {

	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);

	        System.out.print("Enter a string: ");
	        String str = sc.nextLine();

	        boolean special = false;

	        for(int i = 0; i < str.length(); i++) {
	            char ch = str.charAt(i);

	            if(!Character.isLetterOrDigit(ch) && ch!=' ') {
	                special = true;
	                break;
	            }
	        }

	        if(special)
	            System.out.println("Special character found");
	        else
	            System.out.println("No special character found");
	    }
}
