import java.util.*;
import java.io.*;

public class Ahmed {
    public static void main(String[] YAHIA_MOSTAFA) throws IOException, InterruptedException {
      Scanner sc = new Scanner(System.in);
      int n =sc.nextInt();
      long sum=0;
      for (int i=0;i<n;++i)
          sum+=sc.nextLong();
      int i =1;
      for (;sum>0;++i)
          sum-=i;
      System.out.println(i-1);
    }
    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(InputStream system) {
            br = new BufferedReader(new InputStreamReader(system));
        }

        public Scanner(String file) throws Exception {
            br = new BufferedReader(new FileReader(file));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        public char nextChar() throws IOException {
            return next().charAt(0);
        }

        public Long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public boolean ready() throws IOException {
            return br.ready();
        }

        public void waitForInput() throws InterruptedException {
            Thread.sleep(3000);
        }
    }
}

