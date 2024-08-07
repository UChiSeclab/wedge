import java.util.*;
import java.io.*;

public class B{
	public static void main(String[] args)
	{
		FastScanner fs = new FastScanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = fs.nextInt();
		int[] arr = fs.readArray(n);
		int[] count = new int[150002];
		for(int i:arr)
		{
			if(count[i]==0)
			{
				count[i]++;
			}
			else if(i>1 && count[i-1]==0)
			{
				count[i-1]++;
			}
			else if(count[i+1]==0)
			{
				count[i+1]++;
			}
		}
		int ans = 0;
		for(int i:count)if(i>0)ans++;
		out.println(ans);
		
		out.close();
	}
	static class FastScanner {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st=new StringTokenizer("");
		String next() {
			while (!st.hasMoreTokens())
				try {
					st=new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		int[] readArray(int n) {
			int[] a=new int[n];
			for (int i=0; i<n; i++) a[i]=nextInt();
			return a;
		}
		long nextLong() {
			return Long.parseLong(next());
		}
	}
}


