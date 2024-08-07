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
 *
 * @author Washoum
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        inputClass in = new inputClass(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        EBoxers solver = new EBoxers();
        solver.solve(1, in, out);
        out.close();
    }

    static class EBoxers {
        public void solve(int testNumber, inputClass sc, PrintWriter out) {
            int n = sc.nextInt();
            boolean[] taken = new boolean[150002];
            Integer[] tab = new Integer[n];
            for (int i = 0; i < n; i++) {
                tab[i] = sc.nextInt();
            }
            Arrays.sort(tab);
            for (int i = n - 1; i >= 0; i--) {
                if (!taken[tab[i] + 1]) {
                    taken[tab[i] + 1] = true;
                    continue;
                }
                if (!taken[tab[i]]) {
                    taken[tab[i]] = true;
                    continue;
                }
                if (tab[i] > 1) {
                    if (!taken[tab[i] - 1]) {
                        taken[tab[i] - 1] = true;
                    }
                }
            }
            int ans = 0;
            for (int i = 0; i < 150002; i++) {
                if (taken[i])
                    ans++;
            }
            out.println(ans);
        }

    }

    static class inputClass {
        BufferedReader br;
        StringTokenizer st;

        public inputClass(InputStream in) {

            br = new BufferedReader(new InputStreamReader(in));
        }

        public String next() {
            while (st == null || !st.hasMoreElements()) {
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

    }
}

