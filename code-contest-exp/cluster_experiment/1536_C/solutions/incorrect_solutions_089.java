import java.io.*;
import java.util.*;
public class Main {
	static class FastReader {
		BufferedReader br;
		StringTokenizer st;
		
		public FastReader() {
			br = new BufferedReader(new
					InputStreamReader(System.in));
		}
		
		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		
		long nextLong() {
			return Long.parseLong(next());
		}
		
		double nextDouble() {
			return Double.parseDouble(next());
		}
		
		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
	
	static FastReader sc = new FastReader();
	static PrintWriter out = new PrintWriter(System.out);
	
	public static int gcd(int a,int b)
	{
		if(a==0)
			return b;
		return gcd(b%a,a);
	}
	public static void main(String[] args) throws  java.lang.Exception{
		
		FastReader sc=new FastReader();
		int t;
		t=sc.nextInt();
		while(t-->0)
		{
			int n,i,d=0,k=0;
			n=sc.nextInt();
			char str[]=sc.next().toCharArray();
			Map<String,Integer> map=new HashMap<>();
			for(i=0;i<str.length;i++)
			{
				if(str[i]=='D')
					d++;
				else
					k++;
				int gcd=gcd(d,k),a=d/gcd,b=k/gcd;
				String st=a+"#"+b;
				int p = map.containsKey(st) ? map.get(st) + 1 : 1;
				map.put(st, p);
				out.print(p+" ");
			}
			out.println();
			
		}
	}
}