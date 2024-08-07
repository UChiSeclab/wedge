import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;

/**
 * #
 *
 * @author pttrung
 */
public class E_Round_605_Div3 {
    public static long MOD = 1000000007;


    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = in.nextInt();
        }
        ArrayList<Integer>[] map = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            map[i] = new ArrayList<>();
        }
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        LinkedList<Integer> q = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            int a = i + data[i];
            if (a < n) {
                if (data[i] % 2 != data[a] % 2 && dp[i] == -1) {
                    dp[i] = 1;
                    q.add(i);
                }
                map[a].add(i);
            }
            a = i - data[i];
            if (a >= 0) {
                if (data[i] % 2 != data[a] % 2 && dp[i] == -1) {
                    dp[i] = 1;
                    q.add(i);
                }
                map[a].add(i);
            }
        }
        while (!q.isEmpty()) {
            int node = q.poll();
            for (int i : map[node]) {
                if (dp[i] == -1) {
                    dp[i] = 1 + dp[node];
                    q.add(i);
                }
            }
        }
        for (int i : dp) {
            out.print(i + " ");
        }
        out.close();
    }


    static int abs(int a) {
        return a < 0 ? -a : a;
    }

    static class Slope {
        int x, y;

        Slope(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Slope{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Slope slope = (Slope) o;
            return x == slope.x &&
                    y == slope.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static boolean straight(int[] a, int[] b, int[] c) {
        long x1 = b[0] - a[0];
        long y1 = b[1] - a[1];
        long x2 = c[0] - a[0];
        long y2 = c[1] - a[1];
        return x1 * y2 == x2 * y1;
    }

    static long dist(int[] a, int[] b) {
        long x = a[0] - b[0];
        long y = a[1] - b[1];
        return x * x + y * y;
    }

    public static int[] KMP(String val) {
        int i = 0;
        int j = -1;
        int[] result = new int[val.length() + 1];
        result[0] = -1;
        while (i < val.length()) {
            while (j >= 0 && val.charAt(j) != val.charAt(i)) {
                j = result[j];
            }
            j++;
            i++;
            result[i] = j;
        }
        return result;

    }

    public static boolean nextPer(int[] data) {
        int i = data.length - 1;
        while (i > 0 && data[i] < data[i - 1]) {
            i--;
        }
        if (i == 0) {
            return false;
        }
        int j = data.length - 1;
        while (data[j] < data[i - 1]) {
            j--;
        }
        int temp = data[i - 1];
        data[i - 1] = data[j];
        data[j] = temp;
        Arrays.sort(data, i, data.length);
        return true;
    }

    public static int digit(long n) {
        int result = 0;
        while (n > 0) {
            n /= 10;
            result++;
        }
        return result;
    }

    public static double dist(long a, long b, long x, long y) {
        double val = (b - a) * (b - a) + (x - y) * (x - y);
        val = Math.sqrt(val);
        double other = x * x + a * a;
        other = Math.sqrt(other);
        return val + other;

    }

    public static class Point implements Comparable<Point> {

        int index;
        long dist;

        public Point(int index, long dist) {
            this.index = index;
            this.dist = dist;
        }


        @Override
        public int compareTo(Point o) {
            return Long.compare(dist, o.dist);
        }
    }

    public static class FT {

        long[] data;

        FT(int n) {
            data = new long[n];
        }

        public void update(int index, long value) {
            while (index < data.length) {
                data[index] += value;
                index += (index & (-index));
            }
        }

        public long get(int index) {
            long result = 0;
            while (index > 0) {
                result += data[index];
                index -= (index & (-index));
            }
            return result;

        }
    }

    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static long pow(long a, int b) {
        if (b == 0) {
            return 1;
        }
        if (b == 1) {
            return a;
        }

        long val = pow(a, b / 2);
        if (b % 2 == 0) {
            return val * val;
        } else {
            return val * (val * a);

        }
    }

    static class Scanner {

        BufferedReader br;
        StringTokenizer st;

        public Scanner() throws FileNotFoundException {
            // System.setOut(new PrintStream(new BufferedOutputStream(System.out), true));
            br = new BufferedReader(new InputStreamReader(System.in));
            //  br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("input.txt"))));
        }

        public String next() {

            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
            return st.nextToken();
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public String nextLine() {
            st = null;
            try {
                return br.readLine();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }

        public boolean endLine() {
            try {
                String next = br.readLine();
                while (next != null && next.trim().isEmpty()) {
                    next = br.readLine();
                }
                if (next == null) {
                    return true;
                }
                st = new StringTokenizer(next);
                return st.hasMoreTokens();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }
}