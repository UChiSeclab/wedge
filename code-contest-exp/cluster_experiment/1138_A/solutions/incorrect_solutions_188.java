import java.io.*;
import java.util.*;

public class A{
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        
        int n=sc.nextInt();
        
        int[] a=new int[n];
        
        for(int i=0;i<n;i++)
          a[i]=sc.nextInt();
          
          int c1=0;
          int c2=0;                   // 1 1 1 0 0 1 1 
          
       
          
         
          int f=1;
           c1=1;
          int max1=0;
          int max2=0;
          for(int i=0;i<n-1;i++)
            {
                while((i+1)<n && a[i]==1 && a[i]==a[i+1])
                 {
                     c1++;
                     i++;
                 }
                 if(i==n)
                   break;
                 
                // System.out.println("HELLO");
                 max1=Math.max(max1,c1);
                 
                 c1=1;
                 
                 
            }
            c2=1;
            for(int i=0;i<n-1;i++)
             {
                 
                  while((i+1)<n && a[i]==2 && a[i]==a[i+1])
                 {
                     c2++;
                     i++;
                 }
                 
                  if(i==n)
                   break;
                 
                 max2=Math.max(max2,c2);
              //   System.out.println("HELLO");
                 
                 c2=1;
                  
             }
             
             System.out.println(2*Math.min(max1,max2));
    }
}