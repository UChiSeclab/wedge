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
		Scanner sc=new Scanner(System.in);
		int n,a[],i,one=0,two=0;
		n=sc.nextInt();
		a=new int[n];
		for(i=0;i<n;i++)
		{
		    a[i]=sc.nextInt();
		}
		one=two=0;
		for(i=0;i<n;i++)
		{
		    if(a[i]==1)
		    {
		        one++;
		    }
		    else if(a[i]==2)
		    {
		        two++;
		    }
		}
		if(one>two)
		{
		    System.out.println(2*two);
		}
	    else if(two>=one)
	    {
	        System.out.println(2*one);
	    }
	}
}