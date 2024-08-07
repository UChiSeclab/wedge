import java.io.*;
import java.util.*;  
public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br  =new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(System.out);
		int n = Integer.parseInt(br.readLine());
		int[] a = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < a.length; i++) {
			a[i]= Integer.parseInt(st.nextToken());
		}
		ArrayList<Integer>[] adj = new ArrayList[n];
		for (int i = 0; i < adj.length; i++) {
			adj[i]= new ArrayList<Integer>();
		}

		for (int i = 0; i < adj.length; i++) {
			int left=i-a[i];
			if(left>-1)
			{
				adj[left].add(i);
			}
			int right=i+a[i];
			if(right<a.length)
			{
				adj[right].add(i);
			}
		}
	
		int[] ans = new int[n];
		Arrays.fill(ans, -1);
		
		boolean[] visited = new boolean[n];
		Queue<Node> even = new LinkedList<Node>();
		for (int i = 0; i < a.length; i++) {
			if(a[i]%2==0)
			{
				even.add(new Node(i, 0));
				visited[i]=true;
			}
		}
		while(!even.isEmpty())
		{
			Node u= even.poll();
			for(int v: adj[u.node])
			{
				if(!visited[v])
				{
					visited[v]=true;
					even.add(new Node(v, u.level+1));
					if(a[v]%2==1)ans[v]=u.level+1;
				}
			}
			
		}
		
		visited = new boolean[n];
		Queue<Node> odd = new LinkedList<Node>();
		for (int i = 0; i < a.length; i++) {
			if(a[i]%2==1)
			{
				odd.add(new Node(i, 0));
				visited[i]=true;
			}
		}
		while(!odd.isEmpty())
		{
			Node u= odd.poll();
			for(int v: adj[u.node])
			{
				if(!visited[v])
				{
					visited[v]=true;
					odd.add(new Node(v, u.level+1));
					if(a[v]%2==0)ans[v]=u.level+1;
				}
			}
			
		}
		for (int i = 0; i < ans.length; i++) {
			pw.print(ans[i] + " ");
		}
		pw.println();
		pw.close();
	}	
	static class Node
	{
		int node;
		int level;
		public Node(int node, int level)
		{
			this.node=node;
			this.level=level;
		}
		public String toString()
		{
			return "(" + node + "," + level + ")"; 
		}
	}
}