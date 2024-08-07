import java.util.*;
import java.io.*;
public class C {

	public static void main(String[] args) {
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		StringBuilder sb = new StringBuilder();
		while(T-->0) {
			int n = sc.nextInt();
			char[] s = sc.next().toCharArray();
			HashMap<String, Integer> c = new HashMap<>();
			int a = 0;
			int[] res = new int[n];
			for(int i = 0; i < n; i++) {
				if(s[i] == 'D') a++;
				int g = gcd(a, i+1);
				String p = (a/g)+" "+(i+1)/g;
				if(!c.containsKey(p)) c.put(p, 1);
				else c.put(p, c.get(p)+1);
				res[i] = c.get(p);
			}
			for(int i = 0; i < n; i++) {
				sb.append(res[i]+" ");
			}
			sb.replace(sb.length()-1, sb.length(), "\n");
		}
		PrintWriter pw = new PrintWriter(System.out);
		pw.print(sb.toString());
		pw.flush();

	}
	static int gcd(int a, int b) {
		if(b > a) return gcd(b, a);
		else if(b == 0) return a;
		else return gcd(b, a % b);
	}
	
	static class FastScanner {
		public BufferedReader reader;
		public StringTokenizer tokenizer;
		public FastScanner() {
			reader = new BufferedReader(new InputStreamReader(System.in), 32768);
			tokenizer = null;
		}
		public String next() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
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
		public String nextLine() {
			try {
				return reader.readLine();
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
