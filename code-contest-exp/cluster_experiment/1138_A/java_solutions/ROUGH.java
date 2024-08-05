
import java.io.*;
import java.util.*;
import java.util.Stack;


public class ROUGH {

	
	public static class FastReader {
		BufferedReader br;
		StringTokenizer st;
		//it reads the data about the specified point and divide the data about it ,it is quite fast
		//than using direct 

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
	static long mod = (long)(1e9+7);
	
	public static void main(String[] args) {
		 FastReader sc = new FastReader();
	     int n = sc.nextInt();
	     int[] a = new int[n];
	     for(int i=0;i<n;++i) a[i] = sc.nextInt();
	     List<Integer> list = new ArrayList<Integer>();
	     int temp = a[0],ctr = 0;
	     for(int i=0;i<n;++i) {
	    	 if(a[i] == temp) ++ctr;
	    	 else {
	    		 list.add(ctr);
	    		 ctr = 1;
	    		 temp = a[i];
	    	 }
	     }
	     list.add(ctr);
	     int max = Integer.MIN_VALUE;
	     for(int i=0;i+1<list.size();++i) {
	    	 max = Math.max(max,Math.min(list.get(i), list.get(i+1))); 
	     }
	     System.out.println(2*max);
	
	}

	static class Pair{
		int a;
		int b;
		Pair(int a,int b){
			this.a = a;
			this.b = b;
		}
	}
		
	
	
	
}