import java.io.*;
import java.util.*;

public class Boxers {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static PrintWriter pw = new PrintWriter(System.out);
	
	public static void main(String[] args) throws IOException {
		int n = readInt();
		
		Integer[] a = new Integer[n];
		for (int i = 0; i < n; i ++) a[i] = readInt();
		Arrays.sort(a);
		int ans = 0;
		boolean[] vis = new boolean[150002];
		for (int i = 0; i < n; i ++) {
			if (a[i] != 1 && !vis[a[i] - 1]) {
				vis[a[i] - 1] = true;
				ans ++;
			} else if (!vis[a[i]]) {
				vis[a[i]] = true;
				ans ++;
			} else if (!vis[a[i] + 1]) {
				vis[a[i] + 1] = true;
				ans ++;
			}
		}
		pw.println(ans);
		pw.close();
	}

	static String next() throws IOException {
		while (st == null || !st.hasMoreTokens()) 
			st = new StringTokenizer(br.readLine().trim());
		return st.nextToken();
	}
	static long readLong() throws IOException {
		return Long.parseLong(next());
	}
	static int readInt() throws IOException {
		return Integer.parseInt(next());
	}
	static double readDouble() throws IOException {
		return Double.parseDouble(next());
	}
	static char readCharacter() throws IOException {
		return next().charAt(0);
	}
	static String readLine() throws IOException {
		return br.readLine().trim();
	}
}