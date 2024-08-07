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
    Integer [] box = new Integer [n];
    for(int i = 0; i < n; i++){
      box[i] = f.nextInt();
    }
    Arrays.sort(box,Collections.reverseOrder());
    
    int lst = box[0] +2;
    int ans = 0;
    for(int i =0; i < n; i++){
      int cur = -1;
      for(int dx = 1; dx >= -1; dx--){
        if(box[i] + dx > 0 && box[i] + dx < lst){
          cur = box[i] +dx;
          break;
        }
      }
      if(cur ==-1)continue;
      ans++;
      lst = cur;
    }
    out.println(ans);
//    else{
//      boolean flag = false;
//      for(int e : box){
//        if(num[e] > 1){
//          if(e+1 == ans.last()){
//            flag = true;
//          }
//          else{
//            if(e != ans.last()){
//              ans.add(e+1);
//            }
//          }
//        }
//        if(e > 1){
//          if(ans.contains(e-1)){
//          
//          }
//          else{
//            ans.add(e-1);
//            if(num[e] > 1){
//              
//            }
//            else{
//              ans.remove(e);
//            }
//            //out.println(e);
//          }
//        }
//      }
//      if(flag || num[ans.last()] > 1){
//        ans.add(ans.last()+1);
//      }
//      //out.println();
//      out.println(ans.size());
//    }
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