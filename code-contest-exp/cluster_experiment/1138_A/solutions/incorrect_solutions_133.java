// https://codeforces.com/problemset/problem/1138/A

import java.io.*;
import java.util.*;

public class SushiForTwo {
    static InputReader r = new InputReader(System.in);
    static PrintWriter pw = new PrintWriter(System.out);
    public static void main(String[] args) {
        int numPieces = r.nextInt();
        int[] sushi = new int[numPieces];
        for (int i = 0; i < numPieces; i++) {
            sushi[i] = r.nextInt();
        }

        int longest = 0;
        for (int i = 0; i < numPieces - 1; i++) {
            if (sushi[i] + sushi[i + 1] == 3) {
                longest = Math.max(longest, expand(sushi, i, i + 1));
            }
        }

        pw.println(longest);
        pw.close();
    }

    public static int expand(int[] A, int i, int j) {
        int leftPtr = i;
        int rightPtr = j;
        int windowSize = 0;
        while (leftPtr > 0 && rightPtr < A.length - 1 && A[leftPtr] == A[i] && A[rightPtr] == A[j]) {
            windowSize = rightPtr - leftPtr + 1;
            leftPtr--;
            rightPtr++;
        }

        return windowSize;
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