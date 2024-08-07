/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
public class Codechef
{
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
		Scanner input = new Scanner(System.in);
		int test = input.nextInt();
		while(test-->0){
		    int n = input.nextInt();
		    input.nextLine();
		    String s = input.nextLine();
		    int d=0,k=0;
		    int res[] = new int[n];
		    for(int i = 0;i<n;i++){
		        if(s.charAt(i) == 'D')d++;
		        if(s.charAt(i) == 'K')k++;
		        if(d == 0 || k == 0)res[i] = d+k;
		        else{
		            if(d>1 && k>1){
		                int val = find(d+k);
		                if(val == -1)res[i]  =1;
		                else res[i] = (d+k)/val;
		            }else{
		                res[i] = 1;
		            }
		        }
		    }
		    for(int it:res)System.out.print(it+" ");
		    System.out.println();
		    
		}
	}
	public static int find(int num){
	    for(int i =2;i*i<=num;i++){
	        if(num%i == 0)return i;
	    }
	    return -1;
	}
}
 