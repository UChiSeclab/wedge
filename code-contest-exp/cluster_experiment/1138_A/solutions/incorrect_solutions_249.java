import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.LinkedList;
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
            for (int i = 0; i < n; i++) {
                array[i] = in.nextInt();
            }
            //printSubsets(array);
            //Arrays.sort(array);
            long count = 0;
            long check = 0;
            long x = 0;
            List<Long> array_1 = new LinkedList<>();
            for (long i = 0; i < n; i++) {

                if (array[(int) i] == 1) {

                    if (check == 0 || x + 1 == i) {
                        //++count;
                        array_1.add(i);
                        x = i;

                    }
                    ++check;

                }
            }
            out.println(array_1.size() * 2);
            ///if (b == 0)
            // out.println(2);
            ///else
            //out.println(a*2);

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

