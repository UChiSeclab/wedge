import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class A implements Runnable {

    public void run() {
        long startTime = System.nanoTime();

        int[] primes = getPrimes(1000);

        int tc = nextInt();

        while (tc-- > 0) {
            int n = nextInt();
            char[] s = nextToken().toCharArray();
            int[] f = new int[n];
            int[] x = new int[n];
            int[] y = new int[n];
            int a = 0, b = 0;
            for (int i = 0; i < n; i++) {
                if (s[i] == 'D') {
                    a++;
                } else {
                    b++;
                }
                x[i] = a;
                y[i] = b;
                if (a == 0 || b == 0) {
                    f[i] = i + 1;
                    continue;
                }
                int d = gcd(a, b);
                List<Integer> list = getFactorization(d, primes);
                List<Integer> factors = getFactors(list);
                int res = 1;
                for (int factor : factors) {
                    if (factor <= 1) {
                        continue;
                    }
                    int prev = a / factor + b / factor;
                    if (f[i - prev] % (factor - 1) == 0 && (long) a * y[i - prev] == (long) b * x[i - prev]) {
                        res = factor;
                    }
                }
                f[i] = res;
            }
            StringBuilder sb = new StringBuilder();
            for (int v : f) {
                sb.append(' ').append(v);
            }
            println(sb.substring(1));
        }

        if (fileIOMode) {
            System.out.println((System.nanoTime() - startTime) / 1e9);
        }
        out.close();
    }

    public static int gcd(int n, int m) {
        int big, little;
        if (n < m) {
            big = m;
            little = n;
        } else {
            big = n;
            little = m;
        }
        while (little != 0) {
            int temp = little;
            little = big % little;
            big = temp;
        }
        return big;
    }

    public static int[] getPrimes(int max) {
        double log = Math.log(max);
        int[] p = new int[(int) (max / log * (1 + 1 / log + 2.51 / log / log))];
        p[0] = 2;
        int size = 1;
        int[] sieve = getSieve(max);
        for (int i = 3; i <= max; i += 2) {
            if (check(sieve, i)) {
                p[size++] = i;
            }
        }
        int[] r = new int[size];
        System.arraycopy(p, 0, r, 0, size);
        return r;
    }

    public static int[] getSieve(int max) {
        int[] r = new int[max + 64 >> 6];
        r[0] = 1;
        for (int x = 3; x * x <= max; x += 2) {
            if (check(r, x)) {
                for (int y = x * x; y <= max; y += x << 1) {
                    r[y >> 6] |= 1 << (y >> 1 & 31);
                }
            }
        }
        return r;
    }

    private static boolean check(int[] sieve, int x) {
        return (sieve[x >> 6] & 1 << (x >> 1 & 31)) == 0;
    }

    public static List<Integer> getFactorization(int n, int[] p) {
        List<Integer> r = new ArrayList<>();
        for (int i = 0; n != 1 && i < p.length; i++) {
            while (n % p[i] == 0) {
                r.add(p[i]);
                n /= p[i];
            }
        }
        if (n > 1) {
            r.add(n);
        }
        return r;
    }

    public static List<Integer> getFactors(List<Integer> primeDivisors) {
        List<Integer> res = new ArrayList<>();
        go(1, 0, primeDivisors, res);
        Collections.sort(res);
        return res;
    }

    private static void go(int p, int i, List<Integer> primeDivisors, List<Integer> res) {
        if (i == primeDivisors.size()) {
            res.add(p);
            return;
        }
        long v = primeDivisors.get(i);
        int j = i;
        while (j < primeDivisors.size() && v == primeDivisors.get(j)) {
            j++;
        }
        while (i++ <= j) {
            go(p, j, primeDivisors, res);
            p *= v;
        }
    }

    //-----------------------------------------------------------------------------------

    private static boolean fileIOMode;
    private static BufferedReader in;
    private static PrintWriter out;
    private static StringTokenizer tokenizer;

    public static void main(String[] args) throws Exception {
        fileIOMode = args.length > 0 && args[0].equals("!");
        if (fileIOMode) {
            in = new BufferedReader(new FileReader("a.in"));
            out = new PrintWriter("a.out");
        } else {
            in = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(System.out);
        }
        tokenizer = new StringTokenizer("");

        new Thread(new A()).start();
    }

    private static String nextLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    private static String nextToken() {
        while (!tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(nextLine());
        }
        return tokenizer.nextToken();
    }

    private static int nextInt() {
        return Integer.parseInt(nextToken());
    }

    private static long nextLong() {
        return Long.parseLong(nextToken());
    }

    private static double nextDouble() {
        return Double.parseDouble(nextToken());
    }

    private static BigInteger nextBigInteger() {
        return new BigInteger(nextToken());
    }

    private static void print(Object o) {
        if (fileIOMode) {
            System.out.print(o);
        }
        out.print(o);
    }

    private static void println(Object o) {
        if (fileIOMode) {
            System.out.println(o);
        }
        out.println(o);
    }

    private static void printf(String s, Object... o) {
        if (fileIOMode) {
            System.out.printf(s, o);
        }
        out.printf(s, o);
    }
}
