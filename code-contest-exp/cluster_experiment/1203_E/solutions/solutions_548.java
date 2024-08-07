

import java.io.*;
import java.util.*;
public class E1203 {
    public static void main(String args[])throws IOException
        {
            Scanner sc=new Scanner(System.in);
            int n=sc.nextInt();
            int cnt[]=new int[150001];
            for(int i=1;i<=n;i++)
            {
                cnt[sc.nextInt()]++;
            }
            int arr[]=new int[150002];int c=0;
            for(int i=150000;i>=1;i--)
            {
                if(cnt[i]>0)
                {
                    if(arr[i+1]==0)
                    {
                        arr[i+1]=1;
                        cnt[i]--;
                    }
                    if(arr[i]==0 && cnt[i]>0)
                    {
                        arr[i]=1;
                        cnt[i]--;
                    }
                    if(cnt[i]>0)
                        arr[i-1]=1;
                }
            }
            for(int i=1;i<=150001;i++)
            {
                if(arr[i]==1)
                    c++;
            }
            System.out.println(c);
        }

}
