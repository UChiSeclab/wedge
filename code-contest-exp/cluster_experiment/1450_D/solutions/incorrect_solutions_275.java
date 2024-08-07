import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

    static FastScanner sc;
    static PrintWriter pw;
    static final int INF = 1000000000;
    static final long MOD = 998244353;
    static class A {
        int val; int pos;
        A(int x, int y) {
            this.val = x;
            this.pos = y;
        }

        @Override
        public String toString() {
            return val + " " + pos;
        }
    }

    public static void main(String[] args) throws IOException {
        sc = new FastScanner(System.in);
        pw = new PrintWriter(System.out);
        int tt = sc.nextInt();
        for(int ff = 0; ff < tt; ff++) {
            int n = sc.nextInt();
            int[] aa = new int[n];
            boolean[] ss = new boolean[n];
            List<A> a = new ArrayList<>();
            for(int i = 0; i < n; ++i) {
                aa[i] = sc.nextInt();
                if(aa[i] <= n) ss[aa[i] - 1] = true;
                a.add(new A(aa[i], i));
            }

            int ans = 0;
            int q = 0;
            {
                while (q < n && ss[q])
                    q++;

                ans = n - q - 1;
            }
            Collections.sort(a, (A xx, A yy) -> {
                if(xx.val != yy.val) return xx.val - yy.val;
                else return xx.pos - yy.pos;
            });

            TreeSet<Integer> positions = new TreeSet<>();

            for(A elem : a) {
                Integer l_p = positions.lower(elem.pos);
                if(l_p == null) l_p = -1;
                Integer r_p = positions.higher(elem.pos);
                if(r_p == null) r_p = n;
                l_p++;r_p--;
                if( (r_p - elem.pos > 0 && elem.pos - l_p > 0) ||
                        ( (l_p != elem.pos && aa[l_p] == aa[elem.pos]) || (r_p != elem.pos && aa[r_p] == aa[elem.pos])) )
                    ans = Math.max(ans, r_p - l_p - 1);
                //pw.println(l_p + " " + r_p + " " + elem.pos + " " + ans);
                positions.add(elem.pos);
            }

            for(int i = 0; i < n; ++i) {
                if(q == n && i == 0) pw.print(1);
                else if (i <= ans) pw.print(0);
                else pw.print(1);
            }

             pw.println();


        }
        pw.close();

    }
}

class FastScanner {
    static StringTokenizer st = new StringTokenizer("");
    static BufferedReader br;

    public FastScanner(InputStream inputStream) {
        br = new BufferedReader(new InputStreamReader(inputStream));
    }

    public String next() throws IOException {
        while (!st.hasMoreTokens())
            st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(next());
    }
}