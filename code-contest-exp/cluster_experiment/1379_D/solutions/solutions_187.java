
// Problem : D. New Passenger Trams
// Contest : Codeforces - Codeforces Round #657 (Div. 2)
// URL : https://codeforces.com/contest/1379/problem/D
// Memory Limit : 512 MB
// Time Limit : 2000 ms
// Powered by CP Editor (https://github.com/cpeditor/cpeditor)

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class a implements Runnable{
	
    public static void main(String[] args) {
        new Thread(null, new a(), "process", 1<<26).start();
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

		public void solve(int testNumber, FastReader sc, PrintWriter pw) {
			//CHECK FOR QUICKSORT TLE
			//***********************//
			//CHECK FOR INT OVERFLOW
			//***********************//
			int n = sc.nextInt();
			int h = sc.nextInt();
			int m = sc.nextInt();
			int k = sc.nextInt();
			ArrayList<Integer> ar = new ArrayList<>();
			ArrayList<tup> ar2 = new ArrayList<>();
			TreeSet<Integer> ts = new TreeSet<Integer>();
			for(int i = 0; i < n; i++) {
				sc.nextInt();
				int z = sc.nextInt() % (m / 2);
				ar.add(z);
				ar2.add(new tup(z, i  + 1));
				if(z <= k) {
					ar.add(z + m / 2);
					ar2.add(new tup(z + m / 2, i + 1));
				}
			}
			Collections.sort(ar);
			int ptr = 0;
			int ptr2 = 0;
			int min = inf;
			int time = -1;
			while(ptr < ar.size()) {
				if(ar.get(ptr) > m / 2) break;
				while(ptr2 < ar.size() && ar.get(ptr2) - ar.get(ptr) < k) {
					ptr2++;
				}
				//pw.println(ar.get(ptr));
				//pw.println(ptr + " " + ptr2);
				if(min > ptr2 - ptr - 1) {
					min = ptr2 - ptr - 1;
					time = ptr2;
				}
				ptr++;
			}
			pw.print(min + " ");
			pw.println(ar.get(time) % (m / 2));
			for(tup x : ar2) {
				if(ar.get(time) - x.a < k && ar.get(time) - x.a > 0) {
					pw.print(x.b + " ");
				} 
			}
			
			pw.println();
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
	static class tup implements Comparable<tup>{
		int a, b;
		tup(int a,int b){
			this.a=a;
			this.b=b;
		}
		@Override
		public int compareTo(tup o){
			return a == o.a ? Integer.compare(a,o.a) : Integer.compare(o.b, b);
		}
	}
	static void shuffle(int[] a) {
		Random get = new Random();
		for (int i = 0; i < a.length; i++) {
			int r = get.nextInt(i + 1);
			int temp = a[i];
			a[i] = a[r];
			a[r] = temp;
		}
	}

	static void shuffle(long[] a) {
		Random get = new Random();
		for (int i = 0; i < a.length; i++) {
			int r = get.nextInt(i + 1);
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