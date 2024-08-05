import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
public class gym {
	static int m=(int)(1e9)+9;
	public static long power[];
	static PrintWriter pw;
	static class SegmentTree {		// 1-based DS, OOP
		
		int N; 			//the number of elements in the array as a power of 2 (i.e. after padding)
		long[] array, sTree, lazy;
		static long p=31;
		static long m=(long)(1e9)+9;
		SegmentTree(long[] in)		
		{
			array = in; N = in.length - 1;
			
			sTree = new long[N<<1+1];		//no. of nodes = 2*N - 1, we add one to cross out index zero
			lazy = new long[N<<1+1];
			build(1,1,N);
		}
		
		void build(int node, int b, int e)	// O(n)
		{
			if(b == e)					
				sTree[node] = (array[b]*power[b])%m;
			else						
			{
				int mid = b + e >> 1;
				build(node<<1,b,mid);
				build(node<<1|1,mid+1,e);
				sTree[node] = (sTree[node<<1]+sTree[node<<1|1])%m;
			}
		}
		
		
		void update_point(int index, int val)			// O(log n)
		{
			index += N - 1;				
			sTree[index] += val;			
			while(index>1)				
			{
				index >>= 1;
				sTree[index] = sTree[index<<1] + sTree[index<<1|1];		
			}
		}
		
		
		void update_range(int i, int j, int val)		// O(log n) 
		{
			update_range(1,1,N,i,j,val);
		}
		
		void update_range(int node, int b, int e, int i, int j, int val)
		{
			if(i > e || j < b)		
				return;
			if(b >= i && e <= j)		
			{
				sTree[node] = (val*power[b])%m;			
				//lazy[node] += val;				
			}							
			else		
			{
				int mid = b + e >> 1;
				//propagate(node, b, mid, e);
				update_range(node<<1,b,mid,i,j,val);
				update_range(node<<1|1,mid+1,e,i,j,val);
				sTree[node] = (sTree[node<<1] + sTree[node<<1|1])%m;		
			}
			
		}
		void propagate(int node, int b, int mid, int e)		
		{
			lazy[node<<1] += lazy[node];
			lazy[node<<1|1] += lazy[node];
			sTree[node<<1] += (mid-b+1)*lazy[node];		
			sTree[node<<1|1] += (e-mid)*lazy[node];		
			lazy[node] = 0;
		}
		
		long query(int i, int j)
		{
			return query(1,1,N,i,j);
		}
		
		long query(int node, int b, int e, int i, int j)	// O(log n)
		{
			if(i>e || j <b)
				return 0;		
			if(b>= i && e <= j)
				return sTree[node];
			int mid = b + e >> 1;
			//propagate(node, b, mid, e);
			long q1 = query(node<<1,b,mid,i,j);
			long q2 = query(node<<1|1,mid+1,e,i,j);
			return (q1 + q2)%m;	
					
		}
		
		
		
		
	}
	static long power(long x, long y,long m)  
    { 
        if (y == 0) 
            return 1l; 
          
        long p = power(x, y / 2, m) % m; 
        p = (p * p) % m; 
      
        if (y % 2 == 0) 
            return p; 
        else
            return (x * p) % m; 
    }
	public static void main(String[] args) throws Exception {
		//BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		MScanner sc=new MScanner(System.in);
		pw=new PrintWriter(System.out);
		int n=sc.nextInt();
		int[]in=new int[n];
		for(int i=0;i<n;i++) {
			in[i]=sc.nextInt();
		}
		int[]sushi=new int[n];int c=0;
		int i=0;
		while(i<n) {
			if(in[i]==1) {
				int count=0;
				while(i<n && in[i]==1) {
					count++;i++;
				}
				sushi[c++]=count;
			}
			else {
				int count=0;
				while(i<n && in[i]==2) {
					count++;i++;
				}
				sushi[c++]=count;
			}
		}
		if(c==1) {
			pw.println(sushi[0]);
		}
		else {
			int max=0;
			for(int j=1;j<c;j++) {
				max=Math.max(max,Math.min(sushi[j],sushi[j-1])*2);
			}
			pw.println(max);
		}
		pw.flush();
	}
	static class Vector {

		double x, y; 

		Vector(double a, double b) { x = a; y = b; }

		Vector(point a, point b) { this(b.x - a.x, b.y - a.y); }

		Vector scale(double s) { return new Vector(x * s, y * s); }              //s is a non-negative value

		double dot(Vector v) { return (x * v.x + y * v.y); }

		double cross(Vector v) { return x * v.y - y * v.x; }

		double norm2() { return x * x + y * y; }

		Vector reverse() { return new Vector(-x, -y); }

