import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class diluc_and_kaeya {

	public static int gcd(int a,int b) {
		
		while(b!=0) {
			int tmp = a;
			a = b;
			b = tmp%b;
		}
		
		return a;
	}
	
	public static void solve(int n,String s,PrintWriter pw) {
		
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
		
		int ctr = 0;
		
		int[] ans = new int[n];
		
		for(int x=0;x<n;x++) {
			
			if(s.charAt(x)=='D') {
				ctr++;
			}
				
			int div = gcd(ctr,x+1-ctr);
			int A = ctr/div;
			int B = (x+1-ctr)/div;
			
			int vA = Math.min(A, B);
			int vB = Math.max(A, B);
			
			String val = vA+" "+vB;
			
			int current = hm.getOrDefault(val,0)+1;
			
			ans[x] = current;

			hm.put(val, current);
		}
		
		for(int x=0;x<n;x++) {
			pw.print(ans[x]+" ");
		}
		
		pw.println();
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
			
		R_dak.init(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		
		int t = R_dak.nextInt();
		for(int x=0;x<t;x++) {
			
			int n = R_dak.nextInt();	
			String s = R_dak.next();
			
			solve(n,s,pw);
		}
		
		pw.close();
	}

}

/** Class for buffered reading int and double values */

class R_dak {
	static BufferedReader reader;
	static StringTokenizer tokenizer;

	/** call this method to initialize reader for InputStream */
	static void init(InputStream input) {
		reader = new BufferedReader(new InputStreamReader(input));
		tokenizer = new StringTokenizer("");
	}

	/** get next word */
	static String next() throws IOException {
		while (!tokenizer.hasMoreTokens()) {
			// TODO add check for eof if necessary
			tokenizer = new StringTokenizer(reader.readLine());
		}
		return tokenizer.nextToken();
	}

	static int nextInt() throws IOException {
		return Integer.parseInt(next());
	}

	static double nextDouble() throws IOException {
		return Double.parseDouble(next());
	}
	
	static long nextLong() throws IOException {
		return Long.parseLong(next());
	}
}


