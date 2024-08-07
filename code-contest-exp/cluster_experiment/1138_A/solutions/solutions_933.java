/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.Arrays;
import java.math.BigInteger;
 
/* Name of the class has to be "Main" only if the class is public. */
public class Main
{
     public static void main (String[] args) throws java.lang.Exception
     {
     	Scanner in =new Scanner(System.in);
		int t,i,j,l=0,h=0,n,m=0,k=1,p=0;
		String s="",st="";
		n=in.nextInt();
		int a[]=new int[n];
		for(i=0;i<n;i++)
		     a[i]=in.nextInt();
		for(i=0;i<n-1;i++)
		{
		     k=1;
		     while(i<n-1 && a[i]==a[i+1])
		     {
		          i++;
		          k++;
		     }
		     i++;
		     if(2*Math.min(p,k)>m)
		          m=2*Math.min(p,k);
		     p=1;
		     while(i<n-1 && a[i]==a[i+1])
		     {
		          i++;
		          p++;
		     }
		     if(2*Math.min(p,k)>m)
		          m=2*Math.min(p,k);
		}
		System.out.println(m);
	}
}