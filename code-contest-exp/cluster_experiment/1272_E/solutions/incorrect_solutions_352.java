import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class E {

    public static void main(String[] args) {
        FastReader scan = new FastReader();
        PrintWriter out = new PrintWriter(System.out);
        Task solver = new Task();
        int t = 1;
        while (t --> 0) solver.solve(1, scan, out);
        out.close();
    }

    static class Task {
        static ArrayList<Integer>[] adj;
        static int n;
        static int[] a;
        static int[][] dist;
        static ArrayDeque<Integer> index = new ArrayDeque<>(), parity = new ArrayDeque<>();

        public void solve(int testNumber, FastReader scan, PrintWriter out) {
            n = scan.nextInt();
            a = new int[n];
            dist = new int[n][2];
            adj = new ArrayList[n];
            for(int i = 0; i < n; i++) {
                adj[i] = new ArrayList<>();
                dist[i][0] = dist[i][1] = -1;
            }
            for(int i = 0; i < n; i++) {
                a[i] = scan.nextInt();
                if(i+a[i] < n) adj[i+a[i]].add(i);
                if(i-a[i] > 0) adj[i-a[i]].add(i);
                dist[i][a[i]%2] = 0;
                index.addLast(i);
                parity.addLast(a[i] %= 2);
            }
            while(!index.isEmpty()) {
                int curr = index.pollFirst(), p = parity.pollFirst();
                for(int i : adj[curr]) {
                    if(dist[i][p] == -1) {
                        dist[i][p] = dist[curr][p]+1;
                        index.addLast(i);
                        parity.addLast(p);
                    }
                }
            }
            for(int i = 0; i < n; i++) {
                out.print(dist[i][a[i]^1]);
                out.print(" ");
            }
        }
    }

    static void shuffle(int[] a) {
        Random get = new Random();
        for (int i = 0; i < a.length; i++) {
            int r = get.nextInt(a.length);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    static void shuffle(long[] a) {
        Random get = new Random();
        for (int i = 0; i < a.length; i++) {
            int r = get.nextInt(a.length);
            long temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws FileNotFoundException {
            br = new BufferedReader(new FileReader(new File(s)));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}