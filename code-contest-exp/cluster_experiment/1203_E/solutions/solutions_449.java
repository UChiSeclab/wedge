import java.io.*;
import java.util.*;

public class Main {
	
	static class Scanner
	{
	    BufferedReader buf;
	    StringTokenizer tok;
	    Scanner()
	    {
	        buf = new BufferedReader(new InputStreamReader(System.in));
	    }
	    
	    boolean hasNext()
	    {
	        while(tok == null || !tok.hasMoreElements()) 
	        {
	            try
	            {
	                tok = new StringTokenizer(buf.readLine());
	            } 
	            catch(Exception e) 
	            {
	                return false;
	            }
	        }
	        return true;
	    }
	    
	    String next()
	    {
	        if(hasNext()) return tok.nextToken();
	        return null;
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
	    
	}
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[] arr = new int[150001];
		for(int i = 0; i < n; i++)
			arr[sc.nextInt()-1]++;
		int count = 0, pre = 0, end = 0, num = 0;
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] != 0) {
				if(arr[i] > 1 && num < 2) {
					num += arr[i]-1;
					num = Math.min(2, num);
				}
				if(end != 0) {
					pre = end;
					end = 0;
				}
				count++;
			}
			else {
				if(num > 0 && pre > 0) {
					count++;
					num--;
					pre = 0;
				}
				if(num > 0) {
					count++;
					num = 0;
				}
				else end++;
			}
		}
		out.println(count);
		out.close();
	}
	
}