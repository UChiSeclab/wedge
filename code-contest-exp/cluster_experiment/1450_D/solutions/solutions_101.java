import static java.lang.System.exit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;


public class Task5 {
	
	static void solve() throws IOException{
		int n = scanInt();
		int[] lastPos = new int[n];
		int[] cnt = new int[n];
		for (int i = 0; i < n; i++) {
			int val = scanInt() - 1;
			lastPos[val] = i;
			cnt[val]++;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append('1');
		for(int i = 1; i < n; i++) {
			sb.append('0');
		}
		int left = 0;
		int right = n - 1;
		for(int i = 0; i < n; i++) {
			if (cnt[i] == 0) break;
			sb.setCharAt(n - 1 - i, '1');
			if(cnt[i] > 1 || (lastPos[i] != left && lastPos[i] != right)) break;
			if (lastPos[i] == left) {
				left++;
			}else {
				right--;
			}
		}
		
		for(int i = 0; i < n; i++) {
			if (cnt[i] == 0) {
				sb.setCharAt(0, '0');
				break;
			}
		}
		
		out.println(sb.toString());
	}
	
	static int scanInt() throws IOException{
		return Integer.parseInt(scanString());
	}
	
	static long scanLong() throws IOException{
		return Long.parseLong(scanString());
	}
	
	static String scanString() throws IOException{
		while(tok == null || !tok.hasMoreTokens()) {
			tok = new StringTokenizer(in.readLine());
		}
		return tok.nextToken();
	}
	
	static void printCase() {
		out.print("Case #" + test + ": ");
	}

	static void printlnCase() {
		out.println("Case #" + test + ":");
	}
	
	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok; 
	static int test;
	
	public static void main(String[] args) throws IOException{
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			int tests = scanInt();
			for(test = 1; test <= tests; test++) {
				solve();
			}
			in.close();
			out.close();
		}catch(Throwable e) {
			e.printStackTrace();
			exit(1);
		}
	}
}