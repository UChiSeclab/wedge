import java.io.*;
import java.util.*;

public class E{
	public static void main(String[] args) throws IOException {
		// br = new BufferedReader(new FileReader(".in"));
		// out = new PrintWriter(new FileWriter(".out"));
		//new Thread(null, new (), "peepee", 1<<28).start();
		
		int n =readInt();
		Integer[] a = new Integer[n];
		int max = 0;
		
		for (int i = 0; i < n; i++) max = Integer.max(max, a[i] = readInt());
		boolean[] b = new boolean[max+10];
		Arrays.sort(a);
		for (int i = 0; i < n; i++) {
			if (!b[a[i]-1] && a[i]-1 > 0) b[a[i]-1]=true;
			else if (!b[a[i]])  b[a[i]] = true;
			else if (!b[a[i]+1]) b[a[i]+1] = true;
		}
		int c = 0;
		for (boolean bb: b) if (bb)c++;
		out.println(c);
		out.close();
	}
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
	static StringTokenizer st = new StringTokenizer("");
	static String read() throws IOException{return st.hasMoreTokens() ? st.nextToken():(st = new StringTokenizer(br.readLine())).nextToken();}
	static int readInt() throws IOException{return Integer.parseInt(read());}
	static long readLong() throws IOException{return Long.parseLong(read());}
	static double readDouble() throws IOException{return Double.parseDouble(read());}
	
}