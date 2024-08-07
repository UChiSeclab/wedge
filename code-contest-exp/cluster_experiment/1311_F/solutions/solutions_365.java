import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class C1311F_1 {
    public static void main(String[] args) {
        var scanner = new BufferedScanner();
        var writer = new PrintWriter(new BufferedOutputStream(System.out));

        var n = scanner.nextInt();
        var x = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
//            x[i] = (int) (Math.random() * 2e8 - 1e8);
//            x[i] = (int) (Math.random() * n / 2 + n / 2);
        }
        var v = new int[n];
        for (int i = 0; i < n; i++) {
            v[i] = scanner.nextInt();
//            v[i] = (int) (Math.random() * 2e8 - 1e8);
//            v[i] = (int) (Math.random() * 2 * n - n);
        }
        writer.println(solve(n, x, v));

        scanner.close();
        writer.flush();
        writer.close();
    }

    static final int MIN_V = (int) -1e8;
    static final int MAX_V = (int) 1e8;

    private static long solve(int n, int[] x, int[] v) {
        var debug = false; //n == 200000;

        var orderX = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            orderX.add(i);
        }
        var t = System.nanoTime();
        orderX.sort(Comparator.comparingInt(o -> x[o]));
        if (debug) {
            System.out.println("sort finished: " + (System.nanoTime() - t) / 1e9);
        }
        t = System.nanoTime();
        var ans = 0L;
        var toLeft = new SegTree(v);
        var toRight = new SegTree(v);
        for (int i = 0; i < n; i++) {
            var thisX = x[orderX.get(i)];
            var thisV = v[orderX.get(i)];
            if (thisV >= 0) {
                var left = toLeft.acc(MIN_V, 0);
                ans += left[1] * thisX - left[0];
                var right = toRight.acc(0, thisV);
                ans += right[1] * thisX - right[0];
                toRight.add(thisV, thisX);
            } else {
                var left = toLeft.acc(MIN_V, thisV);
                ans += left[1] * thisX - left[0];
                toLeft.add(thisV, thisX);
            }
        }
        if (debug) {
            System.out.println("finish acc: " + (System.nanoTime() - t) / 1e9);
        }
        return ans;
    }

    static class SegTree {
        final List<Node> nodes = new ArrayList<>();
        /**
         * 所有有效的key，递增。
         */
        final int[] keys;

        SegTree(int[] keys) {
            this.keys = distinctAndAsc(keys);
            nodes.add(new Node(0, this.keys.length - 1));
        }

        static int[] distinctAndAsc(int[] keys) {
            var distinct = new HashSet<Integer>();
            for (int key : keys) {
                distinct.add(key);
            }
            var asc = new ArrayList<>(distinct);
            asc.sort(Integer::compareTo);
            return asc.stream().mapToInt(i -> i).toArray();
        }

        void add(int key, int value) {
            add(0, key, value);
        }

        private void add(int index, int key, int value) {
            if (index < 0) {
                return;
            }
            var node = nodes.get(index);
//            debug("node %d: (low=%d,highEx=%d)", index, node.low, node.high);
            if (key < keys[node.low] || keys[node.high] < key) {
                return;
            }
            node.acc[0] += value;
            node.acc[1]++;
            if (node.isLeaf()) {
                return;
            }
            if (node.left < 0) {
                nodes.add(new Node(node.low, node.mid));
                node.left = nodes.size() - 1;
            }
            add(node.left, key, value);
            if (node.right < 0) {
                nodes.add(new Node(node.mid + 1, node.high));
                node.right = nodes.size() - 1;
            }
            add(node.right, key, value);
        }

        long[] acc(int lowKey, int highKey) {
            return acc(0, lowKey, highKey);
        }

        static final long[] EMPTY = new long[]{0, 0};

        private long[] acc(int index, int lowKey, int highKey) {
            if (lowKey > highKey || index < 0) {
                return EMPTY;
            }
            var node = nodes.get(index);
            if (highKey < keys[node.low] || keys[node.high] < lowKey) {
                return EMPTY;
            }
            if (lowKey <= keys[node.low] && keys[node.high] <= highKey) {
                return node.acc;
            }
            var left = acc(node.left, lowKey, highKey);
            var right = acc(node.right, lowKey, highKey);
            return new long[]{left[0] + right[0], left[1] + right[1]};
        }

        static class Node {
            final int low;
            final int high;
            final int mid;
            int left = -1;
            int right = -1;
            long[] acc = new long[]{0, 0};

            Node(int low, int high) {
                this.low = low;
                this.high = high;
                this.mid = Math.floorDiv(low + high, 2);
            }

            public boolean isLeaf() {
                return low >= high;
            }
        }
    }

    static void debug(String fmt, Object... args) {
        System.out.println(String.format(fmt, args));
    }

    public static class BufferedScanner {
        BufferedReader br;
        StringTokenizer st;

        public BufferedScanner(Reader reader) {
            br = new BufferedReader(reader);
        }

        public BufferedScanner() {
            this(new InputStreamReader(System.in));
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

        void close() {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    static long gcd(long a, long b) {
        if (a < b) {
            return gcd(b, a);
        }
        while (b > 0) {
            long tmp = b;
            b = a % b;
            a = tmp;
        }
        return a;
    }

    static long inverse(long a, long m) {
        long[] ans = extgcd(a, m);
        return ans[0] == 1 ? (ans[1] + m) % m : -1;
    }

    private static long[] extgcd(long a, long m) {
        if (m == 0) {
            return new long[]{a, 1, 0};
        } else {
            long[] ans = extgcd(m, a % m);
            long tmp = ans[1];
            ans[1] = ans[2];
            ans[2] = tmp;
            ans[2] -= ans[1] * (a / m);
            return ans;
        }
    }

    private static List<Integer> primes(double upperBound) {
        var limit = (int) Math.sqrt(upperBound);
        var isComposite = new boolean[limit + 1];
        var primes = new ArrayList<Integer>();
        for (int i = 2; i <= limit; i++) {
            if (isComposite[i]) {
                continue;
            }
            primes.add(i);
            int j = i + i;
            while (j <= limit) {
                isComposite[j] = true;
                j += i;
            }
        }
        return primes;
    }


}
