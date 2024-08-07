import java.io.*;
import java.nio.CharBuffer;
import java.util.*;

public class P1203E {

    public static void main(String[] args) {
        SimpleScanner scanner = new SimpleScanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);

        int n = scanner.nextInt();
        HashMap<Integer, Integer> weightMap = new HashMap<>(n);
        for (int i = 0; i < n; ++i) {
            int w = scanner.nextInt();
            weightMap.compute(w, (k, v) -> v == null ? 1 : v + 1);
        }
        ArrayList<int[]> weightList = new ArrayList<>(weightMap.size());
        for (Map.Entry<Integer, Integer> entry : weightMap.entrySet()) {
            weightList.add(new int[]{entry.getKey(), entry.getValue()});
        }
        weightList.sort(Comparator.comparingInt(o -> o[0]));
        int max = 0;
        int ans = 0;
        for (int[] v : weightList) {
            int weight = v[0];
            int num = v[1];
            if (num == 1) {
                if (weight - 1 > max) {
                    ++ans;
                    max = weight - 1;
                } else if (weight > max) {
                    ++ans;
                    max = weight;
                } else {
                    ++ans;
                    max = weight + 1;
                }
            } else if (num == 2) {
                if (weight - 1 > max) {
                    ans += 2;
                    max = weight;
                } else if (weight > max) {
                    ans += 2;
                    max = weight + 1;
                } else {
                    ans += 1;
                    max = weight + 1;
                }
            } else {
                if (weight - 1 > max) {
                    ans += 3;
                } else if (weight > max) {
                    ans += 2;
                } else {
                    ans += 1;
                }
                max = weight + 1;
            }
        }
        writer.println(ans);
        writer.close();
    }

    private static class SimpleScanner {

        private static final int BUFFER_SIZE = 10240;

        private Readable in;
        private CharBuffer buffer;
        private boolean eof;

        SimpleScanner(InputStream in) {
            this.in = new BufferedReader(new InputStreamReader(in));
            buffer = CharBuffer.allocate(BUFFER_SIZE);
            buffer.limit(0);
            eof = false;
        }


        private char read() {
            if (!buffer.hasRemaining()) {
                buffer.clear();
                int n;
                try {
                    n = in.read(buffer);
                } catch (IOException e) {
                    n = -1;
                }
                if (n <= 0) {
                    eof = true;
                    return '\0';
                }
                buffer.flip();
            }
            return buffer.get();
        }

        void checkEof() {
            if (eof)
                throw new NoSuchElementException();
        }

        char nextChar() {
            checkEof();
            char b = read();
            checkEof();
            return b;
        }

        String next() {
            char b;
            do {
                b = read();
                checkEof();
            } while (Character.isWhitespace(b));
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(b);
                b = read();
            } while (!eof && !Character.isWhitespace(b));
            return sb.toString();
        }

        int nextInt() {
            return Integer.valueOf(next());
        }

        long nextLong() {
            return Long.valueOf(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}
