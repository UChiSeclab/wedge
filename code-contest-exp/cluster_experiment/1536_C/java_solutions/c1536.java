import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class c1536 implements Runnable{
	
    public static void main(String[] args) {
    	try{
            new Thread(null, new c1536(), "process", 1<<26).start();
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
			String s = sc.nextLine();
			Map<Long, Integer> map = new HashMap<>();
			
			int[] ans = new int[N];
			int curD = 0;
			int curK = 0;
			for(int i = 0; i < s.length(); i++) {
				if(s.charAt(i) == 'D')
					curD++;
				else
					curK++;
				
				int g = gcd(curD, curK);
				long ratio = (curD/g) * 1000000000l + (curK/g);
				if(map.containsKey(ratio)) {
					ans[i] = map.get(ratio)+1;
					map.put(ratio, ans[i]);
				} else {
					ans[i] = 1;
					map.put(ratio, 1);
				}
			}
			
			for(int each: ans) {
				out.print(each + " ");
			}
			out.println();
		}
		
		static int gcd(int a, int b) { 
			if (a == 0) 
				return b;  
			return gcd(b % a, a);  
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