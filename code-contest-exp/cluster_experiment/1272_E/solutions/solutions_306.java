import java.io.*; 
import java.util.*; 
//import javafx.util.*; 
import java.math.*;
//import java.lang.*;
public class Main 
{ 
    
      static int n;
      // static ArrayList<Integer> adj[];
    //  static int m;
    //  // static int count;
     static boolean vis[];
     static long ans[];
   //  static int ans[];
    //   static int deg[];
    //   static int cou;
    //  static ArrayList<Integer> arr;
    //  static  HashSet<Integer> hs;
    //  static int a[];
    //  static int leaf[];
    //  static int dist[];
    // static final long oo=(long)1e18;
    static int arr[];
    public static void main(String[] args) throws IOException { 
    //    Scanner sc=new Scanner(System.in);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        br = new BufferedReader(new InputStreamReader(System.in));
        //int t=nextInt();
        int t=1;
        outer:while(t--!=0){
            int n = nextInt();
            int[] arr = new int[n];
            for(int i = 0; i < n; i++) {
                arr[i] = nextInt();
            }
            ArrayList<Integer>[] g = new ArrayList[n];
            for(int i = 0; i < n; i++) g[i] = new ArrayList<>();
            for(int i = 0; i < n; i++) {
                if(i + arr[i] < n) {
                    g[i + arr[i]].add(i);
                }
                if(i - arr[i] >= 0) {
                    g[i - arr[i]].add(i);
                }
            }
            int[] dist = new int[n];
            Arrays.fill(dist, -1);
            LinkedList<Integer> q = new LinkedList<Integer>();
            for(int i = 0; i < n; i++) {
                if(i+arr[i] < n && (arr[i] + arr[i+arr[i]]) % 2 == 1) {
                    dist[i] = 1;
                    q.add(i);
                }
                else if(i-arr[i] >= 0 && (arr[i] + arr[i-arr[i]]) % 2 == 1) {
                    dist[i] = 1;
                    q.add(i);
                }
            }
            while(!q.isEmpty()) {
                int u = q.removeFirst();
                for(int v: g[u]) {
                    if(dist[v] < 0) {
                        dist[v] = dist[u]+1;
                        q.add(v);
                    }
                }
            }
            for(int k: dist) 
                pw.print(k+" ");
            pw.println();
        }
        pw.close();
    }
    static long find(int i){
        System.out.println(i);

        vis[i]=true;
        int j=i+arr[i];
        int k=i-arr[i];
        
        if(j<n&&(arr[i]+arr[j])%2!=0){
            
            
            return 1;
        }
        else if(k>=0&&(arr[i]+arr[k])%2!=0){
            return 1;
        }
        else if(j<n&&k>=0){
            if(!vis[j]){
                ans[j]=find(j);
            }
            if(!vis[k]){
                ans[k]=find(k);
            }
            ans[i]=Math.min(ans[j],ans[k])+1;
            return ans[i];

            
        }
        else if(j<n){
            if(vis[j]){
                ans[i]=ans[j]+1;
            }
            else{
                ans[j]=find(j);
                ans[i]=ans[j]+1;
            }
            
            return ans[i];
        }
        else if(k>=0){
            if(vis[k]){
                ans[i]=ans[k]+1;
            }
            else{
                ans[k]=find(k);
                ans[i]=ans[k]+1;
            }
            // if(ans[i]!=-1){
            //     ans[i]+=1;
            // }
            return ans[i];
        }
        else{
            return 1000000000;
        }
    }
   
   
    public static BufferedReader br;
    public static StringTokenizer st;
    public static String next() {
        while (st == null || !st.hasMoreTokens()) {
       try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return st.nextToken();
    }
 
    public static Integer nextInt() {
        return Integer.parseInt(next());
    }
    public static Long nextLong() {
        return Long.parseLong(next());
    }
 
    public static Double nextDouble() {
        return Double.parseDouble(next());
    }
    // static class Pair{
    //     int x;int y;
    //     Pair(int x,int y,int z){
    //         this.x=x;
    //         this.y=y;
    //        // this.z=z;
    //      //   this.z=z;
    //       //  this.i=i;
    //     }
    // }
    // static class sorting implements Comparator<Pair>{
    //     public int compare(Pair a,Pair b){
    //         //return (a.y)-(b.y);
    //         if(a.y==b.y){
    //             return -1*(a.z-b.z);
    //         }
    //         return (a.y-b.y);
    //     }
   // }
    public static int[] na(int n)throws IOException{
        int[] a = new int[n];
        for(int i = 0;i < n;i++)a[i] = nextInt();
        return a;
    }
    static class query implements Comparable<query>{
        int l,r,idx,block;
        static int len;
        query(int l,int r,int i){
            this.l=l;
            this.r=r;
            this.idx=i;
            this.block=l/len;
        }   
        public int compareTo(query a){
            return block==a.block?r-a.r:block-a.block;
        }
    }
    static class Pair implements Comparable<Pair>{
        int a;int b;
        Pair(int a,int b){
            this.a=a;
            this.b=b;
            //this.z=z;
        }   
        public int compareTo(Pair p){
            
            return (a-p.a);
            //return (x-a.x)>0?1:-1;
        }
    }
    // static class sorting implements Comparator<Pair>{  
    //     public int compare(Pair a1,Pair a2){  
    //         if(o1.a==o2.a)
    //             return (o1.b>o2.b)?1:-1;  
    //         else if(o1.a>o2.a)  
    //             return 1;  
    //         else  
    //             return -1;  
    //     }
    // }  
    static boolean isPrime(int n) { 
        if (n <= 1) 
            return false; 
        if (n <= 3) 
            return true; 
        if (n % 2 == 0 ||  
            n % 3 == 0) 
            return false; 
      
        for (int i = 5; 
                 i * i <= n; i = i + 6) 
            if (n % i == 0 || 
                n % (i + 2) == 0) 
                return false; 
      
        return true; 
    } 
    static int gcd(int a, int b) { 
      if (b == 0) 
        return a; 
      return gcd(b, a % b);  
    }  
    // To compute x^y under modulo m 
    static long power(long x, long y, long m){ 
        if (y == 0) 
            return 1;      
        long p = power(x, y / 2, m) % m; 
        p = (p * p) % m; 
      
        if (y % 2 == 0) 
            return p; 
        else
            return (x * p) % m; 
    }
    static long fast_pow(long base,long n,long M){
        if(n==0)
           return 1;
        if(n==1)
        return base;
        long halfn=fast_pow(base,n/2,M);
        if(n%2==0)
            return ( halfn * halfn ) % M;
        else
            return ( ( ( halfn * halfn ) % M ) * base ) % M;
    }
       static long modInverse(long n,int M){
        return fast_pow(n,M-2,M);
    }
    
} 