// package CodeForces;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Stack;

public class GlobalRound12D {

	public static int[] tree;
	public static int oo = (int)1e9;
	
	public static void Build(int treeart, int end, int index, int[] ranges) {
		if(treeart == end) {
			tree[index] = ranges[treeart];
			return;
		}
		int mid = (treeart + end)>>1;
		Build(treeart, mid, index<<1, ranges);
		Build(mid + 1, end, index<<1|1, ranges);
		tree[index] = Integer.min(tree[index<<1|1], tree[index<<1]);
	}
	
	public static void Update(int treeart, int end, int index, int idx) {
		if(treeart > idx || idx > end) {
			return;
		}
		if(treeart == end) {
			tree[index] = oo;
			return;
		}
		int mid = (treeart + end)>>1;
		Update(treeart, mid, index<<1, idx);
		Update(mid + 1, end, index<<1|1, idx);
		tree[index] = Integer.min(tree[index<<1|1], tree[index<<1]);
	}
	
	public static int Query() {
		return tree[1];
	}
	
	public static void solve() {
		int t = s.nextInt();
		while(t-- > 0) {
			int n = s.nextInt();
			int[] arr = s.nextIntArray(n);
			int[] r = new int[n];
			int[] l = new int[n];
			Stack<Integer> st = new Stack<Integer>();
			st.push(0);
			for(int i = 1; i < n; i++){
				while(!st.empty() && arr[i] < arr[st.peek()]){
					int val = st.peek();
					st.pop();
					r[val] = i - 1;
				}
				st.push(i);
			}
			while(!st.empty()){
				int val = st.peek();
				st.pop();
				r[val] = n - 1;
			}
			st.push(n - 1);
			for(int i = n - 2; i >= 0; i--){
				while(!st.empty() && arr[i] < arr[st.peek()]){
					int val = st.peek();
					st.pop();
					l[val] = i + 1;
				}
				st.push(i);
			}
			while(!st.empty()){
				int val = st.peek();
				st.pop();
				l[val] = 0;
			}
			int[] ranges = new int[n + 1];
			ranges[0] = oo;
			for(int i = 0; i < n; i++) {
				ranges[arr[i]] = r[i] - l[i] + 1;
			}
			tree = new int[4 * (n + 1) + 1];
			Build(0, n, 1, ranges);
			int change = 1;
			StringBuilder ans = new StringBuilder();
			for(int i = n; i >= 1; i--) {
				int q = Query();
				if(q >= change) {
					ans.append(1);
				}else {
					ans.append(0);
				}
				Update(0, n, 1, i);
				change++;
			}
			out.println(ans);
		}
	}

	public static void main(String[] args) {
		new Thread(null, null, "Thread", 1 << 27) {
			public void run() {
				try {
					out = new PrintWriter(new BufferedOutputStream(System.out));
					s = new FastReader(System.in);
					solve();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}.start();
	}

	public static PrintWriter out;
	public static FastReader s;

	public static class FastReader {

		private InputStream stream;
		private byte[] buf = new byte[4096];
		private int curChar, snumChars;

		public FastReader(InputStream stream) {
			this.stream = stream;
		}

		public int read() {
			if (snumChars == -1) {
				throw new InputMismatchException();
			}
			if (curChar >= snumChars) {
				curChar = 0;
				try {
					snumChars = stream.read(buf);
				} catch (IOException E) {
					throw new InputMismatchException();
				}
			}
			if (snumChars <= 0) {
				return -1;
			}
			return buf[curChar++];
		}

		public int nextInt() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			int number = 0;
			do {
				number *= 10;
				number += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return number * sgn;
		}

		public long nextLong() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			long sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			long number = 0;
			do {
				number *= 10L;
				number += (long) (c - '0');
				c = read();
			} while (!isSpaceChar(c));
			return number * sgn;
		}

		public int[] nextIntArray(int n) {
			int[] arr = new int[n];
			for (int i = 0; i < n; i++) {
				arr[i] = this.nextInt();
			}
			return arr;
		}

		public long[] nextLongArray(int n) {
			long[] arr = new long[n];
			for (int i = 0; i < n; i++) {
				arr[i] = this.nextLong();
			}
			return arr;
		}

		public String next() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isSpaceChar(c));
			return res.toString();
		}

		public String nextLine() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isEndofLine(c));
			return res.toString();
		}

		public boolean isSpaceChar(int c) {
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public boolean isEndofLine(int c) {
			return c == '\n' || c == '\r' || c == -1;
		}

	}

	
}
