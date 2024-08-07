import java.io.*;
import java.util.*;  
public class Main {
	static int[] visited;
	static int[] ans, a;
	public static int dfs(int v, int parity)
	{
		visited[v]=1;
		int left=v-a[v];
		int leftChild=-1;
		if(left>-1)
		if(left>-1 && parity!=a[left]%2){leftChild=0;}
		else if(left>-1 && visited[left]==0)
		{
			leftChild=dfs(left,parity);
		}
		else if(left>-1 && visited[left]==2)
		{
			leftChild=ans[left];
		}
		int right=v+a[v];
		int rightChild=-1;
		if(right<visited.length && parity!=a[right]%2)rightChild=0;
		else if(right<visited.length && visited[right]==0)
		{
			rightChild=dfs(right,parity);
		}
		else if(right<visited.length && visited[right]==2)
		{
			rightChild=ans[right];
		}
		visited[v]=2;
		if(rightChild==-1 && leftChild==-1)return ans[v]=-1;
		if(rightChild==-1)return ans[v]=leftChild+1;
		if(leftChild==-1) return ans[v]=rightChild+1;
		return ans[v]=Math.min(rightChild+1, leftChild+1);
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
		visited= new int[n];
		for (int i = 0; i < visited.length; i++) {
			if(visited[i]==0)
			{
				dfs(i,a[i]%2);
			}
		}
		for (int i = 0; i < ans.length; i++) {
			pw.print(ans[i]+" ");
		}
		pw.println();
		pw.close();
	}	
}