import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.StringTokenizer;

public class D {
	public static BufferedReader reader;
	public static PrintWriter writer;
	public static StringTokenizer tokenizer;
	
	public static String nextToken() {
		while (tokenizer == null || !tokenizer.hasMoreTokens()) {
			try {
				tokenizer = new StringTokenizer(reader.readLine());
			} catch (IOException e) {
			}
		}
		return tokenizer.nextToken();
	}

	public static int nextInt() {
		return Integer.parseInt(nextToken());
	}

	public static long nextLong() {
		return Long.parseLong(nextToken());
	}

	public static int[] nextIntArray(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = nextInt();
		}
		return a;
	}
	
	public static void solve() {
		int n = nextInt(), k = nextInt(), N = 1 << 14;
		HashSet<Integer> mods = new HashSet<>();
		for (int i = 0; i < N; i++) {
			if (BigInteger.valueOf(i).bitCount() == k) {
				mods.add(i);
			}
		}
		int[] a = new int[N];
		for (int i = 0; i < n; i++) {
			a[nextInt()]++;
		}
		if (k == 0) {
			long ans = 0;
			for (int i = 0; i < N; i++) {
				ans += a[i] * ((long) a[i] - 1) / 2;
			}
			writer.println(ans);
			return;
		}
		long ans = 0;
		for (int i = 0; i < N; i++) {
			if (a[i] == 0) {
				continue;
			}
			for (int j : mods) {
				ans += a[i ^ j] * a[i];
			}
		}
		writer.println(ans / 2);
	}

	public static void main(String[] args) throws IOException {
		reader = new BufferedReader(new InputStreamReader(System.in));
		writer = new PrintWriter(System.out);
		solve();
		reader.close();
		writer.close();
	}
}