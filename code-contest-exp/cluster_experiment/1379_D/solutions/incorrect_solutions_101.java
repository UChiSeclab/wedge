import java.io.*;
import java.util.*;

public class MainD {

    static int N, H, M, K;
    static Freight[] F;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner(System.in);
        N = sc.nextInt();
        H = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();

        F = new Freight[N];
        for (int i = 0; i < N; i++) {
            F[i] = new Freight(sc.nextInt(), sc.nextInt());
        }

        var pw = new PrintWriter(System.out);
        solve(pw);
        pw.flush();
    }

    static void solve(PrintWriter pw) {
        int HM = M / 2;
        var minSet = new HashSet<Integer>();
        for (Freight f : F) {
            if (f.minute < HM) {
                minSet.add(f.minute);
                minSet.add(f.minute + HM);
            } else {
                minSet.add(f.minute - HM);
                minSet.add(f.minute);
            }
        }
        minSet.add( M*2 );

        IntZip zip = new IntZip(minSet);
        FenwickTree bit = new FenwickTree(M, FenwickTree.SUM);
        for (Freight f : F) {
            if (f.minute < HM) {
                bit.add( zip.to_i(f.minute), 1);
                bit.add( zip.to_i(f.minute + HM), 1);

            } else {
                bit.add( zip.to_i(f.minute - HM), 1);
                bit.add( zip.to_i(f.minute), 1);
            }
        }

        int[] mins = zip.toValue;
        int minSize = N;
        int minIndex = -1;
        for (int i = 0; i < N; i++) {
            Freight f = F[i];
            int min = f.minute < HM ? f.minute : f.minute - HM;

            // ほしいものは(min, min+K)
            // bitが半開区間
            int j = lowerBound(mins, min+K);
            int size = (int)bit.query(i, j);
            size -= bit.query(i, i+1);

            if( size <= minSize ) {
                minSize = size;
                minIndex = i;
            }
        }

        // debug(minIndex, minSize);

        // 輸送車の時間からK分後に客車が到着する
        int passMin = F[minIndex].minute + K;
        if( passMin >= HM ) {
            passMin %= HM;
        }

        pw.println(minSize + " " + passMin);

        // [from, to]
        int from = F[minIndex].minute % HM;
        int to = from + K;

        for (int i = 0; i < N; i++) {
            var min = F[i].minute % HM;
            if( (from < min && min < to) || (from < min+HM && min+HM < to)) {
                pw.println(i+1);
            }
        }
    }

    static int lowerBound(int[] array, int value) {
        int low = 0;
        int high = array.length;
        int mid;
        while( low < high ) {
            mid = ((high - low) >>> 1) + low; // (high + low) / 2
            if( array[mid] < value ) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    static int upperBound(int[] array, int value) {
        int low = 0;
        int high = array.length;
        int mid;
        while( low < high ) {
            mid = ((high - low) >>> 1) + low; // (high + low) / 2
            if( array[mid] <= value ) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    static class Freight {
        final int hour, minute;

        public Freight(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }
    }

    static class IntZip {
        Map<Integer, Integer> toIndex;
        int[] toValue;

        IntZip(Set<Integer> set) {
            toValue = new int[set.size()];
            int idx = 0;
            for (Integer each : set) {
                toValue[idx++] = each;
            }
            Arrays.sort(toValue);
            toIndex = new HashMap<>();
            for (int i = 0; i < set.size(); i++) {
                toIndex.put(toValue[i], i);
            }
        }

        int to_i(int value) {
            return toIndex.get(value);
        }

        int to_v(int index) {
            return toValue[index];
        }
    }

    static class FenwickTree {

        interface Monoid {
            long identity();

            long apply(long a, long b);

            long inverse(long a);
        }

        static Monoid SUM = new Monoid() {
            public long identity() {
                return 0;
            }

            public long apply(long a, long b) {
                return a + b;
            }

            public long inverse(long a) {
                return -a;
            }
        };

        static Monoid MAX = new Monoid() {
            public long identity() {
                return 0;
            }

            public long apply(long a, long b) {
                return Math.max(a, b);
            }

            public long inverse(long a) {
                throw new RuntimeException("no inverse");
            }
        };


        int size;
        long[] bit;
        Monoid m;
        long identity;

        FenwickTree(int size, Monoid m) {
            this.size = 1;
            while (this.size < size) this.size *= 2;
            this.bit = new long[this.size + 1];
            this.identity = m.identity();
            if (this.identity != 0) {
                Arrays.fill(this.bit, this.identity);
            }
            this.m = m;
        }

        void add(int i, long v) {
            i++; // 0 index -> 1 index

            while (i <= size) {
                bit[i] = m.apply(bit[i], v);
                i += i & -i;
            }
        }

        // [0, r)
        long query(int r) {
            // 0 index -> 1 index -> -1
            long ret = identity;
            while (r > 0) {
                ret = m.apply(ret, bit[r]);
                r -= r & -r;
            }
            return ret;
        }

        long query(int l, int r) {
            return query(r) + m.inverse(query(l));
        }

        int lowerBound(int v) {
            if (bit[size] < v) return size;

            int x = 0;
            for (int k = size / 2; k > 0; k /= 2) {
                if (bit[x + k] < v) {
                    v -= bit[x + k];
                    x += k;
                }
            }
            return x;
        }
    }

    @SuppressWarnings("unused")
    static class FastScanner {
        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        FastScanner(InputStream in) {
            reader = new BufferedReader(new InputStreamReader(in));
            tokenizer = null;
        }

        String next() {
            if (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        String nextLine() {
            if (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    return reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken("\n");
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = nextInt();
            return a;
        }

        int[] nextIntArray(int n, int delta) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = nextInt() + delta;
            return a;
        }

        long[] nextLongArray(int n) {
            long[] a = new long[n];
            for (int i = 0; i < n; i++) a[i] = nextLong();
            return a;
        }
    }

    static void writeLines(int[] as) {
        PrintWriter pw = new PrintWriter(System.out);
        for (int a : as) pw.println(a);
        pw.flush();
    }

    static void writeLines(long[] as) {
        PrintWriter pw = new PrintWriter(System.out);
        for (long a : as) pw.println(a);
        pw.flush();
    }

    static void writeSingleLine(int[] as) {
        PrintWriter pw = new PrintWriter(System.out);
        for (int i = 0; i < as.length; i++) {
            if (i != 0) pw.print(" ");
            pw.print(as[i]);
        }
        pw.println();
        pw.flush();
    }

    static void debug(Object... args) {
        StringJoiner j = new StringJoiner(" ");
        for (Object arg : args) {
            if (arg == null) j.add("null");
            else if (arg instanceof int[]) j.add(Arrays.toString((int[]) arg));
            else if (arg instanceof long[]) j.add(Arrays.toString((long[]) arg));
            else if (arg instanceof double[]) j.add(Arrays.toString((double[]) arg));
            else if (arg instanceof Object[]) j.add(Arrays.toString((Object[]) arg));
            else j.add(arg.toString());
        }
        System.err.println(j.toString());
    }
}
