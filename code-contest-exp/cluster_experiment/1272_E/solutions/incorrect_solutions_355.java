import java.io.*;
import java.util.*;
public class Main {
	static ArrayList<Integer>[] g;
	static boolean[] v;
	static int[] ans;
	static int[] l;
	static int inf=Integer.MAX_VALUE/2;
	static int func(int curr,int par) {
//		System.out.println(curr);
		if(l[curr]%2!=par) {
			return 0;
		}
		v[curr]=true;
		int mn=inf;
		for(int child:g[curr]) {
			if(!v[child]) {
				if(ans[child]!=-1 && l[child]%2==par) {
					mn=Math.min(mn,ans[child]);
				}
				else mn=Math.min(mn,func(child,par));
			}
		}
		ans[curr]=mn+1;
		if(mn>=inf) {
			ans[curr]=-1;
		}
		v[curr]=false;
//		System.out.println(Arrays.toString(ans));
		return mn+1;
	}
	public static void main(String[] args) throws IOException 
	{ 
		FastScanner f = new FastScanner(); 
		int ttt=1;
//		ttt=f.nextInt();
		PrintWriter out=new PrintWriter(System.out);
		outer:for(int tt=0;tt<ttt;tt++) {
			int n=f.nextInt();
			l=f.readArray(n);
			g=new ArrayList[n];
			v=new boolean[n];
			ans=new int[n];
			Arrays.fill(ans,-1);
			for(int i=0;i<n;i++) g[i]=new ArrayList<>();
			for(int i=0;i<n;i++) {
				if(i-l[i]>-1) {
					g[i].add(i-l[i]);
				}
				if(i+l[i]<n) {
					g[i].add(i+l[i]);
				}
			}
			for(int i=0;i<n;i++) {
				if(ans[i]==-1) {
//					System.out.println("start");
					func(i,l[i]%2);
				}
			}
			for(int i:ans) {
				System.out.print(i+" ");
			}
			System.out.println();
		}
		out.close();
	} 
	static void sort(int[] p) {
        ArrayList<Integer> q = new ArrayList<>();
        for (int i: p) q.add( i);
        Collections.sort(q);
        for (int i = 0; i < p.length; i++) p[i] = q.get(i);
    }
    static long gcd(long a,long b) {
    	if(a==0) return b;
    	return gcd(b%a,a);
    }
	static class FastScanner {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st=new StringTokenizer("");
		String next() {
			while (!st.hasMoreTokens())
				try {
					st=new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		int[] readArray(int n) {
			int[] a=new int[n];
			for (int i=0; i<n; i++) a[i]=nextInt();
			return a;
		}
		long nextLong() {
			return Long.parseLong(next());
		}
		double nextDouble() {
			return Double.parseDouble(next());
		}
		long[] readLongArray(int n) {
			long[] a=new long[n];
			for (int i=0; i<n; i++) a[i]=nextLong();
			return a;
		}
	}
} 	