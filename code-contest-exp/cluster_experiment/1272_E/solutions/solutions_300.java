import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P1272E
{
	static int oo = Integer.MAX_VALUE/2;
	static ArrayList<Integer>[] rev;
	public static void main(String[] args)
	{
		FastScanner scan = new FastScanner();
		int n = scan.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = scan.nextInt();
		rev = new ArrayList[n];
		for (int i = 0; i < rev.length; i++)
			rev[i] = new ArrayList<>();
		for (int i = 0; i < n; i++)
		{
			int a = i - arr[i];
			int b = i + arr[i];
			if (a >= 0)
				rev[a].add(i);
			if (b < n)
				rev[b].add(i);
		}
		ArrayDeque<Integer> q = new ArrayDeque<>();
		int[] dist = new int[n];
		Arrays.fill(dist, oo);
		for (int i = 0; i < rev.length; i++)
		{
			int par1 = arr[i] % 2;
			for (int j = 0; j < rev[i].size(); j++)
			{
				int idx = rev[i].get(j);
				int par2 = arr[idx] % 2;
				if (par1 != par2)
				{
					dist[idx] = 1;
					q.add(idx);
				}
			}
		}
		while (!q.isEmpty())
		{
			int p = q.poll();
			int d = dist[p];
			for (int i = 0; i < rev[p].size(); i++)
			{
				int x = rev[p].get(i);
				if (d+1 < dist[x])
				{
					dist[x] = d+1;
					q.add(x);
				}
			}
		}
		PrintWriter pw = new PrintWriter(System.out);
		for (int i = 0; i < n; i++)
		{
			if (i > 0)
				pw.print(" ");
			pw.print(dist[i] >= oo ? -1 : dist[i]);
		}
		pw.flush();
	}
	static class FastScanner
	{
		BufferedReader br;
		StringTokenizer st;

		public FastScanner()
		{
			try
			{
				br = new BufferedReader(new InputStreamReader(System.in));
				st = new StringTokenizer(br.readLine());
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		public String next()
		{
			if (st.hasMoreTokens())
				return st.nextToken();
			try
			{
				st = new StringTokenizer(br.readLine());
			} catch (Exception e)
			{
				e.printStackTrace();
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

		public String nextLine()
		{
			String line = "";
			try
			{
				line = br.readLine();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			return line;
		}
	}
}
