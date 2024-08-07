import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.BigInteger;
public class Main{
    static InputReader sc;
    static PrintWriter pw;
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        sc = new InputReader(inputStream);
        pw = new PrintWriter(outputStream);
        solve();
        pw.close();
    }
    // static int cnt,edge;
    // static long ans,val;
    // static int time;
    static BigInteger min(BigInteger a, BigInteger b){        
        if(a.compareTo(b)<0)
            return a;
        return b;
    }    
    static List<Integer> sieveOfEratosthenes(int n)
    {        
        boolean prime[] = new boolean[n + 1];
        for (int i = 0; i <= n; i++)
            prime[i] = true;
 
        for (int p = 2; p * p <= n; p++)
        {            
            if (prime[p] == true)
            {
                for (int i = p * p; i <= n; i += p)
                    prime[i] = false;
            }
        }
        List<Integer> list=new ArrayList<>();
        for (int i = 2; i <= n; i++)
        {
            if (prime[i] == true)
                list.add(i);
        }
        return list;
    }
    // static boolean over;
    // static String res;
    public static void solve(){           
        int t=1;
        t=s(0);
        u:while(t-->0){
            int n=s(0);
            char []chrr=s("").toCharArray();
            HashMap<String,Integer> hmap=new HashMap<>();
            String s;
            int a=0,b=0,v;
            long g;
            for(int i=0;i<n;i++){
                if(chrr[i]=='D')
                    a++;
                else
                    b++;
                if(b==0){
                    p(a+" ");
                }
		else if(a==0){
                    p(b+" ");
                }
                else{
                    g=gcd(a,b);
                    s=(a/g)+"_"+(b/g);
                    v=hmap.getOrDefault(s, 0)+1;
                    hmap.put(s,v);
                    p(v+" ");
                }
            }
            pln("");
        }     
    }        
    public static boolean KMPSearch(char []txt, StringBuilder ptr){
        int M = ptr.length();
        int N = txt.length;          
        int lps[] = new int[M];
        int j = 0; 
        computeLPSArray(ptr, M, lps);  
        int i = 0; 
        while (i < N) {
            if (ptr.charAt(j) == txt[i]) {
                j++;
                i++;
            }
            if (j == M) {
                return true;
            }  
            else if (i < N && ptr.charAt(j) != txt[i]) {                
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
        return false;
    }
  
    public static void computeLPSArray(StringBuilder pat, int M, int lps[]){
        int len = 0;
        int i = 1;
        lps[0] = 0;  
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            }
            else 
            {                
                if (len != 0) {
                    len = lps[len - 1];  
                }
                else
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }
    public static void find(int i, List<Integer> list, Tree tr, char []chrr){        
        if(i<list.size()-1){
            if(list.get(i+1)-tr.left.val==0)
                find(i+1,list,tr.left,chrr);
            else
                find(i+1,list,tr.right,chrr);
        }
        if(chrr[tr.val-1]=='0')
            tr.cnt=tr.left.cnt;
        if(chrr[tr.val-1]=='1')
            tr.cnt=tr.right.cnt;
        if(chrr[tr.val-1]=='?')
            tr.cnt=tr.left.cnt+tr.right.cnt;        
    }
    public static void setCount(Tree tr, char[] chrr){
        if(tr==null||tr.max==tr.min)
            return;        
        setCount(tr.left, chrr);
        setCount(tr.right, chrr);
        if(chrr[tr.val-1]=='0')
            tr.cnt=tr.left.cnt;
        if(chrr[tr.val-1]=='1')
            tr.cnt=tr.right.cnt;
        if(chrr[tr.val-1]=='?')
            tr.cnt=tr.left.cnt+tr.right.cnt;
        // pln(tr.cnt);
    }
    public static Tree makeTree(int min, int max){
        Tree tr=new Tree(0,min,max);
        if(min==max){
            tr.cnt=1;
            return tr;
        }
        tr.left=makeTree(min, (max+min)/2);
        tr.right=makeTree((max+min)/2+1, max); 
        // pln(tr.min+" "+tr.max);       
        return tr;
    }
    public static void giveVal(Tree tr, int val){
        Queue<Tree> q=new LinkedList<>();
        Tree pr;
        q.offer(tr);
        while(q.size()>0&&val>0){
            pr=q.poll();
            pr.val=val--;
            if(pr.right.min!=pr.right.max){
                q.offer(pr.right);
            }
            if(pr.left.min!=pr.left.max){
                q.offer(pr.left);
            }
        }
    }
    public static void makePath(Tree tr, List<Integer> []list, List<Integer> temp){
        if(tr.max==tr.min)
            return;
        temp.add(tr.val);
        list[tr.val]=new ArrayList<>(temp);
        makePath(tr.left, list, temp);
        makePath(tr.right, list, temp);
        temp.remove(temp.size()-1);
    }
    static class Tree{
        Tree left,right;
        int max,min,cnt,val;
        Tree(int v,int mn, int mx){
            val=v;
            max=mx;
            min=mn;
            cnt=0;
        }
    }
    static long fact(long p){
        long ans=1l;
        for(long i=2;i<=p;i++)
            ans*=i;
        return ans;
    }    
    static int find(int j, List<Integer> B, List<Integer> A, int i){
        int l=j,r=B.size()-1,m;        
        while(l<=r){
            m=(r-l)/2+l;
            if(A.size()-i-1<=B.size()-m-1)
                l=m+1;
            else
                r=m-1;
        }
        return r;
    }
    static int findRightLimitIndex(List<Integer> B, int x){
        int l=0,r=B.size()-1,m;        
        while(l<=r){            
            m=(r-l)/2+l;
            if(B.get(m)-x<=0)
                l=m+1;
            else
                r=m-1;
        }
        return r;        
    }
    static long nPr(long n, long r){
        long ans=1;
        for(long i=1;i<=r;i++)
            ans*=(n-i+1);
        return ans;
    }
    static long nCr(long n, long r){
        long ans=1;
        for(long i=1;i<=r;i++){
            ans*=(n-i+1);
            ans/=i;
        }
        return ans;
    }
    static void update_DAG(int cur,int val, int []graph, int n)
    {
        if(val>maxx[cur])
        {
            int x=graph[cur];
            if(x!=-1)
                update_DAG(x,val+1,graph,n);
            maxx[cur]=val;
            update(cur,val,n);
        }
    }
    static int []bit, maxx;
    static void update(int i,int val, int n)
    {
        while(i<=n)
        {
            bit[i]=Math.max(bit[i],val);
            i=i+(i&(-i));
        }
    }
    static int query(int i)
    {
        int ret=0;
        while(i>0)
        {
            ret=Math.max(ret,bit[i]);
            i=i-(i&(-i));
        }
        return ret;
    }    
    public static int [][]dir=new int [][]{{1,0},{0,1},{-1,0},{0,-1}};        
    public static int find(List<Integer> list, int x){
        int l=0,r=list.size()-1,m;
        while(l<=r){
            m=(r-l)/2+l;
            if(list.get(m)<=x)
                l=m+1;
            else
                r=m-1;
        }
        return r;
    } 
    static class Node{
        int val;
        long cost;
        Node next;
        Node(int v,long c){
            val=v;
            next=null;
            cost=c;
        }
    }    
    public static long sum(long n){
        long val=0l;
        while(n>0){
            val+=n%10;
            n/=10;
        }
        return val;
    }
    // static class Node{
    //     int left,right;
    //     Node prev,next;
    //     Node(int i, int v){
    //         left=i;
    //         right=v;
    //         prev=next=null;
    //     }
    //     void remove(){
    //         this.prev.next=this.next;
    //         this.next.prev=this.prev;
    //     }
    //     void insert(Node node){
    //         node.next=this;
    //         node.prev=this.prev;
    //         node.prev.next=node;
    //         this.prev=node;
    //     }
    // } 
    public static int  findDiameter(int r, List<List<Integer>>list){
        return findFarthest(findFarthest(r,list)[0],list)[1];
    }
    public static int[] findFarthest(int u, List<List<Integer>>list){
        int n=list.size();
        boolean []vis=new boolean[n+1];
        Queue<Integer>q=new LinkedList<>();
        q.offer(u);
        vis[u]=true;
        int s,pr,cnt=0;
        int []ar=new int[]{u,0};
        while(q.size()>0){
            s=q.size();
            while(s-->0){
                pr=q.poll();
                if(ar[1]<cnt){
                    ar[1]=cnt;
                    ar[0]=pr;
                }
                for(int i:list.get(pr)){
                    if(!vis[i]){
                        vis[i]=true;
                        q.offer(i);
                    }
                }
            }
            cnt++;
        }
        return ar;
    }
    public static long atMostK(char []chrr, int k){
        if(k<0)
            return 0;
        int l=0,cnt=0;
        long ans=0l;
        for(int i=0;i<chrr.length;i++){
            if(chrr[i]=='1')
                cnt++;
            while(cnt>k){
                if(chrr[l++]=='1')
                    cnt--;
            }
            ans+=(long)(i-l)+1l;
        }
        return ans;
    }
    public static int ask(int l, int r){
        System.out.println("? "+l+" "+r);
        System.out.flush();
        return sc.nextInt();
    }
    public static void sort(int []arr){
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<arr.length;i++)
            list.add(arr[i]);
        Collections.sort(list);
        for(int i=0;i<arr.length;i++)
            arr[i]=list.get(i);
    }
    public static void sort(long []arr){
        ArrayList<Long> list=new ArrayList<>();
        for(int i=0;i<arr.length;i++)
            list.add(arr[i]);
        Collections.sort(list);
        for(int i=0;i<arr.length;i++)
            arr[i]=list.get(i);
    }
    public static void swap(char []chrr, int i, int j){
        char temp=chrr[i];
        chrr[i]=chrr[j];
        chrr[j]=temp;
    }
    public static int countSetBits(long n){
        int a=0;
        while(n>0){
            a+=(n&1);
            n>>=1;
        }
        return a;
    }
    static class Pair{
        String s;
        int i;
        Pair(String S, int I){
            s=S;
            i=I;
        }
     //*
    }
    /*/
        public int compareTo(Pair p){
            return (b-p.b);
        }
        public int hashCode(){
            int hashcode = (a+" "+b).hashCode();
            return hashcode;
        }
        public boolean equals(Object obj){
            if (obj instanceof Pair) {
                Pair p = (Pair) obj;
                return (p.a==this.a && p.b == this.b);
            }
            return false;
        }
    }
    //*/
    static boolean isPrime(long n) { 
        if (n <= 1) 
            return false; 
        if (n <= 3) 
            return true; 
        if (n % 2 == 0 || n % 3 == 0) 
            return false; 
        for (int i = 5; i * i <= n; i = i + 6) 
            if (n % i == 0 || n % (i + 2) == 0) 
                return false; 
        return true; 
    } 
    static long gcd(long a, long b) { 
        if (b == 0) 
            return a; 
        return a>b?gcd(b, a % b):gcd(a, b % a);  
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
    static long modInverse(long n,long M){
        return fast_pow(n,M-2,M);
    }
    public static int s(int n){
        return sc.nextInt();
    }
    public static long s(long n){
        return sc.nextLong();
    }
    public static String s(String n){
        return sc.next();
    }
    public static double s(double n){
        return sc.nextDouble();
    }
    public static void p(int n){
        pw.print(n);
    }
    public static void p(long n){
        pw.print(n);
    }
    public static void p(String n){
        pw.print(n);
    }
    public static void p(double n){
        pw.print(n);
    }
    public static void pln(int n){
        pw.println(n);
    }
    public static void pln(long n){
        pw.println(n);
    }
    public static void pln(String n){
        pw.println(n);
    }
    public static void pln(double n){
        pw.println(n);
    }
    public static void feedArr(long []arr){
        for(int i=0;i<arr.length;i++)
            arr[i]=sc.nextLong();
    }
    public static void feedArr(double []arr){
        for(int i=0;i<arr.length;i++)
            arr[i]=sc.nextDouble();
    }
    public static void feedArr(int []arr){
        for(int i=0;i<arr.length;i++)
            arr[i]=sc.nextInt();
    }
    public static void feedArr(String []arr){
        for(int i=0;i<arr.length;i++)
            arr[i]=sc.next();
    }
    public static String printArr(int []arr){
        StringBuilder sbr=new StringBuilder();
        for(int i:arr)
            sbr.append(i+" ");
        return sbr.toString();
    }
    public  static String printArr(long []arr){
        StringBuilder sbr=new StringBuilder();
        for(long i:arr)
            sbr.append(i+" ");
        return sbr.toString();
    }
    public static String printArr(String []arr){
        StringBuilder sbr=new StringBuilder();
        for(String i:arr)
            sbr.append(i+" ");
        return sbr.toString();
    }
    public static String printArr(double []arr){
        StringBuilder sbr=new StringBuilder();
        for(double i:arr)
            sbr.append(i+" ");
        return sbr.toString();
    }
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
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
    }
}
class AncestorQuerier {
    int [][]dp;
    int N=200001;
    int M=22;
    int max;    
    public void preCompute(int []par){
        for(int i=0;i<N;i++){
            if(i>=2&&i<par.length)
                dp[i][0]=par[i];
            else
                dp[i][0]=-1;
        }
        for(int j=1;j<M;j++){
            for(int i=0;i<N;i++){
                if(dp[i][j-1]!=-1)
                    dp[i][j]=dp[dp[i][j-1]][j-1];
            }
        }       
    }    
    public int getAncestor(int val, int k) {
        if(k<0||val>max)
            return -1;
        if(k==0)
            return val;
        int t=(1<<(M-1));        
        for(int i=M-1;i>=0&&val!=-1;i--,t>>=1){
            if(t<=k){
                val=dp[val][i];
                k-=t;
            }
        }
        return val;   
    }
}
