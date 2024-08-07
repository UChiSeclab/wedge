import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author /\
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskA solver = new TaskA();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskA {
        public void solve(int testNumber, Scanner in, PrintWriter out) {
            int n = in.nextInt();
            int[] sushi = in.nextIntArray(n);
            List<Integer> cnt = new ArrayList<>();
            int prev = sushi[0];
            int c = 0;
            for (int i = 0; i < n; i++) {
                if (sushi[i] == prev) {
                    c++;
                } else {
                    cnt.add(c);
                    prev = sushi[i];
                    c = 1;
                }
            }
            cnt.add(c);
            if (cnt.size() == 1) {
                out.println(0);
                return;
            }
            if (cnt.size() == 2) {
                Collections.sort(cnt);
                out.println(2 * cnt.get(0));
                return;
            }
            int max = 0;
            for (int i = 1; i < cnt.size() - 1; i++) {
                max = Math.max(max, cnt.get(i) >= cnt.get(i - 1) || cnt.get(i) >= cnt.get(i + 1) ? cnt.get(i) : 0);
            }
            out.println(2 * max);
        }

    }

    static class Scanner {
        private StringTokenizer st;
        private BufferedReader br;

        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public Scanner(String s) {
            try {
                br = new BufferedReader(new FileReader(s));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public int[] nextIntArray(int n) {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = nextInt();
            }
            return arr;
        }

    }
}

