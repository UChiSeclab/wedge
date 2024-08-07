import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class d1379 implements Runnable{
	
    public static void main(String[] args) {
    	try{
            new Thread(null, new d1379(), "process", 1<<26).start();
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
			int H = sc.nextInt();
			int M = sc.nextInt();
			int K = sc.nextInt();
			
			PriorityQueue<tup> pq = new PriorityQueue<>(); // sorts by train departure, ascending
			
			long[] trams = new long[N+1];
			for(int i = 1; i <= N; i++) {
				long h = sc.nextInt();
				long m = sc.nextInt();
				
				long totalM = h * M + m;
				totalM %= (M/2); // the equivalent time of departure, on an M/2 basis
				
				pq.add(new tup(i, totalM));
				trams[i] = totalM;
			}
			
			Queue<tup> enter = new LinkedList<>();
			Queue<tup> leave = new LinkedList<>();
			
			while(pq.size() > 0) {
				leave.add(pq.poll());
			}
			
			int currentCanceled = 0;
			long startInd = 0;
			long endInd = K;
			// actually test for values (startInd+1) through (endInd-1), because a train can still depart at startInd and endInd
			Queue<tup> temp = new LinkedList<>();
			for(int i = 0; i < N; i++) {
				if(leave.peek().b < K && leave.peek().b != 0) {
					currentCanceled++;
					temp.add(leave.peek());
					leave.add(leave.poll());
				} else {
					enter.add(leave.peek());
					leave.add(leave.poll());
				}
			}
			enter.addAll(temp);
			
			// leave = all trams in ascending order
			// enter = all trams in ascending order, except with 0 -> K pushed to the back
//			System.out.println(leave);
//			System.out.println(enter);
//			System.out.println(currentCanceled);
						
			int leastCanceled = currentCanceled;
			while(enter.size() > 0) {
				tup t = enter.poll();
				// remove all trams that depart before time t.b - K
				while(leave.size() > 0 && leave.peek().b <= (((t.b - K + (M/2)) % (M/2)))) {
					leave.poll();
					currentCanceled--;
				}
				if(currentCanceled < leastCanceled) {
					leastCanceled = currentCanceled;
					startInd = ((t.b - K + (M/2) % (M/2)));
					endInd = t.b;
				}
				// the current tram can no longer depart
				currentCanceled++;
			}
			
			//System.out.println();
			out.println(leastCanceled + " " + endInd);
			for(int i = 1; i <= N; i++) {
				if(endInd < startInd) {
					if(trams[i] > startInd || trams[i] < endInd) {
						out.print(i + " ");
					}
				} else {
					if(trams[i] > startInd && trams[i] < endInd) {
						out.print(i+ " ");
					}
				}
			}
			out.println();
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
	static class tup implements Comparable<tup>, Comparator<tup>{
		int a; long b;
		tup(int a,long b){
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