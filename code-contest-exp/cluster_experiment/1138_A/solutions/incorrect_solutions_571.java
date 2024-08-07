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
            int a = 0;
            int b = 0;
            for (int i = 0; i < n; i++) {
                array[i] = in.nextInt();
                if (array[i] == 1) a++;
                else b++;
            }
            if (a > b)
                out.println(check(array, 2) * 2);
            else {
                out.println(check(array, 1) * 2);

            }


        }

        static long check(int array[], int k) {
            long count = 0;
            long check = 0;
            long x = 0;
            List<Long> array_1 = new LinkedList<>();

            for (long i = 0; i < array.length - 1; i++) {

                if (array[(int) i] == k) {

                    if (array[(int) i] == array[(int) (i + 1)]) {
                        //++count;
                        array_1.add(i);
                        //x = i;

                    }
                    //++check;

                }
            }

            return array_1.size() + 1;
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

