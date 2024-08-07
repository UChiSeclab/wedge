//I'm Whiplash99
import java.io.*;
import java.util.*;
public class C
{
    private static final int lim=(int)(2e6);
    private static int[] spf;
    private static void sieve()
    {
        spf=new int[lim+5];
        spf[0]=spf[1]=-1;

        for(int i=2;i<=lim;i++)
        {
            if(spf[i]==0)
            {
                spf[i] = i;
                if ((long) i * i <= lim)
                {
                    for (int j = i * i; j <= lim; j += i)
                        if (spf[j] == 0) spf[j] = i;
                }
            }
        }
    }

    private static long _gcd(long a, long b) {return a==0?b:_gcd(b%a,a);}
    public static void main(String[] args) throws Exception
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        int i,N;

        int T=Integer.parseInt(br.readLine().trim());
        StringBuilder sb=new StringBuilder();

        sieve();

        while (T-->0)
        {
            N=Integer.parseInt(br.readLine().trim());
            char[] str=br.readLine().trim().toCharArray();

            long[] ans=new long[N];
            int c1=0,c2=0;

            HashSet<Integer> set=new HashSet<>();

            for(i=0;i<N;i++)
            {
                if(str[i]=='D') c1++;
                else c2++;

                if(c1==0||c2==0) ans[i]=Math.max(c1,c2);
                else
                {
                    set.clear();
                    long g=_gcd(c1,c2);

                    ans[i]=1;

                    int tmp=(int)g;
                    while (tmp>1)
                    {
                        set.add(spf[tmp]);
                        tmp/=spf[tmp];
                    }

                    for(int x:set)
                    {
                        int a=c1-c1/x;
                        int b=c2-c2/x;

                        if(ans[a+b-1]==x-1) ans[i]=Math.max(ans[i],x);
                    }
                }
            }

            for(long x:ans) sb.append(x).append(" ");
            sb.append("\n");
        }
        System.out.println(sb);
    }
}