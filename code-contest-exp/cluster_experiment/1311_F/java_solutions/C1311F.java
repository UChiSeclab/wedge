import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class C1311F {
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
        var ascV = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            orderX.add(i);
            ascV.add(v[i]);
        }
        var t = System.nanoTime();
        orderX.sort(Comparator.comparingInt(o -> x[o]));
        ascV.sort(Integer::compareTo);
        if (debug) {
            System.out.println("sort finished: " + (System.nanoTime() - t) / 1e9);
        }
        t = System.nanoTime();
        var ans = 0L;
        var toLeft = new SegTree(ascV);
        var toRight = new SegTree(ascV);
        var minV = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            var thisX = x[orderX.get(i)];
            var thisV = v[orderX.get(i)];
            if (thisV >= 0) {
                var left = toLeft.acc(/*minV*/MIN_V, 0);
                ans += left[1] * thisX - left[0];
                var right = toRight.acc(0, thisV);
                ans += right[1] * thisX - right[0];
                toRight.add(thisV, thisX);
            } else {
                var left = toLeft.acc(/*minV*/MIN_V, thisV);
                ans += left[1] * thisX - left[0];
                toLeft.add(thisV, thisX);
            }
            minV = Math.min(minV, thisV);
        }
        if (debug) {
            System.out.println("finish acc: " + (System.nanoTime() - t) / 1e9);
        }
        return ans;
    }

    static class SegTree {
        final List<Node> nodes = new ArrayList<>();
        final int[] x;

        SegTree(List<Integer> x) {
            nodes.add(new Node(0, x.size() - 1));
            this.x = x.stream().mapToInt(i -> i).toArray();
        }

        void add(int key, int value) {
            add(0, key, value);
        }

        private void add(int index, int key, int value) {
            var node = nodes.get(index);
            if (key < x[node.low] || x[node.high] < key) {
                return;
            }
            node.acc[0] += value;
            node.acc[1]++;
            if (node.low == node.high) {
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
            var node = nodes.get(index);
            if (lowKey > highKey
                    || highKey < x[node.low] || x[node.high] < lowKey) {
                return EMPTY;
            }
            if (lowKey <= x[node.low] && x[node.high] <= highKey) {
                return node.acc;
            }
            var ans = new long[]{0, 0};
            if (node.left >= 0) {
                var left = acc(node.left, lowKey, highKey);
                ans[0] += left[0];
                ans[1] += left[1];
            }
            if (node.right >= 0) {
                var right = acc(node.right, lowKey, highKey);
                ans[0] += right[0];
                ans[1] += right[1];
            }
            return ans;
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
        }
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
