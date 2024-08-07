import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.io.*;

public class Main {
    public class pair {
        int a, colv;
    }

    public void solve() throws IOException {
        int n = nextInt();
        int k = nextInt();
        int[] q = new int[n];
        int[] s = new int[100001];
        s[1] = 1;
        for (int i = 0; i < n; i++) {
            q[i] = nextInt();
        }
        Arrays.sort(q);
        ArrayList<pair> w = new ArrayList<>();
        int x = 1;
        int a = q[0];
        for (int i = 1; i < n; i++) {
            if (q[i] == a) {
                x++;
            } else {
                pair p = new pair();
                p.a = a;
                p.colv = x;
                w.add(p);
                x = 1;
                a = q[i];
            }
        }
        pair p = new pair();
        p.a = a;
        p.colv = x;
        w.add(p);
        for (int i = 2; i < s.length; i++) {
            if (i % 2 == 0) {
                s[i] = s[i / 2];
            } else {
                s[i] = s[i / 2] + 1;
            }
        }
        long t = 0;
        if (k == 0) {
            for (int i = 0; i < w.size(); i++) {
                t += (long) w.get(i).colv * (long) (w.get(i).colv - 1) / 2;
            }
        } else {
            for (int i = 0; i < w.size(); i++) {
                for (int j = i + 1; j < w.size(); j++) {
                    if (s[w.get(i).a ^ w.get(j).a] == k) {
                        t += (long) w.get(i).colv * (long) w.get(j).colv;
                    }
                }
            }
        }
        out.print(t);
    }

    BufferedReader br;
    StringTokenizer sc;
    PrintWriter out;

    public String nextToken() throws IOException {
        while (sc == null || !sc.hasMoreTokens()) {
            try {
                sc = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                return null;
            }
        }
        return sc.nextToken();
    }

    public Integer nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new Main().run();
    }

    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(System.out);
            solve();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}