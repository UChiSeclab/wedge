import java.io.*;
import java.util.*;

public class main {
    static int prefix2[];
    static int prefix1[];
    static boolean check(int m, int y[]) {
        for (int i = 0; i < y.length - 2 * m; i++) {
            int firstHalf = y[i];
            int secondHalf = y[i + m];
            if (firstHalf != secondHalf) {
                int countF = 0;
                int countS = 0;
                if (firstHalf == 1) {
                    countF = prefix1[i + m] - prefix1[i];
                    countS = prefix2[i + 2 * m] - prefix2[i + m];
                } else {
                    countF = prefix2[i + m] - prefix2[i];
                    countS = prefix1[i + 2 * m] - prefix1[i + m];
                }
                if (countF == countS && countS == m) {
                    return true;
                }
            }
        }
        return false;
    }
    public static void main(String[] args) throws IOException {
        sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        int n = nextInt();
        int y[] = new int[n];
        prefix1 = new int[n + 1];
        prefix2 = new int[n + 1];
        for (int i = 0; i < n; i++) {
            y[i] = nextInt();
            prefix2[i + 1] = prefix2[i];
            prefix1[i + 1] = prefix1[i];
            if (y[i] == 1) {
                prefix1[i + 1]++;
            } else {
                prefix2[i + 1]++;
            }
        }
        int l = 1;
        int r = n / 2 + 1;
        while (l != r - 1) {
            int m = (l + r) / 2;
            if (check(m, y)) {
                l = m;
            } else {
                r = m;
            }
        }
        pw.println(l * 2);
        pw.close();
    }
    static StringTokenizer st;
    static BufferedReader sc;
    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(sc.readLine());
        }
        return st.nextToken();
    }
    static int nextInt() throws IOException{
        return Integer.parseInt(next());
    }
    static long nextLong() throws IOException{
        return Long.parseLong(next());
    }
}