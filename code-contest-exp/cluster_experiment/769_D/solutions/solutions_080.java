import java.io.*;
import java.util.*;

public class CF769D
{
	static final boolean _DEBUG = true;

	static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		public MyScanner(BufferedReader _br) {
			br = _br;
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (Exception e) {
					e.printStackTrace();
					return "";
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

	static MyScanner   scan;
	static PrintWriter out;

	static int debugCount = 0;
	static void debug(String msg) {
		if (_DEBUG && debugCount < 200) {
			out.println(msg);
			out.flush();
			debugCount++;
		}
	}
	
    public static void main (String args[]) throws IOException {
//    	scan = new MyScanner(new BufferedReader(new FileReader("test.in")));
    	scan = new MyScanner(new BufferedReader(new InputStreamReader(System.in)));
    	out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        CF769D inst = new CF769D();
        inst.execute();
        out.close();
    }
    
    void execute() throws IOException {
    	int n = scan.nextInt();
    	int k = scan.nextInt();
    	int[] a = new int[10001];
    	for (int i = 0 ; i < n; i++) {
    		a[scan.nextInt()]++;
    	}
    	
    	long ans = 0;
    	
    	int[] table = new int[1<<14];
    	for (int i = 1; i < table.length; i++) {
    		for (int j = 0; j < 14; j++) {
    			if ((i & (1<<j)) != 0) {
    				table[i]++;
    			}
    		}
    	}
    	if (k == 0) {
        	for (int i = 0; i < 10001; i++) {
        		if (a[i] > 1) ans += ((long)a[i] * (a[i]-1)) >> 1;
        	}
    	} else {
        	for (int i = 0; i < 10001; i++) {
        		if (a[i] == 0) continue;
        		for (int j = i + 1; j < 10001; j++) {
        			if (a[j] == 0) continue;
        			if (table[i ^ j] == k) {
        				ans += (long)a[i] * a[j];
        			}
        		}
        	}  		
    	}
    	out.println(ans);
    }
}