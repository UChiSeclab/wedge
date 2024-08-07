import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		PrintWriter out = new PrintWriter(outputStream);
		InputReader in = new InputReader(inputStream);
		
//		for(int i=4;i<=4;i++) {
//			InputStream uinputStream = new FileInputStream("dining.in");
//			String f = i+".in";
//			InputStream uinputStream = new FileInputStream(f);
//			InputReader in = new InputReader(uinputStream);
//			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dining.out")));
			Task t = new Task();
			t.solve(in, out);
			out.close();			
//		}
	}	
	
	static class Task {
		int times = 0;
		public void solve(InputReader in, PrintWriter out) throws IOException {
//			//int xxx = in.nextInt();
//			//while(xxx-->0) {
//				int n = in.nextInt();
//				int k = in.nextInt();
//				int a[] = in.readIntArray(n);
//				int b[] = in.readIntArray(n);
//				ArrayList<Integer>[] g = new ArrayList[n];
//				for(int i=0;i<n;i++) g[i] = new ArrayList<Integer>();
//				for(int i=0;i<n-1;i++) {
//					g[a[i]-1].add(a[i+1]-1);
//					g[b[i]-1].add(b[i+1]-1);
//				}
//				boolean vis[] = new boolean[n];
//				int lvl[] = new int[n];
//				int low[] = new int[n];
//				int hash[] = new int[n];
//				times = 0;
//				dfs(a[0]-1,g,vis,low,lvl,hash);
//				int cycles = 0; int cycle_nodes = 0;
//				DSU dsu = new DSU(n);
//				for(int i=0;i<n;i++) {
//					if(hash[low[i]]<i) {
//						dsu.union(hash[low[i]], i);
//					}else {
//						dsu.union(i,hash[low[i]]);
//					}
//				}
//				HashMap<Integer,Integer> s = new HashMap<Integer,Integer>();
//				for(int i=0;i<n;i++) {
//					int tmp = dsu.find(i);
//					s.put(tmp, s.getOrDefault(tmp, 0)+1);
//					//low[i] = s.get(dsu.find(i));
//				}
//				for(int i:s.keySet()) {
//					if(s.get(i)>1) {
//						cycles++;
//						cycle_nodes+=s.get(i);
//					}
//				}
//				if(k==3) {
//					//Dumper.print("here");
//				}
//				if(n-cycle_nodes+cycles>=k) {
//					System.out.println("YES");
//					//out.println("YES");
//					int temp [] = new int[n];
//					for(int i=0;i<n;i++) temp[i] = low[a[i]-1];
//					for(int i=1;i<n;i++) {
//						if(temp[i]>temp[i-1]+1) temp[i] = temp[i-1]+1;				
//						if(temp[i]>=k) temp[i] = temp[i-1];								
//					}
//					for(int i=0;i<n;i++) {
//						low[a[i]-1] = temp[i]; 
//					}
//					for(int i=0;i<n;i++) {
//						System.out.print((char)('a'+low[i]));
//						//out.print((char)('a'+low[i]));
//					}
//					System.out.println();
//					//out.println();
//				}
//				else {
//					System.out.println("NO");
//					//out.println("NO");	
//				}
//			//}
			//int c = in.nextInt();
			//Dumper.print(Long.MAX_VALUE);
			
			//int c = in.nextInt();
			//while(c-->0) {
			//Dumper.print(Integer.MAX_VALUE);
			//Dumper.print((long)(100001)*100000/2);
			//}
//			int n = in.nextInt();
//			int k = in.nextInt();
//			int val[] = in.readIntArray(n);
//			ArrayList<Integer>[] g = new ArrayList[n];
//			for(int i=0;i<n;i++) g[i] = new ArrayList<Integer>();
//			for(int i=0;i<n-1;i++) {
//				int f = in.nextInt()-1;
//				int t = in.nextInt()-1;
//				g[f].add(t);
//				g[t].add(f);
//			}
//			int lvl[] = new int[n];
//			dfs(g,0,-1,lvl);
//			int dp[][] = new int[n][n];
//			dfs2(g,0,-1,lvl,dp,k,val,n);
//			out.println(dp[0][0]);
//		}
//		void dfs(ArrayList<Integer>[] g, int cur, int pre, int lvl[]) {
//			if(pre!=-1) lvl[cur] = lvl[pre]+1;
//			for(int nxt:g[cur]) {
//				if(pre==nxt) continue;
//				dfs(g,nxt,cur,lvl);
//			}
//		}
//		void dfs2(ArrayList<Integer>[] g, int cur, int pre, int lvl[], int dp[][], int k, int val[], int n) {
//			for(int nxt:g[cur]) {
//				if(pre!=nxt) dfs2(g,nxt,cur,lvl,dp,k,val,n);
//			}
//			
//			//copy best values at each level to this root node
//			for(int nxt:g[cur]) {
//				for(int i=lvl[cur]+k/2+1;i<n;i++) {
//					dp[cur][i] += dp[nxt][i];
//				}
//			}
//			
//			//chose this root node
//			int cur_lvl = lvl[cur];
//			int nxt_lvl = cur_lvl+k+1;
//			dp[cur][cur_lvl] = val[cur];
//			if(nxt_lvl<n) dp[cur][cur_lvl] += dp[cur][nxt_lvl];
//
//			//don't choose root node			
//			for(int j=1;j<=k/2;j++) {				
//				int son_lvl1 = cur_lvl+j;
//				int son_lvl2 = cur_lvl+k+1-j;	
//				if(son_lvl2>=n) continue;				
//				for(int nxt:g[cur]) {
//					if(pre==nxt) continue;
//					int tmp = dp[nxt][son_lvl1] + dp[cur][son_lvl2] - dp[nxt][son_lvl2];
//					if(tmp>dp[cur][son_lvl1]) dp[cur][son_lvl1] = tmp;
//				}
//			}
//			for(int j=n-2;j>=cur_lvl;j--) {
//				dp[cur][j] = Math.max(dp[cur][j], dp[cur][j+1]);
//			}

			int n = in.nextInt();
			int arr[] = in.readIntArray(n);
			//int arr[] = {10,3,10,3,5,4,10,9,9,8,7,10,3,10,8,9,7,7,8,10,7,8,3,10,4,5,10,10,3,9,10,6,9,9,7,6,10,4,3,8,7,7,3,9,9,8,7,5,4,5,3,8,4,4,5,3,9,6,9,9,6,9,3,4,5,6,5,10,5,4,6,10,3,4,4,8,8,3,9,7,8,10,6,5,8,3,4,6,8,9,8,9,4,3,10,8,8,10,7,3};
			int count[] = new int[n];
			Arrays.fill(count, 999999999);
			for(int i=0;i<n;i++) {
				dfsr(arr,i,count);
			}
			for(int i=n-1;i>=0;i--) {
				dfsl(arr,i,count);
			}
			for(int i=0;i<n;i++) {
				out.print(count[i]>=999999999?-1:count[i]);
				out.print(" ");
			}
			out.println();
		}
		int dfsr(int arr[], int cur, int count[]) {
			if(cur+arr[cur]<arr.length) {
				if(arr[cur]%2!=arr[cur+arr[cur]]%2) return count[cur] = 1;
				else return count[cur] = dfsr(arr,cur+arr[cur],count)+1;
			}else {
				return count[cur] = 999999999;
			}
		}
		int dfsl(int arr[], int cur, int count[]) {
			if(cur-arr[cur]>=0) {
				if(arr[cur]%2!=arr[cur-arr[cur]]%2) return count[cur] = 1;
				else return count[cur] = Math.min(count[cur], dfsl(arr,cur-arr[cur],count)+1);
			}else {
				return count[cur] = Math.min(count[cur], 999999999);
			}
		}
		int dfs(int arr[], int cur, int pre, int count[]) {
			if(cur==96) {
				Dumper.print("here");
			}
			if(cur-arr[cur]<0&&arr[cur]+cur>=arr.length) return count[cur] = 999999999;
			if(cur-arr[cur]>=0&&arr[cur]%2!=arr[cur-arr[cur]]%2) return count[cur] = 1;
			if(cur+arr[cur]<arr.length&&arr[cur]%2!=arr[cur+arr[cur]]%2) return count[cur] = 1;
			if(count[cur]!=0) return count[cur];
			int l = 999999999;
			int r = 999999999;
			if(cur-arr[cur]>=0&&cur-arr[cur]!=pre) l = dfs(arr,cur-arr[cur],cur,count);
			if(cur+arr[cur]<arr.length&&cur+arr[cur]!=pre) r = dfs(arr,cur+arr[cur],cur,count);
			return count[cur] = Math.min(l, r)+1;
		}
	
		class pair implements Comparable<pair>{
			int idx, val;
			public pair(int a, int b) {
				idx = a;
				val = b;
			}
			@Override
			public int compareTo(pair t) {
				return t.val-this.val;
			}
		}
		
		class interval implements Comparable<interval>{
			int from; int to; int idx;
			public interval(int a, int b, int c) {
				from=a;
				to=b;
				idx=c;
			}
			@Override
			public int compareTo(interval t) {
				if(this.to==t.to) return this.from-t.from;
				return this.to-t.to;
			}
		}	    
	    class sgt{
	    	sgt lt;
	    	sgt rt;
	    	int l,r;
	    	long sum, max, min, lazy;
	    	public sgt(int L, int R, int arr[]) {
	    		l=L;r=R;
	    		if(l==r-1) {
	    			sum = max = min = arr[l];
	    			lazy = 0;
	    			return;
	    		}
	    		lt = new sgt(l, l+r>>1, arr);
	    		rt = new sgt(l+r>>1, r, arr);
	    		pop_up();
	    	}
	    	void pop_up() {
	    		this.sum = lt.sum + rt.sum;
	    		this.max = Math.max(lt.max, rt.max);
	    		this.min = Math.min(lt.min, rt.min);
	    	}
	    	void push_down() {
	    		if(this.lazy!=0) {
	    			lt.sum+=lazy;
	    			rt.sum+=lazy;
	    			lt.max+=lazy;
	    			lt.min+=lazy;
	    			rt.max+=lazy;
	    			rt.min+=lazy;
	    			lt.lazy+=this.lazy;
	    			rt.lazy+=this.lazy;
	    			this.lazy = 0;
	    		}
	    	}
	    	void change(int L, int R, int v) {
	    		if(R<=l||r<=L) return;
	    		if(L<=l&&r<=R) {
	    			this.max+=v;
	    			this.min+=v;
	    			this.sum+=v*(r-l);
	    			this.lazy+=v;
	    			return;
	    		}
	    		push_down();
	    		lt.change(L, R, v);
	    		rt.change(L, R, v);
	    		pop_up();
	    	}
	    	long query_max(int L, int R) {
	    		if(L<=l&&r<=R) return this.max;
	    		if(r<=L||R<=l) return Long.MIN_VALUE;
	    		push_down();
	    		return Math.max(lt.query_max(L, R), rt.query_max(L, R));	    		
	    	}
	    	long query_min(int L, int R) {
	    		if(L<=l&&r<=R) return this.min;
	    		if(r<=L||R<=l) return Long.MAX_VALUE;
	    		push_down();
	    		return Math.min(lt.query_min(L, R), rt.query_min(L, R));	
	    	}
	    	long query_sum(int L, int R) {
	    		if(L<=l&&r<=R) return this.sum;
	    		if(r<=L||R<=l) return 0;
	    		push_down();
	    		return lt.query_sum(L, R) + rt.query_sum(L, R);
	    	}
	    }	   
			    	
		List<List<String>> convert(String arr[][]){
			int n = arr.length;
			List<List<String>> ret = new ArrayList<>();
			for(int i=0;i<n;i++) {
				ArrayList<String> tmp = new ArrayList<String>();
				for(int j=0;j<arr[i].length;j++) tmp.add(arr[i][j]);
				ret.add(tmp);
			}
			return ret;
		}
		
		public class TreeNode {
			int val;
			TreeNode left;
			TreeNode right;
			TreeNode(int x) { val = x; }
		}
	
		public int GCD(int a, int b) {
		   if (b==0) return a;
		   return GCD(b,a%b);
		}
		public long GCD(long a, long b) {
			   if (b==0) return a;
			   return GCD(b,a%b);
			}		
	}
	
	
    static class ArrayUtils {
        static final long seed = System.nanoTime();
        static final Random rand = new Random(seed);

        public static void sort(int[] a) {
            shuffle(a);
            Arrays.sort(a);
        }

        public static void shuffle(int[] a) {
            for (int i = 0; i < a.length; i++) {
                int j = rand.nextInt(i + 1);
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }

    }	
	static class BIT{
		int arr[];
		int n;
		public BIT(int a) {
			n=a;
			arr = new int[n];
		}
		int sum(int p) {
			int s=0;
			while(p>0) {
				s+=arr[p];
				p-=p&(-p);
			}
			return s;
		}
		void add(int p, int v) {
			while(p<n) {
				arr[p]+=v;
				p+=p&(-p);
			}
		}
	}
	static class DSU{
		int[] arr;
		int[] sz;
		public DSU(int n) {
			arr = new int[n];
			sz = new int[n];
			for(int i=0;i<n;i++) arr[i] = i;
			Arrays.fill(sz, 1);
		}
		public int find(int a) {
			if(arr[a]!=a) arr[a] = find(arr[a]);
			return arr[a];
		}
		public void union(int a, int b) {
			int x = find(a);
			int y = find(b);
			if(x==y) return;
			arr[y] = x;
			sz[x] += sz[y];
		}
		public int size(int x) {
			return sz[find(x)];
		}
	}	
	static class MinHeap<Key> implements Iterable<Key> {
		private int maxN;
		private int n;
		private int[] pq;
		private int[] qp;
		private Key[] keys;
		private Comparator<Key> comparator;
		
		public MinHeap(int capacity){
			if (capacity < 0) throw new IllegalArgumentException();
			this.maxN = capacity;
			n=0;
			pq = new int[maxN+1];
			qp = new int[maxN+1];
			keys = (Key[]) new Object[capacity+1];
			Arrays.fill(qp, -1);
		}
		
		public MinHeap(int capacity, Comparator<Key> c){
			if (capacity < 0) throw new IllegalArgumentException();
			this.maxN = capacity;
			n=0;
			pq = new int[maxN+1];
			qp = new int[maxN+1];
			keys = (Key[]) new Object[capacity+1];
			Arrays.fill(qp, -1);
			comparator = c;
		}			
		public boolean isEmpty()	{ return n==0; 	}
		public int size()			{ return n;		}
		public boolean contains(int i) {
	        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
	        return qp[i] != -1;
		}	
		public int peekIdx() {
	        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
	        return pq[1];		
		}	
		public Key peek(){
			if(isEmpty()) throw new NoSuchElementException("Priority queue underflow");
			return keys[pq[1]];
		}
		public int poll(){
			if(isEmpty()) throw new NoSuchElementException("Priority queue underflow");
			int min = pq[1];
			exch(1,n--);
			down(1);
			assert min==pq[n+1];
			qp[min] = -1;
			keys[min] = null;		
			pq[n+1] = -1;
			return min;
		}
	    public void update(int i, Key key) {
	        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
	        if (!contains(i)) {
	        	this.add(i, key);
	        }else {
	        	keys[i] = key;
	        	up(qp[i]);
	        	down(qp[i]);
	        }
	    }	
		private void add(int i, Key x){
	        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
	        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
	        n++;
	        qp[i] = n;
	        pq[n] = i;
	        keys[i] = x;
	        up(n);
		}
		private void up(int k){
			while(k>1&&less(k,k/2)){
				exch(k,k/2);
				k/=2;
			}
		}
		private void down(int k){
			while(2*k<=n){
				int j=2*k;
				if(j<n&&less(j+1,j)) j++;
				if(less(k,j)) break;
				exch(k,j);
				k=j;
			}
		}
		
		public boolean less(int i, int j){
	        if (comparator == null) {
	            return ((Comparable<Key>) keys[pq[i]]).compareTo(keys[pq[j]]) < 0;
	        }
	        else {
	            return comparator.compare(keys[pq[i]], keys[pq[j]]) < 0;
	        }
		}

		public void exch(int i, int j){
	        int swap = pq[i];
	        pq[i] = pq[j];
	        pq[j] = swap;
	        qp[pq[i]] = i;
	        qp[pq[j]] = j;
		}

		@Override
		public Iterator<Key> iterator() {
			// TODO Auto-generated method stub
			return null;
		}
	}  	

    private static class InputReader
    {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int zcurChar;
        private int znumChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream)
        {
            this.stream = stream;
        }

        public int read()
        {
            if (znumChars == -1)
                throw new InputMismatchException();
            if (zcurChar >= znumChars)
            {
            	zcurChar = 0;
                try
                {
                    znumChars = stream.read(buf);
                } catch (IOException e)
                {
                    throw new InputMismatchException();
                }
                if (znumChars <= 0)
                    return -1;
            }
            return buf[zcurChar++];
        }

        public int nextInt()
        {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-')
            {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do
            {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String nextString()
        {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuilder res = new StringBuilder();
            do
            {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public double nextDouble()
        {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-')
            {
                sgn = -1;
                c = read();
            }
            double res = 0;
            while (!isSpaceChar(c) && c != '.')
            {
                if (c == 'e' || c == 'E')
                    return res * Math.pow(10, nextInt());
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            }
            if (c == '.')
            {
                c = read();
                double m = 1;
                while (!isSpaceChar(c))
                {
                    if (c == 'e' || c == 'E')
                        return res * Math.pow(10, nextInt());
                    if (c < '0' || c > '9')
                        throw new InputMismatchException();
                    m /= 10;
                    res += (c - '0') * m;
                    c = read();
                }
            }
            return res * sgn;
        }

        public long nextLong()
        {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-')
            {
                sgn = -1;
                c = read();
            }
            long res = 0;
            do
            {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public boolean isSpaceChar(int c)
        {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next()
        {
            return nextString();
        }

        public interface SpaceCharFilter
        {
            public boolean isSpaceChar(int ch);
        }
        public int[] readIntArray(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }        
    }    

	static class Dumper {
		static void print_int_arr(int[] arr) {
			for (int i = 0; i < arr.length; i++) {
				System.out.print(arr[i] + " ");
			}
			System.out.println();
			System.out.println("---------------------");
		}

		static void print_char_arr(char[] arr) {
			for (int i = 0; i < arr.length; i++) {
				System.out.print(arr[i] + " ");
			}
			System.out.println();
			System.out.println("---------------------");
		}

		static void print_double_arr(double[] arr) {
			for (int i = 0; i < arr.length; i++) {
				System.out.print(arr[i] + " ");
			}
			System.out.println();
			System.out.println("---------------------");
		}

		static void print_2d_arr(int[][] arr, int x, int y) {
			for (int i = 0; i < x; i++) {
				for (int j = 0; j < y; j++) {
					System.out.print(arr[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("---------------------");
		}

		static void print_2d_arr(boolean[][] arr, int x, int y) {
			for (int i = 0; i < x; i++) {
				for (int j = 0; j < y; j++) {
					System.out.print(arr[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("---------------------");
		}

		static void print(Object o) {
			System.out.println(o.toString());
		}

		static void getc() {
			System.out.println("here");
		}
	}
}