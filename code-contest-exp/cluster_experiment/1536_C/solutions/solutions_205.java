    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.PrintWriter;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.Collections;
    import java.util.Comparator;
    import java.util.HashMap;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Map;
    import java.util.Set;
    import java.util.StringTokenizer;
    import java.util.TreeMap;
    import java.util.TreeSet;

    public class Main implements Runnable {
        int n, m, k;
        static boolean use_n_tests = true;
        long mod = 998244353L;

        void solve(FastScanner in, PrintWriter out, int testNumber) {
            n = in.nextInt();
            char[] c = in.nextc();
            int[] pf = new int[n];
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 'D') {
                    pf[i] = 1;
                }
                if (i > 0) {
                    pf[i] += pf[i - 1];
                }
            }
            Map<Long, List<Integer>> mp = new HashMap<>();
            for (int i = 0; i < c.length; i++) {
                long dcnt = pf[i];
                long kcnt = i + 1 - dcnt;
                if (kcnt == 0 || dcnt == 0) {
                    out.print(i + 1 + " ");
                } else {
                    int g = gcd((int) dcnt, (int) kcnt);
                    dcnt /=g;
                    kcnt /= g;
                    long hash = dcnt * 100000l + kcnt;
                    mp.computeIfAbsent(hash, k -> new ArrayList<>()).add(i);
                    out.print(mp.get(hash).size() + " ");
                }
            }
            out.println();
        }

        int gcd(int a, int b) {
            return b == 0 ? a : gcd(b, a % b);
        }

        public static class FW {
            public static void add(long[] t, int i, long value) {
                for (; i < t.length; i |= i + 1)
                    t[i] += value;
            }
            public static long sum(long[] t, int i) {
                long res = 0;
                for (; i >= 0; i = (i & (i + 1)) - 1)
                    res += t[i];
                return res;
            }

            public static void add(long[] t, int a, int b, long value) {
                add(t, a, value);
                add(t, b + 1, -value);
            }
        }


        // ****************************** template code ***********
        long binpow(long a, int b) {
            long res = 1;
            while (b != 0) {
                if (b % 2 == 0) {
                    b /= 2;
                    a *= a;
                    a %= mod;
                }

                b--;
                res *= a;
                res %= mod;
            }
            return res;
        }

        List<Integer> getGidits(long n) {
            List<Integer> res = new ArrayList<>();
            while (n != 0) {
                res.add((int) (n % 10L));
                n /= 10;
            }
            return res;
        }

        List<Integer> generatePrimes(int n) {
            List<Integer> res = new ArrayList<>();
            boolean[] sieve = new boolean[n + 1];
            for (int i = 2; i <= n; i++) {
                if (!sieve[i]) {
                    res.add(i);
                }
                if ((long) i * i <= n) {
                    for (int j = i * i; j <= n; j += i) {
                        sieve[j] = true;
                    }
                }
            }
            return res;
        }

        int ask(int l, int r) {
            if (l >= r) {
                return -1;
            }
            System.out.printf("? %d %d\n", l + 1, r + 1);
            System.out.flush();
            return in.nextInt() - 1;
        }

        static int stack_size = 1 << 27;

        class Mod {
            long mod;

            Mod(long mod) {
                this.mod = mod;
            }

            long add(long a, long b) {
                a = mod(a);
                b = mod(b);
                return (a + b) % mod;
            }

            long sub(long a, long b) {
                a = mod(a);
                b = mod(b);
                return (a - b + mod) % mod;
            }

            long mul(long a, long b) {
                a = mod(a);
                b = mod(b);
                return a * b % mod;
            }

            long div(long a, long b) {
                a = mod(a);
                b = mod(b);
                return (a * inv(b)) % mod;
            }

            public long inv(long r) {
                if (r == 1)
                    return 1;
                return ((mod - mod / r) * inv(mod % r)) % mod;
            }

            private long mod(long a) {
                return a % mod;
            }
        }

        static class Coeff {
            long mod;
            long[][] C;
            long[] fact;
            boolean cycleWay = false;

            Coeff(int n, long mod) {
                this.mod = mod;
                fact = new long[n + 1];
                fact[0] = 1;
                for (int i = 1; i <= n; i++) {
                    fact[i] = i;
                    fact[i] %= mod;
                    fact[i] *= fact[i - 1];
                    fact[i] %= mod;
                }
            }

            Coeff(int n, int m, long mod) {
                // n > m
                cycleWay = true;
                this.mod = mod;
                C = new long[n + 1][m + 1];
                for (int i = 0; i <= n; i++) {
                    for (int j = 0; j <= Math.min(i, m); j++) {
                        if (j == 0 || j == i) {
                            C[i][j] = 1;
                        } else {
                            C[i][j] = C[i - 1][j - 1] + C[i - 1][j];
                            C[i][j] %= mod;
                        }
                    }
                }
            }

            public long C(int n, int m) {
                if (cycleWay) {
                    return C[n][m];
                }
                return fC(n, m);
            }

            private long fC(int n, int m) {
                return (fact[n] * inv(fact[n - m] * fact[m] % mod)) % mod;
            }

            private long inv(long r) {
                if (r == 1)
                    return 1;
                return ((mod - mod / r) * inv(mod % r)) % mod;
            }
        }

        class Pair {
            int first;
            long second;

            Pair(int f, long s) {
                first = f;
                second = s;
            }

            public int getFirst() {
                return first;
            }

            public long getSecond() {
                return second;
            }
        }

        class MultisetTree<T> {
            int size = 0;

            TreeMap<T, Integer> mp = new TreeMap<>();

            void add(T x) {
                mp.merge(x, 1, Integer::sum);
                size++;
            }

            void remove(T x) {
                if (mp.containsKey(x)) {
                    mp.merge(x, -1, Integer::sum);
                    if (mp.get(x) == 0) {
                        mp.remove(x);
                    }
                    size--;
                }
            }

            boolean contains(T x) {
                return mp.containsKey(x);
            }

            T greatest() {
                return mp.lastKey();
            }

            T smallest() {
                return mp.firstKey();
            }

            int size() {
                return size;
            }

            int diffSize() {
                return mp.size();
            }
        }

        class Multiset<T> {
            int size = 0;

            Map<T, Integer> mp = new HashMap<>();

            void add(T x) {
                mp.merge(x, 1, Integer::sum);
                size++;
            }

            boolean contains(T x) {
                return mp.containsKey(x);
            }

            void remove(T x) {
                if (mp.containsKey(x)) {
                    mp.merge(x, -1, Integer::sum);
                    if (mp.get(x) == 0) {
                        mp.remove(x);
                    }
                    size--;
                }
            }

            int size() {
                return size;
            }

            int diffSize() {
                return mp.size();
            }
        }

        static class Range {
            int l, r;
            int id;

            public int getL() {
                return l;
            }

            public int getR() {
                return r;
            }

            public Range(int l, int r, int id) {
                this.l = l;
                this.r = r;
                this.id = id;
            }
        }

        static class Array {
            static Range[] readRanges(int n, FastScanner in) {
                Range[] result = new Range[n];
                for (int i = 0; i < n; i++) {
                    result[i] = new Range(in.nextInt(), in.nextInt(), i);
                }
                return result;
            }

            static List<List<Integer>> intInit2D(int n) {
                List<List<Integer>> res = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    res.add(new ArrayList<>());
                }
                return res;
            }

            static boolean isSorted(Integer[] a) {
                for (int i = 0; i < a.length - 1; i++) {
                    if (a[i] > a[i + 1]) {
                        return false;
                    }
                }
                return true;
            }

            static public long sum(int[] a) {
                long sum = 0;
                for (int x : a) {
                    sum += x;
                }
                return sum;
            }

            static public long sum(long[] a) {
                long sum = 0;
                for (long x : a) {
                    sum += x;
                }
                return sum;
            }

            static public long sum(Integer[] a) {
                long sum = 0;
                for (int x : a) {
                    sum += x;
                }
                return sum;
            }

            static public int min(Integer[] a) {
                int mn = Integer.MAX_VALUE;
                for (int x : a) {
                    mn = Math.min(mn, x);
                }
                return mn;
            }

            static public int min(int[] a) {
                int mn = Integer.MAX_VALUE;
                for (int x : a) {
                    mn = Math.min(mn, x);
                }
                return mn;
            }

            static public int max(Integer[] a) {
                int mx = Integer.MIN_VALUE;
                for (int x : a) {
                    mx = Math.max(mx, x);
                }
                return mx;
            }

            static public int max(int[] a) {
                int mx = Integer.MIN_VALUE;
                for (int x : a) {
                    mx = Math.max(mx, x);
                }
                return mx;
            }

            static public int[] readint(int n, FastScanner in) {
                int[] out = new int[n];
                for (int i = 0; i < out.length; i++) {
                    out[i] = in.nextInt();
                }
                return out;
            }
        }

        class Graph {
            List<List<Integer>> graph;

            Graph(int n) {
                create(n);
            }

            private void create(int n) {
                List<List<Integer>> graph = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    graph.add(new ArrayList<>());
                }
                this.graph = graph;
            }

            void read(int m, FastScanner in) {
                for (int i = 0; i < m; i++) {
                    int v = in.nextInt() - 1;
                    int u = in.nextInt() - 1;
                    graph.get(v).add(u);
                }
            }

            void readBi(int m, FastScanner in) {
                for (int i = 0; i < m; i++) {
                    int v = in.nextInt() - 1;
                    int u = in.nextInt() - 1;
                    graph.get(v).add(u);
                    graph.get(u).add(v);
                }
            }
        }

        class FastScanner {
            BufferedReader br;
            StringTokenizer st;

            public FastScanner(InputStream io) {
                br = new BufferedReader(new InputStreamReader(io));
            }

            public String line() {
                String result = "";
                try {
                    result = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            public String next() {
                while (st == null || !st.hasMoreElements()) {
                    try {
                        st = new StringTokenizer(br.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return st.nextToken();
            }

            public char[] nextc() {
                return next().toCharArray();
            }

            public int nextInt() {
                return Integer.parseInt(next());
            }

            public int[] nextArray(int n) {
                int[] res = new int[n];
                for (int i = 0; i < n; i++) {
                    res[i] = in.nextInt();
                }
                return res;
            }

            public long[] nextArrayL(int n) {
                long[] res = new long[n];
                for (int i = 0; i < n; i++) {
                    res[i] = in.nextLong();
                }
                return res;
            }

            public Long[] nextArrayL2(int n) {
                Long[] res = new Long[n];
                for (int i = 0; i < n; i++) {
                    res[i] = in.nextLong();
                }
                return res;
            }


            public Integer[] nextArray2(int n) {
                Integer[] res = new Integer[n];
                for (int i = 0; i < n; i++) {
                    res[i] = in.nextInt();
                }
                return res;
            }

            public long nextLong() {
                return Long.parseLong(next());
            }

        }

        void run_t_tests() {
            int t = in.nextInt();
            int i = 0;
            while (t-- > 0) {
                solve(in, out, i++);
            }
        }

        void run_one() {
            solve(in, out, -1);
        }

        @Override
        public void run() {
            in = new FastScanner(System.in);
            out = new PrintWriter(System.out);
            if (use_n_tests) {
                run_t_tests();
            } else {
                run_one();
            }
            out.close();
        }

        static FastScanner in;
        static PrintWriter out;

        public static void main(String[] args) throws InterruptedException {
            Thread thread = new Thread(null, new Main(), "", stack_size);
            thread.start();
            thread.join();
        }
    }