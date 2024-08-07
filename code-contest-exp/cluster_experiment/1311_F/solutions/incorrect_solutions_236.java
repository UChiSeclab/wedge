import java.io.*;
import java.util.*;
import java.math.*;
import java.awt.Point;
 
public class Main {
	//static final long MOD = 998244353L;
	//static final long INF = -1000000000000000007L;
	static final long MOD = 1000000007L;
	//static final int INF = 1000000007;
	
	//static long[] factorial;
	
	public static void main(String[] args) {
		FastScanner sc = new FastScanner();
		PrintWriter pw = new PrintWriter(System.out);
		int N = sc.ni();
		int[][] points = new int[N][3]; //x,v,i
		SegmentTree left = new SegmentTree(N);
		SegmentTree right = new SegmentTree(N);
		
		for (int i = 0; i < N; i++) {
			points[i][0] = sc.ni();
		}
		for (int i = 0; i < N; i++)
			points[i][1] = sc.ni();
		//sort by increasing position
		points = shuffle(points);
		points = sort(points);
		for (int i = 0; i < N; i++)
			points[i][2] = i;
		
		//pw.println(Arrays.deepToString(points));
		SegmentTree leftSize = new SegmentTree(N);
		SegmentTree rightSize = new SegmentTree(N);
		
		for (int i = 0; i < N; i++) {
			if (points[i][1] < 0) {
				leftSize.update(0, 0, N-1, i, 1);
			} else {
				rightSize.update(0, 0, N-1, i, 1);
			}
		}
		for (int i = 0; i < N; i++) {
			if (points[i][1] < 0) {
				left.update(0, 0, N-1, i, points[i][0]);
			} else {
				right.update(0, 0, N-1, i, points[i][0]);
			}
		}
		
		long ans = 0;
		for (int i = 0; i < N; i++) {
			if (right.arr[i] > 0) {
				ans += (points[i][0]*leftSize.query(0, 0, N-1, 0, i)-left.query(0, 0, N-1, 0, i));
			}
		}
		
		PriorityQueue<int[]> pqR = new PriorityQueue<int[]>(new Comparator<int[]>(){
			public int compare(int[] a, int[] b) {
				if (b[1] != a[1]) {
					return b[1] - a[1];
				} else {
					return b[2] - a[2];
				}
			}
		});
		for (int[] point: points) {
			if (point[1] >= 0) {
				pqR.add(point);
			}
		}
		
		while (! pqR.isEmpty()) {
			int[] point = pqR.poll();
			ans += (point[0]*rightSize.query(0, 0, N-1, 0, point[2]) - right.query(0, 0, N-1, 0, point[2]));
			right.update(0, 0, N-1, point[2], 0);
			rightSize.update(0, 0, N-1, point[2], 0);
		}
		
		
		PriorityQueue<int[]> pqL = new PriorityQueue<int[]>(new Comparator<int[]>(){
			public int compare(int[] a, int[] b) {
				if (b[1] != a[1]) {
					return a[1] - b[1];
				} else {
					return a[2] - b[2];
				}
			}
		});
		for (int[] point: points) {
			if (point[1] < 0) {
				pqL.add(point);
			}
		}
		
		while (! pqL.isEmpty()) {
			int[] point = pqL.poll();
			ans += (point[0]*leftSize.query(0, 0, N-1, point[2], N-1) - left.query(0, 0, N-1, point[2], N-1));
			left.update(0, 0, N-1, point[2], 0);
			leftSize.update(0, 0, N-1, point[2], 0);
		}
		
		pw.println(ans);
		
		pw.close();
	}
	
	static class SegmentTree {
		public long[] arr;
		public long[] tree;

		public SegmentTree(int N) {
			//0 indexed seg tree
			arr = new long[N];
			tree = new long[4*N+1];
		}

		public void buildSegTree(int treeIndex, int lo, int hi) {
			if (lo == hi) {
				tree[treeIndex] = arr[lo];
				return;
			}

			int mid = lo + (hi - lo) / 2;
			buildSegTree(2 * treeIndex + 1, lo, mid);
			buildSegTree(2 * treeIndex + 2, mid + 1, hi);
			tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
		}

		public long query(int treeIndex, int lo, int hi, int i, int j) {
			// query for arr[i..j]
			if (lo > j || hi < i)
				return 0;
			if (i <= lo && j >= hi)
				return tree[treeIndex];
			int mid = lo + (hi - lo) / 2;

			if (i > mid)
				return query(2 * treeIndex + 2, mid + 1, hi, i, j);
			else if (j <= mid)
				return query(2 * treeIndex + 1, lo, mid, i, j);

			long leftQuery = query(2 * treeIndex + 1, lo, mid, i, mid);
			long rightQuery = query(2 * treeIndex + 2, mid + 1, hi, mid + 1, j);

			// merge query results
			return merge(leftQuery, rightQuery);
		}

		public void update(int treeIndex, int lo, int hi, int arrIndex, long val) {
			if (lo == hi) {
				tree[treeIndex] = val;
				arr[arrIndex] = val;
				return;
			}

			int mid = lo + (hi - lo) / 2;

			if (arrIndex > mid)
				update(2 * treeIndex + 2, mid + 1, hi, arrIndex, val);
			else if (arrIndex <= mid)
				update(2 * treeIndex + 1, lo, mid, arrIndex, val);

			// merge updates
			tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
		}

		public long merge(long a, long b) {
			return (a+b);
		}
	}
	
	public static long dist(long[] p1, long[] p2) {
		return (Math.abs(p2[0]-p1[0])+Math.abs(p2[1]-p1[1]));
	}
	
	//Find the GCD of two numbers
	public static long gcd(long a, long b) {
		if (a < b) return gcd(b,a);
		if (b == 0)
			return a;
		else
			return gcd(b,a%b);
	}
	
	//Fast exponentiation (x^y mod m)
	public static long power(long x, long y, long m) { 
		if (y < 0) return 0L;
		long ans = 1;
		x %= m;
		while (y > 0) { 
			if(y % 2 == 1) 
				ans = (ans * x) % m; 
			y /= 2;  
			x = (x * x) % m;
		} 
		return ans; 
	}
	
	public static int[][] shuffle(int[][] array) {
		Random rgen = new Random();
		for (int i = 0; i < array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    int[] temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
		return array;
	}
	
    public static int[][] sort(int[][] array) {
    	//Sort an array (immune to quicksort TLE)
 
		Arrays.sort(array, new Comparator<int[]>() {
			  @Override
        	  public int compare(int[] a, int[] b) {
				  return a[0]-b[0]; //ascending order
	          }
		});
		return array;
	}
    
    public static long[][] sort(long[][] array) {
    	//Sort an array (immune to quicksort TLE)
		Random rgen = new Random();
		for (int i = 0; i < array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    long[] temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
		Arrays.sort(array, new Comparator<long[]>() {
			  @Override
        	  public int compare(long[] a, long[] b) {
				  if (a[0] < b[0])
					  return -1;
				  else
					  return 1;
	          }
		});
		return array;
	}
    
    static class FastScanner { 
        BufferedReader br; 
        StringTokenizer st; 
  
        public FastScanner() { 
            br = new BufferedReader(new InputStreamReader(System.in)); 
        } 
  
        String next() { 
            while (st == null || !st.hasMoreElements()) { 
                try { 
                    st = new StringTokenizer(br.readLine());
                } catch (IOException  e) { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
  
        int ni() { 
            return Integer.parseInt(next()); 
        } 
  
        long nl() { 
            return Long.parseLong(next()); 
        } 
  
        double nd() { 
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