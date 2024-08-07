
import java .util.*;
public class MyClass {
    public static void main(String args[]) {
      Scanner bh=new Scanner(System.in);
      int n=bh.nextInt();
      Integer arr[]=new Integer[n];
     // int count[]=new int[150001];
      for(int i=0;i<n;i++)
      {
          arr[i]=bh.nextInt();
         // count[arr[i]]++;
      }
      long res=0;
     // boolean fix[]=new boolean[150001];
      
     Arrays.sort(arr);
     int last=0;
     for(int i=0;i<n;i++)
     {
         if(arr[i]-1>=1&&arr[i]-1>last)
         {
             last=arr[i]-1;
             res++;
         }
         else if(arr[i]>last)
         {
             res++;
             last=arr[i];
         }
         else if(arr[i]+1>last)
         {
             res++;
             last=arr[i]+1;
         }
         
     }
      System.out.println(res);
    }
}