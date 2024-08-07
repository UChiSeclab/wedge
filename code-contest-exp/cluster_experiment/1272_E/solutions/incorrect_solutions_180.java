import java.util.*;
import java.io.*;

public class Main {
	public static void main(String args[]) {new Main().run();}

	FastReader in = new FastReader();
	PrintWriter out = new PrintWriter(System.out);
	void run(){
		work();
		out.flush();
	}
	long mod=1000000007;
	int[] dp,A;
	void work() {
		int n=in.nextInt();
		dp=new int[n];
		A=new int[n];
		Arrays.fill(dp, -1);
		for(int i=0;i<n;i++) {
			A[i]=in.nextInt();
		}
		boolean[] vis=new boolean[n];
		for(int i=0;i<n;i++) {
			if(!vis[i]) {
				dfs(i,vis);
			}
		}
		for(int i=0;i<n;i++) {
			out.print(dp[i]+" ");
		}
	}
	private int dfs(int node, boolean[] vis) {
		int n=vis.length;
		if(vis[node]) {
			return dp[node];
		}
		vis[node]=true;
		int x1=node-A[node];
		int x2=node+A[node];
		if(x1>=0) {
			int r=dfs(x1,vis);
			if(A[x1]%2!=A[node]%2) {
				dp[node]=1;
			}else if(r>0) {
				dp[node]=r+1;
			}
		}
		if(x2<n) {
			int r=dfs(x2,vis);
			if(A[x2]%2!=A[node]%2) {
				dp[node]=1;
			}else if(r>0) {
				if(dp[node]==-1)dp[node]=r+1;
				else {
					dp[node]=Math.min(r+1, dp[node]);
				}
			}
		}
		return dp[node];
	}
}	



class FastReader
{
	BufferedReader br;
	StringTokenizer st;

	public FastReader()
	{
		br=new BufferedReader(new InputStreamReader(System.in));
	}

	public String next() 
	{
		if(st==null || !st.hasMoreElements())
		{
			try {
				st = new StringTokenizer(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
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
}