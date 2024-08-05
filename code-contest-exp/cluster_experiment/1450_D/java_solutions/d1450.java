import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class d1450 implements Runnable{
	
    public static void main(String[] args) {
    	try{
            new Thread(null, new d1450(), "process", 1<<26).start();
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
		int t = scan.nextInt();
		//int t = 1;
		for(int i = 1; i <= t; i++) solver.solve(i, scan, out);
		out.close();
	}

	static class Task {
		static final int oo = Integer.MAX_VALUE;
		static final long OO = Long.MAX_VALUE;

		public void solve(int testNumber, FastReader sc, PrintWriter out) {
			int N = sc.nextInt();
			ArrayDeque<Integer> aq = new ArrayDeque<>();
			int[] num = new int[N+1];
			for(int i = 0; i < N; i++) {
				aq.add(sc.nextInt());
				num[aq.getLast()]++;
			}
			num[0]++;
			
			boolean[] works = new boolean[N+1];
			if(num[1] != 0)
				works[N] = true;
			//dbg(num);
			for(int i = N-1; i >= 2; i--) {
				if(num[N-i] != 1) {
					break;
				}
				if(aq.getFirst() == (N - i) || aq.getLast() == (N-i)) {
					works[i] = true;
					if(aq.getFirst() == N-i)
						aq.pollFirst();
					else
						aq.pollLast();
				}
			}
			if(check(num))
				works[1] = true;
			
			for(int i = 1; i <= N; i++) {
				if(works[i])
					out.print('1');
				else
					out.print('0');
			}
			out.println();
		}
		boolean check(int[] arr) {
			for(int each: arr)
				if(each != 1)
					return false;
			return true;
		}
		
		
		
	}
	static long modInverse(long N, long MOD) {
		return binpow(N, MOD - 2, MOD);
	}
	static long modDivide(long a, long b, long MOD) {
		a %= MOD;
		return (binpow(b, MOD-2, MOD) * a) % MOD;
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
	static int[] reverse(int a[]) 
    { 
        int[] b = new int[a.length]; 
        for (int i = 0, j = a.length; i < a.length; i++, j--) { 
            b[j - 1] = a[i]; 
        } 
        
        return b;
    }
	static long[] reverse(long a[]) 
    { 
        long[] b = new long[a.length]; 
        for (int i = 0, j = a.length; i < a.length; i++, j--) { 
            b[j - 1] = a[i]; 
        } 
        
        return b;
    }
	
	static void shuffle(Object[] a) {
		Random get = new Random();
		for (int i = 0; i < a.length; i++) {
			int r = get.nextInt(a.length);
			Object temp = a[i];
			a[i] = a[r];
			a[r] = temp;
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
	
	static class tup implements Comparable<tup>, Comparator<tup>{
		int a, b;
		tup(int a,int b){
			this.a=a;
			this.b=b;
		}
		public tup() {
		}
		@Override
		public int compareTo(tup o){
			return Integer.compare(b,o.b);
		}
		@Override
		public int compare(tup o1, tup o2) {
			return Integer.compare(o1.b, o2.b);
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
		
		int[] readArray(int size) {
			int[] a = new int[size];
			for(int i = 0; i < size; i++) {
				a[i] = nextInt();
			}
			return a;
		}
		
		long[] readLongArray(int size) {
			long[] a = new long[size];
			for(int i = 0; i < size; i++) {
				a[i] = nextLong();
			}
			return a;
		}
	}
	
	static void dbg(int[] arr) {
		System.out.println(Arrays.toString(arr));
	}
	static void dbg(long[] arr) {
		System.out.println(Arrays.toString(arr));
	}
	static void dbg(boolean[] arr) {
		System.out.println(Arrays.toString(arr));
	}

	static void dbg(Object... args) {
        for (Object arg : args)
            System.out.print(arg + " ");
        System.out.println();
    }
}