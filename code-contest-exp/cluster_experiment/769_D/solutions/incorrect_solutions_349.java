import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * @author maxkrivich
 * @version 1.5
 */
public final class Main {
    char table[][];
    int n, m, k;
    int sX, sY;

    private String reverse(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == 'D')
                sb.append("U");
            else if (s.charAt(i) == 'R')
                sb.append("L");
            else if (s.charAt(i) == 'L')
                sb.append("R");
            else if (s.charAt(i) == 'U')
                sb.append("D");
        }
        return sb.toString();
    }

    private void dfs(int x, int y, String path) {
        if (!(x >= 0 && x < n)) return;
        if (!(y >= 0 && y < m)) return;
        if (path.length() > k / 2) return;
        if (table[x][y] == '*') return;
        if (path.length() == k / 2) {
            String rev = reverse(path);
            int locX = x, locY = y;
            for (int i = 0; i < rev.length(); i++)
                if (rev.charAt(i) == 'D')
                    locX++;
                else if (rev.charAt(i) == 'U')
                    locX--;
                else if (rev.charAt(i) == 'L')
                    locY--;
                else if (rev.charAt(i) == 'R')
                    locY++;
            if (locX == sX && locY == sY) {
                out.print(path);
                out.print(rev);
                out.flush();
                System.exit(0);
            } else return;
        }
        dfs(x + 1, y, path + "D");
        dfs(x, y - 1, path + "L");
        dfs(x, y + 1, path + "R");
        dfs(x - 1, y, path + "U");
    }

    public void solve() {
/*        //DLRU
        n = in.nextInt();
        m = in.nextInt();
        k = in.nextInt();
        if (k % 2 == 1) {
            out.print("IMPOSSIBLE");
            return;
        }
        int ind;
        String s;
        table = new char[n][];
        for (int i = 0; i < n; i++) {
            s = in.nextLine();
            table[i] = s.toCharArray();
            ind = s.indexOf('X');
            if (ind >= 0) {
                sX = i;
                sY = ind;
            }
        }
//        dfs(sX, sY, "");
//        out.println("IMPOSSIBLE");

        //DLRU
        StringBuilder path = new StringBuilder();
        int x = sX, y = sY;
        while (path.length() != k / 2) {
            while ((x + 1 >= 0 && x + 1 < n) && table[x + 1][y] != '*') {
                x++;
                path.append("D");
                if (path.length() == k / 2)
                    break;
            }
            if (path.length() == k / 2)
                break;

            if ((y - 1 >= 0 && y - 1 < m) && table[x][y - 1] != '*') {
                y--;
                path.append("L");
                continue;
            }
            if (path.length() == k / 2)
                break;
            if ((y + 1 >= 0 && y + 1 < m) && table[x][y + 1] != '*') {
                y++;
                path.append("R");
                continue;
            }
            if (path.length() == k / 2)
                break;
            if ((x - 1 >= 0 && x - 1 < n) && table[x - 1][y] != '*') {
                x--;
                path.append("U");
                continue;
            }
            if (path.length() == k / 2)
                break;
            if (path.length() == 0)
                break;
        }
        if (path.length() == 0) {
            out.print("IMPOSSIBLE");
            return;
        }
        String res = path + reverse(path.toString());
        if(res.length() != k) {
            out.println("IMPOSSIBLE");
        }
        else
            out.println(res);*/
        int n = in.nextInt();
        int k = in.nextInt();
        int maxN = 10000;
        int cnt[] = new int[maxN + 10];
        int a[] = new int[maxN + 10];
        long ans = 0l;
        for (int i = 0; i < n; i++)
            cnt[a[i] = in.nextInt()]++;
        for (int i = 0; i < maxN; i++) {
            if (cnt[i] > 0) {
                if (k > 0) {
                    for (int j = i + 1; j <= maxN; j++)
                        if (cnt[j] > 0 && Integer.bitCount(i ^ j) == k)
                            ans += 1l * (cnt[i] * cnt[j]);
                } else
                    ans += 1l * (cnt[i] * (cnt[i] - 1) >> 1);
            }
        }
        out.println(ans);
    }

    int toB(int i) {
        i = i - ((i >> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
        return (((i + (i >> 4)) & 0x0F0F0F0F) * 0x01010101) >> 24;
    }


    //    -------------I/O-------------    \\
    void run() {
        try {
            in = new FScanner(new File("input.txt"));
            out = new PrintWriter(new BufferedWriter(new FileWriter(new File("output.txt"))));
            solve();
            out.close();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void runIO() {
        in = new FScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        solve();
        out.close();
        in.close();
    }

    public static void main(String[] args) {
        new Main().runIO();
    }

    private FScanner in;
    private PrintWriter out;

    class FScanner {

        private BufferedReader br;
        private StringTokenizer st;

        public FScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        void useString(String s) {
            if (s != null) {
                st = new StringTokenizer(s);
            }
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
            String s = "";
            try {
                s = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }

        void close() {
            try {
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    class Pair<X, Y> implements Comparable<Pair<X, Y>> {
        public X first;
        public Y second;

        public Pair(X first, Y second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 11 * hash + Objects.hashCode(this.first);
            hash = 11 * hash + Objects.hashCode(this.second);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Pair<?, ?> other = (Pair<?, ?>) obj;
            if (!Objects.equals(this.first, other.first)) {
                return false;
            }
            if (!Objects.equals(this.second, other.second)) {
                return false;
            }
            return true;
        }

        @Override
        public int compareTo(Pair<X, Y> p) {
            return ((Comparable<X>) first).compareTo(p.first);
        }

        @Override
        public String toString() {
            return first + " " + second;
        }
    }

    static class Library {
        public static boolean isPrime(int n) {
            for (int i = 2; i * i <= n; i++)
                if (n % i == 0)
                    return false;
            return true;
        }

        public static long bpow(long x, long n) {
            long ans = 1l;
            while (n != 0) {
                if (n % 2 == 0)
                    ans *= x;
                x *= x;
                n >>= 1;
            }
            return ans;
        }

        public static long gcd(long a, long b) {
            if (b == 0) return a;
            return gcd(b, a % b);
        }

        public static long lcm(long a, long b) {
            return a / gcd(a, b) * b;
        }


        public static long phi(long n) {
            long res = n;
            for (int i = 2; i * i <= n; ++i)
                if (n % i == 0) {
                    while (n % i == 0)
                        n /= i;
                    res -= res / i;
                }
            if (n > 1)
                res -= res / n;
            return res;
        }

        public static int[] generatePrimes(int n) {
            boolean[] prime = new boolean[n + 1];
            Arrays.fill(prime, 2, n + 1, true);
            for (int i = 2; i * i <= n; i++)
                if (prime[i])
                    for (int j = i * i; j <= n; j += i)
                        prime[j] = false;
            int[] primes = new int[n + 1];
            int cnt = 0;
            for (int i = 0; i < prime.length; i++)
                if (prime[i])
                    primes[cnt++] = i;

            return Arrays.copyOf(primes, cnt);
        }

        public static int[] generateDivisorTable(int n) {
            int[] divisor = new int[n + 1];
            for (int i = 1; i <= n; i++)
                divisor[i] = i;
            for (int i = 2; i * i <= n; i++)
                if (divisor[i] == i)
                    for (int j = i * i; j <= n; j += i)
                        divisor[j] = i;
            return divisor;
        }

    }

}