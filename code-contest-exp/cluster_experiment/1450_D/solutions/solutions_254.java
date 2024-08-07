import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.util.Comparator;
import java.util.Collections;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
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

    static class TaskD {
        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int T = in.nextInt();
            while (T-- > 0) {
                int N = in.nextInt();
                int[] arr = new int[N];
                ArrayList<Integer> perm = new ArrayList<>();
                for (int i = 0; i < N; i++) {
                    arr[i] = in.nextInt() - 1;
                    perm.add(i);
                }
                Collections.sort(perm, new Comparator<Integer>() {

                    public int compare(Integer integer, Integer t1) {
                        return Integer.compare(arr[integer], arr[t1]);
                    }
                });


                TreeSet<Integer> set = new TreeSet<Integer>();
                set.add(-1);
                set.add(N);
                ArrayList<Integer>[] solo = new ArrayList[N];
                int[][] h = new int[N][2];
                for (int i = 0; i < N; i++) {
                    Arrays.fill(h[i], -1);
                    solo[i] = new ArrayList<>();
                }

                for (int i : perm) {
                    int low = set.lower(i);
                    int high = set.higher(i);
                    if (i == low + 1 || i == high - 1) {
                        if ((high - 1) - (low + 1) + 1 > h[arr[i]][0]) {
                            h[arr[i]][1] = h[arr[i]][0];
                            h[arr[i]][0] = (high - 1) - (low + 1) + 1;
                        }
                    } else {
                        solo[arr[i]].add((high - 1) - (low + 1) + 1);
                    }
                    set.add(i);
                }
                for (int i = 0; i < N; i++) {
                    int max = -1;
                    for (int e : solo[i]) {
                        max = Math.max(max, e);
                    }
                    int e = max;
                    if (h[i][1] <= e && e <= h[i][0]) {
                        h[i][1] = e;
                    } else if (e == h[i][1] - 1) {
                        h[i][1]--;
                    } else if (e > h[i][0]) {
                        h[i][0] = e;
                        h[i][1] = e;
                    }
                }
                int lb = -1;
                int hb = N;
                char[] ans = new char[N];
                boolean[] bad = new boolean[N + 1];
                Arrays.fill(ans, '0');
                ans[0] = '1';
                for (int i = 0; i < N; i++) {
                    if (i != arr[perm.get(i)]) {
                        ans[0] = '0';
                    }
                    int K = (N - i);
                    lb = Math.max(h[i][1], lb);
                    hb = Math.min(h[i][0], hb);
                    if (lb <= K && K <= hb) {
                        if (!bad[K]) {
                            ans[K - 1] = '1';
                        }
                    }
                }
                out.println(new String(ans));
            }
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

