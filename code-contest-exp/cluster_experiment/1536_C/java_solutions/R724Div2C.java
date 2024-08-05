//package j.gym.codeforces.regular.r7xx.r724;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * <a href="https://codeforces.com/contest/1536">Codeforces Round #724 (Div. 2)</a>
 */
public class R724Div2C {

    public void solve(InputProvider in, PrintWriter out) throws IOException {
        int testCount = in.nextInt();
        for (int test = 0; test < testCount; test++) {
            in.nextInt();
            char[] chars = in.nextLine().toCharArray();
            Map<Double, Integer> relationToCount = new HashMap<>();
            long dCount = 0;
            long kCount = 0;
            for (char symbol : chars) {
                if (symbol == 'D') {
                    dCount++;
                } else {
                    kCount++;
                }
                if (kCount == 0) {
                    out.print(dCount + " ");
                } else if (dCount == 0) {
                    out.print(kCount + " ");
                } else {
                    double relation = (double) dCount / kCount;
                    Integer count = relationToCount.merge(relation, 1, Integer::sum);
                    out.print(count + " ");
                }
            }
            out.print("\n");
        }
    }

    public static void main(String[] args) throws Exception {
        try (InputProvider input = new InputProvider(System.in);
             PrintWriter output = new PrintWriter(System.out)) {
            new R724Div2C().solve(input, output);
        }
    }

    public static class InputProvider implements AutoCloseable {

        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        public InputProvider(Reader reader) {
            this.reader = new BufferedReader(reader);
        }

        public InputProvider(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
        }

        public String next() throws IOException {
            if (Objects.isNull(tokenizer) || !tokenizer.hasMoreTokens())
                tokenizer = new StringTokenizer(reader.readLine());
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        public String nextLine() throws IOException {
            return reader.readLine();
        }

        @Override
        public void close() throws Exception {
            reader.close();
        }

    }

}
