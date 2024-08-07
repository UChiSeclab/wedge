import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.AbstractCollection;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskE solver = new TaskE();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskE {
        public void solve(int testNumber, Scanner in, PrintWriter out) {
            int n = in.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }
            int[] ans = new Solver().solve(n, a);
            for (int i : ans) out.print(i + " ");
        }

    }

    static class Solver {
        ArrayList<Integer>[] graph;
        int[] ans;
        final int INF = Integer.MAX_VALUE / 2;

        int[] solve(int n, int[] a) {
            graph = new ArrayList[n];
            ans = new int[n];
            Arrays.fill(ans, -1);
            ArrayList<Integer> odd = new ArrayList<>();
            ArrayList<Integer> even = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
                if ((a[i] & 1) == 0) even.add(i);
                else odd.add(i);
            }
            for (int i = 0; i < n; i++) {
                if (i + a[i] < n) graph[i + a[i]].add(i);
                if (i - a[i] >= 0) graph[i - a[i]].add(i);
            }
            bfs(odd, even);
            bfs(even, odd);
            return ans;
        }

        void bfs(ArrayList<Integer> start, ArrayList<Integer> end) {
            int[] d = new int[graph.length];
            Arrays.fill(d, INF);
            LinkedList<Integer> q = new LinkedList<>(start);
            for (int i : start) d[i] = 0;
            while (!q.isEmpty()) {
                int v = q.poll();
                for (int u : graph[v]) {
                    if (d[u] == INF) {
                        d[u] = d[v] + 1;
                        q.add(u);
                    }
                }
            }
            for (int v : end) {
                if (d[v] != INF) ans[v] = d[v];
            }
        }

    }
}

