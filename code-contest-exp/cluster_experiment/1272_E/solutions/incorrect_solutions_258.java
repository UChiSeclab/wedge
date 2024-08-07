import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;
 
public class noParity {
    public static void main(String[] args) throws IOException {
        //FastReader scan = new FastReader("in.txt");
        FastReader scan = new FastReader();
        //PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("out.txt")));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        Task solver = new Task();
        //int t = scan.nextInt();
        int t = 1;
        for(int i = 1; i <= t; i++) solver.solve(i, scan, out);
        out.close();
    }
    static class Task {
    	static int N;
    	static int[] ans;
    	static boolean[] visited;
    	static int[] arr;
        public void solve(int testNumber, FastReader sc, PrintWriter out) {
        	N = sc.nextInt();
        	arr = new int[N];
        	for(int i = 0; i < N; i++) {
        		arr[i] = sc.nextInt();
        	}
        	
        	ans = new int[N];
        	visited = new boolean[N];
        	for(int i = 0; i < N; i++) {
        		if(ans[i] == 0 && arr[i] % 2 == 1) {
        			dfs(i, 1);
        		}
        	}
        	//System.out.println(Arrays.toString(ans));
        	
        	visited = new boolean[N];
        	for(int i = 0; i < N; i++) {
        		if(ans[i] == 0 && arr[i] % 2 == 0) {
        			dfs(i, 0);
            		//System.out.println(i + " " + Arrays.toString(ans));
        		}
        	}
        	
        	for(int i = 0; i < ans.length; i++) {
        		if(ans[i] >= Integer.MAX_VALUE/2)
					ans[i] = -1;
        	}
        	
        	for(int each: ans)
        		out.print(each + " ");
        	out.println();
        	//System.out.println(Arrays.toString(ans));
        }
        
        public static int dfs(int node, int mod) {
        	if(node < 0 || node >= N)
        		return Integer.MAX_VALUE/2;
        	if(arr[node] % 2 == (mod+1)%2)
        		return 0;
        	if(ans[node] != 0)
        		return ans[node];
        	if(visited[node])
        		return Integer.MAX_VALUE/2;
        	visited[node] = true;
        	int x = 1 + Math.min(dfs(node + arr[node], mod), dfs(node - arr[node], mod));
        	ans[node] = x;
        	return x;
        }
        
    }
    static class tup implements Comparable<tup>, Comparator<tup> {
        int a, b;
 
        tup() {
        }
 
        tup(int a, int b) {
            this.a = a;
            this.b = b;
        }
 
        @Override
        public int compareTo(tup o2) {
            return a==o2.a?Integer.compare(b,o2.b):Integer.compare(a, o2.a);
        }

		@Override
		public int compare(tup o1, tup o2) {
			return o1.a==o2.a ? Integer.compare(o1.b, o2.b): Integer.compare(o1.a, o2.a); 
		}
		
		@Override
	    public int hashCode() {
			return Objects.hash(a, b);
	    }

	    @Override
	    public boolean equals(Object obj) {
	    	if (this == obj)
                return true;
	    	if (obj == null)
                return false;
	    	if (getClass() != obj.getClass())
                return false;
	    	tup other = (tup) obj;
	    	return a==other.a && b==other.b;
	    }
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