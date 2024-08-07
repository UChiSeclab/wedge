import java.io.*;
import java.util.*;

public class A_SushiForTwo {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader inp = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Solver solver = new Solver();
        solver.solve(inp, out);
        out.close();
    }

    private static class Solver {
        private void solve(InputReader inp, PrintWriter out) {
            int n = inp.nextInt(), prev = inp.nextInt(), total = 1;
            ArrayList<Integer> a = new ArrayList<>();
            for (int i = 1; i < n; i++) {
                int u = inp.nextInt();
                if (u != prev) {
                    prev  = u;
                    a.add(total);
                    total = 1;
                } else total++;
            }
            a.add(total);
            int res = 0;
            for (int i = 0; i < a.size() - 1; i++) res = Math.max(res, Math.min(a.get(i), a.get(i+1)));
            out.print(res << 1);
        }
    }

    static class InputReader {
        BufferedReader reader;
        StringTokenizer tokenizer;

        InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        String next() {
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
        public long nextLong() {
            return Long.parseLong(next());
        }
    }
}