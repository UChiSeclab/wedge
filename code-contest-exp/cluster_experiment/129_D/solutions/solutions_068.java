import java.io.*;
import java.util.*;

public class CF129D {
	static StringBuilder sb = new StringBuilder();
	static char[] cc;
	static int[] aa;
	static int[] kk = new int[26];
	static long[] ll = new long[26];
	static void search(int n, int k) {
		for (int c = 0; c < 26; c++)
			ll[c] = kk[c] = 0;
		for (int i = 0; i < n; i++) {
			int j = aa[i];
			int c = cc[j] - 'a';
			kk[c]++;
			ll[c] += cc.length - j;
		}
		for (int c = 0; c < 26; c++) {
			if (k <= ll[c]) {
				int m = 0;
				for (int i = 0; i < n; i++) {
					int j = aa[i];
					if (cc[j] - 'a' == c && j + 1 < cc.length)
						aa[m++] = j + 1;
				}
				sb.append((char) ('a' + c));
				if (k > kk[c])
					search(m, k - kk[c]);	// m <= kk[c]
				return;
			}
			k -= ll[c];
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		cc = br.readLine().toCharArray();
		int n = cc.length;
		int k = Integer.parseInt(br.readLine());
		if (k > (long) n * (n + 1) / 2) {
			System.out.println("No such line.");
			return;
		}
		aa = new int[n];
		for (int i = 0; i < n; i++)
			aa[i] = i;
		search(n, k);	// O(n + k) amortized
		System.out.println(sb);
	}
}
