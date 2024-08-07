import java.io.*;
import java.util.*;

public class E
{
	static class Edge implements Comparable<Edge>
	{
		int u;
		int v;
		int w;

		Edge(int uu, int vv, int ww)
		{
			u = uu;
			v = vv;
			w = ww;
		}

		public int compareTo(Edge e)
		{
			return Integer.compare(w, e.w);
		}
	}

	static int oo = (int)1e9;

	public static void solve(FastIO io)
	{
		int n = io.nextInt();
		
		int[] a = new int[n];

		for (int i = 0; i < n; i++)
			a[i] = io.nextInt();

		int[] dist = new int[n];
		Arrays.fill(dist, -1);

		ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
		for (int i = 0; i < n; i++)
			adj.add(new ArrayList<>());

		ArrayDeque<Integer> q = new ArrayDeque<>();


		for (int i = 0; i < n; i++)
		{
			if (i - a[i] >= 0)
			{
				if (a[i] % 2 != a[i - a[i]] % 2)
					dist[i] = 1;

				adj.get(i - a[i]).add(i);
			}

			if (i + a[i] < n)
			{
				if (a[i] % 2 != a[i + a[i]] % 2)
					dist[i] = 1;
					
				adj.get(i + a[i]).add(i);
			}
			
			if (dist[i] == 1)
				q.add(i);
		}

		while (!q.isEmpty())
		{
			int u = q.remove();

			for (int v : adj.get(u))
			{
				if (dist[v] == -1)
				{
					dist[v] = dist[u] + 1;
					q.add(v);
				}
			}
		}

		for (int i = 0; i < n; i++)
			io.print(dist[i] + " ");
		io.println();
	}

	public static void main(String[] args) {
		FastIO io = new FastIO();


		solve(io);
		


		io.close();
	}
}

class FastIO extends PrintWriter
{
	BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	StringTokenizer st = new StringTokenizer("");

	FastIO()
	{
		super(System.out);
	}

	public String next()
	{
		while (!st.hasMoreTokens())
		{
			try {
				st = new StringTokenizer(r.readLine());
			} catch (Exception e) {
				//TODO: handle exception
			}
		}
		return st.nextToken();
	}

	public int nextInt()
	{
		return Integer.parseInt(next());
	}

	public long nextLong()
	{
		return Long.parseLong(next());
	}

	public double nextDouble()
	{
		return Double.parseDouble(next());
	}
}
