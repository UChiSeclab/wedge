import java.io.*;
import java.util.*;

public class Main{
	static long hash(int x,int y) {
		return x*500001l+y;
	}
	static int gcd(int a, int b) {

		if (b == 0)
			return a;
		return gcd(b, a % b);
	}
	static void main() throws Exception{
		int n=sc.nextInt();
		char[]in=sc.nextLine().toCharArray();
		int[]pref=new int[2];
		HashMap<Long, Integer>map=new HashMap<Long, Integer>();
		map.put(hash(0, 0), 0);
		int[]ans=new int[n+1];
		for(int i=0;i<n;i++) {
			if(in[i]=='D') {
				pref[0]++;
			}
			else {
				pref[1]++;
			}
			if(pref[0]==0 || pref[1]==0) {
				ans[i+1]=Math.max(pref[0], pref[1]);
				map.put(hash(pref[0], pref[1]), i+1);
			}
			else {
				int gcd=gcd(pref[0], pref[1]);
				int x=pref[0]/gcd,y=pref[1]/gcd;
				long h=hash(x, y);
				if(map.containsKey(h)) {
					ans[i+1]=ans[map.get(h)]+1;
				}
				else {
					ans[i+1]=1;
				}
				map.put(h, i+1);
			}
			pw.print(ans[i+1]+" ");
		}
		pw.println();
	}
	public static void main(String[] args) throws Exception{
		sc=new MScanner(System.in);
		pw = new PrintWriter(System.out);
		int tc=1;
        tc=sc.nextInt();
		for(int i=1;i<=tc;i++) {
//			pw.printf("Case #%d: ", i);
			main();
		}
		
		pw.flush();
	}
	static PrintWriter pw;
	static MScanner sc;
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
		public int[] intArr(int n) throws IOException {
	        int[]in=new int[n];for(int i=0;i<n;i++)in[i]=nextInt();
	        return in;
		}
		public long[] longArr(int n) throws IOException {
	        long[]in=new long[n];for(int i=0;i<n;i++)in[i]=nextLong();
	        return in;
		}
		public int[] intSortedArr(int n) throws IOException {
	        int[]in=new int[n];for(int i=0;i<n;i++)in[i]=nextInt();
	        shuffle(in);
	        Arrays.sort(in);
	        return in;
		}
		public long[] longSortedArr(int n) throws IOException {
	        long[]in=new long[n];for(int i=0;i<n;i++)in[i]=nextLong();
	        shuffle(in);
	        Arrays.sort(in);
	        return in;
		}
		public Integer[] IntegerArr(int n) throws IOException {
	        Integer[]in=new Integer[n];for(int i=0;i<n;i++)in[i]=nextInt();
	        return in;
		}
		public Long[] LongArr(int n) throws IOException {
	        Long[]in=new Long[n];for(int i=0;i<n;i++)in[i]=nextLong();
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
	 
		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}
	 
		public boolean ready() throws IOException {
			return br.ready();
		}
	 
		public void waitForInput() throws InterruptedException {
			Thread.sleep(3000);
		}
		
	}
	static void dbg(int[]in) {
		System.out.println(Arrays.toString(in));
	}
	static void dbg(long[]in) {
		System.out.println(Arrays.toString(in));
	}
	static void sort(int[]in) {
		shuffle(in);
		Arrays.sort(in);
	}
	static void sort(long[]in) {
		shuffle(in);
		Arrays.sort(in);
	}
	static void shuffle(int[]in) {
		for(int i=0;i<in.length;i++) {
			int idx=(int)(Math.random()*in.length);
			int tmp=in[i];
			in[i]=in[idx];
			in[idx]=tmp;
		}
	}
	static void shuffle(long[]in) {
		for(int i=0;i<in.length;i++) {
			int idx=(int)(Math.random()*in.length);
			long tmp=in[i];
			in[i]=in[idx];
			in[idx]=tmp;
		}
	}
}