    import java.util.*;
    import java.io.*;
    public class A
    {
        public static void main(String [] args)
        {
            Scanner s=new Scanner(System.in);
           // int t=1;
          //  while(t-->0)
          //  {
                int n=s.nextInt();
                int A[]=new int[n];
                Arrays.fill(A,0);
                int a=s.nextInt();
                int j=0;
                A[j]++;
                for(int i=1;i<n;i++)
                {
                    int b=s.nextInt();
                    if(b!=a)
                    {
                        j++;
                        a=b;
                    }
                    A[j]++;
                }
              //  for(int i=0;i<n;i++)
              //  {
               //      System.out.print(A[i]+" ");
               // }
               // System.out.println(" ");
                int S=0,ans=0;
                S=A[0]+A[1];
                if(S%2==0)
                {
                    ans=S;
                }else
                ans=S-1;
                
                int max=ans;
                for(int i=2;i<=j;i++)
                {
                     S=A[i]+A[i-1];
                    if(S%2==0)
                    {
                        ans=S;
                    }else
                    ans=S-1;
                    if(ans>max && Math.abs(A[i]-A[i-1])<=1)
                    {
                        max=ans;
                    }
                }
                if(j==0)
                System.out.print(0);
                else
                System.out.print(max);
                
           // }
        }
    }