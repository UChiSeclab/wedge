import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Main{
    public static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
    static long MOD = (long) (1e9 + 7);
    //    static long MOD = 998244353;
    static long MOD2 = MOD * MOD;
    static FastReader sc = new FastReader();
    static int pInf = Integer.MAX_VALUE;
    static int nInf = Integer.MIN_VALUE;
    static long ded = (long)(1e17)+9;
    public static void main(String[] args) throws Exception {
        int test = 1;
        test = sc.nextInt();
        for (int i = 1; i <= test; i++){
//            out.print("Case #"+i+": ");
            solve();
        }
        out.flush();
        out.close();
    }
    static void solve(){
        int n = sc.nextInt();
        s = sc.next();
//        precomp(n);
        prefix();
        int len = 0;
        char[] c = s.toCharArray();
        int d = 0;
        int k = 0;
        int[] ans = new int[n];
        Arrays.fill(ans,1);
        for(int i = 0; i < n; i++){
            if(c[i]=='D'){
                d++;
            }else{
                k++;
            }
            len++;
            int gcd = (int)gcd(d,k);
            int f = (d/gcd)+(k/gcd);
//            out.println(d+" "+k+" "+gcd);
            if(len%f==0){
                //check for substrings of len (d/gcd+k/gcd)

                int h = 0;
//                out.println(d+" "+k+" "+f+" "+len);
                HashSet<Integer> K = new HashSet<>();
                HashSet<Integer> D = new HashSet<>();
                boolean found = true;
                while (h<len){
//                    D.add(pd[h+f]-pd[h]);
//                    K.add(pk[h+f]-pk[h]);
                    if(pd[h+f]-pd[h]==(d/gcd)&&pk[h+f]-pk[h]==(k/gcd)){
                        h += f;
                    }else{
                        found = false;
                        break;
                    }
                }
//                out.println(D+" "+K);
                if(found){
                    ans[i] = len/(f);
                }else{
                    ans[i] = 1;
                }
            }
        }
        for(int x: ans){
            out.print(x+" ");
        }
        out.println();
//        out.println(Arrays.toString(ans));
    }
    static int[] pd;
    static int[] pk;
    static void prefix(){
        int n = s.length();
        pd = new int[n+1];
        pk = new int[n+1];
        for(int i= 0; i < n; i++){
            if(s.charAt(i)=='D'){
                pd[i+1] = pd[i]+1;
                pk[i+1] = pk[i];
            }else{
                pd[i+1] = pd[i];
                pk[i+1] = pk[i]+1;
            }
        }
    }
    static long[] hash;
    static long[] pow;
    static long mod = (long)(1e9+7);
    static long base = 31L;
    static String s;
    static void precomp(int n){
        hash = new long[n+1];
        pow = new long[n+1];
        pow[0] = 1;
        for(int i = 1; i <= n; i++){
            pow[i] = mul(pow[i-1],base);
        }
        hash[0] = 0;
        long d = 0;
        long k = 0;
        for(int i = 1; i <= n; i++){

            hash[i] = add(s.charAt(i-1),mul(hash[i-1],base));
        }
    }
    static long hash(String a){
        int n  =a.length();
        long hash = 0L;
        for(int i = 1; i <= n; i++){
            hash = add(a.charAt(i-1),mul(hash,base));
        }
        return hash;
    }
    static long hashLR(int l,int r){
        out.println(l+" "+r);
        out.println(hash(s.substring(l,r)));
        return (hash[r]-mul(hash[l],pow[r-l])+mod)%mod;
//        return 0;
    }
    static class Pair implements Comparable<Pair> {
        int x;
        int y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public int compareTo(Pair o){
            if(this.x==o.x){
                return -1*(o.y-this.y);
            }
            return this.x-o.x;
        }

        @Override
        public String toString() {
            return "Pair{" + "x=" + x + ", y=" + y + '}';
        }

        public boolean equals(Pair o){
            return this.x==o.x&&this.y==o.y;
        }
    }
    public static long mul(long a, long b) {
        return ((a % MOD) * (b % MOD)) % MOD;
    }
    public static long add(long a, long b) {
        return ((a % MOD) + (b % MOD)) % MOD;
    }
    public static long c2(long n) {
        if ((n & 1) == 0) {
            return mul(n / 2, n - 1);
        } else {
            return mul(n, (n - 1) / 2);
        }
    }
    //Shuffle Sort
    static final Random random = new Random();
    static void ruffleSort(int[] a) {
        int n = a.length;//shuffle, then sort
        for (int i = 0; i < n; i++) {
            int oi = random.nextInt(n); int temp= a[oi];
            a[oi] = a[i];
            a[i] = temp;
        }
        Arrays.sort(a);
    }
    //Brian Kernighans Algorithm
    static long countSetBits(long n) {
        if (n == 0) return 0;
        return 1 + countSetBits(n & (n - 1));
    }
    //Euclidean Algorithm
    static long gcd(long A, long B) {
        if (B == 0) return A;
        return gcd(B, A % B);
    }
    //Modular Exponentiation
    static long fastExpo(long x, long n) {
        if (n == 0) return 1;
        if ((n & 1) == 0) return fastExpo((x * x) % MOD, n / 2) % MOD;
        return ((x % MOD) * fastExpo((x * x) % MOD, (n - 1) / 2)) % MOD;
    }
    //AKS Algorithm
    static boolean isPrime(long n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i <= Math.sqrt(n); i += 6)
            if (n % i == 0 || n % (i + 2) == 0) return false;
        return true;
    }
    public static long modinv(long x) {
        return modpow(x, MOD - 2);
    }
    public static long modpow(long a, long b) {
        if (b == 0) {
            return 1;
        }
        long x = modpow(a, b / 2);
        x = (x * x) % MOD;
        if (b % 2 == 1) {
            return (x * a) % MOD;
        }
        return x;
    }


    static class FastReader {
        BufferedReader br;
        StringTokenizer st;
        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
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