import java.io.*;
import java.util.*;

public class Main
{
    static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    static StreamTokenizer st=new StreamTokenizer(br);

    static int nextInt() throws IOException
    {
        st.nextToken();
        return (int)st.nval;
    }

    static String nextStr() throws IOException
    {
        st.nextToken();
        return st.sval;
    }

    static int brReadInt() throws IOException
    {
        return Integer.parseInt(br.readLine());
    }

    static long brReadLong() throws IOException
    {
        return Long.parseLong(br.readLine());
    }

    static int gcd(int a, int b)
    {
        return b==0 ? a : gcd(b, a%b);
    }


    public static void main(String[] args) throws IOException
    {
        int T=nextInt();
        while(T-->0)
        {
            int n=nextInt();
            String s=nextStr();
            char[] chs=s.toCharArray();

            Map<Integer, Integer> map=new HashMap<>();
            int[] dp=new int[n];
            int dCnt=0, kCnt=0;
            for(int i=0; i<n; i++)
            {
                if(chs[i]=='D')
                    dCnt++;
                else
                    kCnt++;

                if(i>0 && (dCnt==0 || kCnt==0))
                    dp[i]=dp[i-1]+1;

                int key=getKey(dCnt, kCnt);
                if(!map.containsKey(key))
                    dp[i]=1;
                else
                    dp[i]=dp[map.get(key)]+1;
                map.put(key, i);
            }

            for(int i=0; i<n; i++)
                System.out.print(dp[i]+" ");
            System.out.println();
        }
    }

    static int getKey(int d, int k)
    {
        if(d==0)
            return k;
        if(k==0)
            return 1000_000;

        int g=gcd(d, k);
        return d/g*1000_000+k/g;
    }
}

class HideArray
{
    int[] preSum;

    HideArray(int[] nums)
    {
        int n=nums.length;
        preSum=new int[n+1];
        for(int i=1; i<=n; i++)
            preSum[i]=preSum[i-1]+nums[i-1];
    }

    int getPreSum(int right)
    {
        return preSum[right];
    }
}