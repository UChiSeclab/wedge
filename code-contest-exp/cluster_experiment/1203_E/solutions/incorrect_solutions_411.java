// https://codeforces.com/problemset/problem/1203/E

import java.io.*;
import java.util.*;

public class Boxers {
    static InputReader r = new InputReader(System.in);
    static PrintWriter pw = new PrintWriter(System.out);
    public static void main(String[] args) {
        int size = r.nextInt();
        int[] A= new int[size];
        for (int i = 0; i < size; i++) {
            A[i] = r.nextInt();
        }

        Arrays.sort(A);
        Set<Integer> unique = new HashSet<>();
        for (int i = 0; i < size; i++) {
            if (!unique.contains(A[i])) {
                unique.add(A[i]);
            } else if (A[i] - 1 > 0 && !unique.contains(A[i] - 1)) {
                unique.add(A[i] - 1);
            } else if (!unique.contains(A[i] + 1)) {
                unique.add(A[i] + 1);
            }
        }

        pw.println(unique.size());
        pw.close();
    }

    static class InputReader {
        BufferedReader reader;
        StringTokenizer tokenizer;
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
        String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
        String nextLine() {
            String str = "";
            try {
                str = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return str;
        }
        public int nextInt() { return Integer.parseInt(next()); }
        public long nextLong() { return Long.parseLong(next()); }
        public double nextDouble() { return Double.parseDouble(next()); }
    }
}