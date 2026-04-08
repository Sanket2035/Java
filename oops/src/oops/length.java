package oops;

import java.util.Scanner;
public class length {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String str = sc.nextLine();

        int count = 0;
        char[] arr = str.toCharArray();
        for(int i = 0; i < arr.length; i++) {
            count++;
        }

        System.out.println("Length of string: " + count);
    }
}