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
        int actualCount1=0;
        for(int i=0;i<n;i++)
        {
            if(arr[i]==2)
            {
                if(count1>maxCount1)
                {
                    maxCount1=count1;
                    int tempCount2=1;
                    for(int j=i+1;j<i+count1;j++)
                    {
                        if(arr[i]==2)
                            tempCount2++;
                        else
                            break;
                    }
                    if(tempCount2==count1)
                        actualCount1=tempCount2+count1;
                }
                count1=0;
            }
            else
                count1++;
        }

        int count2=0;
        int maxCount2=Integer.MIN_VALUE;
        int actualCount2=0;
        for(int i=0;i<n;i++)
        {
            if(arr[i]==1)
            {
                if(count2>maxCount2)
                {
                    maxCount2=count2;
                    int tempCount1=1;
                    for(int j=i+1;j<i+count2;j++)
                    {
                        if(arr[i]==2)
                            tempCount1++;
                        else
                            break;
                    }
                    if(tempCount1==count2)
                        actualCount2=tempCount1+count2;
                }
                count2=0;
            }
            else
                count2++;
        }

        System.out.println(Integer.max(actualCount1,actualCount2));
        sc.close();
    }
}
