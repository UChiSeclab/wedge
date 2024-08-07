import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author htvu
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        FMovingPoints solver = new FMovingPoints();
        solver.solve(1, in, out);
        out.close();
    }

    static class FMovingPoints {
        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int[] x = new int[n];
            int[] v = new int[n];
            int ans = 0;
            Map<Integer, Integer> rank = new HashMap<>();
            for (int i = 0; i < n; ++i) {
                x[i] = in.nextInt();
            }
            for (int i = 0; i < n; ++i)
                v[i] = in.nextInt();

            Pair.IntPair[] ps = new Pair.IntPair[n];
            for (int i = 0; i < n; ++i)
                ps[i] = new Pair.IntPair(x[i], v[i]);
            Arrays.sort(ps, (p1, p2) -> !p1.second.equals(p2.second) ? p1.second - p2.second : p1.first - p2.first);
            Arrays.sort(x);
            for (int i = 0; i < n; ++i) {
                // add x[i] to pair sum
                ans += x[i] * (2 * i + 1 - n);
                // record position of x[i] based on v[i]
                rank.put(ps[i].first, i);
            }

            for (int i = 0; i < n; ++i) {
//            ans -= ps[i].first * (rank.get(ps[i].first) - i);
                ans -= x[i] * (i - rank.get(x[i]));
            }
            out.println(ans);
        }

    }

    static class Pair<A extends Comparable<A>, B extends Comparable<B>> implements Comparable<Pair<A, B>> {
        public A first;
        public B second;

        public Pair(A a, B b) {
            this.first = a;
            this.second = b;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return first.equals(pair.first) &&
                second.equals(pair.second);
        }

        public int hashCode() {
            return (first == null ? 0 : first.hashCode()) ^
                (second == null ? 0 : second.hashCode());
        }

        public String toString() {
            return "{" + first + "," + second + '}';
        }

        public int compareTo(Pair<A, B> o) {
            int c = first.compareTo(o.first);
            if (c != 0) return c;

            return second.compareTo(o.second);
        }

        public static class IntPair extends Pair<Integer, Integer> {
            public IntPair(Integer integer, Integer integer2) {
                super(integer, integer2);
            }

        }

    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

