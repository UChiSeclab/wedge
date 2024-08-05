//package src;

import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.rmi.server.Skeleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

public class cc {
	static Scanner scn = new Scanner(System.in);
	//static int max = Integer.MIN_VALUE;
	static int mod=1000000007;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int t=scn.nextInt();
		while(t-->0)
		{	
			int n=scn.nextInt(),a=0,b=0,d=0,k=0,g=1;String str=scn.next();
			int ans[]=new int[n];
			StringBuilder sb=new StringBuilder();
			HashMap<kuch, Integer>map=new HashMap<kuch, Integer>();
			for(int i=0;i<n;i++)
			{
				if(str.charAt(i)=='D')
					d++;
				else
					k++;
				g=gcd(k, d);
				a=d/g;
				b=k/g;
				kuch prr=new kuch(a,b);
				if(map.containsKey(prr))
				{
					map.put(prr, map.get(prr)+1);
					sb.append(map.get(prr)+" ");
				}
				else
				{
					map.put(prr,1);
					sb.append(map.get(prr)+" ");
				}
			}
			System.out.println(sb.toString());
			
		}
		
	}
	
	
	public static boolean palind(String str)
	{
		int n=str.length();
		boolean bl=true;
		for(int i=0;i<=n/2;i++)
		{
			if(str.charAt(i)!=str.charAt(n-i-1))
			{
				bl=false;
				break;
			}
		}
		return bl;
	}
	

	public static int gcd(int a, int b) {
		if (a == 0)
			return b;
		int l = gcd(b % a, a);
		return l;
	}
}
class kuch
{
	int fir,sec;
	kuch(int a,int b)
	{
		this.fir=a;
		this.sec=b;
		
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		
		return fir*7+sec*7;
	}
	@Override
	public boolean equals(Object other) {   //learn this again
		if (this == other)
			return true;

		if (other instanceof kuch) {
			kuch pt = (kuch) other;

			return pt.fir == this.fir && pt.sec == this.sec;
		} else
			return false;
	}
}
class sort implements Comparator<kuch>
{

	@Override
	public int compare(kuch o1, kuch o2) {
		// TODO Auto-generated method stub
		int a=o1.sum-o2.sum;
		if(a<0)
			return 1;
		else
			return -1;
		
	}
	
}
