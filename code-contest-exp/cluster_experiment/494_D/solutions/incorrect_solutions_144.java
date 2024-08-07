import java.util.*;
import java.io.*;
public class D
{
	public static final long MOD = 1000000007;
	
	public static ArrayList<Edge>[] adj;
	public static long[] dist;
	public static long[] size;
	public static long[] sumb;
	public static long[] sumb2;
	public static int[] low;
	public static int[] indices;
	public static int[] depth;
	public static int index;
	public static RMQ rmq;
	public static ArrayList<Query>[] queries;
	public static long[] pathc;
	public static long[] pathc2;
	public static long[] pathbc;
	
	public static void main(String[] args) throws Exception
	{
		FastScanner in = new FastScanner(System.in);
		
		int n = in.nextInt();
		
		adj = new ArrayList[n];
		for(int x = 0; x < adj.length; x++)
		{
			adj[x] = new ArrayList<Edge>();
		}
		
		for(int y = 0; y < n - 1; y++)
		{
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			long c = in.nextInt();
			
			adj[a].add(new Edge(b, c));
			adj[b].add(new Edge(a, c));
		}
		
		dist = new long[n];
		getDist(0, -1, 0);
		
		size = new long[n];
		getSize(0, -1);
		
		sumb = new long[n];
		getSumB(0, -1);
		
		sumb2 = new long[n];
		getSumB2(0, -1);
		
		createRMQ(0);
		
		int q = in.nextInt();
		
		queries = new ArrayList[n];
		for(int z = 0; z < queries.length; z++)
		{
			queries[z] = new ArrayList<Query>();
		}
		
		Query[] result = new Query[q];
		for(int a = 0; a < q; a++)
		{
			int u = in.nextInt() - 1;
			int v = in.nextInt() - 1;
			
			Query query = new Query(a, v);
			
			queries[u].add(query);
			result[a] = query;
		}
		
		pathc = new long[n];
		pathc2 = new long[n];
		pathbc = new long[n];
		calc(0, -1, 0, 0, 0);
		
		for(int b = 0; b < result.length; b++)
		{
			System.out.println(result[b].f);
		}
	}
	
	public static void getDist(int node, int parent, long d)
	{
		dist[node] = d;
		
		for(Edge e : adj[node])
		{
			if(e.end != parent)
			{
				getDist(e.end, node, (d + e.cost) % MOD);
			}
		}
	}
	
	public static long getSize(int node, int parent)
	{
		size[node] = 1;
		
		for(Edge e : adj[node])
		{
			if(e.end != parent)
			{
				size[node] += getSize(e.end, node);
			}
		}
		
		return size[node];
	}
	
	public static long getSumB(int node, int parent)
	{
		sumb[node] = dist[node];
		
		for(Edge e : adj[node])
		{
			if(e.end != parent)
			{
				sumb[node] += getSumB(e.end, node);
				sumb[node] %= MOD;
			}
		}
		
		return sumb[node];
	}
	
	public static long getSumB2(int node, int parent)
	{
		sumb2[node] = dist[node] * dist[node];
		sumb2[node] %= MOD;
		
		for(Edge e : adj[node])
		{
			if(e.end != parent)
			{
				sumb2[node] += getSumB2(e.end, node);
				sumb2[node] %= MOD;
			}
		}
		
		return sumb2[node];
	}
	
	public static void calc(int node, int parent, long sumc, long sumc2, long sumbc)
	{
		long c = size[node] * dist[node];
		c %= MOD;
		
		long c2 = c * dist[node];
		c2 %= MOD;
		
		long bc = dist[node] * sumb[node];
		bc %= MOD;
		
		long total = count((((dist[node] * dist[node]) % MOD) * size[0]) % MOD,
				sumb2[0],
				(sumc2 + c2) % MOD,
				(dist[node] * sumb[0]) % MOD,
				(dist[node] * ((sumc + c) % MOD)) % MOD,
				(sumbc + bc) % MOD);
		
		for(Query query : queries[node])
		{
			int ancestor = lca(node, query.v);
			
			long subtree = -1;
			if(ancestor != query.v)
			{
				subtree = count((((dist[node] * dist[node]) % MOD) * size[query.v]) % MOD,
						sumb2[query.v],
						(((dist[ancestor] * dist[ancestor]) % MOD) * size[query.v]) % MOD,
						(dist[node] * sumb[query.v]) % MOD,
						(dist[node] * ((dist[ancestor] * size[query.v]) % MOD)) % MOD,
						(dist[ancestor] * sumb[query.v]) % MOD);
			}
			else
			{
				subtree = count((((dist[node] * dist[node]) % MOD) * size[query.v]) % MOD,
						sumb2[query.v],
						(((sumc2 + c2 - pathc2[ancestor]) % MOD) + MOD) % MOD,
						(dist[node] * sumb[query.v]) % MOD,
						(dist[node] * ((((sumc + c - pathc[ancestor]) % MOD) + MOD) % MOD)) % MOD,
						(((sumbc + bc - pathbc[ancestor]) % MOD) + MOD) % MOD);
			}
			
			query.f = 2 * subtree;
			query.f %= MOD;
			query.f -= total;
			query.f = ((query.f % MOD) + MOD) % MOD;
		}
		
		for(Edge e : adj[node])
		{
			if(e.end != parent)
			{
				long newc = sumc + dist[node] * (size[node] - size[e.end]);
				newc %= MOD;
				
				long newc2 = sumc2 + dist[node] * dist[node] * (size[node] - size[e.end]);
				newc2 %= MOD;
				
				long newbc = sumbc + dist[node] * (sumb[node] - sumb[e.end]);
				newbc %= MOD;
				
				pathc[e.end] = newc;
				pathc2[e.end] = newc2;
				pathbc[e.end] = newbc;
				
				calc(e.end, node, newc, newc2, newbc);
			}
		}
	}
	
