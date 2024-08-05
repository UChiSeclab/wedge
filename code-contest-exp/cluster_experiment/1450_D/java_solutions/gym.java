import java.io.*;
import java.util.*;

public class gym{
	static void main() throws Exception{
		int n=sc.nextInt();
		int[]in=new int[n];
		int[]ans=new int[n];
		int[]occ=new int[n];
		int[]idx=new int[n];
		ans[0]=1;
		for(int i=0;i<n;i++) {
			int x=sc.nextInt()-1;
			occ[x]++;
			idx[x]=i;
			if(occ[x]>1) {
				ans[0]=0;
			}
		}
		int l=0,r=n-1;
		for(int i=0,j=n-1;i<n-1;i++,j--) {
			if(occ[i]==0) {
				break;
			}
			ans[j]=1;
			if(occ[i]>1) {
				break;
			}
			if(idx[i]!=l && idx[i]!=r) {
				break;
			}
			if(idx[i]==l) {
				l++;
			}
			else {
				r--;
			}
			
		}
		for(int i=0;i<n;i++)pw.print(ans[i]);
		pw.println();
		
	}
	public static void main(String[] args) throws Exception{
		sc=new MScanner(System.in);
		pw = new PrintWriter(System.out);
		int tc=1;
		tc=sc.nextInt();
		while(tc-->0)
			main();
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