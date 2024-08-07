import java.io.*;
import java.util.*;

public class Main {
    static PrintWriter pw;
    static Scanner sc;
    static long ceildiv(long x, long y) { return (x+y-1)/y; }
    static int mod(long x, int m) { return (int)((x%m+m)%m); }
    static void put(TreeMap<Integer, Integer> map, Integer p){if(map.containsKey(p)) map.replace(p, map.get(p)+1); else map.put(p, 1); }
    static void rem(TreeMap<Integer, Integer> map, Integer p){ if(map.get(p)==1) map.remove(p);else map.replace(p, map.get(p)-1); }
    static void printf(double x, int dig){ String s="%."+dig+"f"; pw.printf(s, x); }
    static int Int(boolean x){ return x?1:0; }
    static final int inf=(int)1e9, mod=998244353;
    static final long infL=inf*1l*inf;
    static final double r2=Math.sqrt(2), eps=1e-9;
    public static long gcd(long x, long y) { return y==0? x: gcd(y, x%y); }
    public static void main(String[] args) throws IOException {
        sc=new Scanner(System.in);
        pw=new PrintWriter(System.out);
        int t=sc.nextInt();
        while(t-->0)
            testcase();
        pw.close();
    }
    static void testcase() throws IOException{
        int n=sc.nextInt(), arr[]= sc.nextArr(n);
        int max=range(arr);
        int len=1;
        while(len<n)
            len<<=1;
        int[] a=new int[len];
        Arrays.fill(a, inf);
        for(int i=0; i<n; i++)
            a[i]=arr[i];
        SegmentTree stree=new SegmentTree(a);
        TreeMap<Integer, TreeMap<Integer, Integer>> map=new TreeMap<>();
        for (int i = 0; i < n; i++) {
            Pair p=stree.query(i, arr[i]);
            p.x=Math.max(p.x, 0);
            p.y=Math.min(p.y, arr.length-1);
            int x=p.y-p.x+1;
            if(!map.containsKey(x))
                map.put(x, new TreeMap<>());
            put(map.get(x), arr[i]);
        }
        int i=1, j=n;
        boolean ans[]=new boolean[n];
        while(!map.isEmpty()){
            ans[i-1]=j<=max && map.containsKey(i) && map.get(i).containsKey(j);
            if(map.containsKey(i))
                map.remove(i);
            i++;
            j--;
        }
        printArr(ans);
//        pw.println(map);
    }
    static void remlast(TreeMap<Integer, TreeMap<Integer, Integer>> map){
        rem(map.lastEntry().getValue(), map.lastEntry().getValue().firstKey());
        if(map.lastEntry().getValue().isEmpty())
            map.pollLastEntry();
    }
    static int range(int[] arr){
        TreeSet<Integer> tree=new TreeSet<>();
        for(int x: arr)
            tree.add(x);
        if(!tree.contains(1))
            return 0;
        int prev=tree.pollFirst();
        while(!tree.isEmpty()){
            int x=tree.pollFirst();
            if(x==prev+1)
                prev=x;
            else
                break;
        }
        return prev;
    }
    static void printArr(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            pw.print(arr[i] + " ");
        pw.println(arr[arr.length - 1]);
    }
    static void printArr(long[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            pw.print(arr[i] + " ");
        pw.println(arr[arr.length - 1]);
    }
    static void printArr(double[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            pw.print(arr[i] + " ");
        pw.println(arr[arr.length - 1]);
    }
    static void printArr(Integer[] arr) {
        for (int i = 0; i < arr.length; i++)
            pw.print(arr[i] + " ");
        pw.println();
    }
    static void printArr(ArrayList<Integer> list) {
        for (int i = 0; i < list.size(); i++)
            pw.print(list.get(i)+" ");
        pw.println();
    }
    static void printArr(boolean[] arr) {
        StringBuilder sb=new StringBuilder();
        for(boolean b: arr)
            sb.append(Int(b));
        pw.println(sb.toString());
    }
    static class Scanner {
        StringTokenizer st;
        BufferedReader br;
        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public Scanner(FileReader r) {
            br = new BufferedReader(r);
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
        public int[] nextDigits() throws IOException{
            String s=nextLine();
            int[] arr=new int[s.length()];
            for(int i=0; i<arr.length; i++)
                arr[i]=s.charAt(i)-'0';
            return arr;
        }
        public int[] nextArr(int n) throws IOException {
            int[] arr = new int[n];
            for (int i = 0; i < arr.length; i++)
                arr[i] = nextInt();
            return arr;
        }
        public Integer[] nextsort(int n) throws IOException{
            Integer[] arr=new Integer[n];
            for(int i=0; i<n; i++)
                arr[i]=nextInt();
            return arr;
        }
        public Pair nextPair() throws IOException{
            return new Pair(nextInt(), nextInt());
        }
        public Pair[] nextPairArr(int n) throws IOException{
            Pair[] arr=new Pair[n];
            for(int i=0; i<n; i++)
                arr[i]=nextPair();
            return arr;
        }
        public boolean ready() throws IOException {
            return br.ready();
        }
    }
    static class Pair implements Comparable<Pair>{
        int x;
        int y;
        public Pair(int x, int y) {
            this.x=x;
            this.y=y;
        }
        public boolean contains(int a){
            return x==a || y==a;
        }
        public int hashCode() {
            return (this.x*1000000000+this.y);
        }
        public int compareTo(Pair p){
            if(y==p.y)
                return x-p.x;
            return y-p.y;
        }
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            Pair p = (Pair) obj;
            return this.x==p.x && this.y==p.y;
        }
        public Pair clone(){
            return new Pair(x, y);
        }
        public String toString(){
            return this.x+" "+this.y;
        }
        public void add(Pair p){
            x+=p.x;
            y+=p.y;
        }
    }
    static class LP implements Comparable<LP>{
        long x, y;
        public LP(long a, long b){
            x=a;
            y=b;
        }
        public void add(LP p){
            x+=p.x;
            y+=p.y;
        }
        public boolean equals(LP p){
            return p.x==x && y==p.y;
        }
        public String toString(){
            return this.x+" "+this.y;
        }
        public int compareTo(LP p){
            int a=Long.compare(x, p.x);
            if(a!=0)
                return a;
            return Long.compare(y, p.y);
        }
    }
    static class Triple implements Comparable<Triple>{
        int x, y, z;
        public Triple(int a, int b, int c){
            x=a;
            y=b;
            z=c;
        }
        public int compareTo(Triple t){
            if(this.y!=t.y)
                return y-t.y;
            return x-t.x;
        }
        public String toString(){
            return x+" "+y+" "+z;
        }
    }
    static class SegmentTree{
        int[] arr, tree;
        public SegmentTree(int[] arr){
            this.arr=arr.clone();
            int n=arr.length;
            tree=new int[(n<<1)-1];
            build(0, 0, n-1);
        }
        public void build(int node, int l, int r){
            if(l==r){
                tree[node]=arr[l];
            }else{
                int mid=(l+r)>>1;
                int left=2*node+1, right=2*node+2;
                build(left, l, mid);
                build(right, mid+1, r);
                tree[node]=Math.min(tree[left], tree[right]);
            }
        }
        public Pair query(int idx, int x){
            int b=queryleft(0, 0, arr.length-1, idx+1, arr.length-1, x);
            int a=queryright(0, 0, arr.length-1, 0, idx-1, x);
            return new Pair(a+1, b-1);
        }
        public int queryleft(int node, int l, int r, int i, int j, int x){
            if(i>j)
                return inf;
            if(r<=j && l>=i){
                if(tree[node]>=x)
                    return inf;
                if(l==r)
                    return r;
                int left=2*node+1, right=left+1;
                int mid=(l+r)/2;
                if(tree[left]<x)
                    return queryleft(left, l, mid, i, j, x);
                return queryleft(right, mid+1, r, i, j, x);
            }else if(i>r || j<l){
                return inf;
            }else{
                int left=2*node+1, right=left+1;
                int mid=(l+r)/2;
                return Math.min(queryleft(left, l, mid, i, j, x), queryleft(right, mid+1, r, i, j, x));
            }
        }
        public int queryright(int node, int l, int r, int i, int j, int x){
            if(i>j)
                return j;
            if(r<=j && l>=i){
                if(tree[node]>=x)
                    return -1;
                if(l==r)
                    return r;
                int left=2*node+1, right=left+1;
                int mid=(l+r)/2;
                if(tree[right]<x)
                    return queryright(right, mid+1, r, i, j, x);
                return queryright(left, l, mid, i, j, x);
            }else if(i>r || j<l){
                return -1;
            }else{
                int left=2*node+1, right=left+1;
                int mid=(l+r)/2;
                return Math.max(queryright(left, l, mid, i, j, x), queryright(right, mid+1, r, i, j, x));
            }
        }
    }
}