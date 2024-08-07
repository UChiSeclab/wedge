import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Main {

	char[] str;
	int[] index;
	int[] count = new int[26];
	long[] sum = new long[26];
	StringBuffer sb = new StringBuffer();

	void search(int n, int k) {
		for (int c = 0; c < 26; c++)
			sum[c] = count[c] = 0;
		for (int i = 0; i < n; i++) {
			int j = index[i];
			int c = str[j] - 'a';
			count[c]++;
			sum[c] += str.length - j;
		}
//		Helper.tr(n, k, index, count, sum);
		for (int c = 0; c < 26; c++) {
			if (k <= sum[c]) {
				int m = 0;
				for (int i = 0; i < n; i++) {
					int j = index[i];
					if (str[j] - 'a' == c && j + 1 < str.length)
						index[m++] = j + 1;
				}
				sb.append((char) ('a' + c));
				if (k > count[c])
					search(m, k - count[c]);
				return;
			}
			k -= sum[c];
		}
	}

	private void solve() throws IOException {
		str = ns().toCharArray();
		int n = str.length;
		index = new int[n];
		for (int i=0; i<n;i++)
			index[i]= i;
		int k = ni();
		if (k > (long) n * (n + 1) / 2) {
			System.out.println("No such line.");
			return;
		}
		search(n, k);
		out.println(sb.toString());
		out.close();
	}

	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok;

	private int[][] na(int n) throws IOException {
		int[][] a = new int[n][2];
		for (int i = 0; i < n; i++) {
			a[i][0] = ni();
			a[i][1] = i;
		}
		return a;
	}

	String ns() throws IOException {
		while (!tok.hasMoreTokens()) {
			tok = new StringTokenizer(in.readLine(), " ");
		}
		return tok.nextToken();
	}

	int ni() throws IOException {
		return Integer.parseInt(ns());
	}

	long nl() throws IOException {
		return Long.parseLong(ns());
	}

	double nd() throws IOException {
		return Double.parseDouble(ns());
	}

	String[] nsa(int n) throws IOException {
		String[] res = new String[n];
		for (int i = 0; i < n; i++) {
			res[i] = ns();
		}
		return res;
	}

	int[] nia(int n) throws IOException {
		int[] res = new int[n];
		for (int i = 0; i < n; i++) {
			res[i] = ni();
		}
		return res;
	}

	long[] nla(int n) throws IOException {
		long[] res = new long[n];
		for (int i = 0; i < n; i++) {
			res[i] = nl();
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		tok = new StringTokenizer("");
		Main main = new Main();
		main.solve();
		out.close();
	}
}