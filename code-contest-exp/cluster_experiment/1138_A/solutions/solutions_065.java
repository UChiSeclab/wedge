import java.util.*;

public class Sample
{
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();

        int[] arr=new int[n];
        int[] ans=new int[n];

        for(int i=0;i<n;i++)
            arr[i]=sc.nextInt();

        int numberUpcoming=arr[0];
        int j=0;

        for(int i=0;i<n;i++)
        {
            if(numberUpcoming==arr[i])
                ans[j]++;
            else
            {
                numberUpcoming=arr[i];
                j++;
                ans[j]=1;
            }
        }

        int maxCount=Integer.MIN_VALUE;
        for(int i=0;i<j;i++)
        {
            int temp=Integer.min(ans[i],ans[i+1]);
            maxCount=Integer.max(maxCount,temp);
        }
        System.out.println(2*maxCount);
        sc.close();
    }
}
