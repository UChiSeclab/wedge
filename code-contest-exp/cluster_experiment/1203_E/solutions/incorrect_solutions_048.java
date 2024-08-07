import java.io.*;
import java.util.*;
import java.lang.*;

public class boxer
{
     public static void main(String args[])throws IOException
     {
          BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
          int n=Integer.parseInt(br.readLine());
          int a[]=new int[n];
          StringTokenizer tk=new StringTokenizer(br.readLine());
          int i=0;
          for(i=0;i<n;i++)
          {
               a[i]=Integer.parseInt(tk.nextToken());
               
          }
          HashSet<Integer> h=new HashSet<Integer>();
          for(i=0;i<n;i++)
          {
                if(h.contains(a[i])==false)
               h.add(a[i]);
               else if((a[i]-1)>0 && h.contains(a[i]-1)==false)
               h.add(a[i]-1);
              
               else if(h.contains(a[i]+1)==false)
               h.add(a[i]+1);
          }
          System.out.println(h.size());
     }
}