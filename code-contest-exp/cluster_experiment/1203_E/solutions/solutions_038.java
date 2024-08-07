
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;


public class Main {
	
	public static class FastReader {
		BufferedReader br;
		StringTokenizer st;
	

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (Exception r) {
					r.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());//converts string to integer
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (Exception r) {
				r.printStackTrace();
			}
			return str;
		}
	}
	
	public static PrintWriter out = new PrintWriter (new BufferedOutputStream(System.out));
    public static long mod = (long) (1e9+7);
    public final static int MAX = (int)1e5;
  // static Scanner sc = new Scanner(System.in);
   static List<Integer>[] edge;
	public static void main(String[] args) {
	   
	 FastReader sc = new FastReader();
	 int n = sc.nextInt();
	 Integer[] a = new Integer[n];
	 for(int i=0;i<n;++i) a[i] = new Integer(sc.nextInt());
	 Arrays.sort(a);
	Set<Integer> set = new HashSet<>();
	for(int i=0;i<n;++i) {
		if(a[i].intValue() == 1) {
			if(!set.contains(1)) set.add(1);
			else if(!set.contains(2)) set.add(2);
		}else {
			if(!set.contains(a[i]-1)) set.add(a[i]-1);
			else if(!set.contains(a[i])) set.add(a[i]);
			else if(!set.contains(a[i]+1)) set.add(a[i]+1);
		}
		
	}
	out.print(set.size());
	out.close();
	}
}