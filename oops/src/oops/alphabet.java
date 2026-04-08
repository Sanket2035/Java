package oops;

import java.util.Scanner;
public class alphabet {

	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);

	        System.out.print("Enter a string: ");
	        String str = sc.nextLine();

	        boolean hasAlphabet = false;

	        for(int i = 0; i < str.length(); i++) {
	            char ch = str.charAt(i);

	            if(Character.isLetter(ch)) {
	                hasAlphabet = true;
	                break;
	            }
	        }

	        if(hasAlphabet)
	            System.out.println("String contains alphabets");
	        else
	            System.out.println("String does not contain alphabets");
	    
	}
}
