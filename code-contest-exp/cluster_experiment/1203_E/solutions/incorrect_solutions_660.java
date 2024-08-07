import java.io.*;
import java.util.*;

public class Solution {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), a[] = new int[n];
		int MAX = 150005;
		int[] cnt = new int[MAX];
		for (int i = 0; i < n; i++) {
			a[i] = sc.nextInt();
			cnt[a[i]]++;
		}
		int last = MAX;
		int ans = 0;
		for (int i = MAX - 1; i >= 0; i--) {
			if (cnt[i] == 0)
				continue;
			if (i + 1 < last) {
				cnt[i]--;
				ans++;
			}
			boolean dec=false;
			if (i > 1 && cnt[i] > 1 && cnt[i - 1] == 0) {
				cnt[i]--;
				last=i-1;
				ans++;
				dec=true;

			}
			if (cnt[i] > 0) {
				last=Math.min(i, last);
				ans++;
			}
			if(dec)
				i--;
		}
		out.println(ans);
		out.close();

	}

	static class Scanner {
		BufferedReader br;
		StringTokenizer st;

		Scanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		Scanner(String fileName) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(fileName));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		String nextLine() throws IOException {
			return br.readLine();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws NumberFormatException, IOException {
			return Long.parseLong(next());
		}

		double nextDouble() throws NumberFormatException, IOException {
			return Double.parseDouble(next());
		}

		boolean ready() throws IOException {
			return br.ready();
		}

	}

	static void sort(int[] a) {
		shuffle(a);
		Arrays.sort(a);
	}

	static void shuffle(int[] a) {
		int n = a.length;
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			int tmpIdx = rand.nextInt(n);
			int tmp = a[i];
			a[i] = a[tmpIdx];
			a[tmpIdx] = tmp;
		}
	}

}