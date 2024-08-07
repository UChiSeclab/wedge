import java.util.*;
import java.io.*;
 
public class Main {
    static ArrayList<Integer>[] graph;
    static int[] vis;
    static Queue<Integer>hse=new LinkedList< Integer>();
    static Queue<Integer>hso=new LinkedList< Integer>();
    static Queue<Integer>q=new LinkedList< Integer>();
    static Queue<Integer>Q=new LinkedList< Integer>();
	public static void main(String[] args) throws Exception {
	   int n=sc.nextInt();
	  int[] a=new int[n];
	  
		graph=new ArrayList[n];
		vis=new int[n];
		Arrays.fill(vis, -1);
		for(int i=1;i<=n;i++) {
			graph[i-1]=new ArrayList<Integer>();
		}
		for(int i=1;i<=n;i++) {
			a[i-1]=sc.nextInt();
			if(a[i-1]%2==0) {
				hse.add(i-1);
				q.add(0);
			}else {
				hso.add(i-1);
				Q.add(0);
			}
			if(i-a[i-1]>0) {
				graph[i-a[i-1]-1].add(i-1);
			}
			if(a[i-1]+i<n+1) {
				graph[a[i-1]+i-1].add(i-1);
			}
		}
//		for(int i=0;i<n;i++) {
//			pw.println(i+"*"+graph[i]+"   "+graphinvers[i]);
//		}
		while(!hse.isEmpty()) {
			int z=hse.poll();
			int Z=q.poll();
			for(int i:graph[z]) {
				if(a[i]%2!=0) {
					if(vis[i]==-1) {
						vis[i]=Z+1;
						hse.add(i);
						q.add(Z+1);
					}
					
				}
				
				
				}}
			
		while(!hso.isEmpty()) {
			int z=hso.poll();
			int Z=Q.poll();
			for(int i:graph[z]) {
				if(a[i]%2!=1) {
					if(vis[i]==-1) {
						vis[i]=Z+1;
						hso.add(i);
						Q.add(Z+1);
					}
					
				}
				
				
				}}
		//pw.println(p+"**"+hs);
		
		pw.print(vis[0]);
		for(int i=1;i<n;i++) {
			pw.print(" "+vis[i]);
		}
		pw.println();
		pw.close();
	}
 
 
	static class Scanner {
		StringTokenizer st;
		BufferedReader br;
 
		public Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}
 
		public Scanner(FileReader r) {
			br = new BufferedReader(r);
		}
 
		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
 
		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
 
		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}
 
		public String nextLine() throws IOException {
			return br.readLine();
		}
 
		public double nextDouble() throws IOException {
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if (x.charAt(0) == '-') {
				neg = true;
				start++;
			}
			for (int i = start; i < x.length(); i++)
				if (x.charAt(i) == '.') {
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				} else {
					sb.append(x.charAt(i));
					if (dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg ? -1 : 1);
		}
 
		public boolean ready() throws IOException {
			return br.ready();
		}
 
	}
 
	static class pair implements Comparable<pair> {
		int x;
		int y;
 
		public pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
 
		public String toString() {
			return x + " " + y;
		}
 
		public int compareTo(pair other) {
			if (this.x == other.x) {
				return this.y - other.y;
			} else {
				return this.x - other.x;
			}
		}
	}
 
	static class tuble implements Comparable<tuble> {
		int x;
		int y;
		int z;
 
		public tuble(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
 
		public String toString() {
			return x + " " + y + " " + z;
		}
 
		public int compareTo(tuble other) {
			if (this.x == other.x) {
				return this.y - other.y;
			} else {
				return this.x - other.x;
			}
		}
	}
 
	public static long GCD(long a, long b) {
		if (b == 0)
			return a;
		if (a == 0)
			return b;
		return (a > b) ? GCD(a % b, b) : GCD(a, b % a);
	}
 
	public static long LCM(long a, long b) {
		return a * b / GCD(a, b);
	}
 
	static long Pow(long a, int e, int mod)	// O(log e)
    {
        a %= mod;
        long res = 1;
        while(e > 0)
        {
            if((e & 1) == 1)
                res = (res * a) % mod;
            a = (a * a) % mod;
            e >>= 1;
        }
        return res;
    }
    
    static long nc(int n,int r){
        if (n<r)return 0;
        long v = fac[n];
        v*=Pow(fac[r],mod-2,mod);
        v%=mod;
        v*=Pow(fac[n-r],mod-2,mod);
        v%=mod;
        return v;
    }
 
	public static boolean isprime(long a) {
		if (a == 0 || a == 1) {
			return false;
		}
		if (a == 2) {
			return true;
		}
		for (int i = 2; i < Math.sqrt(a) + 1; i++) {
			if (a % i == 0) {
				return false;
			}
		}
		return true;
	}
	static long fac[];
	static int mod=998244353;
	static Scanner sc = new Scanner(System.in);
	static PrintWriter pw = new PrintWriter(System.out);
}