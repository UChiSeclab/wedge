import java.io.*;
import java.util.*;

public class Main {

    static int b[];
    static ArrayList<Integer>[] a;

    static int[] dfs(ArrayDeque<Integer> deq, boolean[] used, int dist[]) {
        while (!deq.isEmpty()) {
            int v = deq.pollFirst();
            for (int to : a[v]) {
                if (!used[to]) {
                    used[to] = true;
                    dist[to] = Math.min(dist[to], dist[v] + 1);
                    deq.addLast(to);
                } else dist[v] = Math.min(dist[v], dist[to] + 1);
            }
        }
        return dist;
    }


    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = nextInt();
        b = new int[n];
        a = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            a[i] = new ArrayList<>();
            b[i] = nextInt();
        }
        ArrayDeque<Integer> chet = new ArrayDeque<>();
        ArrayDeque<Integer> nechet = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (i - b[i] >= 0) a[i - b[i]].add(i);
            if (i + b[i] < n) a[i + b[i]].add(i);
            if (b[i] % 2 == 0) chet.addLast(i);
            else nechet.addLast(i);
        }
        int[] ans = new int[n];
        for (int i = 0; i < 2; i++) {
            boolean[] used = new boolean[n];
            int[] dist = new int[n];
            Arrays.fill(dist, Integer.MAX_VALUE / 2);
            for (int j = 0; j < n; j++) {
                if ((b[j] & 1) == i) {
                    used[j] = true;
                    dist[j] = 0;
                }
            }
            if (i == 0) dist = dfs(chet, used, dist);
            else dist = dfs(nechet, used, dist);
            for (int j = 0; j < n; j++) {
                if (b[j] % 2 == (i + 1) % 2) {
                    ans[j] = dist[j];
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (ans[i] == Integer.MAX_VALUE / 2) out.print(-1 + " ");
            else out.print(ans[i] + " ");
        }
        out.close();
    }


    static BufferedReader br;
    static StringTokenizer st = new StringTokenizer("");

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return st.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    static long nextLong() throws IOException {
        return Long.parseLong(next());
    }
}
