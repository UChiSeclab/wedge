import java.io.*;
import java.util.*;
public class Main {
    public static void main(String args[])
    {
        FastReader input=new FastReader();
        PrintWriter out=new PrintWriter(System.out);
        int T=input.nextInt();
        while(T-->0)
        {
            int n=input.nextInt();
            String s=input.next();
            int pre[][]=new int[n][2];
            int d=0,k=0;
            for(int i=0;i<s.length();i++)
            {
                if(s.charAt(i)=='D')
                {
                    d++;
                }
                else
                {
                    k++;
                }
                pre[i][0]=d;
                pre[i][1]=k;
            }
            int dp[]=new int[n];
            for(int i=0;i<n;i++)
            {
                int l=i+1;
                int c=1;
                d=pre[i][0];
                k=pre[i][1];
                int x=1;
                for(int j=i;j<n;j+=l)
                {
                    if(pre[j][0]==d*x && pre[j][1]==k*x)
                    {
                        dp[j]=Math.max(dp[j],c);
                        c++;
                    }
                    x++;
                }
            }
            for(int i=0;i<n;i++)
            {
                out.print(dp[i]+" ");
            }
            out.println();
        }
        out.close();
    }
    static class FastReader
    {
        BufferedReader br;
        StringTokenizer st;
        public FastReader()
        {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        String next()
        {
            while (st == null || !st.hasMoreElements())
            {
                try
                {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
        int nextInt()
        {
            return Integer.parseInt(next());
        }
        long nextLong()
        {
            return Long.parseLong(next());
        }
        double nextDouble()
        {
            return Double.parseDouble(next());
        }
        String nextLine()
        {
            String str="";
            try
            {
                str=br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
    }
}