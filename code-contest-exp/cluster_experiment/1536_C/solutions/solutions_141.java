/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
public class Codechef
{static class FastReader 
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
	public static void main (String[] args) throws java.lang.Exception
	{
		FastReader scan = new FastReader();
		PrintWriter pw = new PrintWriter(System.out);
        int t = scan.nextInt();
		while(t-->0){
		   int n = scan.nextInt();
           String str = scan.next();
           HashMap<Double,Integer> h = new HashMap<>();
           double count_d = 0,count_k = 0;
           double key_for_0 = 500000;
           double key  = 0;
           for(int i=0;i<n;i++){
               if(str.charAt(i)=='D')
               count_d++;
               else
               count_k++;
               if(count_k==0)
               key = key_for_0;
               else
               key = count_d/count_k;
               if(h.containsKey(key)){
                   Integer value = h.get(key);
                   pw.print(value+1+" ");
                   h.replace(key,value+1);
               }
               else{
                   pw.print(1+" ");
                   h.put(key,1);
               }
           }
           pw.println();
		   pw.flush();
		}
	}
}
