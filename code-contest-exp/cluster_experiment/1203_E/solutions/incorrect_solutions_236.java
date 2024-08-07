import java.io.*;
import java.util.*;

public class CF1203E {
    static class InputReader {
        BufferedReader reader;
        StringTokenizer tokenizer;
        
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        String next() { // reads in the next string
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } 
            }
            return tokenizer.nextToken();
        }

        public int nextInt() { // reads in the next int
            return Integer.parseInt(next());
        }

        public long nextLong() { // reads in the next long 
            return Long.parseLong(next());
        }

        public double nextDouble() { // reads in the next double 
            return Double.parseDouble(next());
        } 
    }

    static InputReader r = new InputReader(System.in);
    static PrintWriter pw = new PrintWriter(System.out);

    public static void main(String[] args) {
        int n = r.nextInt();
        int counter = 0;
        int[] weights = new int[n];
        for (int i = 0; i<n; i++) {
            weights[i] = r.nextInt();
        }
        Arrays.sort(weights);

        int count = 0;
        while (count<n) {
            int sum = 0;
            sum += weights[count];
            count++;
            int fixer = count;
            for (int i = fixer; i<n; i++) {
                if (weights[i] - weights[i-1] < 2) {
                    sum += weights[i];
                    count++;
                } else {
                    break;
                }
            }
            counter += sum;
        }
        pw.println(counter);
        pw.close();
    }
}