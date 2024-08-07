import java.io.*;
import java.util.*;  
public class Main {
	static boolean[] visited;
	static int[] ans, a;
	public static int dfs(int v, int parity)
	{
		//System.out.println("v: " + v);
		visited[v]=true;
		int left=v-a[v];
		if(left>-1)
		{
			//System.out.println("Left is possible");
			if(parity!=a[left]%2)ans[v]=1;
			else if(parity==a[left]%2 && !visited[left])
			{
				//System.out.println("Will visit");
				int tmp=dfs(left,parity);
				if(tmp!=Integer.MAX_VALUE&& tmp+1<ans[v])
				{
					ans[v]=tmp+1;
				}
				//System.out.println("Back at " + v);
			}
			else if(parity==a[left]%2 && visited[left])
			{
				//System.out.println("already visited before");
				if(ans[left]!=Integer.MAX_VALUE && ans[left]+1<ans[v])
				{
					ans[v]=ans[left]+1;
				}
			}
		}
		//System.out.println("ans after left: " + ans[v]);
		int right=v+a[v];
		if(right<visited.length)
		{
			//System.out.println("Right is possible");
			if(parity!=a[right]%2)ans[v]=1;
			else if(parity==a[right]%2 && !visited[right])
			{
				//System.out.println("Will visit");
				int tmp=dfs(right,parity);
				if(tmp!=Integer.MAX_VALUE&& tmp+1<ans[v])
				{
					ans[v]=tmp+1;
				}
				//System.out.println("Back at " + v);
			}
			else if(parity==a[right]%2 && visited[right])
			{
				//System.out.println("already visited before");
				if(ans[right]!=Integer.MAX_VALUE && ans[right]+1<ans[v])
				{
					ans[v]=ans[right]+1;
				}
			}
		}
		//System.out.println("ans after left: " + ans[v]);

		return ans[v];
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
			//System.out.println("i: " + i);
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