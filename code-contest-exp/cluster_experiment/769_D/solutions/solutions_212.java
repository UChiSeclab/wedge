import java.io.*;
import java.util.*;
public final class k_pairs
{
    static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static FastScanner sc=new FastScanner(br);
    static PrintWriter out=new PrintWriter(System.out);
	static Random rnd=new Random();
	static int maxn=(int)(1e4)+1;
	
    public static void main(String args[]) throws Exception
    {
		int n=sc.nextInt(),k=sc.nextInt();int[] a=new int[n],cnt=new int[maxn];
		for(int i=0;i<n;i++)
		{
			a[i]=sc.nextInt();cnt[a[i]]++;
		}
		long res=0;
		for(int i=0;i<maxn;i++)
		{
			for(int j=i;j<maxn;j++)
			{
				int now=i^j;
				if(Integer.bitCount(now)==k)
				{
					if(i==j)
					{
						long curr=(1L*cnt[i]*(1L*cnt[i]-1));curr/=2;res+=curr;
					}
					else
					{
						long curr=1L*cnt[i]*1L*cnt[j];res+=curr;
					}
				}
			}
		}
		out.println(res);out.close();
    }
}
class FastScanner
{
    BufferedReader in;
    StringTokenizer st;

    public FastScanner(BufferedReader in) {
        this.in = in;
    }
	
    public String nextToken() throws Exception {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(in.readLine());
        }
        return st.nextToken();
    }
	
	public String next() throws Exception {
		return nextToken().toString();
	}
	
    public int nextInt() throws Exception {
        return Integer.parseInt(nextToken());
    }

    public long nextLong() throws Exception {
        return Long.parseLong(nextToken());
    }

    public double nextDouble() throws Exception {
        return Double.parseDouble(nextToken());
    }
}