/*	/ ﾌﾌ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ム
	/ )\⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀  Y
	(⠀⠀| ( ͡° ͜ʖ ͡°）⠀⌒(⠀ ノ
	(⠀ ﾉ⌒ Y ⌒ヽ-く __／
	| _⠀｡ノ| ノ｡ |/
	(⠀ー '_人`ー ﾉ
	⠀|\ ￣ _人'彡ﾉ
	⠀ )\⠀⠀ ｡⠀⠀ /
	⠀⠀(\⠀ #⠀ /
	⠀/⠀⠀⠀/ὣ====================D-
	/⠀⠀⠀/⠀ \ \⠀⠀\
	( (⠀)⠀⠀⠀⠀ ) ).⠀)
	(⠀⠀)⠀⠀⠀⠀⠀( | /
	|⠀ /⠀⠀⠀⠀⠀⠀ | /
	[_] ⠀⠀⠀⠀⠀[___]                     */
// Main Code at the Bottom
import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.BigInteger; 
public class Main {
	//Fast IO class
    static class FastReader {
        BufferedReader br; 
        StringTokenizer st; 
        public FastReader() {
        	boolean env=System.getProperty("ONLINE_JUDGE") != null;
        	if(!env) {
        		try {
					br=new BufferedReader(new FileReader("src\\input.txt"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
        	}
        	else br = new BufferedReader(new InputStreamReader(System.in)); 
        } 
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException  e) {
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
            } 
            catch (IOException e) {
                e.printStackTrace(); 
            } 
            return str; 
        } 
    }     
    static long MOD=1000000000+7;
    //Euclidean Algorithm
    static long gcd(long A,long B){
    	return (B==0)?A:gcd(B,A%B);
    }
    //Modular Exponentiation
    static long fastExpo(long x,long n){
        if(n==0) return 1;
        if((n&1)==0) return fastExpo((x*x)%MOD,n/2)%MOD;
        return ((x%MOD)*fastExpo((x*x)%MOD,(n-1)/2))%MOD;
    }
    //Modular Inverse
    static long inverse(long x) {
    	return fastExpo(x,MOD-2);
    }
    //Prime Number Algorithm
    static boolean isPrime(long n){
        if(n<=1) return false;
        if(n<=3) return true;
        if(n%2==0 || n%3==0) return false;
        for(int i=5;i*i<=n;i+=6) if(n%i==0 || n%(i+2)==0) return false;
        return true;
    }
    //Reverse an array
    static void reverse(int arr[],int l,int r){
    	while(l<r) {
    		int tmp=arr[l];
    		arr[l++]=arr[r];
    		arr[r--]=tmp;
    	}
    }
    //Print array
    static void print1d(int arr[]) {
    	out.println(Arrays.toString(arr));
    }
    static void print2d(int arr[][]) {
    	for(int a[]: arr) out.println(Arrays.toString(a));
    }
    // Pair
    static class pair{
    	int x,y;
    	pair(int a,int b){
    		this.x=a;
    		this.y=b;
    	}
    	public boolean equals(Object obj) {
    		if(obj == null || obj.getClass()!= this.getClass()) return false;
            pair p = (pair) obj;
            return (this.x==p.x && this.y==p.y);
        }
    	public int hashCode() {
            return Objects.hash(x,y);
        }
    }
    static FastReader sc=new FastReader();
    static PrintWriter out=new PrintWriter(System.out);  
    //Main function(The main code starts from here)
    static int N=200000;
    static pair bit[]=new pair[N+1];
    static void add(int idx,int val) {
    	while(idx<=N) {
    		bit[idx].x++;
    		bit[idx].y+=val;
    		idx+=idx&-idx;
    	}
    }
    static pair sum(int idx) {
    	pair res=new pair(0,0);
    	while(idx>0) {
    		res.x+=bit[idx].x;
    		res.y+=bit[idx].y;
    		idx-=idx&-idx;
    	}
    	return res;
    }
    public static void main (String[] args) throws java.lang.Exception {
    	int test;
    	test=1;
    	//test=sc.nextInt();
    	while(test-->0){
    		for(int i=0;i<=N;i++) bit[i]=new pair(0,0);
    		int n=sc.nextInt();
    		pair a[]=new pair[n];
    		for(int i=0;i<n;i++) a[i]=new pair(sc.nextInt(),-1);
    		HashMap<Integer,Integer> map=new HashMap<>();
    		TreeSet<Integer> set=new TreeSet<>();
    		for(int i=0;i<n;i++) {
    			a[i].y=sc.nextInt();
    			set.add(a[i].y);
    		}
    		Arrays.parallelSort(a,(p1,p2)->p1.x-p2.x);
    		int idx=1;
    		for(Integer x: set) map.put(x,idx++);
    		long ans=0;
    		for(int i=n-1;i>=0;i--) {
    			idx=map.get(a[i].y);
    			int val=a[i].x;
    			pair r=sum(N),l=sum(idx-1);
    			long x=r.y-l.y,y=r.x-l.x;
    			ans+=x-val*y;
    			add(idx,val);
    		}
    		out.println(ans);
    		
    		
    	} 	
        out.flush();
        out.close();
    }
}