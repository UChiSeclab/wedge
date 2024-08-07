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
    static final int inf=(int)1e9, mod= 998244353;
    static final long infL=inf*1l*inf;
    static final double eps=1e-9;
    public static long gcd(long x, long y) { return y==0? x: gcd(y, x%y); }
    public static void main(String[] args) throws Exception {
        sc = new Scanner(System.in);
        pw = new PrintWriter(System.out);
        int t = sc.nextInt();
        linearsieve(1000);
        while (t-- > 0)
            testcase();
        pw.close();
    }
    static int[] ans;
    static HashMap<Triple, Integer> dp;
    static int[] prefD, prefK;

    static void testcase() throws IOException {
        int n=sc.nextInt();
        char[] arr=sc.next().toCharArray();
        prefD=new int[n];
        prefK=new int[n];
        prefD[0] = Int(arr[0]=='D');
        prefK[0] = Int(arr[0]=='K');
        for (int i = 1; i < n; i++) {
            int d= Int(arr[i]=='D');
            int k= Int(arr[i]=='K');
            prefD[i]= prefD[i-1]+d;
            prefK[i]= prefK[i-1]+k;
        }
        ans=new int[n];
        dp=new HashMap<>();
        for (int i=0; i<n; i++){
            int gcd= (int) gcd(prefD[i], prefK[i]);
            pw.print(solve(i, prefD[i]/gcd, prefK[i]/gcd)+" ");
        }
        pw.println();
    }
    static int solve(int i, int d, int k){
        Triple t=new Triple(i, d, k);
        if(!dp.containsKey(t)){
            int sum=d+k;
            int blocks= (i+1)/sum;
            div= divisors(blocks);
            for (int x: div) {
                if(x == blocks){
                    dp.put(t, 1);
                    break;
                }
                Triple prev=new Triple(i-sum, d, k);
                int ans= dp.getOrDefault(prev, -1);
                if(ans + 1 == blocks/x){
                    dp.put(t, ans+1);
                    break;
                }
            }
        }
        return dp.get(t);
    }
    static int[] least;
    static TreeSet<Integer> prime;
    static void linearsieve(int x){
        least=new int[x+1];
        prime=new TreeSet<Integer>();
        for(int i=2; i<=x; i++){
            if(least[i]==0){
                least[i]=i;
                prime.add(i);
            }
            for(int y :prime) {
                if(i*y<=x)
                    least[i*y]=y;
                else break;
            }
        }
    }
    static TreeMap<Integer, Integer> logPrimeFact(int x){
        TreeMap<Integer, Integer> ans=new TreeMap<>();
        while(x>1){
            int a=least[x];
            int pow=0;
            while(x%a==0){
                x/=a;
                pow++;
            }
            ans.put(a, pow);
        }
        return ans;
    }
    static TreeSet<Integer> div;
    static TreeSet<Integer> divisors(int x){
        div=new TreeSet<>();
        divisors(logPrimeFact(x), 0, 1);
        return div;
    }
    static void divisors(TreeMap<Integer, Integer> map, int prev, int currVal){
        Integer x= map.ceilingKey(prev+1);
        if(x == null){
            div.add(currVal);
            return;
        }
        int pow= map.get(x);
        int r=1;
        for (int i = 0; i <= pow; i++){
            divisors(map, x, currVal*r);
            r*=x;
        }
    }
    static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++)
            pw.print(arr[i] + " ");
        pw.println();
    }
    static void printArr(long[] arr) {
        for (int i = 0; i < arr.length; i++)
            pw.print(arr[i] + " ");
        pw.println();
    }
    static void printArr(double[] arr) {
        for (int i = 0; i < arr.length; i++)
            pw.print(arr[i] + " ");
        pw.println();
    }
    static void printArr(Integer[] arr) {
        for (int i = 0; i < arr.length; i++)
            pw.print(arr[i] + " ");
        pw.println();
    }
    static void printArr(ArrayList list) {
        for (int i = 0; i < list.size(); i++)
            pw.print(list.get(i)+" ");
        pw.println();
    }
    static void printArr(boolean[] arr) {
        StringBuilder sb=new StringBuilder();
        for(boolean b: arr)
            sb.append(Int(b));
        pw.println(sb);
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
        public long[] nextLongArr(int n) throws IOException{
            long[] arr=new long[n];
            for (int i = 0; i < n; i++)
                arr[i]=sc.nextLong();
            return arr;
        }
        public Pair[] nextPairArr(int n) throws IOException{
            Pair[] arr=new Pair[n];
            for(int i=0; i<n; i++)
                arr[i]=nextPair();
            return arr;
        }
        public boolean hasNext() throws IOException {
            return (st!=null && st.hasMoreTokens()) || br.ready();
        }
    }
    static class Pair implements Comparable<Pair> {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Pair(Map.Entry<Integer, Integer> a) {
            x = a.getKey();
            y = a.getValue();
        }

        public boolean contains(int a) {
            return x == a || y == a;
        }

        public int hashCode() {
            return (this.x * 1000000000 + this.y);
        }

        public int compareTo(Pair p) {
            if (x == p.x)
                return y - p.y;
            return x - p.x;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            Pair p = (Pair) obj;
            return this.x == p.x && this.y == p.y;
        }

        public Pair clone() {
            return new Pair(x, y);
        }

        public String toString() {
            return this.x + " " + this.y;
        }

        public void add(Pair p) {
            x += p.x;
            y += p.y;
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
        public int hashCode(){
            return new Pair(x, y).hashCode()*1000000000 + z;
        }
        public boolean equals(Object o){
            if(o instanceof Triple){
                Triple t= (Triple) o;
                return t.x==x && t.y==y && t.z==z;
            }
            return false;
        }
    }
}