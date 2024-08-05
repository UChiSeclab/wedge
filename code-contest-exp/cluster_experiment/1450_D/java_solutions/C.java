import java.util.*;
import java.io.*;
public class C {

	public static void main(String[] args) {
		FastScanner sc = new FastScanner();
		int t = sc.nextInt();
		StringBuilder sb = new StringBuilder();
		while(t-->0) {
			int n = sc.nextInt();
			int[] arr = new int[n];
			ArrayList<Integer>[] poss = new ArrayList[n+1];
			for(int i = 1; i <= n; i++){
				poss[i] = new ArrayList<>();
			}
			for(int i = 0; i < n; i++){
				arr[i] = sc.nextInt();
				poss[arr[i]].add(i);
			}
			if(poss[1].isEmpty()) {
				char[] ans = new char[n];
				Arrays.fill(ans, '0');
				sb.append(ans);
				sb.append("\n");
				continue;
			}
			SegmentTree st = new SegmentTree(arr);
			int[] life = new int[n+1];
			for(int i = 1; i <= n; i++){
				for(int p: poss[i]) {
					int a1 = -1, b1 = p;
					while(b1 - a1 > 1) {
						int c = (a1 + b1)/2;
						if(st.rmq(c, p-1) < arr[p]) a1 = c;
						else b1 = c;
					}
					int a2 = p, b2 = n;
					while(b2 - a2 > 1) {
						int c = (a2 + b2)/2;
						if(st.rmq(p+1, c) < arr[p]) b2 = c;
						else a2 = c;
					}
					life[i] = Math.max(life[i], a2-a1);
				}
			}
			int[] d = new int[n+2];
			for(int i = 1; i <= n; i++){
				if(!poss[i].isEmpty()) {
					d[0]++; d[life[i]]--;
				}
			}
			int[] dacc = new int[n];
			for(int i = 0; i < n; i++){
				if(i == 0) dacc[i] = d[i];
				else dacc[i] = dacc[i-1] + d[i];
			}
			char[] ans = new char[n];
			for(int i = 0; i < n; i++){
				if(dacc[i] == n-i) ans[i] = '1';
				else ans[i] = '0';
			}
			sb.append(ans);
			sb.append("\n");
		}
		PrintWriter pw = new PrintWriter(System.out);
		pw.println(sb.toString().trim());
		pw.flush();
	}
	
	static class SegmentTree {
		private int[] st; private int[] A;
		private int n;
		private int left (int p) { return p << 1; }
		private int right(int p) { return (p << 1) + 1; }
		
		static int neutral = Integer.MAX_VALUE;					//MIN
		static int op(int x, int y) { return Math.min(x, y); }

		private void build(int p, int L, int R) {
			if (L == R)
				st[p] = A[L];
			else {
				build(left(p) , L              , (L + R) / 2);
				build(right(p), (L + R) / 2 + 1, R          );
				int v1 = st[left(p)], v2 = st[right(p)];
				st[p] = op(v1, v2);
			} }

		private int rmq(int p, int L, int R, int i, int j) {
			if (i >  R || j <  L) return neutral;
			if (L >= i && R <= j) return st[p];

			int v1 = rmq(left(p) , L              , (L+R) / 2, i, j);
			int v2 = rmq(right(p), (L+R) / 2 + 1, R          , i, j);

			return op(v1, v2);
		}

		private int update_point(int p, int L, int R, int idx, int new_value) {
			int i = idx, j = idx;
			if (i > R || j < L)
				return st[p];

			if (L == i && R == j) {
				A[i] = new_value;
				return st[p] = A[L];											// += IF DELTA
			}

			int v1, v2;
			v1 = update_point(left(p) , L              , (L + R) / 2, idx, new_value);
			v2 = update_point(right(p), (L + R) / 2 + 1, R          , idx, new_value);

			return st[p] = op(v1, v2);
		}

		public SegmentTree(int[] _A) {
			A = _A; n = A.length;
			st = new int[4 * n];
			for (int i = 0; i < 4 * n; i++) st[i] = 0;
			build(1, 0, n - 1);
		}

		public int rmq(int i, int j) { return rmq(1, 0, n - 1, i, j); }

		public int update_point(int idx, int new_value) {
			return update_point(1, 0, n - 1, idx, new_value); }
		
		public String toString() {
			return Arrays.toString(A);
		}
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
