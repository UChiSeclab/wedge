import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

public class Solution{
	
	
	
    public static void main(String[] args) throws IOException {
     
    	FastScanner fs = new FastScanner();
    	PrintWriter out = new PrintWriter(System.out);
    	
    		
    		
    	int tt = 1;
    	while(tt-->0) {
    		
    		int n = fs.nextInt();
    		
    		int[][] arr = new int[n][2];
    		
    		for(int i=0;i<n;i++	) {
    			arr[i][0] = fs.nextInt();
    		}
    		
    		for(int i=0;i<n;i++) {
    			arr[i][1] = fs.nextInt();
    		}
    		
    		//Randomizig the arr array
    		for(int i=0;i<n;i++) {
    			int ri = random.nextInt(n);
    			int[] temp = arr[i]; arr[i] = arr[ri]; arr[ri] = temp;
    		}
    		
    		Arrays.sort(arr, (x, y) -> x[0]-y[0]);
    		
    		
    		int[] p = new int[n];
    		
    		
    		for(int i=0;i<n;i++) {
    			p[i] = arr[i][1];
    		}
    		
    		ruffleSort(p);
    		Ftree ft1 = new Ftree(n);
    		Ftree ft2 = new Ftree(n);
    		
    		long ans = 0;
    		
    		for(int i=n-1;i>=0;i--) {
    			int ind = lowerbound(p, arr[i][1]);
    			ans += (ft1.query(n-1)-ft1.query(ind-1)) - (ft2.query(n-1)-ft2.query(ind-1))*(arr[i][0]);
    			ft1.update(ind, arr[i][0]);
    			ft2.update(ind, 1);
    		}
    		
    		out.println(ans);
    		
    		
    		
    		
    	}
    	
    		
    		
    		
    	out.close();
    		
    }
    
    
    
  
    
    static class Ftree{
    	long[] bit;
    	int n;
    	
    	Ftree(int n){
    		this.n = n;
    		bit = new long[n+1];
    	}
    	
    	void update(int ind, int val) {
    		ind++;
    		while(ind<=n) {
    			bit[ind] += val;
    			ind += ind&(-ind);
    		}
    	}
    	
    	long query(int ind) {
    		ind++;
    		long sum = 0;
    		while(ind>0) {
    			sum += bit[ind];
    			ind -= ind&(-ind);
    		}
    		return sum;
    	}
    	
    }
    
    
    static int lowerbound(int[] arr, int x) {
    	int l = 0;
    	int r= arr.length;
    	while(l<r) {
    		int mid = (l+r)/2;
    		if(arr[mid]>=x) r = mid;
    		else l = mid+1;
    	}
    	return r;
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
