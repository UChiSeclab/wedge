import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayDeque;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        NearestOppositeParity solver = new NearestOppositeParity();
        solver.solve(1, in, out);
        out.close();
    }

    static class NearestOppositeParity {
        ArrayList<Integer>[] adj;
        int INF = (int) (1e9);

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int N = in.nextInt();
            adj = new ArrayList[N];
            for (int i = 0; i < N; i++) {
                adj[i] = new ArrayList<>();
            }
            int[] arr = new int[N];
            for (int i = 0; i < N; i++) {
                int a = in.nextInt();
                arr[i] = a;
                if (i + a < N) {
                    adj[i + a].add(i);
                }
                if (i - a >= 0) {
                    adj[i - a].add(i);
                }
            }
            int[] dist = new int[N];
            boolean[] visited;
            for (int j = 0; j < 2; j++) {
                ArrayDeque<Integer> queue = new ArrayDeque<>();
                int[] curDist = new int[N];
                Arrays.fill(curDist, INF);
                for (int i = 0; i < N; i++) {
                    if (arr[i] % 2 == j) {
                        queue.add(i);
                        curDist[i] = 0;
                    }
                }
                while (!queue.isEmpty()) {
                    int next = queue.remove();
                    for (int c : adj[next]) {
                        if (curDist[next] + 1 < curDist[c]) {
                            curDist[c] = curDist[next] + 1;
                            queue.add(c);
                        }
                    }
                }
                for (int i = 0; i < N; i++) {
                    if (arr[i] % 2 != j) {
                        dist[i] = (curDist[i] == INF ? -1 : curDist[i]);
                    }
                }
            }
            for (int i = 0; i < N; i++) {
                out.print(dist[i] + " ");
            }
        }

    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

