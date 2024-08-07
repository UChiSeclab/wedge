import java.io.*;
import java.util.*;

public class Main {
    static int n, a[];
    static int[][] dist;
    static ArrayList<Integer> adjList[];

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        n = sc.nextInt();
        a = sc.nextIntArray(n);
        dist = new int[2][n];
        adjList = new ArrayList[n];
        for (int i = 0; i < n; i++)
            adjList[i] = new ArrayList<>();
        for (int[] aa : dist)
            Arrays.fill(aa, -1);
        int off[] = {-1, 1};
        for (int i = 0; i < n; i++) {
            for (int j : off) {
                int u = i + j * a[i];
                if (u >= 0 && u < n) adjList[u].add(i);
            }
        }
        solve(0);
        solve(1);
        for (int i = 0; i < n; i++) {
            int p = 1 - (a[i] % 2);
            out.print(dist[p][i] + " ");
        }

        out.flush();
        out.close();
    }

    static void solve(int p) {
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++)
            if (a[i] % 2 == p) {
                dist[p][i] = 0;
                q.add(i);
            }
        while (!q.isEmpty()) {
            int u = q.remove();
            for (int v : adjList[u]) {
                if (dist[p][v] == -1) {
                    dist[p][v] = 1 + dist[p][u];
                    q.add(v);
                }
            }

        }
    }


    static class Scanner {

        StringTokenizer st;
        BufferedReader br;

        public Scanner(InputStream system) {
            br = new BufferedReader(new InputStreamReader(system));
        }


        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        public char nextChar() throws IOException {
            return next().charAt(0);
        }

        public Long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public int[] nextIntArray(int n) throws IOException {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public long[] nextLongArray(int n) throws IOException {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }


        public Integer[] nextIntegerArray(int n) throws IOException {
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public double[] nextDoubleArray(int n) throws IOException {
            double[] ans = new double[n];
            for (int i = 0; i < n; i++)
                ans[i] = nextDouble();
            return ans;
        }

        public short nextShort() throws IOException {
            return Short.parseShort(next());
        }

    }

}