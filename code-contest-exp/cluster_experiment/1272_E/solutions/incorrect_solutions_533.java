import java.io.*;
import java.util.*;  
public class Main {
	static boolean[] visited;
	static int[] ans, a;
	public static int dfs(int v, int parity)
	{
		visited[v]=true;
		int left=v-a[v];
		int leftChild=-1;
		if(left>-1)
		if(left>-1 && parity!=a[left]%2){return ans[v]=1;}
		else if(left>-1 && !visited[left])
		{
			leftChild=dfs(left,parity);
		}
		else if(left>-1 && visited[left])
		{
			leftChild=ans[left];
		}
		int right=v+a[v];
		int rightChild=-1;
		if(right<visited.length && parity!=a[right]%2)return ans[v]=1;
		else if(right<visited.length && !visited[right])
		{
			rightChild=dfs(right,parity);
		}
		else if(right<visited.length && visited[right])
		{
			rightChild=ans[right];
		}
		if(rightChild==-1 && leftChild==-1)return ans[v]=-1;
		if(rightChild==-1)return ans[v]=leftChild+1;
		if(leftChild==-1) return ans[v]=rightChild+1;
		return ans[v]=1+Math.min(rightChild, leftChild);
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
		visited= new boolean[n];
		for (int i = 0; i < visited.length; i++) {
			if(!visited[i])
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