package oops;

import java.util.Arrays;
import java.util.Scanner;

public class task {
	
	public static void sum(int[][] a) {
		int sum = 0;
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				sum+=a[i][j];
			}
		}
		System.out.println("Sum of array elements: "+sum);
	}
	
	public static void smallest(int[][] a) {
		int smallest=a[0][0];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				smallest= (smallest>a[i][j])?a[i][j]:smallest;
			}
		}

		System.out.println("Smallest: "+smallest);
	}
	
	public static void secondLargest(int[][] a) {
		int largest,second;
		largest=second=a[0][0];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				largest= (largest<a[i][j])?a[i][j]:largest;
			}
		}
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(second<a[i][j] && a[i][j]!=largest)
					second=a[i][j];
			}
		}
		System.out.println("Second Largest: "+second);
		
	}
	
	public static void countZeros(int[][] a) {
		int zeros=0;
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(a[i][j]==0)
					zeros+=1;
			}
		}
		System.out.println("Number of Zeros: "+zeros);
	}
	
	public static void evenElements(int[][] a) {
		System.out.println("Even Elements in Array: ");
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(a[i][j]%2==0)
					System.out.print(a[i][j]+"\t");
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[][] a= new int[3][3];
		System.out.println("Enter Array elements: ");
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				a[i][j]=sc.nextInt();
			}
		}
		
		sum(a);
		smallest(a);
		secondLargest(a);
		countZeros(a);
		evenElements(a);
		
		sc.close();
	}
}
