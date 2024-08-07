import static java.lang.System.exit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.StringTokenizer;


public class Task4 {
	//correct but slow, faster in task5;
	static void solve() throws IOException{
		int n = scanInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = scanInt();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append('1');
		}
		
		//first min to the left;
		int[] left = new int[n];
		left[0] = -1;
		Stack<Integer> st = new Stack<Integer>();
		st.push(0);
		for (int i = 1; i < n; i++) {
			while(!st.isEmpty() && arr[st.peek()] > arr[i]) {
				st.pop();
			}
			if (!st.isEmpty()) {
				left[i] = st.peek();
			}else {
				left[i] = -1;
			}
			st.push(i);
		}
		//first min to the right;
		int[] right = new int[n];
		right[n-1] = n;
		st = new Stack<Integer>();
		st.push(n-1);
		for (int i = n-2; i >= 0; i--) {
			while(!st.isEmpty() && arr[st.peek()] >= arr[i]) {
				st.pop();
			}
			if (!st.isEmpty()) {
				right[i] = st.peek();
			}else {
				right[i] = n;
			}
			st.push(i);
		}
		int[][] info = new int[n+1][2];
		int start = 1;
		for (int i = 0; i < n; i++) {
			int distLeft = i - left[i] - 1;
			int distRight = right[i] - i - 1;
			int dist = distLeft + distRight;
			if (info[arr[i]][0] > 0) {
				sb.setCharAt(0, '0');
				if (dist == info[arr[i]][1])  sb.setCharAt(dist, '0');
				dist = Math.max(dist, info[arr[i]][1]);
				distLeft = distRight = dist;
			}
			if (Math.min(distLeft, distRight) > 0) {
				for (int j = start; j < dist; j++) {
					sb.setCharAt(j, '0');
				}
				start = Math.max(start, dist - 1);
			}
			info[arr[i]][0] = 1;
			info[arr[i]][1] = dist;
		}
		for (int i = 1; i < n+1; i++) {
			if (info[i][0] == 0) {
				for (int j = 0; j < n - i + 1; j++) {
					sb.setCharAt(j, '0');
				}
				break;
			}
		}
		String answ = sb.toString();
		
		out.println(answ);
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