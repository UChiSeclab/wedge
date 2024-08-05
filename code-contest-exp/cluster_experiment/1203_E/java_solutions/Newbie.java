

import org.omg.CORBA.INTERNAL;

import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

public class Newbie {

    static InputReader sc = new InputReader(System.in);
    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        solver s = new solver();
        long t = 1;
        while (t > 0) {
            s.solve();
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

        public String next() {
            while (token == null || !token.hasMoreTokens()) {
                try {
                    token = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return token.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }
    }

    static class card {
        long a;
        int cnt;
        int i;

        public card(long a, int cnt, int i) {
            this.a = a;
            this.cnt = cnt;
            this.i = i;
        }
    }

    static class ascend implements Comparator<pair> {
        public int compare(pair o1, pair o2) {
            if (o1.a != o2.a)
                return (int) (o1.a - o2.a);
            else
                return o1.b - o2.b;
        }
    }

    static class extra {

        static boolean v[] = new boolean[100001];
        static List<Integer> l = new ArrayList<>();
        static int t;

        static void shuffle(int a[]) {
            List<Integer> l = new ArrayList<>();
            for (int i = 0; i < a.length; i++)
                l.add(a[i]);
            Collections.shuffle(l);
            for (int i = 0; i < a.length; i++)
                a[i] = l.get(i);
        }

        static long gcd(long a, long b) {
            if (b == 0)
                return a;
            else
                return gcd(b, a % b);
        }

        static boolean valid(int i, int j, int r, int c, char ch[][]) {
            if (i >= 0 && i < r && j >= 0 && j < c && ch[i][j] != '#') {
                // System.out.println(i+" /// "+j);
                return true;
            } else {
                //  System.out.println(i+" //f "+j);
                return false;
            }
        }

        static void seive() {
            for (int i = 2; i < 100001; i++) {
                if (!v[i]) {
                    t++;
                    l.add(i);
                    for (int j = 2 * i; j < 100001; j += i)
                        v[j] = true;
                }
            }
        }

        static int binary(LinkedList<Integer> a, long val, int n) {
            int mid = 0, l = 0, r = n - 1, ans = 0;
            while (l <= r) {
                mid = (l + r) >> 1;
                if (a.get(mid) == val) {
                    r = mid - 1;
                    ans = mid;
                } else if (a.get(mid) > val)
                    r = mid - 1;
                else {
                    l = mid + 1;
                    ans = l;
                }
            }
            return (ans + 1);
        }

        static long fastexpo(int x, int y) {
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

       /* void dijsktra(int s, List<pair> l[], int n) {
            PriorityQueue<pair> pq = new PriorityQueue<>(new ascend());
            int dist[] = new int[100005];
            boolean v[] = new boolean[100005];
            for (int i = 1; i <= n; i++)
                dist[i] = 1000000000;
            dist[s] = 0;
            for (int i = 1; i < n; i++) {
                if (i == s)
                    pq.add(new pair(s, 0));
                else
                    pq.add(new pair(i, 1000000000));
            }
            while (!pq.isEmpty()) {
                pair node = pq.remove();
                v[node.a] = true;
                for (int i = 0; i < l[node.a].size(); i++) {
                    int v1 = l[node.a].get(i).a;
                    int w = l[node.a].get(i).b;
                    if (v[v1])
                        continue;
                    if ((dist[node.a] + w) < dist[v1]) {
                        dist[v1] = dist[node.a] + w;
                        pq.add(new pair(v1, dist[v1]));
                    }
                }
            }
        }*/
    }

    static class pair {
        long a;
        int b;

        public pair(long a, int i) {
            this.a = a;
            this.b = i;
        }
    }

    static class pair1 {
        pair p;
        int in;

        public pair1(pair a, int n) {
            this.p = a;
            this.in = n;
        }
    }


    static int inf = 5000013;

    static class solver {
        DecimalFormat df = new DecimalFormat("0.000000000");
        extra e = new extra();
        long mod = 1000000007;

        void solve() {
            int n = sc.nextInt();
            int a[] = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
            }
            new extra().shuffle(a);
            Arrays.sort(a);
            long lst = a[n - 1] + 2;
            int ans = 0;
            for (int i = n - 1; i >= 0; i--) {
                int cur = -1;
                for (int j = 1; j >= -1; j--) {
                    if (a[i] + j > 0 && a[i] + j < lst) {
                        cur = a[i] + j;
                        break;
                    }
                }
                if (cur != -1) {
                    ans++;
                    lst = cur;
                }
            }
            System.out.println(ans);
        }
    }
}

