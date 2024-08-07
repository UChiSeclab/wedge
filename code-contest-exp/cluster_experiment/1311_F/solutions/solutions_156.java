import java.util.*;
import java.io.*;

public class Main {
	public static void main(String args[]) {new Main().run();}

	FastReader in = new FastReader();
	PrintWriter out = new PrintWriter(System.out);
	void run(){
		work();
		out.flush();
	}
	long mod=1000000007;
	long gcd(long a,long b) {
		return a==0?b:gcd(b%a,a);
	}
	long[] A;
	long[] B;
	int n;
	void work() {
		n=ni();
		A=new long[n+1];
		B=new long[n+1];
		long[] P=na(n);
		long[] V=na(n);
		long[][] R=new long[n][2];
		for(int i=0;i<n;i++) {
			R[i]=new long[] {V[i],P[i]};
		}
		Arrays.sort(R,new Comparator<long[]>() {
			public int compare(long[] A1,long[] A2) {
				return (int)(A1[1]-A2[1]);
			}
		});
		long[][] rec=new long[n][3];
		for(int i=0;i<n;i++) {
			rec[i]=new long[] {R[i][0],R[i][1],i+1};
		}
		long ret=0;
		Arrays.sort(rec,new Comparator<long[]>() {
			@Override
			public int compare(long[] A1, long[] A2) {
				return (int)(A1[0]-A2[0]);
			}
		});
		for(int i=0;i<n;i++) {
			int idx=(int)rec[i][2];
			update(idx,rec[i][1],1);
		}
		for(int i=0;i<n;i++) {
			int idx=(int)rec[i][2];
			long p=rec[i][1];
			long sum=query1(idx+1,n);
			long cnt=query2(idx+1,n);
			ret+=sum-cnt*p;
			
			update(idx,-p,-1);
		}
		out.println(ret);
	}
	private long query1(int s, int e) {
		return query1(e)-query1(s-1);
	}
	
	private long query2(int s, int e) {
		return query2(e)-query2(s-1);
	}
	
	long query1(int x) {
		long ret=0;
		for(;x>0;x-=lowbit(x)) {
			ret+=A[x];
		}
		return ret;
	}
	
	long query2(int x) {
		long ret=0;
		for(;x>0;x-=lowbit(x)) {
			ret+=B[x];
		}
		return ret;
	}
	
	int lowbit(int x){
		return x&-x;
	}
	
	private void update(int x, long v,long c) {
		for(;x<=n;x+=lowbit(x)) {
			A[x]+=v;
			B[x]+=c;
		}
	}


	
	//input
	private ArrayList<Integer>[] ng(int n, int m) {
		ArrayList<Integer>[] graph=(ArrayList<Integer>[])new ArrayList[n];
		for(int i=0;i<n;i++) {
			graph[i]=new ArrayList<>();
		}
		for(int i=1;i<=m;i++) {
			int s=in.nextInt()-1,e=in.nextInt()-1;
			graph[s].add(e);
			graph[e].add(s);
		}
		return graph;
	}

	private ArrayList<long[]>[] ngw(int n, int m) {
		ArrayList<long[]>[] graph=(ArrayList<long[]>[])new ArrayList[n];
		for(int i=0;i<n;i++) {
			graph[i]=new ArrayList<>();
		}
		for(int i=1;i<=m;i++) {
			long s=in.nextLong()-1,e=in.nextLong()-1,w=in.nextLong();
			graph[(int)s].add(new long[] {e,w,i});
			graph[(int)e].add(new long[] {s,w});
		}
		return graph;
	}

	private int ni() {
		return in.nextInt();
	}

	private long nl() {
		return in.nextLong();
	}
	
	private String ns() {
		return in.next();
	}

	private long[] na(int n) {
		long[] A=new long[n];
		for(int i=0;i<n;i++) {
			A[i]=in.nextLong();
		}
		return A;
	}
	private int[] nia(int n) {
		int[] A=new int[n];
		for(int i=0;i<n;i++) {
			A[i]=in.nextInt();
		}
		return A;
	}
}	

class FastReader
{
	BufferedReader br;
	StringTokenizer st;

	public FastReader()
	{
		br=new BufferedReader(new InputStreamReader(System.in));
	}


	public String next() 
	{
		while(st==null || !st.hasMoreElements())//回车，空行情况
		{
			try {
				st = new StringTokenizer(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return st.nextToken();
	}

	public int nextInt() 
	{
		return Integer.parseInt(next());
	}

	public long nextLong()
	{
		return Long.parseLong(next());
	}
}
