/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
public class Solution
{
	public static void main (String[] args) throws java.lang.Exception
	{
		Scanner s=new Scanner(System.in);
		int n=s.nextInt();
		int[]arr=new int[n];
		
		for(int i=0;i<n;i++)
		{
			arr[i]=s.nextInt();
		}
		
		int twocount=0,onecount=0;
		int ans=0,sol=0;
		boolean flag=false;
		for(int i=0;i<n;i++)
		{
			
			if(arr[i]==1)
			{
				int pos=i;
				if(onecount>0 && twocount>0)
				onecount=0;
				while(pos<n && arr[pos]==1)
				{
					onecount++;
					if(pos+1<n && arr[pos+1]==1)
					pos++;
					else
						break;
				}
				
				i=pos;
			}
			else if(arr[i]==2)
			{
				int pos=i;
				if(onecount>0 && twocount>0)
				twocount=0;
				while(pos<n && arr[pos]==2)
				{
					twocount++;
					if(pos+1<n && arr[pos+1]==2)
					pos++;
					else
						break;
					
			
				}
				

				i=pos;
			}
			
			ans=2*Math.min(twocount,onecount);
			sol=Math.max(sol, ans);
		}
		System.out.println(sol);
	}
}