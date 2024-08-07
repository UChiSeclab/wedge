import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Queue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.io.InputStream;

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
        ENearestOppositeParity solver = new ENearestOppositeParity();
        solver.solve(1, in, out);
        out.close();
    }

    static class ENearestOppositeParity {
        public void solve(int testNumber, Scanner sc, PrintWriter pw) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++)
                arr[i] = sc.nextInt();
            HashSet<Integer> even = new HashSet<>(), odd = new HashSet<>();
            for (int i = 0; i < n; i++)
                if (arr[i] % 2 == 0)
                    even.add(i);
                else
                    odd.add(i);
            Queue<Integer> q = new LinkedList<>(), dist = new LinkedList<>(), val = new LinkedList<>();
            for (int x : odd) {
                q.add(x);
                dist.add(0);
                val.add(x);
            }
            int[] ans = new int[n];
            Arrays.fill(ans, -1);
            boolean[] vis = new boolean[n];
            int c = 0;
            while (!q.isEmpty() && c++ < (int) 2e5 + 1) {
                int cur = q.poll();
                int dis = dist.poll();
                int v = val.poll();
                if (even.contains(cur)) {
                    if (ans[v] == -1)
                        ans[v] = dis;
                    else
                        ans[v] = Math.min(ans[v], dis);
                } else {
                    if (cur + arr[cur] < n) {
                        q.add(cur + arr[cur]);
                        dist.add(dis + 1);
                        val.add(v);
                    }
                    if (cur - arr[cur] >= 0) {
                        q.add(cur - arr[cur]);
                        dist.add(dis + 1);
                        val.add(v);
                    }
                }
            }
            q = new LinkedList<>();
            dist = new LinkedList<>();
            val = new LinkedList<>();
            for (int x : even) {
                q.add(x);
                dist.add(0);
                val.add(x);
            }
            vis = new boolean[n];
            c = 0;
            while (!q.isEmpty() && c++ < (int) 2e5 + 1) {
                int cur = q.poll();
                int dis = dist.poll();
                int v = val.poll();
                if (odd.contains(cur)) {
                    if (ans[v] == -1)
                        ans[v] = dis;
                    else
                        ans[v] = Math.min(ans[v], dis);
                } else {
                    if (cur - arr[cur] >= 0) {
                        q.add(cur - arr[cur]);
                        dist.add(dis + 1);
                        val.add(v);
                    }
                    if (cur + arr[cur] < n) {
                        q.add(cur + arr[cur]);
                        dist.add(dis + 1);
                        val.add(v);
                    }
                }
            }
            for (int x : ans)
                pw.print(x + " ");
        }

    }

    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(FileReader r) {
            br = new BufferedReader(r);
        }

        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

