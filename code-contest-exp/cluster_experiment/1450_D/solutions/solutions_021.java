import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws Exception {
		new Main();
	}
	
	InputReader ir;
	public Main()  throws Exception {
		ir = new InputReader(System.in);
		int tc = ir.nextInt();
		while(tc-->0)
		solve();
	}

	
	private void solve() {
		int n = ir.nextInt();
		int[] a = new int[n+2];
		for(int i=1; i<=n; i++) a[i] = ir.nextInt();
		a[0] = a[n+1] = 0;
		Stack<Integer> stack = new Stack();
		stack.add(0);
		int[] L = new int[n+1];
		for(int i=1; i<=n; i++) {
			while(!stack.isEmpty() && a[stack.peek()] >= a[i]) stack.pop();
			L[i] = stack.peek()+1;
			stack.add(i);
		}
		int[] R = new int[n+1];
		stack.clear();
		stack.add(n+1);
		for(int i=n; i>=1; i--) {
			while(!stack.isEmpty() && a[stack.peek()] >= a[i]) stack.pop();
			R[i] = stack.peek()-1;
			stack.add(i);
		}
		
		int[] arr = new int[n+1];
	//	for(int i=1; i<=n; i++) arr[i] = Integer.MAX_VALUE; 
		for(int i=1; i<=n; i++) {
			if(arr[a[i]] == 0) arr[a[i]] = R[i]-L[i]+1;
			else arr[a[i]] = Math.min(arr[a[i]], R[i]-L[i]+1);
		}
		for(int i=2; i<=n; i++) arr[i] = Math.min(arr[i], arr[i-1]);
	//	for(int i=1; i<=n; i++) System.out.println(arr[i]);
		StringBuilder ans = new StringBuilder();
		boolean isFalse = false;
		for(int i=1; i<=n; i++) {
			ans.append((arr[n-i+1]) >= i? "1":"0");
		}
		System.out.println(ans.toString());
	}
	
	
	class InputReader {
		private BufferedReader reader;
		private StringTokenizer tokenizer;
		
		public InputReader(InputStream is) {
			reader = new BufferedReader(new InputStreamReader(is), 8*2014);
			tokenizer = null;
		}
		
		public String next() {
			while(tokenizer == null || !tokenizer.hasMoreElements()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
					throw new RuntimeException();
				}
			}
			return tokenizer.nextToken();
		}
		
		public Integer nextInt() {
			return Integer.parseInt(next());
		}
	}
}
