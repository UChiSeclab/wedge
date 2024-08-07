
import java.io.*;
import java.util.*;

public class E {
    Random random = new Random(751454315315L + System.currentTimeMillis());
    ArrayList<Integer>[] g;
    int[] used;
    int[] e;
    int[] o;
    int[] a;

    void dfs(int v) {
        for (int u : g[v]) {
            if (used[u] < 2) {
                if (a[u] % 2 == 1 && a[v] % 2 == 1) {
                    e[u] = Math.min(e[u], e[v] + 1);
                }
                if (a[u] % 2 == 0 && a[v] % 2 == 0) {
                    o[u] = Math.min(o[u], o[v] + 1);
                }
                if (a[u] % 2 == 0 && a[v] % 2 == 1) {
                    o[u] = 1;
                }
                if (a[u] % 2 == 1 && a[v] % 2 == 0) {
                    e[u] = 1;
                }
                used[u]++;
                dfs(u);
            }
        }
    }

    public void solve() throws IOException {
        int n = nextInt();
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = nextInt();
        }
        e = new int[n];
        o = new int[n];
        for (int i = 0; i < n; i++) {
            if (a[i] % 2 == 0) {
                o[i] = n;
            } else {
                e[i] = n;
            }
        }
        for (int i = 0; i < n; i++) {
            if (i - a[i] >= 0) {
                g[i - a[i]].add(i);
            }
            if (i + a[i] < n) {
                g[i + a[i]].add(i);
            }
        }
        used = new int[n];
        for (int i = 0; i < n; i++) {
            if (used[i] < 2) {
                dfs(i);
            }
        }
        for (int i = 0; i < n; i++) {
            if (a[i] % 2 == 0) {
                out.print((o[i] == n ? -1 : o[i]) + " ");
            } else {
                out.print((e[i] == n ? -1 : e[i]) + " ");
            }
        }
    }

    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(System.out);

            solve();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void shuffle(int[] s) {
        for (int i = 0; i < s.length; ++i) {
            int j = random.nextInt(i + 1);
            int t = s[i];
            s[i] = s[j];
            s[j] = t;
        }
    }

    class Pair implements Comparable<Pair> {
        int f;
        int s;

        public Pair(int f, int s) {
            this.f = f;
            this.s = s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return f == pair.f &&
                    s == pair.s;
        }

        @Override
        public int hashCode() {
            return Objects.hash(f, s);
        }

        @Override
        public int compareTo(Pair o) {
            if (f == o.f) {
                return s - o.s;
            }
            return f - o.f;
        }
    }

    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public String nextToken() throws IOException {
        while (in == null || !in.hasMoreTokens()) {
            in = new StringTokenizer(br.readLine());
        }
        return in.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    public int[] nextArr(int n) throws IOException {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = nextInt();
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new E().run();
    }
}