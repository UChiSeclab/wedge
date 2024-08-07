import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.ArrayList;
import java.io.OutputStreamWriter;
import java.util.function.IntFunction;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Consumer;
import java.util.List;
import java.io.Closeable;
import java.io.Writer;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(null, new TaskAdapter(), "", 1 << 29);
        thread.start();
        thread.join();
    }

    static class TaskAdapter implements Runnable {
        @Override
        public void run() {
            InputStream inputStream = System.in;
            OutputStream outputStream = System.out;
            FastInput in = new FastInput(inputStream);
            FastOutput out = new FastOutput(outputStream);
            DBirthday solver = new DBirthday();
            solver.solve(1, in, out);
            out.close();
        }
    }

    static class DBirthday {
        int mod = (int) 1e9 + 7;
        int order = 0;
        int n;
        SegTree<SumImpl, UpdateImpl> st;
        Node[] dfnToNode;
        SumImpl sumBuf = new SumImpl();
        UpdateImpl updBuf = new UpdateImpl();
        Debug debug = new Debug(false);

        void dfs(Node root, Node p, int t) {
            if (t >= mod) {
                t -= mod;
            }
            root.open = ++order;
            dfnToNode[root.open] = root;
            root.depth = t;
            for (Edge e : root.adj) {
                Node node = e.other(root);
                if (node == p) {
                    continue;
                }
                dfs(node, root, t + e.w);
            }
            root.close = order;
        }

        public void updateInterval(int l, int r, long x) {
            updBuf.tl = x;
            st.update(l, r, 1, n, updBuf);
        }

        public long sumOfSquareDist(int l, int r, Node node) {
            sumBuf.clear();
            st.query(l, r, 1, n, sumBuf);
            long tu = node.depth;
            long ans = tu * tu % mod * sumBuf.size % mod
                    + 4 * sumBuf.sqTl
                    + sumBuf.sqTx
                    + 2 * tu * sumBuf.tx % mod
                    - 4 * tu * sumBuf.tl % mod
                    - 4 * sumBuf.tlTx;
            ans = DigitUtils.mod(ans, mod);
            return ans;
        }

        public void dfsForQuery(Node root, Node p) {
            updateInterval(root.open, root.close, root.depth);
            debug.debug("root", root);
            debug.debug("st", st);
            for (Query q : root.qs) {
                q.ans = 2 * sumOfSquareDist(q.v.open, q.v.close, root) - sumOfSquareDist(1, n, root);
                q.ans = DigitUtils.mod(q.ans, mod);
            }
            for (Edge e : root.adj) {
                Node node = e.other(root);
                if (node == p) {
                    continue;
                }
                dfsForQuery(node, root);
            }
            if (p != null) {
                updateInterval(root.open, root.close, p.depth);
            }
        }

        public void solve(int testNumber, FastInput in, FastOutput out) {
            n = in.ri();
            Node[] nodes = new Node[n];
            dfnToNode = new Node[n + 1];
            for (int i = 0; i < n; i++) {
                nodes[i] = new Node();
                nodes[i].id = i;
            }
            for (int i = 0; i < n - 1; i++) {
                Node a = nodes[in.ri() - 1];
                Node b = nodes[in.ri() - 1];
                Edge e = new Edge();
                e.a = a;
                e.b = b;
                e.w = in.ri();
                e.a.adj.add(e);
                e.b.adj.add(e);
            }
            dfs(nodes[0], null, 0);
            st = new SegTree<>(1, n, SumImpl::new, UpdateImpl::new,
                    i -> {
                        SumImpl sum = new SumImpl();
                        sum.size = 1;
                        sum.tx = dfnToNode[i].depth;
                        sum.sqTx = dfnToNode[i].depth * dfnToNode[i].depth % mod;
                        return sum;
                    });
            int q = in.ri();
            Query[] queries = new Query[q];
            for (int i = 0; i < q; i++) {
                queries[i] = new Query(nodes[in.ri() - 1], nodes[in.ri() - 1]);
                queries[i].u.qs.add(queries[i]);
            }
            dfsForQuery(nodes[0], null);
            for (Query query : queries) {
                out.println(query.ans);
            }
        }

    }

    static interface Sum<S, U> extends Cloneable {
        void add(S s);

        void update(U u);

        void copy(S s);

        S clone();

    }

    static class UpdateImpl extends CloneSupportObject<UpdateImpl> implements Update<UpdateImpl> {
        long tl;

        public void update(UpdateImpl update) {
            tl = update.tl;
        }

        public void clear() {
            tl = -1;
        }

        public boolean ofBoolean() {
            return tl >= 0;
        }

    }

    static interface Update<U extends Update<U>> extends Cloneable {
        void update(U u);

        void clear();

        boolean ofBoolean();

        U clone();

    }

    static class Debug {
        private boolean offline;
        private PrintStream out = System.err;
        static int[] empty = new int[0];

        public Debug(boolean enable) {
            offline = enable && System.getSecurityManager() == null;
        }

        public Debug debug(String name, Object x) {
            return debug(name, x, empty);
        }

        public Debug debug(String name, Object x, int... indexes) {
            if (offline) {
                if (x == null || !x.getClass().isArray()) {
                    out.append(name);
                    for (int i : indexes) {
                        out.printf("[%d]", i);
                    }
                    out.append("=").append("" + x);
                    out.println();
                } else {
                    indexes = Arrays.copyOf(indexes, indexes.length + 1);
                    if (x instanceof byte[]) {
                        byte[] arr = (byte[]) x;
                        for (int i = 0; i < arr.length; i++) {
                            indexes[indexes.length - 1] = i;
                            debug(name, arr[i], indexes);
                        }
                    } else if (x instanceof short[]) {
                        short[] arr = (short[]) x;
                        for (int i = 0; i < arr.length; i++) {
                            indexes[indexes.length - 1] = i;
                            debug(name, arr[i], indexes);
                        }
                    } else if (x instanceof boolean[]) {
                        boolean[] arr = (boolean[]) x;
                        for (int i = 0; i < arr.length; i++) {
                            indexes[indexes.length - 1] = i;
                            debug(name, arr[i], indexes);
                        }
                    } else if (x instanceof char[]) {
                        char[] arr = (char[]) x;
                        for (int i = 0; i < arr.length; i++) {
                            indexes[indexes.length - 1] = i;
                            debug(name, arr[i], indexes);
                        }
                    } else if (x instanceof int[]) {
                        int[] arr = (int[]) x;
                        for (int i = 0; i < arr.length; i++) {
                            indexes[indexes.length - 1] = i;
                            debug(name, arr[i], indexes);
                        }
                    } else if (x instanceof float[]) {
                        float[] arr = (float[]) x;
                        for (int i = 0; i < arr.length; i++) {
                            indexes[indexes.length - 1] = i;
                            debug(name, arr[i], indexes);
                        }
                    } else if (x instanceof double[]) {
                        double[] arr = (double[]) x;
                        for (int i = 0; i < arr.length; i++) {
                            indexes[indexes.length - 1] = i;
                            debug(name, arr[i], indexes);
                        }
                    } else if (x instanceof long[]) {
                        long[] arr = (long[]) x;
                        for (int i = 0; i < arr.length; i++) {
                            indexes[indexes.length - 1] = i;
                            debug(name, arr[i], indexes);
                        }
                    } else {
                        Object[] arr = (Object[]) x;
                        for (int i = 0; i < arr.length; i++) {
                            indexes[indexes.length - 1] = i;
                            debug(name, arr[i], indexes);
                        }
                    }
                }
            }
            return this;
        }

    }

    static class SumImpl implements Sum<SumImpl, UpdateImpl> {
        static int mod = (int) 1e9 + 7;
        int size;
        long sqTl;
        long sqTx;
        long tx;
        long tl;
        long tlTx;

        public void clear() {
            sqTx = sqTl = tx = tl = tlTx = size = 0;
        }

        public void add(SumImpl sum) {
            size += sum.size;
            sqTl += sum.sqTl;
            if (sqTl >= mod) {
                sqTl -= mod;
            }
            sqTx += sum.sqTx;
            if (sqTx >= mod) {
                sqTx -= mod;
            }
            tx += sum.tx;
            if (tx >= mod) {
                tx -= mod;
            }
            tl += sum.tl;
            if (tl >= mod) {
                tl -= mod;
            }
            tlTx += sum.tlTx;
            if (tlTx >= mod) {
                tlTx -= mod;
            }

        }

        public void update(UpdateImpl update) {
            tl = update.tl * size % mod;
            sqTl = update.tl * update.tl % mod * size % mod;
            tlTx = update.tl * tx % mod;
        }

        public void copy(SumImpl sum) {
            size = sum.size;
            tl = sum.tl;
            tx = sum.tx;
            sqTl = sum.sqTl;
            sqTx = sum.sqTx;
            tlTx = sum.tlTx;
        }

        public SumImpl clone() {
            SumImpl ans = new SumImpl();
            ans.copy(this);
            return ans;
        }

        public String toString() {
            return "(" + tx + "," + tl + ")";
        }

    }

    static class DigitUtils {
        private DigitUtils() {
        }

        public static int mod(long x, int mod) {
            if (x < -mod || x >= mod) {
                x %= mod;
            }
            if (x < 0) {
                x += mod;
            }
            return (int) x;
        }

        public static int floorAverage(int x, int y) {
            return (x & y) + ((x ^ y) >> 1);
        }

    }

    static class Edge {
        Node a;
        Node b;
        int w;

        public Node other(Node x) {
            return a == x ? b : a;
        }

    }

    static class SegTree<S extends Sum<S, U>, U extends Update<U>> implements Cloneable {
        private SegTree<S, U> left;
        private SegTree<S, U> right;
        public S sum;
        private U update;

        private void modify(U x) {
            update.update(x);
            sum.update(x);
        }

        private void pushDown() {
            if (update.ofBoolean()) {
                left.modify(update);
                right.modify(update);
                update.clear();
                assert !update.ofBoolean();
            }
        }

        private void pushUp() {
            sum.copy(left.sum);
            sum.add(right.sum);
        }

        public SegTree(int l, int r, Supplier<S> sSupplier, Supplier<U> uSupplier,
                       IntFunction<S> func) {
            update = uSupplier.get();
            update.clear();
            if (l < r) {
                sum = sSupplier.get();
                int m = DigitUtils.floorAverage(l, r);
                left = new SegTree<>(l, m, sSupplier, uSupplier, func);
                right = new SegTree<>(m + 1, r, sSupplier, uSupplier, func);
                pushUp();
            } else {
                sum = func.apply(l);
            }
        }

        private boolean cover(int L, int R, int l, int r) {
            return L <= l && R >= r;
        }

        private boolean leave(int L, int R, int l, int r) {
            return R < l || L > r;
        }

        public void update(int L, int R, int l, int r, U u) {
            if (leave(L, R, l, r)) {
                return;
            }
            if (cover(L, R, l, r)) {
                modify(u);
                return;
            }
            int m = DigitUtils.floorAverage(l, r);
            pushDown();
            left.update(L, R, l, m, u);
            right.update(L, R, m + 1, r, u);
            pushUp();
        }

        public void query(int L, int R, int l, int r, S s) {
            if (leave(L, R, l, r)) {
                return;
            }
            if (cover(L, R, l, r)) {
                s.add(sum);
                return;
            }
            int m = DigitUtils.floorAverage(l, r);
            pushDown();
            left.query(L, R, l, m, s);
            right.query(L, R, m + 1, r, s);
        }

        public SegTree<S, U> deepClone() {
            SegTree<S, U> clone = clone();
            clone.sum = clone.sum.clone();
            clone.update = clone.update.clone();
            if (clone.left != null) {
                clone.left = clone.left.deepClone();
                clone.right = clone.right.deepClone();
            }
            return clone;
        }

        public void visitLeave(Consumer<SegTree<S, U>> consumer) {
            if (left == null) {
                consumer.accept(this);
                return;
            }
            pushDown();
            left.visitLeave(consumer);
            right.visitLeave(consumer);
        }

        public String toString() {
            StringBuilder ans = new StringBuilder();
            deepClone().visitLeave(x -> ans.append(x.sum).append(' '));
            return ans.toString();
        }

        public SegTree<S, U> clone() {
            try {
                return (SegTree<S, U>) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new UnsupportedOperationException(e);
            }
        }

    }

    static class Node {
        List<Edge> adj = new ArrayList<>();
        int id;
        int open;
        int close;
        long depth;
        List<Query> qs = new ArrayList<>();

        public String toString() {
            return "" + (id + 1);
        }

    }

    static abstract class CloneSupportObject<T> implements Cloneable {
        public T clone() {
            try {
                return (T) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    static class FastInput {
        private final InputStream is;
        private byte[] buf = new byte[1 << 13];
        private int bufLen;
        private int bufOffset;
        private int next;

        public FastInput(InputStream is) {
            this.is = is;
        }

        private int read() {
            while (bufLen == bufOffset) {
                bufOffset = 0;
                try {
                    bufLen = is.read(buf);
                } catch (IOException e) {
                    bufLen = -1;
                }
                if (bufLen == -1) {
                    return -1;
                }
            }
            return buf[bufOffset++];
        }

        public void skipBlank() {
            while (next >= 0 && next <= 32) {
                next = read();
            }
        }

        public int ri() {
            return readInt();
        }

        public int readInt() {
            int sign = 1;

            skipBlank();
            if (next == '+' || next == '-') {
                sign = next == '+' ? 1 : -1;
                next = read();
            }

            int val = 0;
            if (sign == 1) {
                while (next >= '0' && next <= '9') {
                    val = val * 10 + next - '0';
                    next = read();
                }
            } else {
                while (next >= '0' && next <= '9') {
                    val = val * 10 - next + '0';
                    next = read();
                }
            }

            return val;
        }

    }

    static class FastOutput implements AutoCloseable, Closeable, Appendable {
        private static final int THRESHOLD = 32 << 10;
        private final Writer os;
        private StringBuilder cache = new StringBuilder(THRESHOLD * 2);

        public FastOutput append(CharSequence csq) {
            cache.append(csq);
            return this;
        }

        public FastOutput append(CharSequence csq, int start, int end) {
            cache.append(csq, start, end);
            return this;
        }

        private void afterWrite() {
            if (cache.length() < THRESHOLD) {
                return;
            }
            flush();
        }

        public FastOutput(Writer os) {
            this.os = os;
        }

        public FastOutput(OutputStream os) {
            this(new OutputStreamWriter(os));
        }

        public FastOutput append(char c) {
            cache.append(c);
            afterWrite();
            return this;
        }

        public FastOutput append(long c) {
            cache.append(c);
            afterWrite();
            return this;
        }

        public FastOutput append(String c) {
            cache.append(c);
            afterWrite();
            return this;
        }

        public FastOutput println(long c) {
            return append(c).println();
        }

        public FastOutput println() {
            return append(System.lineSeparator());
        }

        public FastOutput flush() {
            try {
//            boolean success = false;
//            if (stringBuilderValueField != null) {
//                try {
//                    char[] value = (char[]) stringBuilderValueField.get(cache);
//                    os.write(value, 0, cache.length());
//                    success = true;
//                } catch (Exception e) {
//                }
//            }
//            if (!success) {
                os.append(cache);
//            }
                os.flush();
                cache.setLength(0);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            return this;
        }

        public void close() {
            flush();
            try {
                os.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        public String toString() {
            return cache.toString();
        }

    }

    static class Query {
        Node u;
        Node v;
        long ans;

        public Query(Node u, Node v) {
            this.u = u;
            this.v = v;
        }

    }
}

