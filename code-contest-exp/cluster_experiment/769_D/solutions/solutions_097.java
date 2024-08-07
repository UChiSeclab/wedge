import java.io.*;
import java.util.*;

public class Main
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
        Main inst = new Main();
        inst.execute();
        out.close();
    }
    
    private void execute() throws IOException {
    	String[] line = scan.nextLine().split(" +");
    	int N = Integer.parseInt(line[0]);
    	int K = Integer.parseInt(line[1]);
    	line = scan.nextLine().split(" +");
    	int[] nums = new int[10_001];
    	int maxNumber = 0;
    	for (int i = 0; i < N; i++) {
    		int num = Integer.parseInt(line[i]);
    		maxNumber = Math.max(num, maxNumber);
    		nums[num]++;
    	}
    	long pairs = 0;
    	for (int i = 0; i <= maxNumber; i++) {
    		if (nums[i] > 0) {
    			if (K == 0) {
        			pairs += ((long)nums[i]*(nums[i]-1))>>1;
    			}
    			for (int j = i+1; j <= maxNumber; j++) {
        			if (nums[j] > 0 && Integer.bitCount(i ^ j) == K) {
        				pairs += (long)nums[i]*nums[j];
        			}
        		}
//        		out.println(i+" "+pairs);
    		}
    	}
    	out.println(pairs);
    }
}
