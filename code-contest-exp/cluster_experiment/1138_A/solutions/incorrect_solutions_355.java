import java.util.*;
import java.lang.*;
public class sushi
{
    public static void main(String []args) throws Exception
    {
        int n,a[],c[],ans=0;
        c=new int[2];
        c[0]=c[1]=1;
        Scanner s=new Scanner(System.in);
        n=s.nextInt();
        a=new int[n];
        for(int i=0;i<n;i++)
        {
            a[i]=s.nextInt();
            if(i>0)
            {
                if(a[i-1]==a[i])
                c[a[i]-1]++;
                else
                c[a[i]-1]=1;
                ans=c[0]<c[1]?c[0]:c[1];

            }
            
        }
        s.close();
        System.out.println(ans*2);
    }
}