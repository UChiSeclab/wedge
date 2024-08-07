import java.io.*;
import java.util.*;

public class E {

	public static void main(String[] args) {
		FastScanner sc = new FastScanner();
		int n = sc.nextInt();
		int[] arr = new int[n];
		for(int i = 0; i < n; i++) {
			arr[i] = sc.nextInt();
		}
		ArrayList<Integer>[] g = new ArrayList[n];
		for(int i = 0; i < n; i++) g[i] = new ArrayList<>();
		for(int i = 0; i < n; i++) {
			if(i + arr[i] < n) {
				g[i + arr[i]].add(i);
			}
			if(i - arr[i] >= 0) {
				g[i - arr[i]].add(i);
			}
		}
		int[] dist = new int[n];
		Arrays.fill(dist, -1);
		LinkedList<Integer> q = new LinkedList<Integer>();
		for(int i = 0; i < n; i++) {
			if(i+arr[i] < n && (arr[i] + arr[i+arr[i]]) % 2 == 1) {
				dist[i] = 1;
				q.add(i);
			}
			else if(i-arr[i] >= 0 && (arr[i] + arr[i-arr[i]]) % 2 == 1) {
				dist[i] = 1;
				q.add(i);
			}
		}
		while(!q.isEmpty()) {
			int u = q.removeFirst();
			for(int v: g[u]) {
				if(dist[v] < 0) {
					dist[v] = dist[u]+1;
					q.add(v);
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for(int k: dist) sb.append(k+" ");
		System.out.println(sb.toString()+"\n");
	}
	static class FastScanner {
		public BufferedReader reader;
		public StringTokenizer tokenizer;
		public FastScanner() {
			reader = new BufferedReader(new InputStreamReader(System.in), 32768);
			tokenizer = null;
		}
		public String next() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return tokenizer.nextToken();
		}
		public int nextInt() {
			return Integer.parseInt(next());
		}
		public long nextLong() {
			return Long.parseLong(next());
		}
		public double nextDouble() {
			return Double.parseDouble(next());
		}
		public String nextLine() {
			try {
				return reader.readLine();
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
	}


}
