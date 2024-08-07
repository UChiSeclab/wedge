import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

//import graph_representation.edge;
public class hacker49 {
	public static int r1=0;
	static class FastReader 
    { 
        BufferedReader br; 
        StringTokenizer st; 
  
        public FastReader() 
        { 
            br = new BufferedReader(new
                     InputStreamReader(System.in)); 
        } 
  
        String next() 
        { 
            while (st == null || !st.hasMoreElements()) 
            { 
                try
                { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException  e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
  
        int nextInt() 
        { 
            return Integer.parseInt(next()); 
        } 
  
        long nextLong() 
        { 
            return Long.parseLong(next()); 
        } 
  
        double nextDouble() 
        { 
            return Double.parseDouble(next()); 
        } 
  
        String nextLine() 
        { 
            String str = ""; 
            try
            { 
                str = br.readLine(); 
            } 
            catch (IOException e) 
            { 
                e.printStackTrace(); 
            } 
            return str; 
        } 
    } 
	static HashMap<Long,Integer> e=new HashMap<>();
	public static void main(String[] args) {
		OutputStream outputStream =System.out;
	    PrintWriter out =new PrintWriter(outputStream);
		FastReader s=new FastReader();
int n=s.nextInt();
int[] a=new int[n];
for(int i=0;i<n;i++) {
	a[i]=s.nextInt();
}
Arrays.sort(a);
int count=0;
int[] f=new int[150002];
	for(int i=n-1;i>=0;i--) {
		if(f[a[i]]==0) {
			count++;
			f[a[i]]=1;
		}else {
			if(a[i]-1>=1 && f[a[i]-1]==0) {
				f[a[i]-1]=1;
				count++;
			}else {
				if(a[i]+1<=150001 && f[a[i]+1]==0) {
					f[a[i]+1]=1;
					count++;
				}
			}
			
		}
	}
	out.println(count);
 					out.close();
	}
static int[] is_prime=new int[1001];
	public static ArrayList<Integer> primes=new ArrayList<>();
 	public static void sieve() {
		long maxN=1000;
		for(int i=1;i<=maxN;i++) {
			is_prime[i]=1;
		}
		is_prime[0]=0;
		is_prime[1]=0;
		for(long i=2;i*i<=maxN;i++) {
			if(is_prime[(int) i]==1) {
//				primes.add((int) i);
				for(long j=i*i;j<=maxN;j+=i) {
					is_prime[(int) j]=0;
				}
			}
		}
//		for(int i=1;i<=1000000;i++) {
//			pre[i]=pre[i-1]+is_prime[i];
//		}
		for(int i=0;i<=maxN;i++) {
			if(is_prime[i]==1) {
				primes.add(i);
			}
		}
//		int count=0;
//		for(long i=1;i<=maxN;i++) {
//			if(is_prime[(int) i]==1) {
//				count++;
//			}
//			if(is_prime[count]==1) {
//				arr[(int) i]=1;
//			}else {
//				arr[(int)i]=0;
//			}
//		}
	}
	static int[] r=new int[100001];
	static class pair5{
		private int a;
		private int b;
		pair5(int a,int b){
			this.a=a;
			this.b=b;
		}
	}
	static int[] dx= {0,-1,0,1};
	static int[] dy= {1,0,-1,0};
	static boolean is(int x,int y,int n,int m) {
		if(x<=n && x>=1 && y<=m && y>=1) {
			return true;
			
		}
		return false;
	}
//	static ArrayList<pair5>[] t=new ArrayList[51];
	static int[] c=new int[200001];
	static char[][] a=new char[51][51];
	static int[][] vis=new int[51][51];
	static int g=10000000;
	static int count1=0;
	static boolean p=false;
	static void dfs(pair5 node,char par,int count,int n,int m,int u,int v) {
		int x=node.a;
		int y=node.b;
		vis[node.a][node.b]=1;
		count++;
		for(int i=0;i<4;i++) {
			if(is(x+dx[i],y+dy[i],n,m) && vis[x+dx[i]][y+dy[i]]==0 && a[x+dx[i]][y+dy[i]]==par ) {
				dfs(new pair5(x+dx[i],y+dy[i]),par,count,n,m,u,v);
			}else {
				if(is(x+dx[i],y+dy[i],n,m) && vis[x+dx[i]][y+dy[i]]==1 && a[x+dx[i]][y+dy[i]]==par && count>=4 && x+dx[i]==u && y+dy[i]==v) {
					p=true;
//					System.out.println(x+" "+y +" "+dx[i]+" "+dy[i]);
				}
				
			}
		}
//		count1++;
//		g=Math.min(c[node], g);
		
	}

	static class pair implements Comparable<pair>{
		private long i;
		private long v;
		pair(long i,long v){
			this.i=i;
			this.v=v;
		}
		public int compareTo(pair o) {
//			if(o.v<this.v) {
//				return -1;
//			}else {
//				return 1;
//			}
			return Long.compare(this.v, o.v);
		}
		
	}
	
	static int nextPowerOf2(int n) 
	{ 
	    n--; 
	    n |= n >> 1; 
	    n |= n >> 2; 
	    n |= n >> 4; 
	    n |= n >> 8; 
	    n |= n >> 16; 
	    n++; 
	      
	    return n; 
	} 
//	static class node{
//		private int a;
//		private int b;
//		private int c;
//		private int d;
//		
//	}
	
	
	public static class node{
		private int a;
		private int b;
		node(int a,int b){
			this.a=a;
			this.b=b;
		}
	}

	public static long GCD(long a,long b) {
		if(b==(long)0) {
			return a;
		}
		return GCD(b , a%b);
	}
//	public static void Create(node[] arr,node[] segtree,int low,int high,int pos) {
//		if(low==high) {
//			segtree[pos]=arr[low];
//			return;
//		}
//		int mid=(low+high)/2;
//		Create(arr,segtree,low,mid,2*pos+1);
//		Create(arr,segtree,mid+1,high,2*pos+2);
//		if(segtree[2*pos+1].a+segtree[2*pos+2].a>=10) {
//			int a=segtree[2*pos+1].a;
//			int b=segtree[2*pos+2].a;
//			int c=segtree[2*pos+1].b;
//			int d=segtree[2*pos+2].b;
//			segtree[pos]=new node((a+b)%10,1+c+d);
//		}else {
//			int a=segtree[2*pos+1].a;
//			int b=segtree[2*pos+2].a;
//			int c=segtree[2*pos+1].b;
//			int d=segtree[2*pos+2].b;
//			segtree[pos]=new node(a+b,c+d);
//			
//		}
//	}
//	public static node Query(node[] segtree,int qlow,int qhigh,int low,int high,int pos) {
//		if(low>=qlow && high<=qhigh) {
//			return segtree[pos];
//		}
//		if(qhigh<low || qlow>high) {
//			return new node(0,0);
//		}
//		int mid=(low+high)/2;
//	 node a=Query(segtree,qlow,qhigh,low,mid,2*pos+1);
//	node b=Query(segtree,qlow,qhigh,mid+1,high,2*pos+2);
//	int c=a.a;
//	int d=b.a;
//	if(c+d>=10) {
//	node e=new node((c+d)%10,1+a.b+b.b);
//	return e;
//	}else {
//		node e=new node(c+d,a.b+b.b);
//		return e;
//	}
//	}
//	
	static class pair1 implements Comparable<pair1>{
		private int a;
		private int b;
		private int c;
		private int d;
		pair1(int a,int b,int c,int d){
			this.a=a;
			this.b=b;
			this.c=c;
			this.d=d;
		}
		public int compareTo(pair1 o) {
			if(this.a<o.a) {
				return 1;
			}else if(this.a==o.a) {
				if(this.b<o.b) {
					return 1;
				}else {
					return -1;
				}
			}else {
				return -1;
			}
				
		}
	}
	public static void update(long[] segtree,int low,int high,int qlow,int qhigh,long val,int pos) {
		if(qlow>high || qhigh<low) {
			return ;
		}
		if(low>=qlow &&qhigh>=high) {
			segtree[pos]+=val;
			return;
		}
		int mid=(low+high)/2;
		update(segtree,low,mid,qlow,qhigh,val,2*pos);
		update(segtree,mid+1,high,qlow,qhigh,val,2*pos+1);
		
	}
	public static long query(long[] segtree,int low,int high,int index,int pos) {
		
		if(low==high) {
			return segtree[pos];
		}
		int mid=(low+high)/2;
		long res=0;
		if(index<=mid) {
			res= query(segtree,low,mid,index,2*pos);
		}else {
			res=query(segtree,mid+1,high,index,2*pos+1);
		}
		return res+segtree[pos];
	}
	
//	public static class pair5{
//		private int a;
//		private int b;
//		pair5(int a,int b){
//			this.a=a;
//			this.b=b;
//		}
//		public int compareTo(pair5 o) {
//			return Integer.compare(o.b, this.b);
//		}
//	}
//	public static pair5[] merge_sort(pair5[] A, int start, int end) {
//		if (end > start) {
//			int mid = (end + start) / 2;
//			pair5[] v = merge_sort(A, start, mid);
//			pair5[] o = merge_sort(A, mid + 1, end);
//			return (merge(v, o));
//		} else {
//			pair5[] y = new pair5[1];
//			y[0] = A[start];
//			return y;
//		}
//	}
//	public static pair5[] merge(pair5 a[], pair5 b[]) {
////		int count=0;
//		pair5[] temp = new pair5[a.length + b.length];
//		int m = a.length;
//		int n = b.length;
//		int i = 0;
//		int j = 0;
//		int c = 0;
//		while (i < m && j < n) {
//			if (a[i].a < b[j].a) {
//				temp[c++] = a[i++];
//			} else {
//				temp[c++] = b[j++];
//			}
//		}
//		while (i < m) {
//			temp[c++] = a[i++];
//		}
//		while (j < n) {
//			temp[c++] = b[j++];
//		}
//		return temp;
//	}
////	
	public static int upper_bound(int[] a ,int n,int x) {
		int l=-1;
		int r=n;
		while(r>l+1) {
			int mid=(l+r)/2;
			if(a[mid]<x) {
				l=mid;
			}else {
				r=mid;
			}
		}
		return r;
				
	}

//	
//	
	public static long lower_bound(long n,long i) {
		long l=0;
		long r=n+1;
		while(r>l+1) {
			long mid=(l+r)/2;
//			System.out.println(mid);
//			mid*=mid;
			long y=2*mid-1;
			long r1=(y*y);
			if((r1-1)/2<i) {
				l=mid;
			}else {
				r=mid;
			}
//			System.out.println(l);
//			if(a[mid].i<x) {
//				l=mid;
//			}else {
//				r=mid;
//			}
		}
		return l;
				
	}
	public static int ty(int[] a, int x, int y)
    {
        Arrays.sort(a);
        int count=0;
        int n=a.length;
        for(int i=0;i<n;i++) {
        	int g=0;
        	if(x%a[i]==0) {
        		g=x/a[i];
        	}else {
        		g=x/a[i]+1;
        	}
        	int k1=upper_bound(a,n,g);
        	if(k1==n) {
        		count+=0;
        	}else {
        		int g1=0;
            	if(y%a[i]==0) {
            		g1=y/a[i];
            	}else {
            		g1=y/a[i]+1;
            	}
//            	int k2=lower_bound(a,n,g1);
//            	if(k2>=k1) {
//            		count+=(k2-k1+1);
//            	}
        	}
//        System.out.println(count);
        }
        return count;
        
    }
	static boolean isValid(int x,int y,int n,int m) {
		if(x>=1 && x<=n && y>=1 && y<=m) {
			return true;
		}else {
			return false;
		}
	}
	
	static long[] fac=new long[1000001];
	static void fac() {
		fac[0]=1;
		for(int i=1;i<=1000000;i++) {
//			fac[i]=((fac[i-1]%mod)*(i%mod))%mod;
		}
	}
	static ArrayList<Integer>[] f=new ArrayList[2001];
	public static void init(int n) {
		
		for(int j=1;j<=n;j++) {
		for (int l=1; l<=Math.sqrt(j); l++)
        {
            if (j%l==0)
            {
                // If divisors are equal, print only one
                if (j/l == l) {
                    f[j].add(l);}
      
                else { // Otherwise print both
//                    System.out.print(i+" " + n/i + " " );
            f[j].add(l);
            f[j].add(j/l);
                }
                
            }
        }
		
		}
		
	}
	
	public static long[] merge_sort(long[] A, int start, int end) {
		if (end > start) {
			int mid = (end + start) / 2;
			long[] v = merge_sort(A, start, mid);
			long[] o = merge_sort(A, mid + 1, end);
			return (merge(v, o));
		} else {
			long[] y = new long[1];
			y[0] = A[start];
			return y;
		}
	}
	public static long[] merge(long a[], long b[]) {
//		int count=0;
		long[] temp = new long[a.length + b.length];
		int m = a.length;
		int n = b.length;
		int i = 0;
		int j = 0;
		int c = 0;
		while (i < m && j < n) {
			if (a[i] < b[j]) {
				temp[c++] = a[i++];
			
			} else {
				temp[c++] = b[j++];
			}
		}
		while (i < m) {
			temp[c++] = a[i++];
		}
		while (j < n) {
			temp[c++] = b[j++];
		}
		return temp;
	}
	
	public static long[] merge_sort1(long[] A, int start, int end) {
		if (end > start) {
			int mid = (end + start) / 2;
			long[] v = merge_sort1(A, start, mid);
			long[] o = merge_sort1(A, mid + 1, end);
			return (merge1(v, o));
		} else {
			long[] y = new long[1];
			y[0] = A[start];
			return y;
		}
	}
	public static long[] merge1(long a[], long b[]) {
//		int count=0;
		long[] temp = new long[a.length + b.length];
		int m = a.length;
		int n = b.length;
		int i = 0;
		int j = 0;
		int c = 0;
		while (i < m && j < n) {
			if (a[i] < b[j]) {
				temp[c++] = a[i++];
			
			} else {
				temp[c++] = b[j++];
			}
		}
		while (i < m) {
			temp[c++] = a[i++];
		}
		while (j < n) {
			temp[c++] = b[j++];
		}
		return temp;
	}
	
	static long MOD=1000000007;
	static class pair2{
		private long a;
		private int b;
		pair2(long a,int b){
			this.a=a;
			this.b=b;
		}
	}
	




//	for(int i=2;i<=100000;i++) {
		
	
}