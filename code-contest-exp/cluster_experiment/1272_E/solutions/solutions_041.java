// javac e.java && java _

import java.io.*;
import java.util.*;

public class e {
	public static void main(String[] args) { new e(); }
	FS in = new FS();
	PrintWriter out = new PrintWriter(System.out);
	
	int n, trash = 1 << 20;
	int[] pd, d;
	ArrayList<Integer>[] adj;
	ArrayDeque<Integer> q;
	
	e() {
		n = in.nextInt();
		pd = new int[n];
		adj = new ArrayList[n];
		for (int i = 0; i < n; i++)
			adj[i] = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			int a = in.nextInt();
			int j = i - a;
			if (j >= 0)
				adj[j].add(i);
			j = i + a;
			if (j < n)
				adj[j].add(i);
			pd[i] = (a & 1);
		}

		d = new int[n];
		Arrays.fill(d, -1);

		q = new ArrayDeque<>();
		for (int i = 0; i < n; i++)
			if (pd[i] == 0) {
				q.add(i);
				q.add(0);
			}
		while (q.size() > 0) {
			ArrayDeque<Integer> nxt = new ArrayDeque<>();
			while (q.size() > 0) {
				int node = q.poll();
				int dist = q.poll();
				
				for (int nbr : adj[node])
					if (pd[nbr] == 1 && d[nbr] == -1) {
						d[nbr] = 1 + dist;
						q.add(nbr);
						q.add(1 + dist);
					}
			}
			q = nxt;
		}

		for (int i = 0; i < n; i++)
			if (pd[i] == 1) {
				q.add(i);
				q.add(0);
			}
		while (q.size() > 0) {
			ArrayDeque<Integer> nxt = new ArrayDeque<>();
			while (q.size() > 0) {
				int node = q.poll();
				int dist = q.poll();
				
				for (int nbr : adj[node])
					if (pd[nbr] == 0 && d[nbr] == -1) {
						d[nbr] = 1 + dist;
						q.add(nbr);
						q.add(1 + dist);
					}
			}
			q = nxt;
		}

		for (int i = 0; i < n; i++)
			out.print(d[i] + " ");
		out.println();
		out.close();
	}
	

	int min(int a, int b) { if (a < b) return a; return b; }	
	int max(int a, int b) { if (a > b) return a; return b; }	
	long min(long a, long b) { if (a < b) return a; return b; }	
	long max(long a, long b) { if (a > b) return a; return b; }	

	boolean z(int x) { if (x == 0) return true; return false; }
	boolean z(long x) { if (x == 0) return true; return false; }
	
	class FS {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");
		String next() {
			while (!st.hasMoreTokens()) {
				try { st = new StringTokenizer(br.readLine()); }
				catch (Exception e) {}
			} return st.nextToken();
		}
		int nextInt() { return Integer.parseInt(next()); }
		long nextLong() { return Long.parseLong(next()); }
		double nextDouble() { return Double.parseDouble(next()); }
	}
}

