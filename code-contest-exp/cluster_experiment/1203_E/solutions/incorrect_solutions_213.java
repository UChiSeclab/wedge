import java.io.*;
import java.text.*;
import java.util.*;
import java.math.*;
public class E {
  public static void main(String[] args) throws Exception {
    new E().run();
  }
  public void run() throws Exception {
    FastScanner f = new FastScanner();
    PrintWriter out = new PrintWriter(System.out);
    int n = f.nextInt();
    TreeSet<Integer> box = new TreeSet<Integer>();
    TreeSet<Integer> ans = new TreeSet<Integer>();
    for(int i = 0; i < n; i++){
      int temp = f.nextInt();
      box.add(temp);
      ans.add(temp);
    }
    if(box.size() == n){
      out.println(n);
    }
    else{
      for(int e : box){
        if(e > 1){
          ans.add(e+1);
          ans.add(e-1);
        }
        else{
          ans.add(e+1);
        }
      }
      out.println(ans.size());
    }
    
    out.flush();
  }
  static class FastScanner {
    public BufferedReader reader;
        public StringTokenizer tokenizer;
        public FastScanner() {
          reader = new BufferedReader(new InputStreamReader(System.in), 32768);
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
        public double nextDouble() {
          return Double.parseDouble(next());
        }
        public String nextLine() {
          try {
            return reader.readLine();
          } catch(IOException e) {
            throw new RuntimeException(e);
          }
        }
  }
}