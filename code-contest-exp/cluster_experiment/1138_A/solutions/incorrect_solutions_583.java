import java.util.*;

public class Sample
{
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();

        int[] arr=new int[n];
        for(int i=0;i<n;i++)
            arr[i]=sc.nextInt();

        int count1=0;
        int maxCount1=Integer.MIN_VALUE;
        int actualCount=0;
        for(int i=0;i<n;i++)
        {
            if(arr[i]==2)
            {
                if(count1>maxCount1)
                {
                    maxCount1=count1;
                    int count2=1;
                    for(int j=i+1;j<i+count1;j++)
                    {
                        if(arr[i]==2)
                            count2++;
                        else
                            break;
                    }
                    if(count2==count1)
                        actualCount=count2+count1;
                }
                count1=0;
            }
            else
                count1++;
        }
        System.out.println(actualCount);
        sc.close();
    }
}
