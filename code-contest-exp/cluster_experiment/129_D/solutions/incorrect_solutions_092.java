import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	class SuffixArray {
		class Suffix implements Comparable<Suffix> {
			int index;
			int[] rank = new int[2];

			public int compareTo(Suffix o) {
				if (rank[0] != o.rank[0])
					return rank[0] - o.rank[0];
				return rank[1] - o.rank[1];
			}
		}

		String str;

		public SuffixArray(String str) {
			this.str = str;
		}

		public int[] buildSuffixArray() {

			int n = str.length();
			Suffix[] suffixes = new Suffix[n];
			int[] arr = new int[n];
			// create and store suffixes
			// Also stores rank of first two chars of suffix
			for (int i = 0; i < n; i++) {
				suffixes[i] = new Suffix();
				suffixes[i].index = i;
				suffixes[i].rank[0] = str.charAt(i) - 'a';
				suffixes[i].rank[1] = ((i + 1) < n) ? (str.charAt(i + 1) - 'a') : -1;
			}

			Arrays.sort(suffixes);

			int[] ind = new int[n]; // to map index in suffix[] to index in original
									// string
			for (int k = 4; k < 2 * n; k = k * 2) {
				// Assigning rank and index values to first suffix
				int rank = 0;
				int prev_rank = suffixes[0].rank[0];
				suffixes[0].rank[0] = rank;
				ind[suffixes[0].index] = 0;

				// Assigning rank to suffixes
				for (int i = 1; i < n; i++) {
					// If first rank and next ranks are same as that of previous
					// suffix in array, assign the same new rank to this suffix
					if (suffixes[i].rank[0] == prev_rank && suffixes[i].rank[1] == suffixes[i - 1].rank[1]) {
						prev_rank = suffixes[i].rank[0];
						suffixes[i].rank[0] = rank;
					} else // Otherwise increment rank and assign
					{
						prev_rank = suffixes[i].rank[0];
						suffixes[i].rank[0] = ++rank;
					}
					ind[suffixes[i].index] = i;
				}

				// Assign next rank to every suffix
				for (int i = 0; i < n; i++) {
					int nextindex = suffixes[i].index + k / 2;
					suffixes[i].rank[1] = (nextindex < n) ? suffixes[ind[nextindex]].rank[0] : -1;
				}

				// Sort the suffixes according to first k characters
				Arrays.sort(suffixes);
			}

			for (int i = 0; i < n; i++)
				arr[i] = suffixes[i].index;

			return arr;
		}

		public int[] kasai(int[] sa) {
			int n = str.length(), k = 0;
			int[] lcp = new int[n];
			int[] rank = new int[n];
			for (int i = 0; i < n; i++)
				rank[sa[i]] = i;
			for (int i = 0; i < n; i++) {
				if (rank[i] == n - 1) {
					k = 0;
					continue;
				}
				int j = sa[rank[i] + 1];
				while (i + k < n && j + k < n && str.charAt(i + k) == str.charAt(j + k)) {
					k++;
				}
				lcp[rank[i]] = k;
				k = (k == 0) ? 0 : k - 1;
			}
			return lcp;
		}

	}

	private void solve() throws IOException {
		String s = ns();
		SuffixArray obj = new SuffixArray(s);
		int SA[] = obj.buildSuffixArray();

		// Helper.tr(SA);
		int k = ni();
		for (int i = 0; i < SA.length; i++) {
			if (k <= s.length() - SA[i]) {
				out.println(s.substring(SA[i], SA[i] + k));
				break;
			}
			k -= s.length() - SA[i];
		}
		// out.println("WIN");
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