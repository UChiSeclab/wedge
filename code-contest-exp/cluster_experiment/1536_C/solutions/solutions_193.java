import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.TreeMap;
import java.util.StringTokenizer;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
                TreeMap<long[], Long> map = new TreeMap<>((a, b) -> Long.compare(a[0], b[0]) == 0 ? Long.compare(a[1], b[1]) : Long.compare(a[0], b[0]));
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == 'D') cd++;
                    else ck++;
                    long temp = gcd(cd, ck);
                    long temp2 = cd / temp;
                    long temp3 = ck / temp;
                    long val = map.getOrDefault(new long[]{temp2, temp3}, 0l);
                    pw.print(val + 1 + " ");
                    map.put(new long[]{temp2, temp3}, val + 1);
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

