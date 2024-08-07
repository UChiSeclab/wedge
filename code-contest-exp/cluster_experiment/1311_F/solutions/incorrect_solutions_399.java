import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class f624 implements Runnable{
	
    public static void main(String[] args) {
    	try{
            new Thread(null, new f624(), "process", 1<<26).start();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
	public void run() {
		FastReader scan = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		//PrintWriter out = new PrintWriter("file.out");
		Task solver = new Task();
		//int t = scan.nextInt();
		int t = 1;
		for(int i = 1; i <= t; i++) solver.solve(i, scan, out);
		out.close();
	}

	static class Task {
		static final int inf = Integer.MAX_VALUE;

		public void solve(int testNumber, FastReader sc, PrintWriter out) {
			int N = sc.nextInt();
			tup[] points = new tup[N];
			long[] v = new long[N];
			for(int i = 0; i < N; i++) {
				points[i] = new tup();
				points[i].a = sc.nextInt();
			}
			for(int i = 0; i < N; i++) {
				points[i].b = sc.nextInt();
				v[i] = points[i].b;
			}
			
			Arrays.sort(points, new tup());
			//System.out.println(Arrays.toString(points));
			int ID = 0;
			for(int i = 0; i < N; i++) {
				if(i == 0) {
					points[i].b = ++ID;
					continue;
				}
				if(v[i] == v[i-1])
					points[i].b = ID;
				else
					points[i].b = ++ID;
			}
//			System.out.println(Arrays.toString(points));			
			Arrays.sort(points, new comp());
			//System.out.println(Arrays.toString(points));
			
			segt segtree = new segt(ID+1);
			long totalSum = 0;
			for(int i = 0; i < N; i++) {
				tup res = segtree.querySum((int)points[i].b, ID);
				//System.out.println(res);
				long sum = res.a;
				long nodes = res.b;
				totalSum += sum - nodes * points[i].a;
				segtree.update((int)points[i].b, (int)points[i].a);
				

//				System.out.println(sum + " " + nodes + " " + totalSum);
//				System.out.println(Arrays.toString(segtree.t));
				//System.out.println("YEE");
				
			}
			
			out.println(totalSum);
		}
		
		static final tup ZERO = new tup(0, 0);
		
		static class segt {
	        tup[] t;
	        int N;
	        public segt(int n) {
	            t = new tup[4*n];
	            for(int i = 0; i < t.length; i++) {
	            	t[i] = new tup(0, 0);
	            }
	            N = n;
	        }
	        
	        tup querySum(int l, int r) {
	        	return querySum(1, 0, N-1, l, r);
	        }
	 
	        tup querySum(int v, int tl, int tr, int l, int r) {
	            if (l > r)
	                return ZERO;
	            if (l == tl && r == tr)
	                return t[v];
	            int tm = (tl + tr) / 2;
	            tup A = querySum(v*2, tl, tm, l, Math.min(r, tm));
	            tup B = querySum(v*2+1, tm+1, tr, Math.max(l, tm+1), r);
	            return new tup(A.a + B.a, A.b + B.b);
	        }
	        
	        void update(int pos, int new_val) {
	        	update(1, 0, N-1, pos, new_val);
	        }
	 
	        void update(int v, int tl, int tr, int pos, long new_val) {
	        	//System.out.println(v + " " + tl + tr + pos + new_val);
	            if (tl == tr) {
	            	t[v].a += new_val;
	            	t[v].b ++;
	            } else {
	                int tm = (tl + tr) / 2;
	                if (pos <= tm)
	                    update(v*2, tl, tm, pos, new_val);
	                else
	                    update(v*2+1, tm+1, tr, pos, new_val);
	                t[v].a = t[v*2].a + t[v*2+1].a;
	                t[v].b = t[v*2].b + t[v*2+1].b;
	            }
	        }
	    }
	}
	static long binpow(long a, long b, long m) {
		a %= m;
		long res = 1;
		while (b > 0) {
			if ((b & 1) == 1)
				res = res * a % m;
			a = a * a % m;
			b >>= 1;
		}
		return res;
	}
	static void sort(int[] x){
		shuffle(x);
		Arrays.sort(x);
	}
	static void sort(long[] x){
		shuffle(x);
		Arrays.sort(x);
	}
	static class comp implements Comparator<tup>{

		@Override
		public int compare(tup o1, tup o2) {
			// TODO Auto-generated method stub
			return Long.compare(o2.a, o1.a);
		}
		
	}
	static class tup implements Comparable<tup>, Comparator<tup>{
		long a, b;
		tup(long a,long b){
			this.a=a;
			this.b=b;
		}
		public tup() {
		}
		@Override
		public int compareTo(tup o){
			return Long.compare(b,o.b);
		}
		@Override
		public int compare(tup o1, tup o2) {
			return Long.compare(o1.b, o2.b);
		}
		
		@Override
	    public int hashCode() {
			return Objects.hash(a, b);
	    }
 
	    @Override
	    public boolean equals(Object obj) {
	    	if (this == obj)
                return true;
	    	if (obj == null)
                return false;
	    	if (getClass() != obj.getClass())
                return false;
	    	tup other = (tup) obj;
	    	return a==other.a && b==other.b;
	    }
	    
	    @Override
	    public String toString() {
	    	return a + " " + b;
	    }
	}
	
	static void shuffle(int[] a) {
		Random get = new Random();
		for (int i = 0; i < a.length; i++) {
			int r = get.nextInt(a.length);
			int temp = a[i];
			a[i] = a[r];
			a[r] = temp;
		}
	}

	static void shuffle(long[] a) {
		Random get = new Random();
		for (int i = 0; i < a.length; i++) {
			int r = get.nextInt(a.length);
			long temp = a[i];
			a[i] = a[r];
			a[r] = temp;
		}
	}

	static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		public FastReader(String s) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(new File(s)));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}

}