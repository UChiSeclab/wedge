//package codeforces.round624div3;

import java.io.*;
import java.util.*;

public class MovingPoints {
    private static int UNVISITED = 0;
    private static int VISITING = -1;
    private static int VISITED = 1;

    public static void main(String[] args) {
//        try {
//            FastScanner in = new FastScanner(new FileInputStream("src/input.in"));
//            PrintWriter out = new PrintWriter(new FileOutputStream("src/output.out"));

        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

            solve(1, in, out);

//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void solve(int q, FastScanner in, PrintWriter out) {
        for (int qq = 0; qq < q; qq++) {
            int n = in.nextInt();
            int[] x = new int[n];
            for(int i = 0; i < n; i++) {
                x[i] = in.nextInt();
            }
            Integer[] v = new Integer[n];
            for(int i = 0;i < n; i++) {
                v[i] = in.nextInt();
            }
            v = ArrayUtils.compress(v);

            BinaryIndexedTree cntBit = new BinaryIndexedTree(n);
            BinaryIndexedTree sumBit = new BinaryIndexedTree(n);
            int[][] p = new int[n][2];
            for(int i = 0; i < n; i++) {
                p[i][0] = x[i];
                p[i][1] = v[i];
            }
            Arrays.sort(p, Comparator.comparingInt(a -> a[0]));
            long sum = 0;
            for(int i = 0; i < n; i++) {
                long prevCnt = cntBit.rangeSum(p[i][1]);
                long prevSum = sumBit.rangeSum(p[i][1]);
                sum += (prevCnt * p[i][0] - prevSum);
                cntBit.adjust(p[i][1], 1);
                sumBit.adjust(p[i][1], p[i][0]);
            }
            out.println(sum);
        }
        out.close();
    }

    private static class ArrayUtils {
        /*
            Compress all values of a to its rank in [1, a.length]
         */
        private static Integer[] compress(Integer[] a) {
            int n = a.length;
            Integer[] ret = new Integer[n];
            Integer[] copy = Arrays.copyOf(a, n);
            Arrays.sort(copy);
            int rank = 1;
            Map<Integer, Integer> map = new HashMap<>();
            for(int v : copy) {
                if(!map.containsKey(v)) {
                    map.put(v, rank);
                    rank++;
                }
            }
            for(int i = 0; i < n; i++) {
                ret[i] = map.get(a[i]);
            }
            return ret;
        }
    }

    private static class BinaryIndexedTree {
        private long[] ft;

        private BinaryIndexedTree(int n) {
            ft = new long[n + 1];
        }

        private long rangeSum(int l, int r) {
            return rangeSum(r) - (l == 1 ? 0 : rangeSum(l - 1));
        }

        private long rangeSum(int r) {
            long sum = 0;
            for(; r > 0; r -= leastSignificantOne(r)) {
                sum += ft[r];
            }
            return sum;
        }

        private void adjust(int k, int diff) {
            for(; k < ft.length; k += leastSignificantOne(k)) {
                ft[k] += diff;
            }
        }
        private int leastSignificantOne(int i) {
            return i & (-i);
        }
    }

    private static long modularAdd(long a, long b, int mod) {
        long sum = a + b;
        if (sum >= mod) {
            sum -= mod;
        }
        return sum;
    }

    private static long modularSubtract(long a, long b, int mod) {
        long diff = a - b;
        if (diff < 0) {
            diff += mod;
        }
        return diff;
    }

    private static Stack<Integer> topologicalSort(Set<Integer>[] g) {
        Stack<Integer> stack = new Stack<>();
        int[] state = new int[g.length];

        for (int node = 0; node < g.length; node++) {
            if (!topoSortHelper(g, stack, state, node)) {
                return null;
            }
        }
        return stack;
    }

    private static boolean topoSortHelper(Set<Integer>[] g, Stack<Integer> stack, int[] state, int currNode) {
        if (state[currNode] == VISITED) {
            return true;
        } else if (state[currNode] == VISITING) {
            return false;
        }
        state[currNode] = VISITING;
        for (int neighbor : g[currNode]) {
            if (!topoSortHelper(g, stack, state, neighbor)) {
                return false;
            }
        }
        state[currNode] = VISITED;
        stack.push(currNode);
        return true;
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
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


