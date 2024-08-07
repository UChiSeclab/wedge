import java.util.*;
public class S
{
      public static void main(String args[])
      {
            Scanner in=new Scanner(System.in);
            int n=in.nextInt();
            int a[]=new int[n];
            int b[]=new int[n];
            for(int i=0;i<n;i++)
            {
                  a[i]=in.nextInt();
            }
            for(int i=0;i<n;i++)
            {
                  b[i]=in.nextInt();
            }
            int sum=0,rel=0;
            for(int i=0;i<n-1;i++)
            {
                  for(int j=i+1;j<n;j++)
                  {
                        if(a[j]>a[i])
                        {
                              rel=b[j]-b[i];
                        }
                        else
                        {
                              rel=b[i]-b[j];
                        }
                        if(rel>=0)
                        {
                              sum+=(Math.abs(a[i]-a[j]));
                        }
                  
                        
                  }
            }
            System.out.println(sum);
      }
}