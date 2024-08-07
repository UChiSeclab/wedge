import java.io.*;
import java.util.*;

public class Task {

    public static void main(String[] args) throws IOException {

        new Task().go();
    }

    PrintWriter out;
    Reader in;
    BufferedReader br;

    Task() throws IOException {

        try {

            //br = new BufferedReader( new FileReader("input.txt") );
            in = new Reader("input.txt");
            out = new PrintWriter( new BufferedWriter(new FileWriter("output.txt")) );
        }
        catch (Exception e) {

            //br = new BufferedReader( new InputStreamReader( System.in ) );
            in = new Reader();
            out = new PrintWriter( new BufferedWriter(new OutputStreamWriter(System.out)) );
        }
    }

    void go() throws IOException {

        int t = 1;
        while (t > 0) {
            solve();
            //out.println();
            t--;
        }

        out.flush();
        out.close();
    }

    int inf = 2000000000;
    int mod = 1000000007;
    double eps = 0.000000001;

    int n;
    int m;
    ArrayList<Integer>[] g;
    void solve() throws IOException {
        int n = in.nextInt();
        int max = 200000;
        int[] cnt = new int[max];
        for (int i = 0; i < n; i++)
            cnt[in.nextInt()]++;
        int ans = 0;
        for (int i = 1; i < max - 1; i++) {
            if (cnt[i - 1] > 0) {
                ans++;
            } else if (cnt[i] == 0 && cnt[i + 1] > 0) {
                cnt[i + 1]--;
                ans++;
            } else if (cnt[i] > 0) {
                ans++;
                cnt[i]--;
            }
        }
        out.println(ans);
    }

    long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    class Pair implements Comparable<Pair> {
        int a;
        int b;

        Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int compareTo(Pair p) {
            if (a != p.a)
                return Integer.compare(a, p.a);
            else
                return Integer.compare(b, p.b);
        }
    }

    class Item {

        int a;
        int b;
        int c;

        Item(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

    }

    class Reader {

        BufferedReader  br;
        StringTokenizer tok;

        Reader(String file) throws IOException {
            br = new BufferedReader( new FileReader(file) );
        }

        Reader() throws IOException {
            br = new BufferedReader( new InputStreamReader(System.in) );
        }

        String next() throws IOException {

            while (tok == null || !tok.hasMoreElements())
                tok = new StringTokenizer(br.readLine());
            return tok.nextToken();
        }

        int nextInt() throws NumberFormatException, IOException {
            return Integer.valueOf(next());
        }

        long nextLong() throws NumberFormatException, IOException {
            return Long.valueOf(next());
        }

        double nextDouble() throws NumberFormatException, IOException {
            return Double.valueOf(next());
        }


        String nextLine() throws IOException {
            return br.readLine();
        }

        int[] nextIntArray(int n) throws IOException {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        ArrayList<Integer>[] nextGraph(int n, int m) throws IOException {
            ArrayList<Integer>[] g = new ArrayList[n];
            for (int i = 0; i < n; i++)
                g[i] = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                int x = nextInt() - 1;
                int y = nextInt() - 1;
                g[x].add(y);
                g[y].add(x);
            }

            return g;
        }

    }

}