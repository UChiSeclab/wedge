import java.io.*;
import java.util.*;


public class Main {
	public static void main(String[] args) throws Exception {
		MScanner sc = new MScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int n=sc.nextInt();
		int[]in=sc.takearr(n);
		int[]ans=new int[n];
		LinkedList<Integer>[]par=new LinkedList[n];
		for(int i=0;i<n;i++)par[i]=new LinkedList<Integer>();
		
		Arrays.fill(ans, -1);
		LinkedList<Integer>q=new LinkedList<Integer>();
		for(int i=0;i<n;i++) {
			int parity=in[i]%2;
			if(i+in[i]<n && in[i+in[i]]%2!=parity) {
				ans[i]=1;
				q.add(i);
			}
			else {
				if(i-in[i]>=0 && in[i-in[i]]%2!=parity) {
					ans[i]=1;
					q.add(i);
				}
				
			}
		}
		for(int i=0;i<n;i++) {
			int parity=in[i]%2;
			if(i+in[i]<n && in[i+in[i]]%2==parity) {
				par[i+in[i]].add(i);
			}
			else {
				if(i-in[i]>=0 && in[i-in[i]]%2==parity) {
					par[i-in[i]].add(i);
				}
				
			}
		}
		while(!q.isEmpty()) {
			int u=q.removeFirst();
			for(int v:par[u]) {
				if(ans[v]==-1) {
					ans[v]=ans[u]+1;
					q.add(v);
				}
				else {
					if(ans[v]>ans[u]+1) {
						ans[v]=ans[u]+1;
					}
				}
			}
		}
		for(int i:ans)pw.print(i+" ");
		pw.flush();
	}
	static class MScanner {
		StringTokenizer st;
		BufferedReader br;

		public MScanner(InputStream system) {
			br = new BufferedReader(new InputStreamReader(system));
		}

		public MScanner(String file) throws Exception {
			br = new BufferedReader(new FileReader(file));
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public int[] takearr(int n) throws IOException {
			int[] in = new int[n];
			for (int i = 0; i < n; i++)
				in[i] = nextInt();
			return in;
		}

		public long[] takearrl(int n) throws IOException {
			long[] in = new long[n];
			for (int i = 0; i < n; i++)
				in[i] = nextLong();
			return in;
		}

		public Integer[] takearrobj(int n) throws IOException {
			Integer[] in = new Integer[n];
			for (int i = 0; i < n; i++)
				in[i] = nextInt();
			return in;
		}

		public Long[] takearrlobj(int n) throws IOException {
			Long[] in = new Long[n];
			for (int i = 0; i < n; i++)
				in[i] = nextLong();
			return in;
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}

		public char nextChar() throws IOException {
			return next().charAt(0);
		}

		public Long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

		public void waitForInput() throws InterruptedException {
			Thread.sleep(3000);
		}
	}
}