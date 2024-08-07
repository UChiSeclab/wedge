import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class SolutionD extends Thread {
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new
                                            InputStreamReader(System.in));
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

    private static final FastReader scanner = new FastReader();
    private static final PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        new Thread(null, new SolutionD(), "Main", 1 << 26).start();
    }

    public void run() {
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            solve();
        }
        out.close();
    }

    static class Pair {
        int a;
        int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }


    private static void solve() {
        int n = scanner.nextInt();
        int[] a = new int[n];

        List<Integer>[] dist = new List[n+1];

        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();

            if (dist[a[i]] == null) {
                dist[a[i]] = new ArrayList<>();
            }
            dist[a[i]].add(i);
        }

        Stack<Pair> stack = new Stack<>();

        int[] nextMin = new int[n];
        Arrays.fill(nextMin, n);

        for (int i = 0; i < n; i++) {
            Pair p = new Pair(i, a[i]);

            while (!stack.isEmpty() && stack.peek().b > a[i]) {
                nextMin[stack.pop().a] = i;
            }
            stack.push(p);
        }

        int[] prevMin = new int[n];
        Arrays.fill(prevMin, -1);
        stack.clear();
        for (int i = n-1; i >= 0; i--) {
            Pair p = new Pair(i, a[i]);

            while (!stack.isEmpty() && stack.peek().b > a[i]) {
                prevMin[stack.pop().a] = i;
            }
            stack.push(p);
        }

        boolean[] isGood = new boolean[n+1];
        Arrays.fill(isGood, true);

        int lastGood = n;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == null) {
                Arrays.fill(isGood, 0, Math.min(lastGood, n - i + 1), false);
                lastGood = 0;
            } else {
                int maxLength = 0;
                for (int index: dist[i]) {
                    maxLength = Math.max(maxLength, nextMin[index] - prevMin[index] - 1);
                }

                int from = Math.min(lastGood+1, Math.min(maxLength, n - i +1));
                Arrays.fill(isGood, from, Math.min(lastGood+1, n - i + 1), false);
                lastGood = from;
            }
        }

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            s.append(isGood[i] ? "1" : "0");
        }
        out.println(s);
    }


    //REMINDERS:
    //- CHECK FOR INTEGER-OVERFLOW BEFORE SUBMITTING

    //- CAN U BRUTEFORCE OVER SOMETHING, TO MAKE IT EASIER TO CALCULATE THE SOLUTION
}