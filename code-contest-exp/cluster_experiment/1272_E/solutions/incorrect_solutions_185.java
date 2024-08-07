

//                I know stuff but probably my rating tells otherwise...

//               Kya hua, code samajhne ki koshish kar rhe ho?? Mat karo,
//                      mujhe bhi samajh nhi aata kya likha hai


import java.io.*;
import java.util.*;
import static java.lang.Math.*;

public class _1272E{

    static void MainSolution() {
        n = ni();
        ArrayList<Integer> adj[] = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
        int ar[] = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int x = ni();
            ar[i] = x;
            if (i + x <= n) adj[i].add(i + x);
            if (i - x >= 1) adj[i].add(i - x);
        }
        int vis[] = new int[n + 1];
        int dist[] = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        //int eve = -1, odd = -1;
        //pl(Arrays.toString(adj));
        for (int i = 1; i <= n; i++) {
            if (vis[i] == 0) {
                vis=new int[n+1];
                ArrayList<pair> even = new ArrayList<>(), od = new ArrayList<>();
                LinkedList<pair> q = new LinkedList<>();
                q.add(new pair(i, ar[i], 0));
                while (!q.isEmpty()) {
                    pair x = q.poll();
                    if (vis[x.i] == 1) continue;
                    vis[x.i] = 1;
                    if ((x.a & 1) == 1) {
                        for (pair y : even) {
                            dist[y.i] = min(dist[y.i], x.b - y.b);
                        }
                        even.clear();
                        od.add(x);
                    } else {
                        for (pair y : od) {
                            dist[y.i] = min(dist[y.i], x.b - y.b);
                        }
                        od.clear();
                        even.add(x);
                    }

                    for (int y : adj[x.i]) q.add(new pair(y, ar[y], x.b + 1));
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) p(-1);
            else p(dist[i]);
        }
    }

    static class pair {
        int i, a, b;

        pair(int i, int c, int d) {
            a = c;
            b = d;
            this.i = i;
        }
    }


    //----------------------------------------The main code ends here------------------------------------------------------
    /*-------------------------------------------------------------------------------------------------------------------*/
    //-----------------------------------------Rest's all dust-------------------------------------------------------------


    static int mod9 = 1_000_000_007;
    static int n, m, l, k, t;
    static AwesomeInput input = new AwesomeInput(System.in);
    static PrintWriter pw = new PrintWriter(System.out, true);

    // The Awesome Input Code is a fast IO method //
    static class AwesomeInput {
        private InputStream letsDoIT;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        private AwesomeInput(InputStream incoming) {
            this.letsDoIT = incoming;
        }

        public int read() {
            if (numChars == -1)
                throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = letsDoIT.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        private long ForLong() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            }
            while (!isSpaceChar(c));
            return res * sgn;
        }

        private String ForString() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            }
            while (!isSpaceChar(c));

            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            boolean isSpaceChar(int ch);
        }
    }

    // functions to take input//
    static int ni() {
        return (int) input.ForLong();
    }

    static String ns() {
        return input.ForString();
    }

    static long nl() {
        return input.ForLong();
    }

    static double nd() throws IOException {
        return Double.parseDouble(new BufferedReader(new InputStreamReader(System.in)).readLine());
    }

    //functions to give output
    static void pl() {
        pw.println();
    }

    static void p(Object o) {
        pw.print(o + " ");
    }

    static void pws(Object o) {
        pw.print(o + "");
    }

    static void pl(Object o) {
        pw.println(o);
    }

    // Fast Sort is Radix Sort
    public static int[] fastSort(int[] f) {
        int n = f.length;
        int[] to = new int[n];
        {
            int[] b = new int[65537];
            for (int i = 0; i < n; i++) b[1 + (f[i] & 0xffff)]++;
            for (int i = 1; i <= 65536; i++) b[i] += b[i - 1];
            for (int i = 0; i < n; i++) to[b[f[i] & 0xffff]++] = f[i];
            int[] d = f;
            f = to;
            to = d;
        }
        {
            int[] b = new int[65537];
            for (int i = 0; i < n; i++) b[1 + (f[i] >>> 16)]++;
            for (int i = 1; i <= 65536; i++) b[i] += b[i - 1];
            for (int i = 0; i < n; i++) to[b[f[i] >>> 16]++] = f[i];
            int[] d = f;
            f = to;
            to = d;
        }
        return f;
    }

    public static void main(String[] args) {      //threading has been used to increase the stack size.

        new Thread(null, null, "Vengeance", 1 << 25)  //the last parameter is stack size desired.
        {
            public void run() {
                try {
                    double s = System.currentTimeMillis();
                    MainSolution();
                    //pl(("\nExecution Time : " + ((double) System.currentTimeMillis() - s) / 1000) + " s");
                    pw.flush();
                    pw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }.start();
    }
}