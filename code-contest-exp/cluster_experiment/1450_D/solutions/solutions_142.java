import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastScanner in = new FastScanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskD solver = new TaskD();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskD {
        public void solve(int testNumber, FastScanner in, PrintWriter out) {
            int numTests = in.nextInt();
            for (int test = 0; test < numTests; test++) {
                int n = in.nextInt();
                int[] a = new int[n];
                int[] am = new int[n];
                for (int i = 0; i < n; i++) {
                    a[i] = in.nextInt() - 1;
                    ++am[a[i]];
                }
                boolean perm = true;
                for (int i = 0; i < n; i++) {
                    if (am[i] != 1) {
                        perm = false;
                    }
                }
                int[] prev = new int[n];
                int[] stack = new int[n];
                int sp = 0;
                for (int i = 0; i < n; i++) {
                    while (sp > 0 && a[stack[sp - 1]] >= a[i]) {
                        --sp;
                    }
                    if (sp > 0) {
                        prev[i] = stack[sp - 1];
                    } else {
                        prev[i] = -1;
                    }
                    stack[sp++] = i;
                }
                int[] next = new int[n];
                sp = 0;
                for (int i = n - 1; i >= 0; i--) {
                    while (sp > 0 && a[stack[sp - 1]] > a[i]) {
                        --sp;
                    }
                    if (sp > 0) {
                        next[i] = stack[sp - 1];
                    } else {
                        next[i] = n;
                    }
                    stack[sp++] = i;
                }

                List<Integer>[] events = new List[n + 3];
                for (int i = 0; i < events.length; i++) {
                    events[i] = new ArrayList<>();
                }
                for (int i = 0; i < n; i++) {
                    int id = a[i] + 1;
                    if (prev[i] + 1 == i) {
                        // add to segment [1, next[i] - i]
                        events[1].add(id);
                        events[next[i] - i + 1].add(-id);
                    } else if (i + 1 == next[i]) {
                        // add to segment [1, i - prev[i]]
                        events[1].add(id);
                        events[i - prev[i] + 1].add(-id);
                    } else {
                        // add to point (next[i] - prev[i] - 1)
                        int x = next[i] - prev[i] - 1;
                        events[x].add(id);
                        events[x + 1].add(-id);
                    }
                }

                FenwickTree tree = new FenwickTree(n);
                int[] cur = new int[n];
                char[] ans = new char[n];
                for (int k = 1; k <= n; k++) {
                    for (int id : events[k]) {
                        int i = Math.abs(id) - 1;
                        boolean wasOn = cur[i] == 1;
                        if (id > 0) {
                            ++cur[i];
                        } else {
                            --cur[i];
                        }
                        boolean nowOn = cur[i] == 1;
                        if (wasOn != nowOn) {
                            if (wasOn) {
                                tree.add(i, -1);
                            } else {
                                tree.add(i, 1);
                            }
                        }
                    }
                    int l = 0;
                    int r = n;
                    while (r - l > 1) {
                        int m = (l + r) / 2;
                        if (tree.sum(m - 1) == m) {
                            l = m;
                        } else {
                            r = m;
                        }
                    }
                    ans[k - 1] = (l >= n - k + 1) ? '1' : '0';
                }
                ans[0] = perm ? '1' : '0';
                out.println(new String(ans));
            }
        }

        class FenwickTree {
            int[] a;

            FenwickTree(int n) {
                a = new int[n];
            }

            public void add(int pos, int val) {
                while (pos < a.length) {
                    a[pos] += val;
                    pos |= pos + 1;
                }
            }

            public int sum(int r) {
                int res = 0;
                while (r >= 0) {
                    res += a[r];
                    r = (r & (r + 1)) - 1;
                }
                return res;
            }

        }

    }

    static class FastScanner {
        private BufferedReader in;
        private StringTokenizer st;

        public FastScanner(InputStream stream) {
            in = new BufferedReader(new InputStreamReader(stream));
        }

        public String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(in.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

