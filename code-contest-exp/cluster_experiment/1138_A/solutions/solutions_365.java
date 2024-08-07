/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
public class Main
{
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
		Scanner  s = new Scanner(System.in);
		int n = s.nextInt();
		int a[] = new int[n];
		int max =2;
		int same = 0;
		int num = -1;
		int count = 0;
		
		for(int i=0;i<n;i++){
			a[i] = s.nextInt();
			if(a[i]!= num){
				num = a[i];
				count = same;
				same = 1;
			}
			else{
				same++;
				max = Math.max(Math.min(same, count)*2, max);
			}
			
		}
		max = Math.max(Math.min(same, count)*2, max);
		System.out.println(max);
	}
}