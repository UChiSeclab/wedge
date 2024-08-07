import java.util.Scanner;
public class sushi
{
    public static void main(String args[])
    {
        Scanner s= new Scanner(System.in);
        int n= s.nextInt();
        int t[]= new int[n+1];
        int i=1, j=0,max=0, c=0, sum=0, k;
        int b[]= new int[n];
        for(i=0; i<n; i++)
        {
            t[i]= s.nextInt();
        }
        for(i=1; i<=n; i++)
        {
            if(t[i]==t[i-1])
            {
                sum=sum+1;
            }
            else if(t[i]!=t[i-1])
            {
                b[j]= sum+1;
                sum=0;
                j++;
            }
        }
        if(b[0]<b[1])
        max=b[0];
        else
        max=b[1];
        for(i=2; i<j; i++)
        {
            if(b[i]<=b[i-1])
            {
                c=b[i];
            }
            else
            {
                c=b[i-1];
            }
            if(max<c)
            {
                max=c;
            }
        }
        System.out.println(2*max);
    }
}