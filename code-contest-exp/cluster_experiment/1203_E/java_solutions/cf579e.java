import java.io.*;
import java.util.*;

public class cf579e {
	static class InputReader{
		BufferedReader reader;
		StringTokenizer tokenizer;
		
		public InputReader(InputStream stream) {
			reader = new BufferedReader (new InputStreamReader(stream), 32768);
			tokenizer = null;
		}
		
		String next() {
			while(tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				}catch(IOException e) {
					throw new RuntimeException(e);
				}
			}
			
			return tokenizer.nextToken();
		}
		
		public int nextInt() {
			return Integer.parseInt(next());
		}
		
		public long nextLong() {
			return Long.parseLong(next());
		}
		
		public double nextDouble() {
			return Double.parseDouble(next());
		}
		
	}
	

	static InputReader r = new InputReader(System.in);
	static PrintWriter pw = new PrintWriter(System.out);
	
	public static void main (String[] args) {
		
		//CODE GOES HERE
		
		int n = r.nextInt();
		int[] a = new int[n];
		
		for(int i=0; i<n; i++) {
			a[i] = r.nextInt();
		}
		
		Arrays.sort(a);
		
		int last = 0;
		int out = 0;
		
		for(int i=0; i<n; i++) {
			if(a[i]+1>=last) {
				out++;
				last = Math.max(last+1, a[i]-1);
			}
		}
		
		pw.println(out);
		
		pw.close();
		
	}
	
}