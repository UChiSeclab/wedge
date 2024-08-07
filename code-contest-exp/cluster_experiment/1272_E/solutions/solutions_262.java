import java.util.*;
import java.io.*;
public class e {
	static int n;
	static int[] arr, d;
	static boolean[] seen;
	static ArrayDeque[] backEdges;
	public static void main(String[] args) {
		FS sc = new FS();
		n = sc.nextInt();
		arr = new int[n];
		for(int i = 0; i < n; ++i) arr[i] = sc.nextInt();
		
		backEdges = new ArrayDeque[n];
		for(int i = 0; i < n; ++i) backEdges[i] = new ArrayDeque<>();
		for(int i = 0; i < n; ++i) {
			int ai = i - arr[i], bi = i + arr[i];
			if(ai >= 0) backEdges[ai].add(i);
			if(bi < n) backEdges[bi].add(i);
		}
		
		d = new int[n];
		seen = new boolean[n];
		Arrays.fill(d, Integer.MAX_VALUE / 2);
		ArrayDeque<Integer> ad = new ArrayDeque<>();
		for(int i = 0; i < n; ++i) {
			int ai = i - arr[i], bi = i + arr[i];
			if((ai >= 0 && arr[i] % 2 != arr[ai] % 2) || (bi < n && arr[i] % 2 != arr[bi] % 2)) {
				d[i] = 1;
				ad.add(i);
				seen[i] = true;
			}
		}
		
		while(!ad.isEmpty()) {
			int i = ad.poll();
			
			for(int j : (ArrayDeque<Integer>) backEdges[i]) {
				if(!seen[j]) {
					d[j] = d[i] + 1;
					ad.add(j);
					seen[j] = true;
				}
			}
		}
		
		for(int i = 0; i < n; ++i) {
			int out = d[i];
			if(out > n * 3) out = -1;
			System.out.print(out + " ");
		}
		System.out.println();
	}
	static class FS {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");
		String next() {
			while(!st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch(Exception e) {}
			}
			return st.nextToken();
		}
		int nextInt() {
			return Integer.parseInt(next());
		}
		long nextLong() {
			return Long.parseLong(next());
		}
	}
}