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
 * @author Twinkle Twinkle Little Star
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        ASushiForTwo solver = new ASushiForTwo();
        solver.solve(1, in, out);
        out.close();
    }

    static class ASushiForTwo {
        public void solve(int testNumber, InputReader in, PrintWriter out) {

            int n = in.nextInt();
            int array[] = new int[n];
            int dd = 0;
            int ff = 0;
            for (int i = 0; i < n; i++) {
                array[i] = in.nextInt();
                if (array[i] == 1) dd++;
                else if (array[i] == 2) ff++;
            }
            int count = 0;
            for (int i = 0; i < n - 1; i++) {
                if (array[i] >= array[i + 1]) {

                    count++;
                    if (count == n / 2 + 1)
                        break;
                }
            }
            if (count % 2 == 0)
                out.println(count);
            else out.println(count + 1);
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