		Vector normalize() 
		{ 
			double d = Math.sqrt(norm2());
			return scale(1 / d);
		}		
	}
	static class point implements Comparable<point>{
		static final double EPS = 1e-9;
		double x, y;  
		public int compareTo(point p)
		{
			if(Math.abs(x - p.x) > EPS) return x > p.x ? 1 : -1;
			if(Math.abs(y - p.y) > EPS) return y > p.y ? 1 : -1;
			return 0;
		}
		point(double a, double b) { x = a; y = b; }
		static boolean ccw(point p, point q, point r)
		{
			return new Vector(p, q).cross(new Vector(p, r)) > 0;
		}
		
		static boolean collinear(point p, point q, point r)
		{
			return Math.abs(new Vector(p, q).cross(new Vector(p, r))) < EPS;
		}
		
		static double angle(point a, point o, point b)  // angle AOB
		{
			Vector oa = new Vector(o, a), ob = new Vector(o, b);
			return Math.acos(oa.dot(ob) / Math.sqrt(oa.norm2() * ob.norm2()));
		}
		boolean between(point p, point q)
		{
			return x < Math.max(p.x, q.x) + EPS && x + EPS > Math.min(p.x, q.x)
					&& y < Math.max(p.y, q.y) + EPS && y + EPS > Math.min(p.y, q.y);
		}
	}
	public static class Polygon { 
		static final double EPS = 1e-9;
		point[] g; 			//first point = last point, counter-clockwise representation
		Polygon(point[] o) { g = o; }
		public static Polygon convexHull(point[] points)	//all points are unique, remove duplicates, edit ccw to accept collinear points
		{
			int n = points.length;
			Arrays.sort(points);
			point[] ans = new point[n<<1];
			int size = 0, start = 0;

			for(int i = 0; i < n; i++)
			{
				point p = points[i];
				while(size - start >= 2 && !point.ccw(ans[size-2], ans[size-1], p))	--size;
				ans[size++] = p;
			}
			start = --size;

			for(int i = n-1 ; i >= 0 ; i--)
			{
				point p = points[i];
				while(size - start >= 2 && !point.ccw(ans[size-2], ans[size-1], p))	--size;
				ans[size++] = p; 
			}
			//			if(size < 0) size = 0			for empty set of points
			return new Polygon(Arrays.copyOf(ans, size));			
		}
		public boolean inside(point p)	//for convex/concave polygons - winding number algorithm 
		{
			double sum = 0.0;
			for(int i = 0; i < g.length - 1; ++i)
			{
				double angle = point.angle(g[i], p, g[i+1]);
				
				if(point.ccw(p, g[i], g[i+1]))
					sum += angle;
				else
					sum -= angle;
			}
			//System.out.println(sum);
			return Math.abs(2 * Math.PI - Math.abs(sum)) < EPS;		//abs makes it work for clockwise
		}
	}
	static class tri implements Comparable<tri>{
    	double l;int idx;
    	tri(double x,int z){
    		l=x;idx=z;
    	}
		@Override
		public int compareTo(tri o) {
			if(l!=o.l) {
				if(l>o.l)return 1;
				return -1;
			}
			return idx-o.idx;
		}
		public boolean equals(tri o) {
			if(this.compareTo(o)==0)return true;
			return false;
		}
		public String toString() {
			return "("+l+" "+idx+")";
		}
    }
	static class MScanner 
	{
	    StringTokenizer st;
	    BufferedReader br;

	    public MScanner(InputStream s){    br = new BufferedReader(new InputStreamReader(s));}

	    public String next() throws IOException 
	    {
	        while (st == null || !st.hasMoreTokens()) 
	            st = new StringTokenizer(br.readLine());
	        return st.nextToken();
	    }

	    public int nextInt() throws IOException {return Integer.parseInt(next());}
	    
	    public long nextLong() throws IOException {return Long.parseLong(next());}

	    public String nextLine() throws IOException {return br.readLine();}
	    
	    public double nextDouble() throws IOException
	    {
	        String x = next();
	        StringBuilder sb = new StringBuilder("0");
	        double res = 0, f = 1;
	        boolean dec = false, neg = false;
	        int start = 0;
	        if(x.charAt(0) == '-')
	        {
	            neg = true;
	            start++;
	        }
	        for(int i = start; i < x.length(); i++)
	            if(x.charAt(i) == '.')
	            {
	                res = Long.parseLong(sb.toString());
	                sb = new StringBuilder("0");
	                dec = true;
	            }
	            else
	            {
	                sb.append(x.charAt(i));
	                if(dec)
	                    f *= 10;
	            }
	        res += Long.parseLong(sb.toString()) / f;
	        return res * (neg?-1:1);
	    }
	    
	    public boolean ready() throws IOException {return br.ready();}


	}

}