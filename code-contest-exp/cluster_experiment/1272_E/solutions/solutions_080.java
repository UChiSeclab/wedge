//package codeforces.round605div3;

import java.io.*;
import java.util.*;

public class NearestOppositeParity {
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
            int[] a = new int[n];
            for(int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }

            int[] moves = new int[n];   //move[i]: the min moves needed for position i to reach an opposite parity position
            Arrays.fill(moves, - 1);
            List<List<Integer>> dg = new ArrayList<>();
            for(int i = 0; i < n; i++) {
                dg.add(new ArrayList<>());
            }

            Queue<Integer> queue = new LinkedList<>();
            for(int i = 0; i < n; i++) {
                if(i - a[i] >= 0) {
                    dg.get(i - a[i]).add(i);
                    if(a[i] % 2 != (a[i - a[i]]) % 2) {
                        moves[i] = 1;
                    }
                }
                if(i + a[i] < n) {
                    dg.get(i + a[i]).add(i);
                    if(a[i] % 2 != (a[i + a[i]]) % 2) {
                        moves[i] = 1;
                    }
                }
                if(moves[i] == 1) {
                    queue.add(i);
                }
            }

            while(queue.size() > 0) {
                int curr = queue.poll();
                for(int neighbor : dg.get(curr)) {
                    //neighbor has not been explored yet
                    if(moves[neighbor] < 0) {
                        moves[neighbor] = moves[curr] + 1;
                        queue.add(neighbor);
                    }
                }
            }

            for(int i = 0; i < n; i++) {
                out.print(moves[i] + " ");
            }
        }
        out.close();
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


