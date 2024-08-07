import java.io.*;
import java.util.*;
import java.math.BigInteger;
import static java.math.BigInteger.ZERO;

public  class Solution {


    static PrintWriter out = new PrintWriter(System.out);
    static FastReader in = new FastReader();
    static long mod = (long) 1e9 + 7;
    static Pair[] moves = new Pair[]{new Pair(-1, 0), new Pair(1, 0), new Pair(0, -1), new Pair(0, 1)};
  //  static boolean[]  isprime =  new boolean[1000000001];
    static  int[][][]  dp;
 public static void main(String hi[]) throws Exception
    {
     BufferedReader infile = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(infile.readLine());
        int T = Integer.parseInt(st.nextToken());
        StringBuilder sb = new StringBuilder();
        while(T-->0)
        {
            st = new StringTokenizer(infile.readLine());
            int N = Integer.parseInt(st.nextToken());
            char[] arr = infile.readLine().toCharArray();
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            int dc = 0;
            int kc = 0;
            for(char c: arr)
            {
                if(c == 'D')
                    dc++;
                else
                    kc++;
                int a = dc;
                int b = kc;
                if(a == 0)
                    b = 1;
                else if(b == 0)
                    a = 1;
                else
                {
                    int gcd = GCD(a, b);
                    a /= gcd;   b /= gcd;
                }
                String key = a+":"+b;
               map.put(key,map.getOrDefault(key,0)+1);
                sb.append(map.get(key)+" ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
}

    public static boolean isPS(int x) {
        if (x == 1) {
            return true;
        }
        for (int i = 2; i <= x / 2; i++) {
            if (i * i == x) {
                return true;
            }
        }
        return false;
    }

    public static long calc(int type, long X, long K) {
        if (type == 1) {
            return (X + 99999) / 100000 + K;
        } else {
            return (K * X + 99999) / 100000;
        }
    }

    static int sd(long i) {
        int d = 0;
        while (i > 0) {
            d += i % 10;
            i = i / 10;
        }
        return d;
    }


    static int lower(long A[], long x) {
        int l = -1, r = A.length;
        while (r - l > 1) {
            int m = (l + r) / 2;
            if (A[m] >= x) {
                r = m;
            } else {
                l = m;
            }
        }
        return r;
    }

    static int upper(long A[], long x) {
        int l = -1, r = A.length;
        while (r - l > 1) {
            int m = (l + r) / 2;
            if (A[m] <= x) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }

    static void swap(int A[], int a, int b) {
        int t = A[a];
        A[a] = A[b];
        A[b] = t;
    }

    static int lowerBound(int A[], int low, int high, int x) {
        if (low > high) {
            if (x >= A[high]) {
                return A[high];
            }
        }

        int mid = (low + high) / 2;

        if (A[mid] == x) {
            return A[mid];
        }

        if (mid > 0 && A[mid - 1] <= x && x < A[mid]) {
            return A[mid - 1];
        }

        if (x < A[mid]) {
            return lowerBound(A, low, mid - 1, x);
        }

        return lowerBound(A, mid + 1, high, x);
    }


    static long pow(long a, long b) {
        long pow = 1;
        long x = a;
        while (b != 0) {
            if ((b & 1) != 0) {
                pow = (pow * x) % mod;
            }
            x = (x * x) % mod;
            b /= 2;
        }
        return pow;
    }

    static boolean isPrime(long N) {
        if (N <= 1) {
            return false;
        }
        if (N <= 3) {
            return true;
        }
        if (N % 2 == 0 || N % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= N; i = i + 6) {
            if (N % i == 0 || N % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    static void print(char A[]) {
        for (char c : A) {
            out.print(c);
        }
        out.println();
    }

    static void print(boolean A[]) {
        for (boolean c : A) {
            out.print(c + " ");
        }
        out.println();
    }

    static void print(int A[]) {
        for (int c : A) {
            out.print(c + " ");
        }
        out.println();
    }

    static void print(long A[]) {
        for (long i : A) {
            out.print(i + " ");
        }
        out.println();

    }

    static void print(List<Integer> A) {
        for (int a : A) {
            out.print(a + " ");
        }
    }

    static int i() {
        return in.nextInt();
    }

    static long l() {
        return in.nextLong();
    }

    static String s() {
        return in.nextLine();
    }

    static int[] input(int N) {
        int A[] = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = in.nextInt();
        }
        return A;
    }

    static long[] inputLong(int N) {
        long A[] = new long[N];
        for (int i = 0; i < A.length; i++) {
            A[i] = in.nextLong();
        }
        return A;
    }

  static int GCD(int a, int b)
    {
        if(a > b)
        {
            int t = a;
            a = b;
            b = t;
        }
        if(a == 0)
            return b;
        return GCD(b%a, a);
    }

    static long GCD(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return GCD(b, a % b);
        }
    }

}

class SegmentTree {

    long[] t;

    public SegmentTree(int n) {
        t = new long[n + n];
        Arrays.fill(t, Long.MIN_VALUE);
    }

    public long get(int i) {
        return t[i + t.length / 2];
    }

    public void add(int i, long value) {
        i += t.length / 2;
        t[i] = value;
        for (; i > 1; i >>= 1) {
            t[i >> 1] = Math.max(t[i], t[i ^ 1]);
        }
    }

    // max[a, b]
    public long max(int a, int b) {
        long res = Long.MIN_VALUE;
        for (a += t.length / 2, b += t.length / 2; a <= b; a = (a + 1) >> 1, b = (b - 1) >> 1) {
            if ((a & 1) != 0) {
                res = Math.max(res, t[a]);
            }
            if ((b & 1) == 0) {
                res = Math.max(res, t[b]);
            }
        }
        return res;
    }
}

class Pair {

    int i, j;

    Pair(int i, int j) {
        this.i = i;
        this.j = j;
    }
}

class FastReader {

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

    BigInteger nextBigInteger() {
         //To change body of generated methods, choose Tools | Templates.
        return new BigInteger(next());
    }


}
class The_Comparator implements Comparator<String> {
    public int compare(String str1, String str2)
    {
        String first_Str;
        String second_Str;
        first_Str = str1;
        second_Str = str2;
        return first_Str.compareTo(second_Str);
    }
}