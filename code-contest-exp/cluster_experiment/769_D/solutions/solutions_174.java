//package s;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class B {
    FastScanner in;
    PrintWriter out;

    int n, k;
    long ans = 0;
    int mas[];
    long an[][][] = new long[10001][16][15];

    long ans(int number, int bit, int iz) {
        if (bit > 15) return 0;
        if (iz > k) return 0;
        if (number < 10001 && an[number][bit][iz] != -1) {
            if (k != 0 || bit != 15)
                return an[number][bit][iz];
            return an[number][bit][iz] - 1;
        }
        long ans = 0;
        ans += ans(number, bit + 1, iz);
        ans += ans((number ^ (1 << bit)), bit + 1, iz + 1);
        if(number < 10001)
        	an[number][bit][iz] = ans;
        return ans;
    }

    void solve() {
        n = in.nextInt();
        k = in.nextInt();
        mas = new int[n];
        for (int i = 0; i < 10001; i++) {
            for (int j = 0; j < 15; j++) {
                for (int l = 0; l < 15; l++) {
                    an[i][j][l] = -1;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            mas[i] = in.nextInt();
            if (an[mas[i]][15][k] == -1)
                an[mas[i]][15][k]++;
            an[mas[i]][15][k]++;
        }
        for (int i = 0; i < n; i++) {
            ans += (ans(mas[i], 0, 0));
        }
        System.out.println(ans / 2);
    }

    void ran() {
        in = new FastScanner();
        try {
            out = new PrintWriter(new File("volley.out"));
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new B().ran();
    }


    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}