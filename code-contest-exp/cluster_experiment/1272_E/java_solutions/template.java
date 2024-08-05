import java.io.*;
import java.util.*;

public class template {
	public static void main(String[] args) throws IOException {
		FastReader scan = new FastReader();
		//PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter()));
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		Task solver = new Task();
		//int t = scan.nextInt();
		int t = 1;
		for(int i = 1; i <= t; i++) solver.solve(i, scan, out);
		out.close();
	}
	static class Task {
		public void solve(int testNumber, FastReader sc, PrintWriter pw) {
		    int a =sc.nextInt();
		    int[] n = new int[a];
		    for(int i=0;i<a;i++){
		        n[i]=sc.nextInt();
            }
		    ArrayList<ArrayList<Integer>> adjl = new ArrayList<ArrayList<Integer>>();
		    for(int i=0;i<a;i++){
		        adjl.add(new ArrayList<Integer>());
            }
		    int[] ans = new int[a];
		    Arrays.fill(ans,Integer.MAX_VALUE/2);
		    Queue<Integer> q = new LinkedList<>();
		    for(int i = 0;i<a;i++){
                if(i+n[i]<a&&n[i]%2!=n[i+n[i]]%2){
                    ans[i]=1;
                    q.add(i);
                }
                if(i-n[i]>=0&&n[i]%2!=n[i-n[i]]%2){
                    ans[i]=1;
                    q.add(i);
                }
            }
		    for(int i=0;i<a;i++){
		        if(i+n[i]<a){
		            adjl.get(n[i]+i).add(i);
                }
                if(i-n[i]>=0){
                    adjl.get(i-n[i]).add(i);
                }
            }
            while(!q.isEmpty()){
                int po = q.poll();
                for(int x : adjl.get(po)) {
                    if (ans[po] + 1 < ans[x]) {
                        ans[x] = ans[po] + 1;
                        q.add(x);
                    }
                }
            }
            pw.print(ans[0]==Integer.MAX_VALUE/2?-1:ans[0]);
            for(int i=1;i<ans.length;i++){
                pw.print(" "+(ans[i]==Integer.MAX_VALUE/2?-1:ans[i]));
            }


		}

		/*public static long exp(long base, int power){
			if(power == 0) return 1;
			if(power == 1) return (base + mod) % mod;
			long ans = exp(base,power/2);
			ans = (ans*ans + mod) % mod;
			if(power%2 == 1) ans = (ans*base + mod) % mod;
			return (ans + mod) % mod;
		}
		*/
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
    static void shuffle(int[] a) {
        Random get = new Random();
        for (int i = 0; i < a.length; i++) {
            int r = get.nextInt(a.length);
            int temp = a[i];
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