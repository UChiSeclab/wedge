import java.util.*;
import java.io.*;

public class _1450_D {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		int t = Integer.parseInt(in.readLine());
		for(int i = 0; i < t; i++) {
			int n = Integer.parseInt(in.readLine());
			int[] a = new int[n];
			int[] freqs = new int[n + 1];
			StringTokenizer line = new StringTokenizer(in.readLine());
			for(int j = 0; j < n; j++) {
				a[j] = Integer.parseInt(line.nextToken());
				if(a[j] > 0 && a[j] <= n) freqs[a[j]]++;
			}
			int firstworks = freqs[1] > 0 ? 1 : 0;
			int lastworks = 1;
			for(int j = 1; j <= n; j++) {
				if(freqs[j] != 1) {
					lastworks = 0;
					break;
				}
			}
			int lpointer = 0;
			int rpointer = n - 1;
			int fail = n - 1;
			for(int j = 1; j < n; j++) {
				if(a[lpointer] == j) {
					freqs[j]--;
					lpointer++;
				}else if(a[rpointer] == j) {
					freqs[j]--;
					rpointer--;
				}else {
					fail = j;
					break;
				}
				if(freqs[j + 1] <= 0 || freqs[j] > 0) {
					fail = j;
					break;
				}
			}
			StringBuilder sb = new StringBuilder();
			if(firstworks == 0) {
				for(int j = 0; j < n; j++) {
					sb.append(0);
				}
				out.println(sb.toString());
			}else {
				sb.append(lastworks);
				for(int j = 2; j < n - fail + 1; j++) {
					sb.append(0);
				}
				for(int j = n - fail + 1; j <= n; j++) {
					sb.append(1);
				}
				out.println(sb.toString());
			}
		}
		in.close();
		out.close();
	}
}