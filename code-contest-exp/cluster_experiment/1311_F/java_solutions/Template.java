import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

public class Template {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskB solver = new TaskB();
        solver.solve(in, out);
        out.close();
    }

    static class TaskB {
        public void solve(InputReader in, PrintWriter out) {
            int T = in.nextInt();
            int[] place = in.nextIntArray(T);
            int[] speed = in.nextIntArray(T);

            int sumDist = 0;
            for(int i = 0; i < T; i++)
            {
                int dist = 0;
                for(int h = i; h < T; h++)
                {
                    if(i==h)continue;
                    if(place[i]==place[h])
                    {
                        dist = 0;
                    }
                    else if(place[i]>place[h])
                    {
                        if(speed[i]>=speed[h])
                        {
                            dist = place[i]-place[h];
                        }else{
                            dist = 0;
                        }
                    }
                    else
                    {
                        if(speed[i]>=speed[h])
                        {
                            dist = 0;
                        }else{
                            dist = place[h]-place[i];
                        }
                    }
                    sumDist+=dist;
                }

            }
            out.println(sumDist);
        }
    }

    static class InputReader {
        private BufferedReader reader;
        private StringTokenizer tokenizer = new StringTokenizer("");

        public InputReader(InputStream inputStream) {
            this.reader = new BufferedReader(
                    new InputStreamReader(inputStream));
        }

        public String next() {
            while (!tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

    }
}