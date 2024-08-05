import java.io.*;
import java.util.*;

public class Problem_E {
	static final int INF = Integer.MAX_VALUE / 2;
	static int N;
	static int[] A, B;

	public static int solve(int x) {
		if (B[x] != -1) {
			return B[x];
		}
		B[x] = INF;
		if (x - A[x] >= 0) {
			if (Math.abs(A[x] - A[x - A[x]]) % 2 == 1) {
				return B[x] = 1;
			}
			B[x] = Math.min(B[x], solve(x - A[x]) + 1);
		}
		if (x + A[x] < N) {
			if (Math.abs(A[x] - A[x + A[x]]) % 2 == 1) {
				return B[x] = 1;
			}
			B[x] = Math.min(B[x], solve(x + A[x]) + 1);
		}
		return B[x];
	}

	public static void main(String[] args) {
		InputReader in = new InputReader();
		StringBuilder out = new StringBuilder();

		N = in.nextInt();
		A = new int[N];
		for (int i = 0; i < N; i++) {
			A[i] = in.nextInt();
		}
		B = new int[N];
		Arrays.fill(B, -1);
		for (int i = 0; i < N; i++) {
			solve(i);
			out.append(B[i] == INF ? -1 : B[i]).append(' ');
		}
		out.setCharAt(out.length() - 1, '\n');

		System.out.print(out);
	}

	static class InputReader {
		public BufferedReader reader;
		public StringTokenizer st;

		public InputReader() {
			reader = new BufferedReader(new InputStreamReader(System.in));
		}

		public String next() {
			while (st == null || !st.hasMoreTokens()) {
				st = new StringTokenizer(nextLine());
			}
			return st.nextToken();
		}

		public String nextLine() {
			try {
				return reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}
	}
}
