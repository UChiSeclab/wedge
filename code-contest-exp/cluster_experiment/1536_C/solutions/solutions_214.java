import java.io.*;
import java.util.*;
import java.awt.Point;
public class c {

	public static void main(String[] args)throws IOException {
		FastScanner fs = new FastScanner();
		fs.checkSystem();
		StringBuilder ans = new StringBuilder("");
		int T = fs.nextInt();
		for (int tt = 0; tt < T; tt++) {
			int n = fs.nextInt();
			String s = fs.next();
			Map<Point, Integer> mp = new HashMap<Point, Integer>();
			int d = 0, k = 0;

			for (int i = 0; i < n; i++) {
				if (s.charAt(i) == 'D')d++;
				else
					k++;
				int D = d / gcd(d, k);
				int K = k / gcd(d, k);
				if (mp.containsKey(new Point(D, K)) == true) {
					mp.put(new Point(D, K), mp.get(new Point(D, K)) + 1);
				} else
					mp.put(new Point(D, K), 1);
				ans.append((mp.get(new Point(D, K)) + " "));
			}
			ans.append("\n");
		}
		System.out.print(ans);
	}

	static void sort(int[] a) {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : a) l.add(i);
		Collections.sort(l);
		for (int i = 0; i < a.length; i++) a[i] = l.get(i);
	}
	static int gcd(int a, int b) {
		if (b == 0) return a;
		return gcd(b, a % b);
	}

	static class FastScanner {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer("");
		void checkSystem() {
			if (System.getProperty("ONLINE_JUDGE") == null) {
				try {
					System.setOut(new PrintStream(
					                  new FileOutputStream("output.txt")));
					br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
				} catch (Exception e) {
				}
			}
		}
		String next() {
			while (!st.hasMoreTokens())
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}
		int[] readArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++) a[i] = nextInt();
			return a;
		}
		long nextLong() {
			return Long.parseLong(next());
		}
		double nextDouble() {
			return Double.parseDouble(next());
		}
	}


}
class Pair<T, V> {
	private T first; private V second;
	public Pair(T first, V second) {
		this.first = first;
		this.second = second;
	}
	public T getFirst() {
		return this.first;
	}
	public V getSecond() {
		return this.second;
	}
	public String toString() {
		return "{" + this.first + "," + this.second + "}";
	}
}