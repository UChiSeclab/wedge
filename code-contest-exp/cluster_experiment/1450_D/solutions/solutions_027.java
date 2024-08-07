import java.util.*;
import java.io.*;
import java.lang.*;

public class B {
    static class FastReader {
            BufferedReader  br;
            StringTokenizer st;
        
            public FastReader() {
              br = new BufferedReader(new InputStreamReader(System.in));
            }
        
            String next() {
              while (st == null || !st.hasMoreElements()) {
                try {
                  st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
              return st.nextToken();
            }
        
            int nextInt() {
              return Integer.parseInt(next());
            }
        
            long nextLong() {
              return Long.parseLong(next());
            }
        
            double nextDouble() {
              return Double.parseDouble(next());
            }
        
            String nextLine() {
              String str = "";
              try {
                str = br.readLine();
              } catch (IOException e) {
                e.printStackTrace();
              }
              return str;
            }
          }
         static  FastReader sc=new FastReader();
    public static void main(String[] args) throws Exception {
          int t=sc.nextInt();
          while(t-->0)
      B.go();   
          }



                    
    static void go() {
      int n=sc.nextInt();
      int a[]=new int[n];
      for(int i=0;i<n;i++){
        a[i]=sc.nextInt();
      }
      int b[]=a.clone();
      sort(b);
      StringBuilder ans=new StringBuilder("");
      int low=n;
      for(int i=0;i<n;i++){
          if(b[i]!=(i+1)){
            low=i;
            break;
          }
      }
      if(low==n){
        ans.append(1);
      }else{
      for(int i=n;i>low;i--){
        ans.append(0);

      }
    }
    int min=Integer.MAX_VALUE;
    for(int i=1;i<n-1;i++){
      if(a[i-1]>a[i]&&a[i]<a[i+1]){
        min=Math.min(min,a[i]);
      }
    }
    if(min==Integer.MAX_VALUE){
      if(low==n){
        for(int i=n-1;i>0;i--){
           ans.append(1);
        }
      }else{
        for(int i=low;i>0;i--){
         ans.append(1);
        }
      }
    }else{
      if(low==n){
        for(int i=n-1;i>min;i--){
         ans.append(0);
        }
      }else{
        for(int i=low;i>min;i--){
       ans.append(0);
        }
      }
      for(int i=Math.min(min,low);i>=1;i--){
         ans.append(1);
      }
    }
    System.out.println(ans);
    }
  static  void sort(int [] a) {
         ArrayList<Integer> aa = new ArrayList<>();
         for (int i : a) {
      aa.add(i);}
         Collections.sort(aa);
            for (int i = 0; i < a.length; i++)a[i] = aa.get(i);
              }
  
  }

