import java.util.*;
import java.io.*;
/*

5
1 2 1
4 3 1
3 5 1
1 3 1
5
1 1
1 5
2 4
2 1
3 5

 */

public class d 
{
	public static void main(String[] arg) throws IOException
	{
		new d();
	}
	ArrayList<Edge>[] adj;
	ArrayList<Pair>[] queries;
	int cnt;
	int[] pre;
	int[] post;
	ST st;
	int n;
	public d() throws IOException
	{
		FastScanner in = new FastScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		n = in.nextInt();
		adj = new ArrayList[n];
		for(int i = 0; i < n; i++) adj[i] = new ArrayList<Edge>();
		for(int i = 0; i < n-1; i++)
		{
			int v = in.nextInt()-1;
			int u = in.nextInt()-1;
			int w = in.nextInt();
			adj[v].add(new Edge(u, w));
			adj[u].add(new Edge(v, w));
		}
		int Q = in.nextInt();
		Pair[] qList = new Pair[Q];
		queries = new ArrayList[n];
		for(int i = 0; i < n; i++) queries[i] = new ArrayList<Pair>();
		pre = new int[n];
		post = new int[n];
		st = new ST(n);
		cnt = 0;
		int source = 0;
		precomp(source, source, 0);
		for(int i = 0; i < Q; i++)
		{
			int u = in.nextInt()-1;
			int v = in.nextInt()-1;
			Pair p = new Pair(u, v, i);
			qList[i] = p;
			queries[p.a].add(p);
		}
		solve(source, source);
		for(int i = 0; i < Q; i++)
		{
			out.println(qList[i].ans);
		}
		in.close(); out.close();
	}
	void solve(int v, int p)
	{
		for(Pair pair : queries[v])
		{
			int a = st.sum(pre[pair.b], post[pair.b])%MOD;
			int b = st.sum(0, cnt-1)%MOD;
			pair.ans = a-(b-a+MOD)%MOD+MOD;
			pair.ans %= MOD;
		}
		
		for(Edge e : adj[v])
		{
			if(e.to == p) continue;
			st.update(0, cnt-1, e.w);
			st.update(pre[e.to], post[e.to], (-2*e.w+2L*MOD)%MOD);
			
			solve(e.to, v);
			
			st.update(0, cnt-1, (-e.w+MOD)%MOD);
			st.update(pre[e.to], post[e.to], 2*e.w);
		}
	}
	void precomp(int v, int p, long d)
	{
		pre[v] = cnt++;
		for(Edge e : adj[v])
		{
			int to = e.to;
			if(to == p) continue;
			precomp(to, v, d+e.w);
		}
		post[v] = cnt-1;
		st.update(pre[v], pre[v], d);
	}
	class ST
	{
		int[] x1;
		int[] x2;
		int[] d;
		int s;
		public ST(int size)
		{
			s = size;
			x1 = new int[4*s+1];
			x2 = new int[4*s+1];
			d = new int[4*s+1];
		}
		public void update(int l, int r, long v)
		{
			update(l, r, v, 0, s-1, 1);
		}
		private void update(int lq, int rq, long v, int li, int ri, int c)
		{
			if(rq < li || ri < lq) return;
			if(lq <= li && ri <= rq)
			{
				d[c] += v;
				d[c] %= MOD;
				return;
			}
			int m = li + (ri-li)/2;
			prop(c);
			update(lq, rq, v, li, m, 2*c);
			update(lq, rq, v, m+1, ri, 2*c+1);
			fix(c, li, ri);
		}
		public int sum(int l, int r)
		{
			return sum(l, r, 0, s-1, 1);
		}
		private int sum(int lq, int rq, int li, int ri, int c)
		{
			if(rq < li || ri < lq) return 0;
			if(lq <= li && ri <= rq)
			{
				return (int)(((x2[c]+2*(long)x1[c]*d[c]%MOD)+(ri-li+1)*(long)d[c]%MOD*d[c])%MOD);
			}
			int m = li + (ri-li)/2;
			prop(c);
			int ret = sum(lq, rq, li, m, 2*c);
			ret += sum(lq, rq, m+1, ri, 2*c+1);
			ret %= MOD;
			fix(c, li, ri);
			return ret;
		}
		void prop(int c)
		{
			d[2*c] += d[c];
			d[2*c] %= MOD;
			
			d[2*c+1] += d[c];
			d[2*c+1] %= MOD;
			
			d[c] = 0;
		}
		void fix(int c, int l, int r)
		{
			int m = l + (r-l)/2;
			x1[c] = 0;
			x1[c] += (x1[2*c]+(m-l+1)*(long)d[2*c]);
			x1[c] %= MOD;
			x1[c] += (x1[2*c+1]+(r-(m+1)+1)*(long)d[2*c+1]);
			x1[c] %= MOD;
			
			x2[c] = 0;
			x2[c] += ((x2[2*c]+2*(long)x1[2*c]*d[2*c]%MOD)+(m-l+1)*(long)d[2*c]%MOD*d[2*c]%MOD);
			x2[c] %= MOD;
			x2[c] += ((x2[2*c+1]+2*(long)x1[2*c+1]*d[2*c+1]%MOD)+(r-(m+1)+1)*(long)d[2*c+1]%MOD*d[2*c+1]%MOD);
			x2[c] %= MOD;
		}
	}
	final int MOD = 1_000_000_007;
	class Edge
	{
		int to;
		long w;
		public Edge(int a, long b)
		{
			to = a;
			w = b;
		}
	}
	class Pair
	{
		int a, b, id;
		long ans;
		public Pair(int aa, int bb, int cc)
		{
			a = aa;
			b = bb;
			id = cc;
		}
	}
	class FastScanner
	{
		BufferedReader br;
		StringTokenizer st;
		public FastScanner(InputStream in)
		{
			br = new BufferedReader(new InputStreamReader(in));
			st = new StringTokenizer("");
		}
		public String next() throws IOException
		{
			while(!st.hasMoreElements()) st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		public int nextInt() throws IOException
		{
			return Integer.parseInt(next());
		}
		public void close() throws IOException
		{
			br.close();
		}
	}
}