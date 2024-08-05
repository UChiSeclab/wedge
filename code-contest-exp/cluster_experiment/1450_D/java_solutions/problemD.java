import com.sun.source.tree.Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class problemD {
    static class Solution {
        void solve() {
            int n = fs.nextInt();
            int[] a = fs.readArray(n);

            TreeSet[] indices = new TreeSet[n+1];
            for (int i = 0 ; i <= n ; i ++) indices[i] = new TreeSet();

            for (int i = 0 ; i < n ; i ++ ) indices[ a[i] ].add(i);

            char[] ans = new char[n];
            Arrays.fill(ans, '0');

            boolean first = true;
            for (int i = 1; i <= n ; i ++ ) if (indices[i].size() != 1) {
                first = false;
                break;
            }

            ans[n-1] = indices[1].isEmpty() ? '0' : '1';
            ans[0] = first ? '1' : '0';

            int elem = 1, s = 0, e = n -1;
            for (int k = n-2; k >= 1 ; k --) {
                int count = 0;
                count += a[s] == elem ? 1 : 0;
                count += a[e] == elem ? 1 : 0;
                if (count == 1) {
                    if (a[s] == elem) {
                        indices[elem].remove(s);
                        s++;
                    } else {
                        indices[elem].remove(e);
                        e--;
                    }
                    if (!indices[elem].isEmpty()) {
                        break;
                    }
                    elem++;
                    if (indices[elem].isEmpty()) {
                        break;
                    }
                    ans[k] = '1';
                } else {
                    break;
                }
            }

            out.println(ans);
        }
    }

    public static void main(String[] args) throws Exception {
        int T = 1;
        T = fs.nextInt();
        Solution solution = new Solution();
        for (int t = 0; t < T; t++) solution.solve();
        out.close();
    }

    static void debug(Object... O) {
        System.err.println("DEBUG: " + Arrays.deepToString(O));
    }

    private static FastScanner fs = new FastScanner();
    private static PrintWriter out = new PrintWriter(System.out);

    static class FastScanner { // Thanks SecondThread.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer("");

        String next() {
            while (!st.hasMoreTokens())
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        int[] readArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = nextInt();
            return a;
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextString() {
            return next();
        }
    }
}
