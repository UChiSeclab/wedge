import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        new Main().run(in, out);
        out.close();
    }

    public static long mod = 17352642619633L;

    List<Integer>[] adj;
    int oo = 987654321;
    void run(FastScanner in, PrintWriter out) {

        // even/odd connected components?
        int N = in.nextInt();
        int[] a = new int[N];

        for (int i = 0; i < N; i++) a[i] = in.nextInt();
        adj = new List[N];
        for (int i = 0; i < N; i++) adj[i] = new ArrayList<>();

        int[] ret = new int[N];
        Arrays.fill(ret, -1);

        LinkedList<int[]> ll = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            int l = i-a[i];
            int r = i+a[i];
            if ((l >= 0 && a[l]%2 != a[i]%2) || (r < N && a[r]%2 != a[i]%2)) {
                ll.offer(new int[] {i, 1});
            } else {
                if (l >= 0) adj[l].add(i);
                if (r < N)  adj[r].add(i);
            }
        }

        while (!ll.isEmpty()) {
            int[] top = ll.pollFirst();
            int v = top[0];
            int d = top[1];

            if (ret[v] != -1) {
                continue;
            }

            ret[v] = d;
            for (int u : adj[v]) {
                ll.offerLast(new int[] {u, d+1});
            }
        }

        for (int x : ret) out.print(x + " ");
        out.println();


        // a_j has opp parity from a_i
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(InputStream in) {
            br = new BufferedReader(new InputStreamReader(in));
            st = null;
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
    }
}
