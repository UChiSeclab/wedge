import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Queue;
import java.io.BufferedReader;
import java.util.LinkedList;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Jaynil
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskE solver = new TaskE();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskE {
        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int a[] = new int[n];
            for (int i = 0; i < n; i++) a[i] = in.nextInt();
            boolean eset[] = new boolean[n];
            int even[] = new int[n];
            boolean oset[] = new boolean[n];
            int odd[] = new int[n];
            Arrays.fill(even, Integer.MAX_VALUE);
            Queue<Integer[]> q = new LinkedList<>();
            ArrayList<Integer> g[] = new ArrayList[n];
            for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (i - a[i] >= 0) g[i - a[i]].add(i);
                if (i + a[i] < n) g[i + a[i]].add(i);
            }
            for (int i = 0; i < n; i++) {
                if (a[i] % 2 == 0) {
                    even[i] = 0;
                    q.add(new Integer[]{i, 0});
                }
            }
            while (q.size() > 0) {
                Integer temp[] = q.poll();
                int i = temp[0];
                int val = temp[1];
                if (eset[i]) continue;
                eset[i] = true;
                even[i] = val;
                for (int x : g[i]) {
                    if (!eset[x])
                        q.add(new Integer[]{x, even[i] + 1});
                }
            }
            q = new LinkedList<>();

            for (int i = 0; i < n; i++) {
                if (a[i] % 2 != 0) {
                    odd[i] = 0;
                    q.add(new Integer[]{i, 0});
                }
            }
            while (q.size() > 0) {
                Integer temp[] = q.poll();
                int i = temp[0];
                int val = temp[1];
                if (oset[i]) continue;
                oset[i] = true;
                odd[i] = val;
                for (int x : g[i]) {
                    if (!oset[x])
                        q.add(new Integer[]{x, odd[i] + 1});
                }
            }

//        for(int i=0;i<n;i++){
//            out.print(even[i]+" ");
//        }
//        out.println();
//        for(int i=0;i<n;i++){
//            out.print(odd[i]+" ");
//        }
//        out.println();
            for (int i = 0; i < n; i++) {
                if (even[i] == Integer.MAX_VALUE || even[i] == 0) even[i] = -1;
                if (odd[i] == Integer.MAX_VALUE || odd[i] == 0) odd[i] = -1;
                if (a[i] % 2 == 0) out.print(odd[i] + " ");
                else out.print(even[i] + " ");
            }
            out.println();
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


