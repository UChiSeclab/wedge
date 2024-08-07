import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    FastScanner in;
    PrintWriter out;

    Map<Integer, Set<Integer>> good = new HashMap<>();
    Map<Integer, Integer> a = new HashMap<>();
    Set<Integer> res;
    int q;

    char invertBit(char c) {
        if (c == '0') {
            return '1';
        }

        return '0';
    }

    Set<Integer> getGood(int x, int k) {
        res = new HashSet<>();
        String s = Integer.toString(x, 2);
        int n = s.length();

        if (k > n) {
            return res;
        }

        genAll(new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, -1, k, s);
        return res;
    }

    void genAll(int[] a, int prev, int k, String s) {
        if (q == k) {
            StringBuilder t = new StringBuilder();
            int j = 0;

            for (int i = 0; i < s.length(); i++) {
                if (a[j] == i) {
                    j++;
                    t.append(invertBit(s.charAt(i)));
                } else {
                    t.append(s.charAt(i));
                }
            }

            res.add(Integer.parseInt(t.toString(), 2));
            return;
        }

        for (int i = prev + 1; i < k; i++) {
            q++;
            a[q - 1] = i;
            prev = i;
            genAll(a, prev, k, s);
            q--;
        }
    }

    public void solve() throws IOException {
        int n = in.nextInt();
        int k = in.nextInt();

        for (int i = 0; i < n; i++) {
            int x = in.nextInt();

            if (a.containsKey(x)) {
                a.put(x, a.get(x) + 1);
                continue;
            }

            a.put(x, 1);
            good.put(x, getGood(x, k));
        }

        long ans[] = new long[1];

        a.forEach((k1, v1) -> {
            Set<Integer> allGood = good.get(k1);

            allGood.forEach(val -> {
                if (k1.equals(val)) {
                    ans[0] += (v1 - 1) * v1 / 2;
                } else if (a.containsKey(val)) {
                    ans[0] += a.get(val);
                }
            });
        });

        out.println(ans[0]);
    }

    public void run() {
        try {
//            in = new FastScanner(new File("d.in"));
//            out = new PrintWriter(new File("d.out"));

            in = new FastScanner();
            out = new PrintWriter(new OutputStreamWriter(System.out));

            solve();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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

    public static void main(String[] arg) {
        new Main().run();
    }
}