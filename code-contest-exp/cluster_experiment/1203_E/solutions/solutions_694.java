import java.io.*;
import java.util.*;

public class Main {
	static Scanner sc = new Scanner(System.in);
	static PrintWriter out = new PrintWriter(System.out);
	
	public static void main(String[] args) throws Exception {
		int n = sc.nextInt();
		Integer arr[] = new Integer[n];
		for(int i = 0; i < n; i++) {
			arr[i] = sc.nextInt();
		}
		
		Arrays.sort(arr);
		TreeSet<Integer> st = new TreeSet<>();
		for(int i = n - 1; i >= 0; i--) {
			if(!st.contains(arr[i] + 1)) {
				st.add(arr[i] + 1);
				continue;
			}
			
			if(!st.contains(arr[i])) {
				st.add(arr[i]);
				continue;
			}
			
			if(arr[i] > 1) {
				st.add(arr[i] - 1);
			}
		}
		out.println(st.size());
		out.close();
	}
}

class Scanner {
	StringTokenizer st;
	BufferedReader br;

	public Scanner(InputStream system) {
		br = new BufferedReader(new InputStreamReader(system));
	}

	public Scanner(String file) throws Exception {
		br = new BufferedReader(new FileReader(file));
	}

	public String next() throws IOException {
		while (st == null || !st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return st.nextToken();
	}

	public String nextLine() throws IOException {
		return br.readLine();
	}

	public int nextInt() throws IOException {
		return Integer.parseInt(next());
	}

	public double nextDouble() throws IOException {
		return Double.parseDouble(next());
	}

	public Long nextLong() throws IOException {
		return Long.parseLong(next());
	}
}
