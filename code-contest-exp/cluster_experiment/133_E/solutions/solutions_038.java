import java.io.*;
import java.util.StringTokenizer;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Artem Gilmudinov
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream;
        inputStream = System.in;
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
            int mid = n + 1;
            int cnt = 2 * n + 4;
            boolean[][][] dp = new boolean[cnt][m + 1][2]; //0-left, 1-right
            boolean[][][] prev = new boolean[cnt][m + 1][2];
            prev[mid][0][1] = true;
            int temp = 0;
            int dir = 0;
            for (int i = 0; i < n; i++) {
                for (int j = -n; j <= n; j++) {
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
                for (int j = 0; j < cnt; j++) {
                    for (int z = 0; z <= m; z++) {
                        for (int k = 0; k < 2; k++) {
                            prev[j][z][k] = dp[j][z][k];
                            dp[j][z][k] = false;
                        }
                    }
                }
            }
            int ans = 0;
            for (int i = -n; i <= n; i++) {
                for (int j = 0; j < 2; j++) {
                    if (prev[i + mid][m][j]) {
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

