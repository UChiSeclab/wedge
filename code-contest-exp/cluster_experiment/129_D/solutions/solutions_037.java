import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        r94D solver = new r94D();
        solver.solve(1, in, out);
        out.close();
    }

    static class r94D {
        public void solve(int testNumber, FastScanner in, PrintWriter out) {
            String s = in.ns();

            int k = in.ni();
            int n = s.length() + 1;
            int[] p = new int[n];
            int[] c = new int[n];
            s = s + "|";
            StringUtil.suffixArray(s, p, c);

//        System.out.println("Arrays.toString(p) = " + Arrays.toString(p));
//        System.out.println("Arrays.toString(c) = " + Arrays.toString(c));
            int[] used = new int[n];
            int ai = -1;
            int li = -1;
            for (int i = 0; i < n - 1 && k > 0; i++) {
                int maxlen = n - 1 - p[i];
//            System.out.println("maxlen = " + maxlen);
                for (int len = used[i] + 1; len <= maxlen && k > 0; len++) {
                    for (int j = i; j < n - 1; j++) {
//                    System.out.println("len = " + len);
//                    System.out.println("j = " + j);
//                    System.out.println("s.substring(ai, ai+li) = " + s.substring(p[j], p[j] + len));
                        k--;
                        if (k == 0) {
                            ai = p[j];
                            li = len;
                            break;
                        }
                        used[j]++;
                        if (j >= n - 2 || used[j + 1] != used[j] - 1 || s.charAt((p[j] + len - 1) % n) != s.charAt((p[j + 1] + len - 1) % n)) {
                            break;
                        }
                    }
                }
            }
            if (ai == -1) {
                out.println("No such line.");
            } else {
                out.println(s.substring(ai, ai + li));
            }

        }

    }

    static class FastScanner {
        private BufferedReader in;
        private StringTokenizer st;

        public FastScanner(InputStream stream) {
            in = new BufferedReader(new InputStreamReader(stream));
        }

        public String ns() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    String rl = in.readLine();
                    if (rl == null) {
                        return null;
                    }
                    st = new StringTokenizer(rl);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        public int ni() {
            return Integer.parseInt(ns());
        }

    }

    static class StringUtil {
        public static void suffixArray(String s, int[] pres, int[] cres) {
            int n = s.length();
            int abc = 256;
            int[] p = new int[n], cnt = new int[Math.max(n, abc)], c = new int[n];
            for (int i = 0; i < n; i++) {
                cnt[s.charAt(i)]++;
            }
            for (int i = 1; i < abc; i++) {
                cnt[i] += cnt[i - 1];
            }
            for (int i = 0; i < n; i++) {
                p[--cnt[s.charAt(i)]] = i;
            }
            int classes = 1;
            for (int i = 1; i < n; i++) {
                if (s.charAt(p[i]) != s.charAt(p[i - 1]))
                    classes++;
                c[p[i]] = classes - 1;
            }

            int[] pn = new int[n];
            int[] cn = new int[n];
            for (int log = 0; (1 << log) < n; log++) {
                int len = 1 << log;
                for (int i = 0; i < n; i++) {
                    pn[i] = p[i] - len;
                    if (pn[i] < 0) pn[i] += n;
                }
                Arrays.fill(cnt, 0);
                for (int i = 0; i < n; i++) {
                    cnt[c[pn[i]]]++;
                }
                for (int i = 1; i < classes; i++) {
                    cnt[i] += cnt[i - 1];
                }
                for (int i = n - 1; i >= 0; i--) {
                    p[--cnt[c[pn[i]]]] = pn[i];
                }
                cn[p[0]] = 0;
                classes = 1;
                for (int i = 1; i < n; i++) {
                    if (c[p[i]] != c[p[i - 1]] || c[(p[i] + len) % n] != c[(p[i - 1] + len) % n])
                        classes++;
                    cn[p[i]] = classes - 1;
                }
                System.arraycopy(cn, 0, c, 0, n);
            }
            System.arraycopy(p, 0, pres, 0, n);
            System.arraycopy(c, 0, cres, 0, n);

        }

    }
}

