
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static long GCD(long a, long b) { return a==0L ? b : GCD(b%a, a); }

    public static void main( String[] args ) {

        BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
        // BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        int n=in.nextInt();
        long a[]=new long[n];
        Integer arr[]=new Integer[n];
        for(int i=0;i<n;i++){
            arr[i] = in.nextInt();
        }
        long res = 0; 
        Arrays.sort(arr);
        int last = 0;
        for(int i = 0;i < n;i++){
            if(arr[i] - 1 >= 1 && arr[i] - 1 > last){
                last = arr[i] - 1;
                res++;
            }
            else if(arr[i] > last){
                res++;
                last = arr[i];
            }
            else if(arr[i] + 1 > last){
                res++;
                last=arr[i] + 1;
            }
            
        }
        out.print(res);
        out.flush();
    }


    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

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

        public long nextLong() {
          return Long.parseLong(next());
        }

    }
}
