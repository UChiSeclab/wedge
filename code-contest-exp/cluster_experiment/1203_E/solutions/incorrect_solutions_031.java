import java.io.*;
import java.util.*;

public class Main {
    static final PrintWriter out = new PrintWriter(System.out);
    static class FIO {
        BufferedReader in;
        StringTokenizer st;
        FIO() {
            in = new BufferedReader(new InputStreamReader(System.in));
            st = null;
        }
        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(in.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
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
    }
    static final FIO in = new FIO();
    public static void main(String[] args) {
        int tc = 1;
        while (tc-- > 0) {
            new solver();
        }
    }
    static class solver {
        solver() {
            final int MAXN = 150002;
            int N = in.nextInt();
            int[] oc = new int[MAXN];
            boolean[] poss = new boolean[MAXN];
            for (int i = 0; i < N; ++i)
                ++oc[in.nextInt()];
            
            for (int i = 1; i < MAXN; ++i) {
                if (oc[i] == 0) continue;
                poss[i] = true;
                if (oc[i] >= 2) {
                    if (i - 1 > 0 && !poss[i-1]) poss[i-1] = true;
                    else if (!poss[i+1]) poss[i + 1] = true;
                }
                if (oc[i] >= 3) {
                    if (i - 1 > 0 && !poss[i-1]) poss[i-1] = true;
                    else if (!poss[i+1]) poss[i + 1] = true;
                }
            }
            int res = 0;
            for (int i = 1; i < MAXN; ++i) {
                if (poss[i]) res++;
            }
            out.println(res);
            out.close();
            

        }
    }


}