	public static long count(long a2, long b2, long c2, long ab, long ac, long bc)
	{
		long ret = a2;
		ret %= MOD;
		ret += b2;
		ret %= MOD;
		ret += 4 * c2;
		ret %= MOD;
		ret += 2 * ab;
		ret %= MOD;
		ret -= 4 * ac;
		ret = ((ret % MOD) + MOD) % MOD;
		ret -= 4 * bc;
		ret = ((ret % MOD) + MOD) % MOD;
		
		return ret;
	}
	
	public static void createRMQ(int root)
	{
		low = new int[adj.length];
		Arrays.fill(low, -1);
		indices = new int[2 * adj.length];
		depth = new int[adj.length];
		index = 0;
		dfs(root, 0);
		
		int[] array = new int[index];
		for(int z = 0; z < index; z++)
		{
			array[z] = depth[indices[z]];
		}
		
		rmq = new RMQ(array);
	}
	
	public static void dfs(int node, int d)
	{
		low[node] = index;
		depth[node] = d;
		indices[index] = node;
		index++;
		
		for(Edge e : adj[node])
		{
			if(low[e.end] == -1)
			{
				dfs(e.end, d + 1);
				indices[index] = node;
				index++;
			}
		}
	}
	
	public static int lca(int a, int b)
	{
		if(low[a] > low[b])
		{
			int temp = a;
			a = b;
			b = temp;
		}
		
		return indices[rmq.queryIndex(low[a], low[b])];
	}
	
	static class Edge
	{
		int end;
		long cost;
		
		public Edge(int e, long c)
		{
			end = e;
			cost = c;
		}
	}
	
	static class Query
	{
		int index;
		int v;
		long f;
		
		public Query(int i, int v)
		{
			index = i;
			this.v = v;
			f = -1;
		}
	}
	
	static class RMQ
	{
		int[][][] min;
		int[][][] index;
		
		public RMQ(int[] array)
		{
			int n = array.length;
			
			int p = Integer.numberOfTrailingZeros(Integer.highestOneBit(n));
			
			min = new int[2][p + 1][n];
			index = new int[2][p + 1][n];
			
			min[0][0] = Arrays.copyOf(array, array.length);
			min[1][0] = Arrays.copyOf(array, array.length);
			
			for(int i = 0; i < array.length; i++)
			{
				index[0][0][i] = i;
				index[1][0][i] = i;
			}
			
			for(int j = 1; j < min[0].length; j++)
			{
				for(int k = 0; k < array.length; k++)
				{
					int size = 1 << (j - 1);
					
					for(int a = 0; a < 2; a++)
					{
						int next = k + (-2 * a + 1) * size;
						
						min[a][j][k] = min[a][j - 1][k];
						index[a][j][k] = index[a][j - 1][k];
						if(next >= 0 && next < n && min[a][j - 1][next] < min[a][j][k])
						{
							min[a][j][k] = min[a][j - 1][next];
							index[a][j][k] = index[a][j - 1][next];
						}
					}
				}
			}
		}
		
		public int query(int left, int right)
		{
			int p = Integer.numberOfTrailingZeros(Integer.highestOneBit(right - left + 1));
			
			return Math.min(min[0][p][left], min[1][p][right]);
		}
		
		public int queryIndex(int left, int right)
		{
			int p = Integer.numberOfTrailingZeros(Integer.highestOneBit(right - left + 1));
			
			if(min[0][p][left] <= min[1][p][right])
			{
				return index[0][p][left];
			}
			else
			{
				return index[1][p][right];
			}
		}
	}
	
	static class FastScanner
	{
		BufferedReader br;
		StringTokenizer st;
		
		public FastScanner(InputStream input)
		{
			br = new BufferedReader(new InputStreamReader(input));
			st = new StringTokenizer("");
		}
		
		public String next() throws IOException
		{
			if(st.hasMoreTokens())
			{
				return st.nextToken();
			}
			else
			{
				st = new StringTokenizer(br.readLine());
				return next();
			}
		}
		
		public int nextInt() throws IOException
		{
			return Integer.parseInt(next());
		}
	}
}
