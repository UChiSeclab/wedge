import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Locale;
import java.util.StringTokenizer;

public class Solution implements Runnable {
	
	private PrintStream out;
	private BufferedReader in;
	private StringTokenizer st;
	
	public void solve() throws IOException {
		long time0 = System.currentTimeMillis();
		
		int t = nextInt();
		for (int test = 1; test <= t; test++) {
			int n = nextInt();
			int[] a = new int[n];
			for (int i = 0; i < n; i++) {
				a[i] = nextInt() - 1;
			}
			boolean[] answer = solve(n, a);
			for (int i = 0; i < n; i++) {
				if (answer[i]) {
					out.print('1');
				} else {
					out.print('0');
				}
			}
			out.println();
		}
		
		System.err.println("time: " + (System.currentTimeMillis() - time0));
	}
	
	private boolean[] solve(int n, int[] a) {
		boolean[] result = new boolean[n];
		int[] cnt = new int[n];
		for (int i = 0; i < n; i++) {
			cnt[a[i]]++;
		}
		int good = n + 1;
		int left = 0;
		int right = n - 1;
		for (int i = 0; i < n - 1; i++) {
			if (cnt[i] == 0) {
				break;
			}
			good = right - left + 1;
			if (cnt[i] > 1) {
				break;
			}
			if (a[left] == i) {
				left++;
			} else if (a[right] == i) {
				right--;
			} else {
				break;
			}
		}
		for (int i = good; i <= n; i++) {
			result[i - 1] = true;
		}
		result[0] = isPerm(n, cnt);
		return result;
	}

	private boolean isPerm(int n, int[] cnt) {
		for (int i = 0; i < n; i++) {
			if (cnt[i] != 1) {
				return false;
			}
		}
		return true;
	}

	public double nextDouble() throws IOException {
		return Double.parseDouble(next());
	}
	
	public long nextLong() throws IOException {
		return Long.parseLong(next());
	}
	
	public int nextInt() throws IOException {
		return Integer.parseInt(next());
	}
	
	public String next() throws IOException {
		while (!st.hasMoreTokens()) {
			String line = in.readLine();
			if (line == null) {
				return null;
			}
			st = new StringTokenizer(line);
		}
		return st.nextToken();
	}
	
	@Override
	public void run() {
		try {
			solve();
			out.close();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	
	public Solution(String name) throws IOException {
		Locale.setDefault(Locale.US);
		if (name == null) {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintStream(new BufferedOutputStream(System.out));
		} else {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(name + ".in")));
			out = new PrintStream(new BufferedOutputStream(new FileOutputStream(name + ".out")));
		}
		st = new StringTokenizer("");
	}
	
	public static void main(String[] args) throws IOException {
		new Thread(new Solution(null)).start();
	}
}
