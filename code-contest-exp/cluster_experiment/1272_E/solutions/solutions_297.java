import java.io.*;
import java.util.*;

public class E {

	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

	public static void main(String[] args) throws IOException {
		readInput();
		out.close();
	}
	
	static void dbg() {
		for (int row = 0; row < n; row++) {
			if (dp[row] < 1e5) out.print(dp[row]);
			else out.print('*');
			out.print(" ");
		}
		out.println();
	}

	static int[] dp;
	static int n;
	static int[] a;
	
	public static void readInput() throws IOException {
		// br = new BufferedReader(new FileReader(".in"));
		// out = new PrintWriter(new FileWriter(".out"));
		n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		a = new int[n];
		dp = new int[n];
		for (int i = 0; i < n; i+=1) a[i] = Integer.parseInt(st.nextToken());
		int[] ans = new int[n];
		Arrays.fill(ans, Integer.MAX_VALUE);
		
		if (n == 1) {
			out.println(-1);
			return;
		}
		
		List<Integer>[] adj = new List[n];
		for (int i= 0 ; i < n; i++) adj[i] = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			if (i-a[i] >= 0) {
				adj[i-a[i]].add(i);
			}
			if (i + a[i] < n) {
				adj[i+a[i]].add(i);
			}
		}
//		for (int i = 0; i < n; i++)System.out.println(adj[i]);
		
		for (int mod = 0; mod < 2; mod++) {
			
			Queue<Integer> q = new LinkedList<Integer>();
			boolean[] vis = new boolean[n];
			for (int i=  0; i < n; i++) {
				dp[i] = Integer.MAX_VALUE/3;
			}
			
			for (int i= 0 ; i  < n; i++) {
				if (a[i]%2==mod) {
					for (int x: adj[i]) {
						q.add(x);
						dp[x]=1;
					}
				}
			}
			//dbg();
			
			while (!q.isEmpty()) {
				int i = q.poll();
				if (vis[i]) continue;
				vis[i] = true;
				for (int x: adj[i]) {
					if (dp[x] > dp[i]+1) {
						dp[x] = dp[i]+1;
						q.add(x);
					}
				}
			}
			
			//dbg();
			
			
			for (int i= 0; i < n; i++) {
				if (a[i] % 2 != mod) ans[i] = Integer.min(ans[i],dp[i]);
			}
			
		}
		p(ans);
		
		
	}
	
	static void p(int[] ans) {
		for (int x: ans) {
			if (x > 1e7) out.print("-1 ");
			else out.print(x + " ");
		}
		out.println();
	}
	
}