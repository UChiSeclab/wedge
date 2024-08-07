import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        try (InputReader in = new InputReader(System.in)) {
            int N = in.nextInt();
            int K = in.nextInt();

            final int MAX_VALUE = 10_000;
            int[] count = new int[MAX_VALUE + 1];

            for (int i = 0; i < N; i++) {
                count[in.nextInt()]++;
            }

            long solution = 0;

            if (K == 0){
                for (int i = 0; i <= MAX_VALUE; i++)
                    solution += (long)count[i] * (count[i] - 1) / 2;
            }
            else {
                for (int i = 0; i <= MAX_VALUE; i++) {
                    for (int j = i + 1; j <= MAX_VALUE; j++) {
                        if (Integer.bitCount(i ^ j) == K)
                            solution += (long) count[i] * count[j];
                    }
                }
            }

            System.out.println(solution);
        }
    }

    static class InputReader implements AutoCloseable{
        private BufferedReader reader;
        private StringTokenizer tokenizer;
        private InputStream inputStream;

        InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 1 << 20);
            tokenizer = null;
            inputStream = stream;
        }

        private String nextToken() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()){
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                }
                catch (IOException e){
                    throw new RuntimeException(e);
                }
            }

            return  tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(nextToken());
        }

        public String nextString(){
            return nextToken();
        }

        public void close() {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}