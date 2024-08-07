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
import java.util.*;
import java.lang.*;
import java.io.*;
public class Main
{
	//Fast IO class
    static class FastReader 
    { 
        BufferedReader br; 
        StringTokenizer st; 
        public FastReader() 
        { 
        	boolean env=System.getProperty("ONLINE_JUDGE") != null;
        	if(!env) {
        		try {
					br=new BufferedReader(new FileReader("src\\input.txt"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
        	}
        	else {
        		br = new BufferedReader(new
                        InputStreamReader(System.in)); 
        	}
        } 
        String next() 
        { 
            while (st == null || !st.hasMoreElements()) 
            { 
                try
                { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException  e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
        int nextInt() 
        { 
            return Integer.parseInt(next()); 
        } 
        long nextLong() 
        { 
            return Long.parseLong(next()); 
        } 
        double nextDouble() 
        { 
            return Double.parseDouble(next()); 
        } 
        String nextLine() 
        { 
            String str = ""; 
            try
            { 
                str = br.readLine(); 
            } 
            catch (IOException e) 
            { 
                e.printStackTrace(); 
            } 
            return str; 
        } 
    }     
    static long MOD=1000000000+7;
    //Euclidean Algorithm
    static long gcd(long A,long B){
        if(B==0) return A;
        return gcd(B,A%B);
    }
    //Modular Exponentiation
    static long fastExpo(long x,long n){
        if(n==0) return 1;
        if((n&1)==0) return fastExpo((x*x)%MOD,n/2)%MOD;
        return ((x%MOD)*fastExpo((x*x)%MOD,(n-1)/2))%MOD;
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
    static <T> void reverse(T arr[],int l,int r){
    	Collections.reverse(Arrays.asList(arr).subList(l, r));
    }
    //Print array
    static <T> void print1d(T arr[]) {
    	out.println(Arrays.toString(arr));
    }
    static <T> void print2d(T arr[][]) {
    	for(T a[]: arr) out.println(Arrays.toString(a));
    }
    // Pair
    static class pair{
    	long x,y;
    	pair(long a,long b){
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
    static ArrayList<Integer> graph[];
    static int a[],ans[],V;
    static void bfs(ArrayList<Integer> start,ArrayList<Integer> end) {
    	int d[]=new int[V];
    	Arrays.fill(d, Integer.MAX_VALUE);
    	Queue<Integer> q=new LinkedList<>();
    	for(Integer i: start) {
    		d[i]=0;
    		q.add(i);
    	}
    	while(!q.isEmpty()) {
    		int v=q.poll();
    		for(Integer x: graph[v]) {
    			if(d[x]==Integer.MAX_VALUE) {
    				d[x]=d[v]+1;
    				q.add(x);
    			}
    		}
    	}
    	for(Integer i: end) if(d[i]!=Integer.MAX_VALUE) ans[i]=d[i];	
    }
    public static void main (String[] args) throws java.lang.Exception
    {
    	int test=1;
        while(test-->0) {
        	int n=sc.nextInt();
        	a=new int[n];ans=new int[n]; V=n;
        	Arrays.fill(ans, -1);
        	graph=new ArrayList[n];
        	for(int i=0;i<n;i++) graph[i]=new ArrayList<>();
        	ArrayList<Integer> odd=new ArrayList<>(),even=new ArrayList<>();
        	for(int i=0;i<n;i++) {
        		a[i]=sc.nextInt();
        		if(a[i]%2==0) even.add(i);
        		else odd.add(i);
        	}
        	for(int i=0;i<n;i++) {
        		int l=i-a[i],r=i+a[i];
        		if(l>=0) graph[l].add(i);
        		if(r<n) graph[r].add(i);
        	}
        	bfs(odd,even);
        	bfs(even,odd);
        	for(int i: ans) out.print(i+" ");
        }
        out.close();
    }
}