import java.util.*;
import java.lang.*;
import java.io.*;

public class Main
{
	public static void main (String[] args) throws java.lang.Exception
	{
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int n = in.nextInt();
		ArrayList<Integer> a = new ArrayList<Integer>();
		for(int i = 0; i < n; i++)
		{
			a.add(in.nextInt());
		}
		
		Collections.sort(a, Collections.reverseOrder());
		int last = a.get(0) + 2;
		int ans = 0;
		for(int i = 0; i < n; i++)
		{
			int cur = -1;
			for(int dx = 1; dx >= -1; dx--)
			{
				if(a.get(i) + dx > 0 && a.get(i) + dx < last)
				{
					cur = a.get(i) + dx;
					break;
				}
			}
			
			if(cur == -1)
				continue;
			ans++;
			last = cur;
		}
		
		out.println(ans);
		
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
		
		public long nextLong()
		{
			return Long.parseLong(next());
		}
	}
}