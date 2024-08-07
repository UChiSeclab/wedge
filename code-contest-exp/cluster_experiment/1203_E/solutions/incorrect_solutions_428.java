import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	static StreamTokenizer st = new StreamTokenizer(new BufferedInputStream(System.in));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static PrintWriter pr = new PrintWriter(new BufferedOutputStream(System.out));
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
//		long tic = System.currentTimeMillis();
//		long toc = System.currentTimeMillis();
		int n = nextInt();
		long arr[] = new long[150005];
		for (int i = 0; i < n; i++) {
			arr[nextInt()]++;
		}
		int ans = 0;
		for (int i = 1; i < 150003; i++) {
			if (arr[i] >= 1) {
				ans++;
			} else if (arr[i] == 0) {
				if ((arr[i - 1] > 1)) {
					ans++;
				} else if (arr[i + 1] > 0) {
					ans++;
					arr[i + 1]--;
				}
			}
		}
		System.out.println(ans);
		
//		System.out.println("Elapsed time: " + (toc - tic) + " ms");
	}

	static long pow(long a, long b, long mod) {
		if (b == 0)
			return 1;
		if (b == 1)
			return a;
		if ((b & 1) == 0)
			return pow(a * a % mod, b >> 1, mod);
		else
			return a * pow(a * a % mod, b >> 1, mod) % mod;
	}

	static long gcd(long a, long b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	static int nextInt() {
		try {
			st.nextToken();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (int) st.nval;
	}

	static double nextDouble() {
		try {
			st.nextToken();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return st.nval;
	}

	static String next() {
		try {
			st.nextToken();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return st.sval;
	}

	static long nextLong() {
		try {
			st.nextToken();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (long) st.nval;
	}
}