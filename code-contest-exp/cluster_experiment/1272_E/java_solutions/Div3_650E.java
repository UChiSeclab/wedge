import java.util.*;
import java.io.*;

public class Div3_650E {
	
	static int n;
	static int[] array;
	static ArrayList<Integer>[] adjList;
	static boolean[] vis;
	static int[] count;
	
	public static int dfs(int u, int p) {
		if(array[u] % 2 == p)
			return 0;
		if(vis[u])
			return count[u];
		vis[u] = true;
		if(adjList[u].size() == 0)
			return count[u] = -1;
		if(adjList[u].size() == 1) {
			int r = dfs(adjList[u].get(0), p);
			if(r == -1)
				return count[u] = -1;
			else
				return count[u] = 1 + r;
		}
		if(adjList[u].size() == 2) {
			int r = dfs(adjList[u].get(0), p);
			int l = dfs(adjList[u].get(1), p);
			if(r == -1 && l == -1)
				return count[u] = -1;
			else if(r == -1)
				return count[u] = 1 + l;
			else if(l == -1)
				return count[u] = 1 + r;
			else
				return count[u] = 1 + Math.min(l, r);
		}
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(System.out);
		StringTokenizer st;
		
		
		n = sc.nextInt();
		array = new int[n];
		for(int i = 0; i < n; i++)
			array[i] = sc.nextInt();
		
		adjList = new ArrayList[n];
		for(int i = 0; i < n; i++)
			adjList[i] = new ArrayList<Integer>();
		
		for(int i = 0; i < n; i++) {
			int p = i + array[i];
			if(p < n)
				adjList[i].add(p);
			p = i - array[i];
			if(p >= 0)
				adjList[i].add(p);
		}
		
		vis = new boolean[n];
		count = new int[n];
		Arrays.fill(count, -1);
		
		for(int i = 0; i < n; i++) {
			if(!vis[i]) {
				dfs(i, 1 - (array[i] % 2));
			}
		}
		
		for(int i = 0; i < n; i++)
			pw.print(count[i] + (i == n - 1 ? "\n" : " "));
		
		pw.flush();
		
	}
}