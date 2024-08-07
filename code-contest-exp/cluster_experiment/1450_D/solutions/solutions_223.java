//make sure to make new file!
import java.io.*;
import java.util.*;

public class DG12{
   
   public static void main(String[] args)throws IOException{
      BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
      PrintWriter out = new PrintWriter(System.out);
      
      int t = Integer.parseInt(f.readLine());
      
      for(int q = 1; q <= t; q++){

         int n = Integer.parseInt(f.readLine());
      
         StringTokenizer st = new StringTokenizer(f.readLine());
         
         int[] array = new int[n];
         int[] freq = new int[n+1];
         for(int k = 0; k < n; k++){
            array[k] = Integer.parseInt(st.nextToken());
            freq[array[k]]++;
         }
         
         int[] indexof = new int[n+1];
         Arrays.fill(indexof,-1);
         int num1 = 0;
         for(int k = 0; k < n; k++){
            if(freq[array[k]] == 1){
               indexof[array[k]] = k;
               num1++;
            }
         }
         
         int[] answer = new int[n];
         
         int l = 0;
         int r = n-1;
         
         for(int k = 1; k < n; k++){
            if(freq[k] > 0 && (k==1 || (freq[k-1] == 1 && (indexof[k-1] == l|| indexof[k-1] == r)))){
               answer[n-k] = 1;
               if(indexof[k-1] == l) l++;
               else if(indexof[k-1] == r) r--;
            } else {
               break;
            }
         }
         
         if(num1 == n) answer[0] = 1;
         
         StringJoiner sj = new StringJoiner("");
         for(int k = 0; k < n; k++){
            sj.add("" + answer[k]);
         }
         out.println(sj.toString());
      

      }
      
      
      
      
      out.close();
   }
   
      
}