import java.io.*;
import java.util.*;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class Main {
    public static final boolean DO_LOG = false; // TODO: move to run params
    private static Logger log = new Logger();

    static class Task {

        PrintWriter out;
        MyScanner in;

        class Edge {
            int to;
        }

        class GraphFamily {
            String name;
            int n;
            boolean vis[];
            int dist[];

            boolean isCorrect[];
            public GraphFamily(int n, IntFunction<Boolean> isCorrectF, String name) {
                this.n = n;
                this.name = name;
                this.vis = new boolean[n + 1];
                this.dist = new int[n + 1];
                this.isCorrect = new boolean[n + 1];

                for (int i = 1; i <= n; i++) {
                    vis[i] = false;
                    dist[i] = 0;
                    isCorrect[i] = (boolean) isCorrectF.apply(a[i]);
                }
            }

            public int getDist(int i) {
                if (!vis[i] ) {
                    Stack<Integer> indexes = new Stack<>();
                    indexes.push(i);

                    while (!indexes.isEmpty()) {
                        // try to move if not known, write distance
                        int cntIndex = indexes.peek();
                        vis[cntIndex] = true;

                        log.info("getDist " + i + " " + name + " :: " + indexes + ", cnt index " + cntIndex + " good? " + isCorrect[cntIndex]);

                        if (cntIndex != i && isCorrect[cntIndex]) {
                            dist[cntIndex] = 0;
                            indexes.pop();
                        } else {

                            boolean uncertain = false;
                            int minDist = Integer.MAX_VALUE;
                            log.info("Can go to " + to[cntIndex]);
                            for (int toV: to[cntIndex]) {
                                if (!uncertain) {
                                    if (!vis[toV]) {
                                        indexes.push(toV);
                                        log.info("found new path via " + toV + " stack will be " + indexes);
                                        uncertain = true;
                                    } else {
                                        if (dist[toV] != -1) {
                                            minDist = Math.min(minDist, dist[toV]);
                                        }
                                    }
                                }
                            }



                            if (!uncertain) {
                                log.info("min dist seems to be " + minDist);
                                if (minDist == Integer.MAX_VALUE) {
                                    dist[cntIndex] = -1;
                                } else {
                                    dist[cntIndex] = minDist + 1;
                                }
                                indexes.pop();
                            }


                        }
                    }
                }

                return dist[i];
            }

            @Override
            public String toString() {
                return Arrays.toString(isCorrect);
            }
        }
        List<Integer> [] to;
        int[] a;
        int n;
        public void solve(MyScanner in, PrintWriter out) {
            this.in = in;
            this.out = out;

            // *_*

            n = in.nextInt();
            a = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                a[i] = in.nextInt();
            }

            GraphFamily even = new GraphFamily(n, x -> x % 2 == 0, "even");
            GraphFamily odd = new GraphFamily(n, x -> x % 2 == 1, "odd");

            to = new List[n + 1];
            for (int i = 1; i <= n; i++) {
                to[i] = new ArrayList<>();
            }

            for (int i = 1; i <= n; i++) {
                if (i - a[i] >= 1) {
                    to[i].add(i - a[i]);
                }
                if (i + a[i] <= n) {
                    to[i].add(i + a[i]);
                }
            }

            for (int i = 1; i <= n; i++) {
                log.info("From " + i + " (val=" + a[i] + "): " + to[i]);
            }
            log.info("Even" + even);
            log.info("Odd" + odd);

            for (int i = 1; i <= n; i++) {
                GraphFamily graphFamily = a[i] % 2 == 0 ? odd : even;
                out.print(graphFamily.getDist(i) + " " );
            }

        }


    }

    public static void main(String[] args) {

        MyScanner in = new MyScanner();
        PrintWriter out = new PrintWriter(System.out);
        Task solver = new Task();

        solver.solve(in, out);

        out.close();
    }

    public static class Logger {

        public void info(String s) {
            if (DO_LOG) {
                System.out.println(s);
            }
        }
    }

    public static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        public MyScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
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

/*me after this round
░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
░░░░░░░░░░░░░░░░▄▄███▄▄▄░▄▄██▄░░░░░░░
░░░░░░░░░██▀███████████████▀▀▄█░░░░░░
░░░░░░░░█▀▄▀▀▄██████████████▄░░█░░░░░
░░░░░░░█▀▀░▄██████████████▄█▀░░▀▄░░░░
░░░░░▄▀░░░▀▀▄████████████████▄░░░█░░░
░░░░░▀░░░░▄███▀░░███▄████░████░░░░▀▄░
░░░▄▀░░░░▄████░░▀▀░▀░░░░░░██░▀▄░░░░▀▄
░▄▀░░░░░▄▀▀██▀░░░░░▄░░▀▄░░██░░░▀▄░░░░
█░░░░░█▀░░░██▄░░░░░▀▀█▀░░░█░░░░░░█░░░
█░░░▄▀░░░░░░██░░░░░▀██▀░░█▀▄░░░░░░▀▀▀
▀▀▀▀░▄▄▄▄▄▄▀▀░█░░░░░░░░░▄█░░█▀▀▀▀▀█░░
░░░░█░░░▀▀░░░░░░▀▄░░░▄▄██░░░█░░░░░▀▄░
░░░░█░░░░░░░░░░░░█▄▀▀▀▀▀█░░░█░░░░░░█░
░░░░▀░░░░░░░░░░░░░▀░░░░▀░░░░▀░░░░░░░░
░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
 */