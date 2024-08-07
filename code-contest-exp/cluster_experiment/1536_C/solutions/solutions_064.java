import java.io.*;
import java.util.*;

public class Main {
      public static void main(String[] args) throws Exception {
          Scanner scn = new Scanner(System.in);
          int t = Integer.parseInt(scn.nextLine());
          while(t-- > 0){
              int n = Integer.parseInt(scn.nextLine());
              String str = scn.nextLine();
              
              int[] countD = new int[n];
              countD[0] = str.charAt(0) == 'D' ? 1 : 0;
              
              int[] countK = new int[n];
              countK[0] = str.charAt(0) == 'K' ? 1 : 0;
              
              for(int i = 1; i < n ;i++){
                  countD[i] = (str.charAt(i) == 'D' ? 1 : 0) + countD[i-1];
                   countK[i] = (str.charAt(i) == 'K' ? 1 : 0) + countK[i-1];
              }
              
              int[] ans = new int[n];
              HashMap<String, Integer> map = new HashMap<>();
              
              for(int i = 0; i < n; i++){
                  int c = gcd(countD[i] , countK[i]);
                  countD[i] /= c;
                  countK[i] /= c;
                  
                  String s = countD[i] +"#"+ countK[i];
                  
                  ans[i] = map.containsKey(s) ? map.get(s)+1 : 1;
                  map.put(s , ans[i]);
              }
              StringBuilder sb = new StringBuilder();
              for(int i = 0; i < n; i++){
                  sb.append(ans[i] +" ");
              }
              sb.append("\n");
              System.out.print(sb);
              
          }
      }
      static int gcd(int a , int b){
          if(b == 0)return a;
          
          return gcd(b , a % b);
      }
}