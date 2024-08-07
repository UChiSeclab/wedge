import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        CDilucAndKaeya solver = new CDilucAndKaeya();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }

    static class CDilucAndKaeya {
        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int n = in.nextInt();
            char[] arr = new char[n + 1];
            String s = in.next();
            HashMap<Double, Integer> hm = new HashMap<>();
//        int[] prefD= new int[n+1];
//        int[] prefK= new int[n+1];
            int cd = 0, ck = 0;
            int[] ans = new int[n + 1];
            int ci = 0;

            for (int i = 1; i <= n; i++) {
                arr[i] = s.charAt(i - 1);
                cd += (arr[i] == 'D' ? 1 : 0);
                ck += (arr[i] == 'K' ? 1 : 0);
                if (ck != 0) {
                    ans[i] = hm.getOrDefault(1.0 * cd / ck, 0) + 1;
                    hm.put(1.0 * cd / ck, hm.getOrDefault(1.0 * cd / ck, 0) + 1);
                } else {
                    ans[i] = ci + 1;
                    ci++;
                }
            }
//
//        Arrays.fill(ans,1);
//        for(int i=1;i<=n/2;i++){
//            int j=2;
//            while(i*j<=n){
//                int idx=(i*(j-1))/ans[i*(j-1)];
//                if((prefD[i]==(prefD[i*j]-prefD[i*(j-1)]) && prefK[i]==(prefK[i*j]-prefK[i*(j-1)]))){
//                    ans[i*j]=Math.max(ans[i*j],j);
//                }else {
//                    break;
//                }
//                j++;
//            }
//        }
            for (int i = 1; i <= n; i++) {
                out.print(ans[i] + " ");
            }
            out.println();
        }

    }

    static class InputReader {
        BufferedReader reader;
        StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void println() {
            writer.println();
        }

        public void close() {
            writer.close();
        }

    }
}

