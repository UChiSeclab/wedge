import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.stream.Stream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Main {
	


	
public static void main(String[] args){
 
   new Thread(null, null, "Anshum Gupta", 99999999) {
        public void run() {
            try {
                solve();
            } catch(Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }.start();
}
 
static final long INF_LONG = (long)(1e18 + 5), MOD = 998244353; //(long)1e9 + 7
static final int MXN = (int)(1001), MXV = (int)(1e6), log = 18, INF = (int)1e9 + 500;
static boolean[] vis;
static ArrayList<ArrayList<Integer>> adj;
static int n, m, q, k, h, w;
static char[] str;



public static void solve() throws Exception {
	   // solve the problem here
		s = new MyScanner();
   		out = new MyWriter(new BufferedOutputStream(System.out));
        int tc = s.nextInt();   
    	for(int i=1; i<=tc; i++) {
    		testcase();
    	}
          
        out.flush();
        out.close();
}


static void testcase() {
	
	n = s.nextInt();
	int[] a = new int[n], pos = new int[n], cnt = new int[n];
	
	// an element at index i occurs in how many k length arrays ?
	// min(i + 1, k, n - i)
	
	for(int i = 0; i < n; i++) {
		a[i] = s.nextInt() - 1;
		pos[a[i]] = i;
		cnt[a[i]]++;
 	}
	
	char[] ans = new char[n];
	Arrays.fill(ans, '0');
	
	if(isPerm(cnt)) ans[0] = '1';
	
	
	int l = 0, r = n - 1, k = 0;
	
	while(k < n - 1 && cnt[k] == 1 && (pos[k] == l || pos[k] == r)) {
		
		if(pos[k] == l) l++;
		
		else r--;
		
		k++;
		
	}
	
	for(int i = n - k; i < n; i++) ans[i] = '1';
	
	if(cnt[k] > 0) ans[n - k - 1] = '1';
	
	out.println(new String(ans));
	
	
	
	
}



private static boolean isPerm(int[] cnt) {
	
	for(int i = 0; i < n; i++) if(cnt[i] != 1) return false;
	return true;
}

public static MyWriter out;
public static MyScanner s;
static void shuffleArray(int[] a) {
	Random random = new Random();
	for (int i = a.length-1; i > 0; i--) {
		int index = random.nextInt(i + 1);
		int tmp = a[index];
		a[index] = a[i];
		a[i] = tmp;
	}
}
static void shuffleSort(int[] a) {
	shuffleArray(a);
	Arrays.parallelSort(a);
}
static class MyScanner {
 
    BufferedReader br;
    StringTokenizer st;
 
    public MyScanner() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }
    public MyScanner(String fileName) {
    	try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
 
    int nextInt() { return Integer.parseInt(next()); }
    long nextLong() { return Long.parseLong(next()); }
    double nextDouble() { return Double.parseDouble(next()); }
    int[] nextIntArray(int n){
    	int[]a = new int[n];
    	for(int i=0; i<n; i++) {
    		a[i] = this.nextInt();
    	}
    	return a;
    }
    long[] nextlongArray(int n) {
    	long[]a = new long[n];
    	for(int i=0; i<n; i++) {
    		a[i] = this.nextLong();
    	}
    	return a;
    }
    Integer[] nextIntegerArray(int n){
    	Integer[]a = new Integer[n];
    	for(int i=0; i<n; i++) {
    		a[i] = this.nextInt();
    	}
    	return a;
    }
    Long[] nextLongArray(int n) {
    	Long[]a = new Long[n];
    	for(int i=0; i<n; i++) {
    		a[i] = this.nextLong();
    	}
    	return a;
    }
    char[][] next2DCharArray(int n, int m){
    	char[][]arr = new char[n][m];
    	for(int i=0; i<n; i++) {
    		arr[i] = this.next().toCharArray();
    	}
    	return arr;
    }
    ArrayList<ArrayList<Integer>> readUndirectedUnweightedGraph(int n, int m) {
    	ArrayList<ArrayList<Integer>>adj = new ArrayList<ArrayList<Integer>>();
    	for(int i=0; i<=n; i++)adj.add(new ArrayList<Integer>());
    	for(int i=0; i<m; i++) {
    		int u = s.nextInt();
    		int v = s.nextInt();
    		adj.get(u).add(v);
    		adj.get(v).add(u);
    	}
    	return adj;
    }
    ArrayList<ArrayList<Integer>> readDirectedUnweightedGraph(int n, int m) {
    	ArrayList<ArrayList<Integer>>adj = new ArrayList<ArrayList<Integer>>();
    	for(int i=0; i<=n; i++)adj.add(new ArrayList<Integer>());
    	for(int i=0; i<m; i++) {
    		int u = s.nextInt();
    		int v = s.nextInt();
    		adj.get(u).add(v);
    	}
    	return adj;
    }
    String nextLine(){
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
 
static class MyWriter extends PrintWriter {
    MyWriter(OutputStream out) {
        super(out);
    }
    void println(int[] x) {
        println(x, " ");
    }
    void println(int[] x, String delimiter) {
        if (x.length > 0) {
            print(x[0]);
            for (int i = 1; i < x.length; i++) {
                print(delimiter);
                print(x[i]);
            }
        }
        println();
    }
    void println(long[] x) {
        println(x, " ");
    }
    void println(long[] x, String delimiter) {
        if (x.length > 0) {
            print(x[0]);
            for (int i = 1; i < x.length; i++) {
                print(delimiter);
                print(x[i]);
            }
        }
        println();
    }
    void println(Iterable<?> iterable) {
        println(iterable, " ");
    }
    void println(Iterable<?> iterable, String delimiter) {
        Iterator<?> i = iterable.iterator();
        if (i.hasNext()) {
            print(i.next());
            while (i.hasNext()) {
                print(delimiter);
                print(i.next());
            }
        }
        println();
    }
    void printLines(int[] x) {
        println(x, System.lineSeparator());
    }
    void printLines(long[] x) {
        println(x, System.lineSeparator());
    }
    void printLines(Iterable<?> iterable) {
        println(iterable, System.lineSeparator());
    }
}
 
}