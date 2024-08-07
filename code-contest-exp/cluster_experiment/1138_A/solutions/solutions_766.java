import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ShushiForTwo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Scanner input =new Scanner(System.in);
		int n = input.nextInt();
		int[] arr =new int[n];
		for(int i=0; i<n;i++)arr[i] = input.nextInt();
		
		int i=0;
		int total=0;
		while(i<n) {
			int cnt=0;
			int cnt1=0;
			if(arr[i]==1) {
				 cnt=1;
				 i++;
				while(i<n && arr[i]==1) {
					i++;
					cnt++;
				}
				int j=i;
				if(j<n && arr[j]==2) {
					 cnt1=1;
					 j++;
					while(j<n && arr[j]==2) {
						cnt1++;
						j++;
					}
					total = Math.max(total,2*Math.min(cnt,cnt1));
				}
			}
			else {
				if(arr[i]==2) {
					 cnt1=1;
					 i++;
					while(i<n && arr[i]==2) {
						i++;
						cnt1++;
					}
					int j=i;
					if(j<n && arr[j]==1) {
						cnt=1;
						j++;
						while(j<n && arr[j]==1) {
							cnt++;
							j++;
						}
						total = Math.max(total,2*Math.min(cnt,cnt1));
						
					}
				}
			}
		}
		System.out.println(total);
		

	}
	
	
	static class Scanner 
	{
		StringTokenizer st;
		BufferedReader br;
 
		public Scanner(InputStream s){	br = new BufferedReader(new InputStreamReader(s));}
 
		public String next() throws IOException 
		{
			while (st == null || !st.hasMoreTokens()) 
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
 
		public int nextInt() throws IOException {return Integer.parseInt(next());}
		
		public long nextLong() throws IOException {return Long.parseLong(next());}
 
		public String nextLine() throws IOException {return br.readLine();}
		
		public double nextDouble() throws IOException
		{
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if(x.charAt(0) == '-')
			{
				neg = true;
				start++;
			}
			for(int i = start; i < x.length(); i++)
				if(x.charAt(i) == '.')
				{
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				}
				else
				{
					sb.append(x.charAt(i));
					if(dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg?-1:1);
		}
		
		public boolean ready() throws IOException {return br.ready();}
 
 
	}
	

}
