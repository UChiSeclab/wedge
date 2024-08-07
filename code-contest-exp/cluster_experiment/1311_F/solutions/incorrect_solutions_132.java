import java.io.*;
import java.util.*;


public class  Main{
	static class pair implements Comparable<pair>{
		int x,v;
		pair(int xx,int vv){
			x=xx;v=vv;
		}
		@Override
		public int compareTo(pair o) {
			// TODO Auto-generated method stub
			return x-o.x;
		}
		
		
	}
	static class ftree{
		int n;
		long[]ft;
		ftree(int z){
			ft=new long[z+1];
			n=z;
		}
		void updatesum(int idx,int k) {
			while(idx<=n) {
				ft[idx]+=k;
				idx+=(idx&(-1*idx));
			}
			
		}
		long querysum(int idx) {
			long sum=0;
			while(idx>0) {
				sum+=ft[idx];
				idx-=(idx&(-1*idx));
			}
			return sum;
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		MScanner sc=new MScanner(System.in);
		PrintWriter pw=new PrintWriter(System.out);
		int n=sc.nextInt();
		int[]x=sc.takearr(n);
		int[]v=sc.takearr(n);
		pair[]in=new pair[n];
		Integer[]tmp=new Integer[n];
		for(int i=0;i<n;i++) {
			tmp[i]=v[i];
		}
		Arrays.sort(tmp);
		for(int i=0;i<n;i++) {
			in[i]=new pair(x[i], v[i]);
		}
		Arrays.sort(in);
		HashMap<Integer, Integer>map=new HashMap<Integer, Integer>();
		for(int i=0;i<n;i++) {
			map.put(tmp[i], i+1);
		}
		long ans=0;
		ftree ftOcc=new ftree(n+7);
		ftree ftSum=new ftree(n+7);
		for(int i=0;i<n;i++) {
			int curx=in[i].x,curv=in[i].v;
			if(v[i]>=0) {
				long cnt=ftOcc.querysum(map.get(curv));
				long sum=ftSum.querysum(map.get(curv));
				ans+=(curx*1l*cnt-sum);
			}
			ftOcc.updatesum(map.get(curv), 1);
			ftSum.updatesum(map.get(curv), curx);
		}
		
		pw.println(ans);
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
	        int[]in=new int[n];for(int i=0;i<n;i++)in[i]=nextInt();
	        return in;
		}
		public long[] takearrl(int n) throws IOException {
	        long[]in=new long[n];for(int i=0;i<n;i++)in[i]=nextLong();
	        return in;
		}
		public Integer[] takearrobj(int n) throws IOException {
	        Integer[]in=new Integer[n];for(int i=0;i<n;i++)in[i]=nextInt();
	        return in;
		}
		public Long[] takearrlobj(int n) throws IOException {
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