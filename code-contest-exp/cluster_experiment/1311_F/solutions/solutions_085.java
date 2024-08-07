//make sure to make new file!
import java.io.*;
import java.util.*;

public class F624{
   
   public static void main(String[] args)throws IOException{
      BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
      PrintWriter out = new PrintWriter(System.out);
      
      int n = Integer.parseInt(f.readLine());
      
      StringTokenizer st1 = new StringTokenizer(f.readLine());
      StringTokenizer st2 = new StringTokenizer(f.readLine());
      
      Integer[] x = new Integer[n];
      int[] v = new int[n];
      Pair[] pairs = new Pair[n];
      
      for(int k = 0; k < n; k++){
         x[k] = Integer.parseInt(st1.nextToken());
         v[k] = Integer.parseInt(st2.nextToken());
         pairs[k] = new Pair(x[k],v[k]);
      }
      
      Arrays.sort(pairs);
      Arrays.sort(x);
      
      long answer = 0L;
      
      HashMap<Integer,Integer> indexof = new HashMap<Integer,Integer>();
      for(int k = 0; k < n; k++){
         answer += (long)x[k] * (long)(-n+1+2*k);
         indexof.put(x[k],k);
      }
      
      for(int k = 0; k < n; k++){
         if(k < indexof.get(pairs[k].x)){
            //subtract
            answer -= (long)pairs[k].x*(long)(indexof.get(pairs[k].x)-k);
         } else if(k > indexof.get(pairs[k].x)){
            answer += (long)pairs[k].x*(long)(k-indexof.get(pairs[k].x));
         }
      }
      
      out.println(answer);
      
      

      
      
      
      
      
      out.close();
   }
   
   public static class Pair implements Comparable<Pair>{
      int x;
      int v;
      public Pair(int a, int b){
         x = a;
         v = b;
      }
      public int compareTo(Pair p){
         if(p.v != v){
            return v-p.v;
         }
         return x-p.x;
      }
   }
   
      
}