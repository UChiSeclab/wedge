import java.util.*;
import java.io.*;

public class Ahmed {
    public static void main(String[] YAHIA_MOSTAFA) throws IOException, InterruptedException {
      Scanner sc = new Scanner(System.in);
      int n =sc.nextInt();
      TreeSet<Long>ts =new TreeSet<>();
      Long[] x =new Long[n];
      for (int i =0;i<n;++i)
          x[i]=sc.nextLong();
      Arrays.sort(x);
      for (long i : x){
          if (i>1&&!ts.contains(i-1))
              ts.add(i-1);
          else if (!ts.contains(i))
              ts.add(i);
          else if (!ts.contains(i+1))
              ts.add(i+1);
      }
      System.out.println(ts.last());
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

