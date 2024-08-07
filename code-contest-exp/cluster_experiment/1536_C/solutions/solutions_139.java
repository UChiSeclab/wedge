//package pack;
import java.io.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.math.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Math.sqrt;
import static java.lang.Math.floor;







public class topcoder {
  
  
  
		   static int gcd(int a, int b) {
			   
			   if(a == 0)
				   return b;
			   
			   return gcd(b%a,a);
		   }
		    
  
  
		public static void main(String args[])throws IOException{
			 
	    	//	 System.setIn(new FileInputStream("Case.txt"));
	    		 BufferedReader ob = new BufferedReader(new InputStreamReader(System.in));
	    		 BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	    
	    		  
	    		  
	    	      int t = Integer.parseInt(ob.readLine());
	    	   
	    	      
	    	  I:    while(t --> 0) {
	    	     int n = Integer.parseInt(ob.readLine());
	    	    String s = ob.readLine();
	    	    
	    	    HashMap<String,Integer>map = new HashMap<>();
	    	    
	    	    
	    	    int d = 0;
	    	    int k = 0;
	    	    
	    	   for(char c : s.toCharArray()) {
	    		   if(c == 'D')
	    			   d++;
	    		   else
	    			   k++;
	    		   
	    		   int gcd = gcd(d,k);
	    		   int a = d/gcd;
	    		   int b = k/gcd;
	    		   String str = a+"#"+b;
	    	//	   System.out.println(gcd+" "+a+" "+b);
	    		   int p = map.containsKey(str)? map.get(str)+1 : 1;
	    		   map.put(str, p);
	    		   bw.write(p+" ");
	    	   }
	    	      bw.write("\n");
	    	      bw.flush();
	    	  }
		}
}
	    	     
	    	     
	               
	                  
	                  
	    		 



	 

	    
	    		    		
	    		    		
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			