import java.util.*;
import java.io.*;
public class C {

	public static void main(String[] args) {
		FastScanner sc = new FastScanner();
		int t = sc.nextInt();
		StringBuilder sb = new StringBuilder();
		while(t-->0) {
			int n = sc.nextInt();
			int[] arr = new int[n];
			ArrayList<Integer>[] poss = new ArrayList[n+1];
			for(int i = 1; i <= n; i++){
				poss[i] = new ArrayList<>();
			}
			for(int i = 0; i < n; i++){
				arr[i] = sc.nextInt();
				poss[arr[i]].add(i);
			}
			TreeSet<Pair> ps = new TreeSet<>();
			ps.add(new Pair(0, n-1));
			int[] life = new int[n+1];
			for(int i = 1; i <= n; i++){
				for(int p: poss[i]) {
					Pair pa = ps.floor(new Pair(p,p));
					if(pa == null) continue;
					life[i] = Math.max(life[i], pa.b - pa.a + 1);
					ps.remove(pa);
					ps.add(new Pair(pa.a, p-1));
					ps.add(new Pair(p+1, pa.b));
				}
			}
			boolean[] valid = new boolean[n+1];
			for(int i = 1; i <= n; i++){
				if(poss[i].isEmpty()) break;
				else valid[i] = true;
			}
			int[] d = new int[n+2];
			for(int i = 1; i <= n; i++){
				if(!poss[i].isEmpty()) {
					d[0]++; d[life[i]]--;
				}
			}
			int[] dacc = new int[n];
			for(int i = 0; i < n; i++){
				if(i == 0) dacc[i] = d[i];
				else dacc[i] = dacc[i-1] + d[i];
			}
			char[] ans = new char[n];
			for(int i = 0; i < n; i++){
				if(valid[n-i] && dacc[i] == n-i) ans[i] = '1';
				else ans[i] = '0';
			}
			sb.append(ans);
			sb.append("\n");
		}
		PrintWriter pw = new PrintWriter(System.out);
		pw.println(sb.toString().trim());
		pw.flush();
	}
	
	static class Pair implements Comparable<Pair>{
		int a, b;
		public Pair(int a, int b){
			this.a = a; this.b = b;
		}
		public int compareTo(Pair p){
			return a - p.a;
		}
		public String toString(){
			return a + " " + b;
		}
	}
	
	static class FastScanner {
		public BufferedReader reader;
		public StringTokenizer tokenizer;
		public FastScanner() {
			reader = new BufferedReader(new InputStreamReader(System.in), 32768);
			tokenizer = null;
		}
		public String next() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return tokenizer.nextToken();
		}
		public int nextInt() {
			return Integer.parseInt(next());
		}
		public long nextLong() {
			return Long.parseLong(next());
		}
		public double nextDouble() {
			return Double.parseDouble(next());
		}
		public String nextLine() {
			try {
				return reader.readLine();
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
