/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
public class Ideone
{
	public static void main (String[] args) throws java.lang.Exception
	{
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int a[]=new int[n];
		for(int i=0;i<n;i++)
		{
			a[i]=sc.nextInt();
		}
		ArrayList<Integer> b = new ArrayList<Integer>();
		ArrayList<Integer> bb = new ArrayList<Integer>();
		int k=1;
		for(int i=0;i<n;i++)
		{
			if(i==n-1)
			{
				b.add(k);
			}
			else
			{
				if(a[i]==a[i+1])
				k++;
				else
				{
					b.add(k);
					k=1;
				}
			}
		}
		for(int i=0;i<b.size()-1;i++)
		{
			int x=b.get(i);
			int y=b.get(i+1);
			int z=Math.min(x,y);
			bb.add(z);
		}
		int max=-1;
		for(int i=0;i<bb.size()-1;i++)
		{
			int m=bb.get(i);
			if(m>max)
			max=m;
		}
		//System.out.println(Arrays.toString(a));
		//b.forEach(p -> System.out.print(p));
		//System.out.println();
		//bb.forEach(pp -> System.out.print(pp));
		//System.out.println();
		System.out.println(2*max);
	}
}