import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        solve(in, out);
        out.close();
    }

    static String reverse(String s) {
        return (new StringBuilder(s)).reverse().toString();
    }

    static void sieveOfEratosthenes(int n, int factors[]) 
    { 
        factors[1]=1;
        for(int p = 2; p*p <=n; p++) 
        { 
            if(factors[p] == 0) 
            { 
                factors[p]=p;
                for(int i = p*p; i <= n; i += p) 
                    factors[i] = p; 
            } 
        } 
    }

    static void sort(int ar[]) {
        int n = ar.length;
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < n; i++)
            a.add(ar[i]);
        Collections.sort(a);
        for (int i = 0; i < n; i++)
            ar[i] = a.get(i);
    }

    static void sort1(long ar[]) {
        int n = ar.length;
        ArrayList<Long> a = new ArrayList<>();
        for (int i = 0; i < n; i++)
            a.add(ar[i]);
        Collections.sort(a);
        for (int i = 0; i < n; i++)
            ar[i] = a.get(i);
    }

    static long ncr(long n, long r, long mod) {
        if (r == 0)
            return 1;
        long val = ncr(n - 1, r - 1, mod);
        val = (n * val) % mod;
        val = (val * modInverse(r, mod)) % mod;
        return val;
    }

    public static void solve(InputReader sc, PrintWriter pw) {
        int i, j = 0;
        // int t = 1;  
        int t = sc.nextInt();
        u: while (t-- > 0) {
            int n = sc.nextInt();
            int a[] = new int[n];
            HashMap<Integer,Integer> map = new HashMap<>();
            for(i=0;i<n;i++){
                a[i] = sc.nextInt();
                map.put(a[i],map.getOrDefault(a[i], 0) + 1);
            }
            int c = 1;
            int l = 0, r = n-1;
            while(l<r){
                if(map.getOrDefault(c,0)>0){
                    c++;
                    if(a[l]==c-1&&map.get(c-1)==1){
                        l++;
                        continue;
                    }
                    if(a[r]==c-1&&map.get(c-1)==1){
                        r--;
                        continue;
                    }
                    break;
                }
                else
                    break;
            }
            for(i=1;i<=n;i++){
                if(map.getOrDefault(i,0)!=1)
                    break;
            }
            pw.print(i==n+1?1:0);
            for(i=c;i<n;i++){
                pw.print(0);
            }
            for(i=1;i<c;i++){
                pw.print(1);
            }
            pw.println();
        }
    }

    static class Pair1 {
        long a;
        long b;
        
        Pair1(long a, long b) {
            this.a = a;
            this.b = b;
        }
    }

    static class Pair implements Comparable<Pair> {
        int a;
        int b;
        
        Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
 
        public int compareTo(Pair p) {
            if(a!=p.a)
                return a-p.a;
            return b-p.b;
        }
    }

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
        return gcd(b, a % b);
    }

    static long fast_pow(long base, long n, long M) {
        if (n == 0)
            return 1;
        if (n == 1)
            return base % M;
        long halfn = fast_pow(base, n / 2, M);
        if (n % 2 == 0)
            return (halfn * halfn) % M;
        else
            return (((halfn * halfn) % M) * base) % M;
    }

    static long modInverse(long n, long M) {
        return fast_pow(n, M - 2, M);
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
    }
}