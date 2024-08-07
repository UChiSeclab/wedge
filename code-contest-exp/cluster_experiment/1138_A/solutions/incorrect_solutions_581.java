import java.util.*;
import java.io.*;
public class A {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      
      int n = sc.nextInt();
      
      int[] num = new int[n];
      for(int i = 0; i < n; i++)
         num[i] = sc.nextInt();
      
      int one = 0;
      int two = 0;
      if(num[0] == 1)
         one++;
      else
         two++;
      int change = 0;
      int max = 0;
      for(int i = 1; i < n; i++) {
         if(num[i] != num[i-1] && change != 0) {
            max = Math.max(max, 2*Math.min(one, two));
            if(num[i] == 2)
               two = 0;
            else
               one = 0;
            if(num[i] == 1)
               one++;
            else
               two++;
            change = 0;
         }
         else {
            if(num[i] == 1)
               one++;
            else
               two++;
            if(num[i] != num[i-1])
               change++;
         }
      }
      max = Math.max(max, 2 * Math.min(one, two));
      System.out.println(max);
   }
}