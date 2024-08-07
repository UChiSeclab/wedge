import java.util.*;
import java.io.*;

public class MovingPoints {

	public static void main(String[] args) {
		JS scan = new JS();
		int n = scan.nextInt();
		long[] xs = new long[n];
		int[] vs = new int[n];
		TreeSet<Integer> set = new TreeSet<Integer>();
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i = 0; i < n; i++) xs[i] = scan.nextLong();
		for(int i = 0; i < n; i++) {
			vs[i] = scan.nextInt();
			set.add(vs[i]);
		}
		int curr = 0;
		for(int i : set) map.put(i, curr++);
		BIT[][] b = new BIT[2][2];
		for(int i = 0; i < 2; i++)
			for(int j = 0; j < 2; j++)
				b[i][j] = new BIT(curr);
		Point[] pts = new Point[n];
		for(int i = 0; i < n; i++) 
			pts[i] = new Point(xs[i], map.get(vs[i]));
		Arrays.sort(pts);
		long ans = 0;
		for(int i = 0; i < n; i++) {
			long x = b[0][0].query(0, pts[i].v); // the sum
			long s = b[0][1].query(0, pts[i].v)*pts[i].x; // the count
			ans += s-x;
			b[0][0].update(pts[i].v, pts[i].x);
			b[0][1].update(pts[i].v, 1);
		}
//		for(int i = n-1; i >= 0; i--) {
//			long x = b[1][0].query(pts[i].v, curr-1);
//			long s = b[1][1].query(pts[i].v, curr-1)*pts[i].x; // the count
//			ans += x-s;
//			b[1][0].update(pts[i].v, pts[i].x);
//			b[1][1].update(pts[i].v, 1);
//		}
		System.out.println(ans);
	}
	
	static class Point implements Comparable<Point>{
		
		long x;
		int v;
		
		public Point(long x, int v) {
			this.x = x;
			this.v = v;
		}
		
		public int compareTo(Point o) {
			return Long.compare(this.x, o.x);
		}
	}

	static class BIT {
		int n;
		long[] tree;
		
		public BIT(int n) {
			this.n = n;
			tree = new long[n + 2];
		}
		
		long read(int i) {
			i++;
			long sum = 0;
			while (i > 0) {
				sum += tree[i];
				i -= i & -i;
			}
			return sum;
		}
		
		void update(int i, long val) {
			i++;
			while (i <= n) {
				tree[i] += val;
				i += i & -i;
			}
		}
		
		long query(int left, int right) { return read(right)-read(left-1); }
	}
 
	static class JS{
		public int BS = 1<<16;
		public char NC = (char)0;
		byte[] buf = new byte[BS];
		int bId = 0, size = 0;
		char c = NC;
		double num = 1;
		BufferedInputStream in;
 
		public JS() {
			in = new BufferedInputStream(System.in, BS);
		}
 
		public JS(String s) throws FileNotFoundException {
			in = new BufferedInputStream(new FileInputStream(new File(s)), BS);
		}
 
		public char nextChar(){
			while(bId==size) {
				try {
					size = in.read(buf);
				}catch(Exception e) {
					return NC;
				}				
				if(size==-1)return NC;
				bId=0;
			}
			return (char)buf[bId++];
		}
 
		public int nextInt() {
			return (int)nextLong();
		}
 
		public long nextLong() {
			num=1;
			boolean neg = false;
			if(c==NC)c=nextChar();
			for(;(c<'0' || c>'9'); c = nextChar()) {
				if(c=='-')neg=true;
			}
			long res = 0;
			for(; c>='0' && c <='9'; c=nextChar()) {
				res = (res<<3)+(res<<1)+c-'0';
				num*=10;
			}
			return neg?-res:res;
		}
 
		public double nextDouble() {
			double cur = nextLong();
			return c!='.' ? cur:cur+nextLong()/num;
		}
 
		public String next() {
			StringBuilder res = new StringBuilder();
			while(c<=32)c=nextChar();
			while(c>32) {
				res.append(c);
				c=nextChar();
			}
			return res.toString();
		}
 
		public String nextLine() {
			StringBuilder res = new StringBuilder();
			while(c<=32)c=nextChar();
			while(c!='\n') {
				res.append(c);
				c=nextChar();
			}
			return res.toString();
		}
 
		public boolean hasNext() {
			if(c>32)return true;
			while(true) {
				c=nextChar();
				if(c==NC)return false;
				else if(c>32)return true;
			}
		}
	}
}
