import java.io.*;
import java.util.*;
import java.math.*;
 
public class Main {
	
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
	
	static int n;
	static int[] arr, ans;
	
	public static void main(String args[]) throws Exception {
		FastReader cin = new FastReader();

		n = cin.nextInt();
		arr = new int[n];
		ans = new int[n];
		
		for (int i = 0; i < n; i++) {
			arr[i] = cin.nextInt();
		}
		
		int cur = arr[0];
		int count = 0;
		for (int i = 0; i < n; i++) {
			if (cur == arr[i]) {
				ans[count]++;
			} else {
				count++;
				ans[count] = 1;
				cur = arr[i];
			}
		}
		
		int max = 0;
		for (int i = 1; i <= count; i++) {
			int s = Math.min(ans[i], ans[i-1]);
			max = Math.max(max, s);
		}
		System.out.println(max*2);
	}
}