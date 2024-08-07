import java.io.*;
import java.util.*;  
public class Main {
	static boolean[] visited;
	static int[] ans, a;
	public static int dfs(int v, int parity)
	{
		visited[v]=true;
		int left=v-a[v];
		if(left>-1)
		{
			if(parity!=a[left]%2)ans[v]=0;
			else if(parity==a[left]%2 && !visited[left])
			{
				ans[v]=Math.min(ans[v], dfs(left,parity));
			}
			else if(parity==a[left]%2 && visited[left])
			{
				ans[v]=Math.min(ans[v],ans[left]);
			}
		}
		int right=v+a[v];
		if(right<visited.length)
		{
			if(parity!=a[right]%2)ans[v]=0;
			else if(parity==a[right]%2 && !visited[right])
			{
				ans[v]=Math.min(ans[v], dfs(right,parity));
			}
			else if(parity==a[right]%2 && visited[right])
			{
				ans[v]=Math.min(ans[v], ans[right]);
			}
		}
		return ans[v]=ans[v]==Integer.MAX_VALUE?Integer.MAX_VALUE:ans[v]+1;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br  =new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(System.out);
		int n = Integer.parseInt(br.readLine());
		a = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < a.length; i++) {
			a[i]= Integer.parseInt(st.nextToken());
		}
		ans=new int[n];
		Arrays.fill(ans, Integer.MAX_VALUE);
		visited= new boolean[n];
		for (int i = 0; i < visited.length; i++) {
			if(!visited[i])
			{
				dfs(i,a[i]%2);
			}
		}
		for (int i = 0; i < ans.length; i++) {
			pw.print((ans[i]==Integer.MAX_VALUE?-1:ans[i])+" ");
		}
		pw.println();
		pw.close();
	}	
}