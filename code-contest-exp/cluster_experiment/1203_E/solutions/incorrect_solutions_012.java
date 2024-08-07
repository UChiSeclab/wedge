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
    HashSet<Integer> box = new HashSet<Integer>();
    TreeSet<Integer> ans = new TreeSet<Integer>();
    int [] num = new int[150000];
    for(int i = 0; i < n; i++){
      int temp = f.nextInt();
      box.add(temp);
      num[temp] ++;
      ans.add(temp);
    }
    if(box.size() == n){
      out.println(n);
    }
    else{
      for(int e : box){
        if(num[e] > 1){
          if(e > 1){
            ans.add(e+1);
            ans.add(e-1);
          }
          else{
            ans.add(e+1);
          }
        }
      }
      out.println(ans.size());
    }
//    for(int i : ans){
//      out.print(i + " ");
//    }
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