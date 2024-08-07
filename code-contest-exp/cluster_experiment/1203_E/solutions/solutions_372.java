import java.io.*;
import java.util.*;

public class A {
	
	public static void main(String[] args) {
		FastReader scan = new FastReader();
		PrintWriter out = new PrintWriter(System.out);
		Task solver = new Task();
		solver.solve(1, scan, out);
		out.close();
	}
	
	static class Task {
		public void solve(int testNumber, FastReader scan, PrintWriter out) {
			int n = scan.nextInt();
			ArrayList<Integer> a = new ArrayList<>();
			for(int i = 0; i < n; i++) a.add(scan.nextInt());
			Collections.sort(a);
			int t = 0;
			for(int i = a.size()-1; i >= 0; i--) {
				if(i == a.size()-1) {
					t++;
					a.set(i, a.get(i)+1);
				}
				else {
					int prev = a.get(i+1), curr = a.get(i);
					if(prev == 1) break;
					int diff = prev-curr;
					if(diff >= 2) {
						a.set(i, curr+1);
						t++;
					}
					else if(diff == 1) {
						t++;
					}
					else if(diff == 0) {
						a.set(i, curr-1);
						t++;
					}
					else a.set(i, prev);
				}
			}
			out.println(t);
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
