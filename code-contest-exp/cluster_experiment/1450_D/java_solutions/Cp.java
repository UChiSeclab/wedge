//Jo Hack Kiya Wo M.......D
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

/* Name of the class has to be "Main" only if the class is public. */
public class Cp
{
	
	 //	Template For Fast i/o copied From Gfg
		static class FastReader 
	    { 
	        BufferedReader br; 
	        StringTokenizer st; 
	  
	        public FastReader() 
	        { 
	            br = new BufferedReader(new
	                     InputStreamReader(System.in)); 
	        } 
	  
	        String next() 
	        { 
	            while (st == null || !st.hasMoreElements()) 
	            { 
	                try
	                { 
	                    st = new StringTokenizer(br.readLine()); 
	                } 
	                catch (IOException  e) 
	                { 
	                    e.printStackTrace(); 
	                } 
	            } 
	            return st.nextToken(); 
	        } 
	  
	        int nextInt() 
	        { 
	            return Integer.parseInt(next()); 
	        } 
	        long nextLong() 
	        { 
	            return Long.parseLong(next()); 
	        } 
	  
	        double nextDouble() 
	        { 
	            return Double.parseDouble(next()); 
	        } 
	  
	        String nextLine() 
	        { 
	            String str = ""; 
	            try
	            { 
	                str = br.readLine(); 
	            } 
	            catch (IOException e) 
	            { 
	                e.printStackTrace(); 
	            } 
	            return str; 
	        } 
	    }
		
	public static void main(String[] args) {
	   FastReader obj=new FastReader();
       PrintWriter out=new PrintWriter(System.out);
       int t=obj.nextInt();
       while(t-->0)
       {
    	   int n=obj.nextInt();
    	   int []a=new int[n];
    	   for(int i=0;i<n;i++)
    	   {
    		   a[i]=obj.nextInt();
    	   }
    	   char s[]=new char[n];
    	   for(int i=0;i<n;i++)
    	   {
    		   s[i]='0';
    	   }
    	   int []freq=new int[n+1];
    	   for(int i=0;i<n;i++)
    	   {
    		   freq[a[i]]++;
    	   }
    	   int start=0, end=n-1;
           for(int i=0;i<n;i++)
           {
               if(freq[i+1]>0)
                   s[n-i-1]='1';
               else 
                   break;
               if(freq[i+1]>1)
                   break;
               if(a[start]==i+1)
                   start++;
               else if(a[end]==i+1)
                   end--;
               else
                       break;
           }
           HashSet<Integer> set=new HashSet<>();
           for(int i=0;i<n;i++)
           {
        	   set.add(a[i]);
           }
           if(set.size()==n)
           {
        	   s[0]='1';
           }
    	   for(int i=0;i<n;i++)
    	   {
    		   out.print(s[i]);
    	   }
    	   out.println();
    	   out.flush();
       }
	}
	public static void print(char mat[][])
	{
		PrintWriter out=new PrintWriter(System.out);
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[0].length;j++)
			{
				out.print(mat[i][j]);
			}
			out.println();
		}
		out.flush();
	}
	public static boolean check(char mat[][],int n)
	{
		for(int i=0;i<n;i++)
		{
			int count=0;
			for(int j=0;j<n;j++)
			{
				if(mat[i][j]=='X')
				{
					count++;
				}
				else 
				{
					count=0;
				}
				if(count==3)
				{
					return false;
				}
			}
		}
		for(int j=0;j<n;j++)
		{
			int count=0;
			for(int i=0;i<n;i++)
			{
				if(mat[i][j]=='X')
				{
					count++;
				}
				else 
				{
					count=0;
				}
				if(count==3)
				{
					return false;
				}
			}
		}
		return true;
	}
}



