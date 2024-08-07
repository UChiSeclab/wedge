import java.io.*;
import java.util.*;

public class Main {

    static ArrayList<Integer>[] a;
    static int b[];
    static int used[];
    static int ans[];

    static void dfs(int v) {
        used[v] = 1;
        boolean way = false;
        for (int to : a[v]) {
            if ((b[v] & 1) != (b[to] & 1)) {
                ans[v] = 1;
                used[v] = 2;
                return;
            }
            if (used[to] == 2) ans[v] = Math.min(ans[v], ans[to] + 1);
            else {
                if (used[to] == 0) {
                    dfs(to);
                } else way = true;
                ans[v] = Math.min(ans[v], ans[to] + 1);
            }
        }
        if (way) used[v] = 0;
        else used[v]++;
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = nextInt();
        a = new ArrayList[n];
        b = new int[n];
        used = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = nextInt();
        }
        for (int i = 0; i < n; i++) {
            a[i] = new ArrayList<>();
            if (i - b[i] >= 0) a[i].add(i - b[i]);
            if (i + b[i] < n) a[i].add(i + b[i]);
        }
        ans = new int[n];
        Arrays.fill(ans, Integer.MAX_VALUE / 2);
        for (int i = 0; i < n; i++) {
            if (used[i] == 0) dfs(i);
        }
        for (int i = 0; i < n; i++) {
            if(ans[i] == Integer.MAX_VALUE / 2) out.print(-1 +" ");
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
