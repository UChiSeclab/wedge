


import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.awt.Point;

public class Newbie {

    static InputReader sc = new InputReader(System.in);
    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        solver s = new solver();
        int t = 1;
        while (t > 0) {
            s.sol();
            t--;
        }
        out.close();
    }
           /* static class descend implements Comparator<pair1> {
                public int compare(pair1 o1, pair1 o2) {
                    if (o1.pop != o2.pop)
                        return (int) (o1.pop - o2.pop);
                    else
                        return o1.in - o2.in;
                }
            }*/

    static class InputReader {
        public BufferedReader br;
        public StringTokenizer token;

        public InputReader(InputStream stream) {
            br = new BufferedReader(new InputStreamReader(stream), 32768);
            token = null;
        }

        public String sn() {
            while (token == null || !token.hasMoreTokens()) {
                try {
                    token = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return token.nextToken();
        }

        public int ni() {
            return Integer.parseInt(sn());
        }

        public String snl() throws IOException {
            return br.readLine();
        }

        public long nlo() {
            return Long.parseLong(sn());
        }

        public double nd() {
            return Double.parseDouble(sn());
        }

        public int[] na(int n) {
            int a[] = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = sc.ni();
            return a;
        }

        public long[] nal(int n) {
            long a[] = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = sc.nlo();
            return a;
        }
    }

    static class extra {

        static boolean v[] = new boolean[100001];
        static List<Integer> l = new ArrayList<>();
        static int t;

        static void shuffle(int a[]) {
            for (int i = 0; i < a.length; i++) {
                int t = (int) Math.random() * a.length;
                int x = a[t];
                a[t] = a[i];
                a[i] = x;
            }
        }

        static void shufflel(long a[]) {
            for (int i = 0; i < a.length; i++) {
                int t = (int) Math.random() * a.length;
                long x = a[t];
                a[t] = a[i];
                a[i] = x;
            }
        }

        static int gcd(int a, int b) {
            if (b == 0)
                return a;
            else
                return gcd(b, a % b);
        }

        static boolean valid(int i, int j, int r, int c) {
            if (i >= 0 && i < r && j >= 0 && j < c) {
                // System.out.println(i+" /// "+j);
                return true;
            } else {
                //  System.out.println(i+" //f "+j);
                return false;
            }
        }

        static void seive() {
            for (int i = 2; i < 101; i++) {
                if (!v[i]) {
                    t++;
                    l.add(i);
                    for (int j = 2 * i; j < 101; j += i)
                        v[j] = true;
                }
            }
        }

        static long fastexpo(long x, long y) {
            long res = 1;
            while (y > 0) {
                if ((y & 1) == 1) {
                    res *= x;
                }
                y = y >> 1;
                x = x * x;
            }
            return res;
        }

        static long lfastexpo(long x, long y, long p) {
            long res = 1;
            x = x % p;
            while (y > 0) {
                if ((y & 1) == 1) {
                    res = (res * x) % p;
                }
                y = y >> 1;
                x = (x * x) % p;
            }
            return res;
        }
    }

    static int inf = 5000013;

    static class solver {
        DecimalFormat df = new DecimalFormat("0.00000000");
        extra e = new extra();
        long mod = (long) (1000000007);
        List<Integer> list[];

        void sol() throws IOException {
            int n = sc.ni();
            int a[] = new int[n];
            List<Integer> even = new ArrayList<>();
            List<Integer> odd = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                a[i] = sc.ni();
                if (a[i] % 2 == 0)
                    even.add(i);
                else
                    odd.add(i);
            }
            int ans[] = new int[n];
            Arrays.fill(ans, Integer.MAX_VALUE);
            list = new ArrayList[n];
            for (int i = 0; i < n; i++)
                list[i] = new ArrayList<>();
            v = new boolean[n];
            for (int i = 0; i < n; i++) {
                int lf = i - a[i];
                int nx = i + a[i];
                if (lf >= 0)
                    list[lf].add(i);
                if (nx < n)
                    list[nx].add(i);
            }
            Arrays.fill(ans,-1);
            bfs(ans, odd, even);
            bfs(ans, even, odd);
            for (int h : ans)
                out.print(h + " ");
            out.println();
        }

        boolean v[];

        void bfs(int ans[], List<Integer> s, List<Integer> ch) {
            int n = ans.length;
            int d[] = new int[n];
            Queue<Integer> q = new LinkedList<>();
            Arrays.fill(d,Integer.MAX_VALUE);
            for(int h:s)
            {
                d[h]=0;
                q.add(h);
            }
            while (!q.isEmpty())
            {
                int t=q.poll();
                for(int ver:list[t])
                {
                    if(d[ver]==Integer.MAX_VALUE)
                    {
                        d[ver]=d[t]+1;
                        q.add(ver);
                    }
                }
            }
            for(int h:ch)
            {
                if(d[h]!=Integer.MAX_VALUE)
                    ans[h]=d[h];
            }
        }
    }
}

