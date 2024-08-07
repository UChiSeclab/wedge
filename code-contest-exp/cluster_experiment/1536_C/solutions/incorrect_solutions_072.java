import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        CDilucAndKaeya solver = new CDilucAndKaeya();
        solver.solve(1, in, out);
        out.close();
    }

    static class CDilucAndKaeya {
        int n;
        String s;

        public void readInput(Scanner sc) {
            n = sc.nextInt();
            s = sc.next();
        }

        public void solve(int testNumber, Scanner sc, PrintWriter pw) {
            int tc = sc.nextInt();
            while (tc-- > 0) {
                readInput(sc);
                long cd = 0, ck = 0;
                ArrayList<Long> ans = new ArrayList<>();
                HashMap<Long, Long> map = new HashMap<>();
                long cod = 0, cok = 0;
                for (int i = 0; i < n; i++) {
                    if (s.charAt(i) == 'D') cod++;
                    else cok++;
                    cd = Math.min(cod, cok);
                    ck = Math.max(cod, cok);
                    long temp = (cd * n) + ck;

                    long gcd = gcd(cd, ck);

                    if (gcd == 1) {
                        ans.add(1l);
                    } else {
                        long temp2 = cd / gcd;
                        long temp3 = ck / gcd;
                        long temp4 = temp2 * (gcd - 1);
                        long temp5 = temp3 * (gcd - 1);
                        long temp6 = (temp4 * n) + temp5;
                        if (map.containsKey(temp6)) {
                            ans.add(map.get(temp6) + 1);
                        } else ans.add(1l);
                    }
                    map.put(temp, ans.get(ans.size() - 1));
                }
                for (int i = 0; i < ans.size(); i++) {
                    pw.print(ans.get(i) + " ");
                }
                pw.println();
            }
        }

        private long gcd(long a, long b) {
            if (b == 0)
                return a;
            return gcd(b, a % b);
        }

    }

    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public String next() {
            try {
                while (st == null || !st.hasMoreTokens())
                    st = new StringTokenizer(br.readLine());
                return st.nextToken();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

