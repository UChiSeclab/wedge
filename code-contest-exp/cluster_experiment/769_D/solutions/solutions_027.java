import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Liavontsi Brechka
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskD solver = new TaskD();
        solver.solve(1, in, out);
        out.close();
    }

    static
    @SuppressWarnings("Duplicates")
    class TaskD {
        private static final int MAX = 10001;
        private static final int DOUBLE_MAX = 20002;

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int k = in.nextInt();
            int[] a = new int[n];
            int[] count = new int[MAX];
            for (int i = 0; i < n; i++) count[a[i] = in.nextInt()]++;

            int[] bitsCount = new int[DOUBLE_MAX];
            int numberNumber;
            for (int i = 0; i < DOUBLE_MAX; i++) {
                numberNumber = i;
                while (numberNumber > 0) {
                    bitsCount[i] += (numberNumber & 1);
                    numberNumber >>>= 1;
                }
            }

            long res = 0;
            int xor;
            for (int i = 0; i < MAX; i++) {
                if (count[i] >= 1) {
                    for (int j = i; j < MAX; j++) {
                        if (count[j] >= 1) {
                            xor = i ^ j;
                            if (bitsCount[xor] == k) {
                                if (i == j) res += (((long) count[j] * (count[j] - 1)) / 2L);
                                else res += ((long) count[i] * count[j]);
                            }
                        }
                    }
                }
            }
            out.print(res);
        }

    }

    static class InputReader {
        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        public InputReader(InputStream in) {
            reader = new BufferedReader(new InputStreamReader(in));
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(readLine());
            }
            return tokenizer.nextToken();
        }

        public String readLine() {
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return line;
        }

    }
}

