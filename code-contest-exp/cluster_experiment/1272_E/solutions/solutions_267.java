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
    	static List<List<Integer>> adjList = new ArrayList<>(); // 2nd value is distance away from opposite
    	static List<List<Integer>> oppositeAdj = new ArrayList<>();
        public void solve(int testNumber, FastReader sc, PrintWriter out) {
        	int N = sc.nextInt();
        	int[] arr = new int[N];
        	for(int i = 0; i < N; i++) {
        		arr[i] = sc.nextInt();
        		adjList.add(new ArrayList<>());
        		oppositeAdj.add(new ArrayList<>());
        	}
        	
        	Queue<tup> queue = new LinkedList<>();
        	for(int i = 0; i < N; i++) {
        		boolean start = false;
        		
        		if(inBounds(i-arr[i], N) && arr[i] % 2 == arr[i-arr[i]] % 2) {
        			adjList.get(i).add(i-arr[i]);
        			oppositeAdj.get(i-arr[i]).add(i);
        		} else if (inBounds(i-arr[i], N)) {
        			start = true;
        		}
        		if(inBounds(i+arr[i], N) && arr[i] % 2 == arr[i+arr[i]] % 2) {
        			adjList.get(i).add(i+arr[i]);
        			oppositeAdj.get(i+arr[i]).add(i);
        		} else if (inBounds(i+arr[i], N)) {
        			start = true;
        		}
        		
        		if(start)
        			queue.add(new tup(i, 1));
        	}
        	
        	//System.out.println(adjList);
        	//System.out.println(oppositeAdj);
        	
        	int[] ans = new int[N];
        	//System.out.println(queue);
        	//System.out.println(Arrays.toString(ans));
        	
        	while(queue.size() > 0) {
        		tup I = queue.poll();
        		//System.out.println(Arrays.toString(ans));
        		if(ans[I.a] == 0) {
        			ans[I.a] = I.b;
        			for(int each: oppositeAdj.get(I.a)) {
        				if(ans[each] == 0)
        					queue.add(new tup(each, I.b+1));
        			}
        		}
        	}
        	
        	
        	for(int each: ans) {
        		if(each == 0)
        			out.print("-1 ");
        		else
        			out.print(each + " ");
        	}
        	out.println();
        	//System.out.println(Arrays.toString(ans));
        }
        
        public static boolean inBounds(int i, int N) {
        	return i >= 0 && i < N;
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
	    
	    public String toString() {
	    	return a + " " + b;
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