import java.io.*;
import java.util.*;
 
/**
 * Only By Abhi_Valani
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Unstoppable solver = new Unstoppable();
	//	int t=in.nextInt();
	//	while(t-->0)
        solver.solve(in, out);
        out.close();
    }
 
    static class Unstoppable {
        int a[],n,entry[];
        boolean visit[];
        public int function(int i)
        {
             if(i>=n||i<0){  return Integer.MIN_VALUE; }
            if(entry[i]!=0) return entry[i];
            int left=0,right=0,flag=0;
            if(i+a[i]<n) { if(a[i+a[i]]%2!=a[i]%2){ flag=1; entry[i]=1; return 1;  } }
            if(i-a[i]>=0){ if(a[i-a[i]]%2!=a[i]%2){ flag=1; entry[i]=1; return 1; } }
            else if(flag==0){
              right=1+function(i+a[i]);
            }
             if(right!=1) {
              left=1+function(i-a[i]);  
            }
             
             if(left<=0&&right<=0) { entry[i]=-1;  }
             else if(left<0&&right>0) { entry[i]=right; }
              else if(left>0&&right<0) { entry[i]=left; }
              else entry[i]=Math.min(left,right); 
              return entry[i];
         }
     
        public void solve(InputReader in, PrintWriter out) {
           n=in.nextInt();
           a=new int[n];
          
          entry=new int[n];
          visit=new boolean[n];
          Arrays.fill(entry,0);
          for(int i=0;i<n;i++) a[i]=in.nextInt();
          for(int i=0;i<n;i++) {
              int left=0;
             
              if(i-a[i]>=0&&entry[i-a[i]]>0) left=entry[i-a[i]]+1;
              int right=function(i);
              if(left>0&&right>0) entry[i]=Math.min(left,right);
              else if(left>0) entry[i]=left;
              else if(right>0)
              entry[i]=right;
              else entry[i]=-1;
              out.print(entry[i]+" ");
          } 
        }

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
        public long nextLong(){
            return Long.parseLong(next());
        }
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }
}
 