	import java.math.BigInteger;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
	import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.Scanner; 
import java.util.StringTokenizer;
import java.util.TreeMap; 
	public class SequenceWithDigits {
	
		static FastReader sc= new FastReader();
		static List<Integer> C;
		static List<Integer> B;
		
		
		
		public static void main(String[] args) {
		
			
			int n=sc.nextInt();
			ArrayList<Integer> a=new ArrayList<>();
			for(int i=0;i<n;i++) {
				
				a.add(sc.nextInt());
				
				
			}
			int ones=0;int twos=0;
			for(int i=0;i<n;i++) {if(a.get(i)==1)ones++;else twos++;}
			if(ones==0||twos==0) {System.out.println(0);return;}

			ones=1;twos=1;
			int currMax=Integer.MIN_VALUE;
			int min=Integer.MAX_VALUE;
			for(int i=0;i<n-1;i++) {
				if(a.get(i)!=a.get(i+1)) {i++;continue;}
				if(a.get(i)==1) {
					 ones=1;
					while(a.get(i)==a.get(i+1)) {
						i++;
						ones++;
						if(i==a.size()-1)break;
					}
					
				}
				if(a.get(i)==2) {
					 twos=1;
					while(a.get(i)==a.get(i+1)) {
						i++;
						twos++;
						
						if(i==a.size()-1)break;
					}
					
				}
				min=Math.min(ones, twos);
				currMax=Math.max(min, currMax);
				
			}
			min=Math.min(ones, twos);
			currMax=Math.max(min, currMax);
			System.out.println(currMax*2);
			
		}
		

	    static boolean isPrime(long n) 
	    { 
	        // Corner cases 
	        if (n <= 1) 
	            return false; 
	        if (n <= 3) 
	            return true; 
	  
	        // This is checked so that we can skip 
	        // middle five numbers in below loop 
	        if (n % 2 == 0 || n % 3 == 0) 
	            return false; 
	  
	        for (int i = 5; i * i <= n; i = i + 6) 
	            if (n % i == 0 || n % (i + 2) == 0) 
	                return false; 
	  
	        return true; 
	    } 
	    
	    
	    
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
		
		
			
	}
