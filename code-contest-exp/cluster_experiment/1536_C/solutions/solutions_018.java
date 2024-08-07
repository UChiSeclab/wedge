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
            StringBuilder ans=new StringBuilder();

            Map<int[], Integer> map=new TreeMap<>((o1, o2)->o1[0]==o2[0] ? o1[1]-o2[1] : o1[0]-o2[0]);
            int dCnt=0, kCnt=0;
            for(int i=0; i<n; i++)
            {
                if(chs[i]=='D')
                    dCnt++;
                else
                    kCnt++;

                if(dCnt==0 || kCnt==0)
                {
                    System.out.print((i+1)+" ");
                    continue;
                }

                int g=gcd(dCnt, kCnt);
                int[] key={dCnt/g, kCnt/g};
                int res=map.getOrDefault(key, 0)+1;
                map.put(key, res);
                ans.append(res).append(" ");
            }
            System.out.println(ans);
        }
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