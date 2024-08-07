import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static java.lang.Math.max;

public class F {

    static FastIn in;

    static boolean DO_TEST = false;
    static {
        if (DO_TEST) {
            String t1 = "7 6\n" +
                    "2 5 7 10 14 15 17\n" +
                    "1 3 10 0\n" +
                    "1 7 12 7\n" +
                    "4 5 13 3\n" +
                    "4 7 10 1\n" +
                    "4 7 10 1\n" +
                    "1 5 11 2";
            System.out.println("INPUT:");
            System.out.println(t1);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(t1.getBytes());
            in = FastIn.wrapInputStream(byteArrayInputStream);
            System.out.println("OUTPUT:");
        } else {
            in = FastIn.wrapSystemIn();
        }
    }

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        int[] a = in.nextIntArrayOfSize(n);

        int[] start = new int[m];
        int[] finish = new int[m];
        long[] cost = new long[m];
        int[] segments = new int[m];

        for (int i = 0; i < m; i++) {
            start[i] =  in.nextInt() - 1;
            finish[i] = in.nextInt() - 1;
            cost[i] = in.nextLong();
            segments[i] = in.nextInt();
        }

        ArrayList<ArrayList<Integer>> ids = new ArrayList<>(m);
        for (int i = 0; i < n; i++) {
            ids.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            ids.get(start[i]).add(i);
        }

        int[][] dp = new int[n + 1][n];


        long v = Integer.MIN_VALUE;
        for (int l = 0; l < n; l++) {
            for (int r = l; r < n; r++) {
                dp[0][r] = a[r] - a[l];
            }

            for (int k = 1; k < n + 1; k++) {
                int j = l;
                for (int r = l; r < n; r++) {
                    while (j + 1 < n && max(dp[k - 1][j], a[r] - a[j]) >= max(dp[k - 1][j + 1], a[r] - a[j + 1])) {
                        j++;
                    }
                    dp[k][r] = max(dp[k - 1][j], a[r] - a[j]);
                }
            }

            for (int id: ids.get(l)) {
                v = max(v, dp[segments[id]][finish[id]] * cost[id]);
            }
        }


       System.out.println(v);
    }



    static final class FastIn {
            private final BufferedReader bufferedReader;
            private StringTokenizer stringTokenizer;

            static FastIn wrapInputStream(InputStream inputStream) {
                return new FastIn(inputStream);
            }

            static FastIn wrapSystemIn() {
                return wrapInputStream(System.in);
            }

            private FastIn(InputStream inputStream) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                this.bufferedReader = new BufferedReader(inputStreamReader);
            }

            int nextInt() {
                return Integer.parseInt(nextToken());
            }

            long nextLong() {
                return Long.parseLong(nextToken());
            }

            String nextString() {
                return nextToken();
            }

            int[] nextIntArrayOfSize(int size) {
                int[] array = new int[size];
                for (int i = 0; i < size; i++) {
                    array[i] = nextInt();
                }
                return array;
            }

            long[] nextLongArrayOfSize(int size) {
                long[] array = new long[size];
                for (int i = 0; i < size; i++) {
                    array[i] = nextLong();
                }
                return array;
            }

            private String nextToken() {
                try {
                    skipUntilNextTokenOrEndOfStream();
                    assert hasNextToken();
                    return stringTokenizer.nextToken();
                } catch (IOException ioException) {
                    throw new RuntimeException(ioException);
                }
            }

            boolean hasNextToken() throws IOException {
                skipUntilNextTokenOrEndOfStream();
                return stringTokenizer.hasMoreTokens();
            }

            private void skipUntilNextTokenOrEndOfStream() throws IOException {
                while (stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
                    String nextLine = bufferedReader.readLine();
                    if (nextLine == null) {
                        return;
                    }
                    this.stringTokenizer = new StringTokenizer(nextLine);
                }
            }
        }
}
