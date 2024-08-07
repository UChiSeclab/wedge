import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;

public class Solution{
	
	static ArrayList<Integer>[] adjlist;
	static int[] ans;
	static final int inf = (int)1e9;
	static int n;
	
	
	
    public static void main(String[] args) throws IOException {
     
    	FastScanner fs = new FastScanner();
    	PrintWriter out = new PrintWriter(System.out);
    	
    		
    		
    	int tt = 1;
    	while(tt-->0) {
    		
    		n = fs.nextInt();
    		int[] a = new int[n];
    		
    		ArrayList<Integer> odd = new ArrayList<Integer>();
    		ArrayList<Integer> even = new ArrayList<Integer>();
    		
    		for(int i=0;i<n;i++) {
    			a[i] = fs.nextInt();
    			if((a[i]&1)==1) odd.add(i);
    			else even.add(i);
    		}
    		
    		adjlist = new ArrayList[n];
    		for(int i=0;i<n;i++) adjlist[i] = new ArrayList<Integer>();
    		
    		for(int i=0;i<n;i++) {
    			int l = i-a[i];
    			int r = i+a[i];
    			if(l>=0) adjlist[l].add(i);
    			if(r<n) adjlist[r].add(i);
    		}
    		
    		ans = new int[n];
    		Arrays.fill(ans, -1);
    		
    		int x = 1;
    		
    		bfs(even, odd);
    		bfs(odd, even);
    		
    		for(int i=0;i<n;i++) out.print(ans[i]+" ");
    		out.println();
    		
    		
    		
    	}
    	
    		
    		
    		
    	out.close();
    		
    }
    
    
    
    //multisource bfs
    static void bfs(ArrayList<Integer> start, ArrayList<Integer> end) {
    	int[] d = new int[n];
    	Arrays.fill(d, inf);
    	
    	ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
    	
    	for(int i: start) {
    		d[i] = 0;
    		queue.add(i);
    	}
    	
    	
    	while(!queue.isEmpty()) {
    		int v = queue.poll();
    		for(int u : adjlist[v]) {
    			if(d[u]==inf) {
    				d[u] = 1 + d[v];
    				queue.add(u);
    			}
    		}
    	}
    	
    	for(int i: end) {
    		if(d[i]!=inf) {
    			ans[i] = d[i];
    		}
    	}
    	
    	
    }
  
    
    
    
    static final Random random=new Random();
    	
    static void ruffleSort(int[] a) {
    	int n=a.length;//shuffle, then sort 
    	for (int i=0; i<n; i++) {
    		int oi=random.nextInt(n); int temp=a[oi];
    		a[oi]=a[i]; a[i]=temp;
    	}
    	Arrays.sort(a);
    }
   
  
    
    	
    	
    static class FastScanner{
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st = new StringTokenizer("");
     
    	public String next(){
    		while(!st.hasMoreElements()){
    			try{
    				st = new StringTokenizer(br.readLine());
    			} catch(IOException e){
    				e.printStackTrace();
    			}
    		}
    		return st.nextToken();
    	}
    		
    	public String nextLine() throws IOException {
    		return br.readLine();
    	}
    		
    	public int nextInt(){
    		return Integer.parseInt(next());
    	}
     
    	public int[] readArray(int n){
    		int[] a = new int[n];
    		for(int i=0;i<n;i++)
    			a[i] = nextInt();
    		return a;
    	}
    		
    	public long nextLong() {
    		return Long.parseLong(next());
    	}
    		
    	public char nextChar() {
    		return next().toCharArray()[0];
    	}
    }
   	
}
