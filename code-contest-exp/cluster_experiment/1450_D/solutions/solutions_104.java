import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class D {

    public static void main(String[] args) {
        FastReader scan = new FastReader();
        PrintWriter out = new PrintWriter(System.out);
        Task solver = new Task();
        int t = scan.nextInt();
        for(int tt = 1; tt <= t; tt++) solver.solve(tt, scan, out);
        out.close();
    }

    static class Task {

        public void solve(int testNumber, FastReader scan, PrintWriter out) {
            int n = scan.nextInt();
            TreeSet<Integer>[] pos = new TreeSet[n + 1];
            int[] minDist = new int[n + 1];
            for(int i = 0; i <= n; i++) pos[i] = new TreeSet<>();
            for(int i = 0; i < n; i++) {
                pos[scan.nextInt()].add(i);
            }
            TreeSet<Integer> have = new TreeSet<>();
            for(int i = 1; i <= n; i++) {
                if(pos[i].isEmpty()) minDist[i] = -1;
                else {
                    int bestDist = -1;
                    for(int x : pos[i]) {
                        Integer prev = have.lower(x), next = have.higher(x);
                        int total = 0;
                        total += prev == null ? x + 1 : x - prev;
                        total += next == null ? n - x : next - x;
                        bestDist = Math.max(bestDist, total - 1);
                    }
                    minDist[i] = bestDist;
                }
                have.addAll(pos[i]);
            }
            for(int i = 2; i <= n; i++) minDist[i] = Math.min(minDist[i], minDist[i - 1]);
            for(int i = 1; i <= n; i++) {
                out.print(minDist[n - i + 1] >= i ? 1 : 0);
            }
            out.println();
        }
    }

    static void ruffleSort(int[] a) {
        Random get = new Random();
        for (int i = 0; i < a.length; i++) {
            int r = get.nextInt(a.length);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
        Arrays.sort(a);
    }

    static void ruffleSort(long[] a) {
        Random get = new Random();
        for (int i = 0; i < a.length; i++) {
            int r = get.nextInt(a.length);
            long temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
        Arrays.sort(a);
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