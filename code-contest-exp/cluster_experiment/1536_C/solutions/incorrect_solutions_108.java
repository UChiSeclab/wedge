import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Khater
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
        public void solve(int testNumber, Scanner sc, PrintWriter pw) {
            int t = 1;
            t = sc.nextInt();
            while (t-- > 0) {
                int n = sc.nextInt();
                char[] arr = sc.nextLine().toCharArray();
                int[] D = new int[n];
                int[] K = new int[n];
                int d = 0;
                int k = 0;
                for (int i = 0; i < n; i++) {
                    if (arr[i] == 'D') d++;
                    D[i] = d;
                }
                for (int i = 0; i < n; i++) {
                    if (arr[i] == 'K') k++;
                    K[i] = k;
                }
//			pw.println(Arrays.toString(D));
//			pw.println(Arrays.toString(K));
                boolean[] f = new boolean[n + 1];
                int[] ans = new int[n];
                Arrays.fill(ans, 1);
                for (int l = 1; l <= n; l++) {
                    int c = 0;
                    int d1 = D[l - 1];
                    int k1 = K[l - 1];
                    int lastd = 0;
                    int lastk = 0;
                    int inc = d1 / gcd(d1, k1) + k1 / gcd(d1, k1);
                    if (f[inc]) continue;
                    f[inc] = true;
                    loop:
                    for (int j = l - 1; j < n; j += inc) {

                        int newD = D[j] - lastd;
                        int newK = K[j] - lastk;
                        //	pw.println(j+" "+ l+" "+ newD+" "+ d1 +" "+ newK+" "+ k1 +);
                        if (1l * newD * k1 == 1l * newK * d1) {
                            lastd = D[j];
                            lastk = K[j];
                            c++;
                            ans[j] = Math.max(ans[j], c);
                        }
                    }
                }
                for (int x : ans) pw.print(x + " ");
                pw.println();
            }

        }

        int gcd(int a, int b) {
            if (b == 0)
                return a;
            return gcd(b, a % b);
        }

    }

    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(FileReader r) {
            br = new BufferedReader(r);
        }

        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public String nextLine() {
            try {
                return br.readLine();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}

