package oops;

import java.util.Scanner;
public class vovel {

	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);

	        System.out.print("Enter a string: ");
	        String str = sc.nextLine();

	        boolean vowelFound = false;

	        for(int i = 0; i < str.length(); i++) {
	            char ch = str.charAt(i);

	            if(ch=='a'||ch=='e'||ch=='i'||ch=='o'||ch=='u'||
	               ch=='A'||ch=='E'||ch=='I'||ch=='O'||ch=='U') {
	                vowelFound = true;
	                break;
	            }
	        }

	        if(vowelFound)
	            System.out.println("Vowel is present");
	        else
	            System.out.println("No vowel found");
	    }
	
}
