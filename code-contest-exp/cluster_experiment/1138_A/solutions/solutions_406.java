import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

public class problem1 {

    boolean ONLINE_JUDGE = (System.getProperty("ONLINE_JUDGE") != null);

    public problem1() throws IOException {
        InputReader in;
        if (ONLINE_JUDGE) {
            in = new InputReader(System.in);
        } else {
            in = new InputReader(new FileInputStream("input.txt"));
        }
        PrintWriter out = new PrintWriter(System.out);

        int T = 1;
        for (int caseNo = 1; caseNo <= T; caseNo++) {
            solve(caseNo, in, out);
        }

        out.close();
    }

    int lower_bound(int arr[], int key, int low, int high)
    {
        if (low > high)
            //return -1;
            return low;

        int mid = low + ((high - low) >> 1);
        //if (arr[mid] == key) return mid;

        //Attention here, we go left for lower_bound when meeting equal values
        if (arr[mid] >= key)
            return lower_bound(arr, key, low, mid - 1);
        else
            return lower_bound(arr, key, mid + 1, high);
    }


    private void solve(int caseNo, InputReader in, PrintWriter out) throws IOException {

        int n = in.nextInt();

        int arr[] = new int[n];

        for (int i = 0; i < n; ++i) {
            arr[i] = in.nextInt();
        }

        List<Integer> l = new ArrayList<>();
        l.add(1);
        for (int i = 1; i < n; ++i) {
            if (arr[i - 1] != arr[i]) {
                l.add(1);
            } else {
                l.set(l.size() - 1, l.get(l.size() - 1) + 1);
            }
        }

        int ans = 0;
        for (int i = 1; i < l.size(); ++i) {
            ans = max(ans, min(l.get(i), l.get(i - 1)));
        }

        out.println(ans * 2);

    }

    static class InputReader {

        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        public interface SpaceCharFilter {

            public boolean isSpaceChar(int ch);
        }

        public String next() {
            return nextString();
        }

        public String nextString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public int nextInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public Long nextLong() {
            return Long.parseLong(nextString());
        }

        public Double nextDouble() {
            return Double.parseDouble(nextString());
        }

        public char nextCharacter() {
            return nextString().charAt(0);
        }

        public int[] nextIntArray(int N) {
            int A[] = new int[N];
            for (int i = 0; i < N; i++) {
                A[i] = nextInt();
            }
            return A;
        }

        public long[] nextLongArray(int N) {
            long A[] = new long[N];
            for (int i = 0; i < N; i++) {
                A[i] = nextLong();
            }
            return A;
        }

        public double[] nextDoubleArray(int N) {
            double A[] = new double[N];
            for (int i = 0; i < N; i++) {
                A[i] = nextDouble();
            }
            return A;
        }
    }

    int min(int... a) {
        int min = Integer.MAX_VALUE;
        for (int v : a) {
            min = Math.min(min, v);
        }
        return min;
    }

    long min(long... a) {
        long min = Long.MAX_VALUE;
        for (long v : a) {
            min = Math.min(min, v);
        }
        return min;
    }

    double min(double... a) {
        double min = Double.MAX_VALUE;
        for (double v : a) {
            min = Math.min(min, v);
        }
        return min;
    }

    int max(int... a) {
        int max = Integer.MIN_VALUE;
        for (int v : a) {
            max = Math.max(max, v);
        }
        return max;
    }

    long max(long... a) {
        long max = Long.MIN_VALUE;
        for (long v : a) {
            max = Math.max(max, v);
        }
        return max;
    }

    double max(double... a) {
        double max = Double.MIN_VALUE;
        for (double v : a) {
            max = Math.max(max, v);
        }
        return max;
    }

    private void debug(Object... o) {
        if (ONLINE_JUDGE) {
            return;
        }
        System.out.println(Arrays.deepToString(o));
    }

    public static void main(String args[]) throws IOException {
        new problem1();
    }
}