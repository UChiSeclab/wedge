import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


public class Main{



    static long MOD = 1000000007L;
    //static long MOD = 998244353L;
    static long [] fac;
    static long [] pow;
    static long [] inv;
    static int[][] dir = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static long lMax = 0x3f3f3f3f3f3f3f3fL;
    static int iMax = 0x3f3f3f3f;
    static HashMap <Long, Long> memo = new HashMap();
    static MyScanner sc = new MyScanner();
    //static ArrayList <Integer> primes;
    public static void main(String[] args) {
        out = new PrintWriter(new BufferedOutputStream(System.out));
        // Start writing your solution here. -------------------------------------
        int t = sc.nextInt();
        //int t = 1;

        int nn = 1000;
        /*fac = new long[nn + 1];
        fac[1] = 1;
        for(int i = 2; i <= nn; i++)
            fac[i] = fac[i - 1] * 1L * i % MOD;*/

        /*pow = new long[nn + 1];
        pow[0] = 1L;
        for(int i = 1; i <= nn; i++)
            pow[i] = pow[i - 1] * 2L;*/

        /*inv = new long[nn + 1];
        inv[1] = 1;
        for (int i = 2; i <= nn; ++i) {
            inv[i] = (MOD - MOD / i) * inv[(int)(MOD % i)] % MOD;
        }*/

        //primes = sieveOfEratosthenes(100001);

        while(t-- > 0){
            //Solution sol = new Solution();
            //sol.Sum(10);
            char[] res = solve();

            for(char c : res)
                out.print(c);
            out.println();

        }
        out.close();
    }
    static boolean [] visited;
    static ArrayList<Integer>[] neigh;
    static int loopRoot;
    static ArrayList <Integer> cycle;
    static boolean loopEnd;
    static long cnt;

    static int total = 0;
    static char[] solve() {
        //long n = sc.nextLong();
        int n = sc.nextInt();
        //int k = sc.nextInt();
        int[] a = new int[n];
        int[] cnt = new int[n + 1];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
            cnt[a[i]]++;
        }
        boolean valid = true;
        for(int i = 1; i <= n; i++)
            if(cnt[i] != 1){
                valid = false;
                break;
            }
        char[] c = new char[n];
        Arrays.fill(c, '0');
        int lo = 0, hi = n - 1;
        for(int j = n - 1; j >= 0; j--){
            int i = n - j;
            if(cnt[i] == 0) break;
            c[j] = '1';
            if(a[lo] == i){
                lo++;
                cnt[i]--;
            }
            else if(a[hi] == i){
                hi--;
                cnt[i]--;
            }
            if(cnt[i] != 0) break;
        }

        c[0] = valid ? '1' : '0';
        return c;
    }

    public class Interval {
        int start;
        int end;
        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static ArrayList<Integer> sieveOfEratosthenes(int n) {
        boolean prime[] = new boolean[n + 1];
        Arrays.fill(prime, true);
        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * 2; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }


    public static int lowerBound(int[] a, int v){ return lowerBound(a, 0, a.length, v); }
    public static int lowerBound(int[] a, int l, int r, int v)
    {
        if(l > r || l < 0 || r > a.length)throw new IllegalArgumentException();
        int low = l-1, high = r;
        while(high-low > 1){
            int h = high+low>>>1;
            if(a[h] >= v){
                high = h;
            }else{
                low = h;
            }
        }
        return high;
    }
    public static long C(int n, int m)
    {
        if(m == 0 || m == n) return 1l;
        if(m > n || m < 0) return 0l;
        long res = fac[n] * quickPOW((fac[m] * fac[n - m]) % MOD, MOD - 2) % MOD;

        return res;
    }
    public static long quickPOW(long n, long m)
    {
        long ans = 1l;
        while(m > 0)
        {
            if(m % 2 == 1)
                ans = (ans * n) % MOD;
            n = (n * n) % MOD;
            m >>= 1;
        }
        return ans;
    }

    public static int gcd(int a, int b)
    {
        if(a % b == 0) return b;
        return gcd(b, a % b);
    }
    public static long gcd(long a, long b)
    {
        if(a % b == 0) return b;
        return gcd(b, a % b);
    }
    //-----------PrintWriter for faster output---------------------------------
    public static PrintWriter out;
    //-----------MyScanner class for faster input----------
    public static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        public MyScanner() {
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
    //--------------------------------------------------------
}
