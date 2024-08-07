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
 * @author Artem Gilmudinov
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        Reader in = new Reader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskE solver = new TaskE();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskE {
        public void solve(int testNumber, Reader in, PrintWriter out) {
            String s = in.rl();
            int n = s.length();
            int m = in.ni();
            int mid = 125;
            boolean[][][] dp = new boolean[250][m + 1][2]; //0-left, 1-right
            boolean[][][] prev = new boolean[250][m + 1][2];
            prev[mid][0][1] = true;
            int temp = 0;
            int dir = 0;
            for (int i = 0; i < n; i++) {
                for (int j = -101; j <= 101; j++) {
                    temp = j + mid;
                    for (int z = 0; z <= m; z++) {
                        for (int d = 0; d < 2; d++) {
                            if (d == 0) {
                                dir = 1;
                            } else {
                                dir = -1;
                            }
                            if (s.charAt(i) == 'F') {
                                for (int k = 0; k <= z; k++) {
                                    if (k % 2 == 0) {
                                        dp[temp][z][d] |= prev[temp + dir][z - k][d];
                                    } else {
                                        dp[temp][z][d] |= prev[temp][z - k][1 - d];
                                    }
                                }
                            } else {
                                for (int k = 0; k <= z; k++) {
                                    if (k % 2 == 0) {
                                        dp[temp][z][d] |= prev[temp][z - k][1 - d];
                                    } else {
                                        dp[temp][z][d] |= prev[temp + dir][z - k][d];
                                    }
                                }
                            }
                        }
                    }
                }
                for (int j = 0; j < 250; j++) {
                    for (int z = 0; z <= m; z++) {
                        for (int k = 0; k < 2; k++) {
                            prev[j][z][k] = dp[j][z][k];
                        }
                    }
                }
            }
            int ans = 0;
            for (int i = -101; i <= 101; i++) {
                for (int j = 0; j < 2; j++) {
                    if (dp[i + mid][m][j]) {
                        ans = Math.max(Math.abs(i), ans);
                    }
                }
            }
            out.println(ans);
        }

    }

    static class Reader {
        private BufferedReader in;
        private StringTokenizer st = new StringTokenizer("");
        private String delim = " ";

        public Reader(InputStream in) {
            this.in = new BufferedReader(new InputStreamReader(in));
        }

        public String next() {
            if (!st.hasMoreTokens()) {
                st = new StringTokenizer(rl());
            }
            return st.nextToken(delim);
        }

        public String rl() {
            try {
                return in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public int ni() {
            return Integer.parseInt(next());
        }

    }
}

