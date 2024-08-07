import java.io.*;
import java.util.*;

/*
───────────────■■■─────────────────────────
──█▄─▄█─▄▀▀▀▄─█──▄█─────█──▄▀─▄▀▀▀▄─▀▀█▀▀──
──█─▀─█─█───█─█─█─█─────█■█───█───█───█────
──█───█─▀▄▄▄▀─█▀──█─────█──▀▄─▀▄▄▄▀───█────
───────────────────────────────────────────
*/

public class Main {

    static FastReader in;
    static PrintWriter out;
    static Random rand = new Random();
    static int n;
    static int[] a;
    static ArrayList<Integer>[] g;
    static LinkedList<Integer> q0, q1;
    static int[] ans;

    static void solve() {
        n = in.nextInt();

        a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());

        ans = new int[n];
        Arrays.fill(ans, -1);

        q0 = new LinkedList<>();
        q1 = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (i - a[i] >= 0) {
                int j = i - a[i];
                if ((a[i] + a[j]) % 2 == 1) {
                    if (a[i] % 2 == 0) {
                        q0.addLast(i);
                    } else {
                        q1.addLast(i);
                    }
                    ans[i] = 1;
                }
                g[j].add(i);
            }
            if (i + a[i] < n) {
                int j = i + a[i];
                if ((a[i] + a[j]) % 2 == 1) {
                    if (a[i] % 2 == 0) {
                        q0.addLast(i);
                    } else {
                        q1.addLast(i);
                    }
                    ans[i] = 1;
                }
                g[j].add(i);
            }
        }

        while (!q0.isEmpty()) {
            int v = q0.pollFirst();
            for (int u : g[v]) {
                if (ans[u] == -1) {
                    q0.addLast(u);
                    ans[u] = ans[v] + 1;
                }
            }
        }

        while (!q1.isEmpty()) {
            int v = q1.pollFirst();
            for (int u : g[v]) {
                if (ans[u] == -1) {
                    q1.addLast(u);
                    ans[u] = ans[v] + 1;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            out.print(ans[i]);
            out.print(' ');
        }
        out.print('\n');
    }

    public static void main(String[] args) {
        in = new FastReader(System.in);
//        in = new FastReader(new FileInputStream("input.txt"));
        out = new PrintWriter(System.out);
//        out = new PrintWriter(new FileOutputStream("output.txt"));


        int q = 1;

        while (q-- > 0) {
            solve();
        }

        out.close();
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        FastReader(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }

        Integer nextInt() {
            return Integer.parseInt(next());
        }

        Long nextLong() {
            return Long.parseLong(next());
        }

        Double nextDouble() {
            return Double.parseDouble(next());
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                st = new StringTokenizer(nextLine());
            }
            return st.nextToken();
        }

        String nextLine() {
            String s = "";
            try {
                s = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }
    }
}