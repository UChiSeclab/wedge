import java.util.*;
import java.lang.*;
import java.io.*;

public class Main
{
	static final int MAX = 200005;
	static int[][][] dp = new int[MAX][2][2];
	static int[] a = new int[MAX];
	static int[] use = new int[MAX];
	
	static int solve(int pos, int f1, int f2)
	{
		if(pos > 150001)
			return 0;
		int ans = dp[pos][f1][f2];
		if(ans != -1)
			return ans;
		ans = 0;
		
		int tmp = use[pos];
		int x = 0;
		
		if(tmp == 0)
			return ans = solve(pos + 1, 0, 0);
		
		if(pos > 1 && tmp > 0 && f1 == 0)
		{
			x++;
			tmp--;
		}
		
		if(tmp > 0 && f2 == 0)
		{
			x++;
			tmp--;
			if(tmp > 0)
			{
				ans = Math.max(ans, solve(pos + 1, 1, 1) + x + 1);
			}
			ans = Math.max(ans, solve(pos + 1, 0, 0) + x);
		}
		else
		{
			if(tmp > 0)
				ans = Math.max(ans, solve(pos + 1, 0, 1) + x + 1);
			ans = Math.max(ans, solve(pos + 1, 0, 0) + x);
		}
		
		return ans;
	}
	
	public static void main (String[] args) throws java.lang.Exception
	{
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int n = in.nextInt();
		for(int i = 0; i < MAX; i++)
			use[i] = 0;
			
		for(int i = 0; i < n; i++)
		{
			a[i] = in.nextInt();
			use[a[i]]++;
		}
		
		for(int i = 0; i < MAX; i++)
		{
			for(int j = 0; j < 2; j++)
			{
				for(int k  = 0; k < 2; k++)
				{
					dp[i][j][k] = -1;
				}
			}
		}
		
		out.println(solve(1, 0, 0));
		
		out.close();
	}
	
	static class InputReader
	{
		public BufferedReader reader;
		public StringTokenizer tokenizer;
		
		public InputReader(InputStream stream)
		{
			reader = new BufferedReader(new InputStreamReader(stream), 32768);
			tokenizer = null;
		}
		
		public String next()
		{
			while(tokenizer == null || !tokenizer.hasMoreTokens())
			{
				try
				{
					tokenizer = new StringTokenizer(reader.readLine());
				}
				catch(IOException e)
				{
					throw new RuntimeException(e);
				}
			}
			
			return tokenizer.nextToken();
		}
		
		public int nextInt()
		{
			return Integer.parseInt(next());
		}
	}
}