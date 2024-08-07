import java.io.*;
import java.util.StringTokenizer;

public class D {
    private static final int MAX_MASK = 1 << 14;
    private static final int MAX_NUM = 10001;

    private void run(FastReader in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();

        int[] a = new int[n];
        int[] b = new int[MAX_MASK];
        for (int i = 0; i < n; ++i) {
            a[i] = in.nextInt();
            b[a[i]]++;
        }

        if (k == 0) {
            long res = 0;
            for (int i = 0; i < MAX_NUM; i++) {
                res += (long) b[i] * (b[i] - 1) / 2;
            }
            out.println(res);
        } else {
            int[] masks = new int[MAX_MASK];
            int cnt = 0;
            for (int mask = 0; mask < MAX_MASK; mask++) {
                if (Integer.bitCount(mask) == k)
                    masks[cnt++] = mask;
            }

            long res = 0;
            for (int first = 0; first < MAX_NUM; first++) {
                if (b[first] == 0) continue;
                for (int i = 0; i < cnt; i++) {
                    int mask = masks[i];
                    int second = first ^ mask;
                    res += (long) b[first] * b[second];
                }
            }

            out.println(res / 2);
        }
//        out.println(naive(a, k));
    }

    private int naive(int[] a, int k) {
        int res = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                if (k == Integer.bitCount(a[i] ^ a[j]))
                    res += 1;
            }
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        try (FastReader in = new FastReader(new BufferedReader(new InputStreamReader(System.in)));
             PrintWriter out = new PrintWriter(System.out)) {
            new D().run(in, out);
        }
    }

    private static class FastReader implements Closeable {
        private final BufferedReader reader;
        private StringTokenizer tokenizer = null;

        private FastReader(BufferedReader reader) {
            this.reader = reader;
        }

        public String nextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(nextLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        @Override
        public void close() throws IOException {
            reader.close();
        }
    }
}
