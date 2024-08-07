import java.util.*;
import java.io.*;
public class Main
{
    static class FastReader 
    { 
        BufferedReader br; 
        StringTokenizer st; 
  
        public FastReader() 
        { 
            br = new BufferedReader(new
                     InputStreamReader(System.in)); 
        } 
  
        String next() 
        { 
            while (st == null || !st.hasMoreElements()) 
            { 
                try
                { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException  e) 
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
            String str = ""; 
            try
            { 
                str = br.readLine(); 
            } 
            catch (IOException e) 
            { 
                e.printStackTrace(); 
            } 
            return str; 
        } 
    }
    public static void main(String args[])
    {
        FastReader fs=new FastReader();
        PrintWriter pw=new PrintWriter(System.out);
        int t=fs.nextInt();
        while(t-->0)
        {
            int n=fs.nextInt();
            int a[]=new int[n];
            int c[]=new int[n+1];
            for(int i=0;i<n;i++)
            {
                a[i]=fs.nextInt();
                c[a[i]]++;
            }
            if(c[1]==0)
            {
                for(int i=0;i<n;i++)
                pw.print(0);
                pw.println();
            }
            else
            {
                if(a[0]!=1&&a[n-1]!=1)
                {
                    for(int i=1;i<n;i++)
                    pw.print(0);
                    pw.println(1);
                }
                else
                {
                    int till=-1;
                    for(int i=1;i<=n;i++)
                    {
                        if(c[i]>1)
                        {
                            till=i;
                            break;
                        }
                        else if(c[i]==0)
                        {
                            till=i-1;
                            break;
                        }
                    }
                    if(till!=-1)
                    {
                        for(int i=0;i<n;i++)
                        {
                            if(a[i]==till&&i+1<n&&a[i+1]<till)
                            {
                                int j=i+1;
                                int num=a[j];
                                while(j<n&&a[j]<=a[j-1])
                                {
                                    num=Math.min(num,a[j]);
                                    j++;
                                }
                                if(j==n)
                                break;
                                j=i-1;
                                till=Math.min(till,num);
                            }
                        }
                        for(int i=0;i<n;i++)
                        {
                            if(i<n-till)
                            pw.print(0);
                            else
                            pw.print(1);
                        }
                        pw.println();
                    }
                    else
                    {
                        int cnt=-1;
                        int min=n;
                        for(int i=0;i<n;i++)
                        {
                            if(i+1<n&&a[i+1]<a[i])
                            {
                                int j=i+1;
                                int num=a[j];
                                while(j<n&&a[j]<a[j-1])
                                {
                                    num=Math.min(num,a[j]);
                                    j++;
                                }
                                if(j==n)
                                break;
                                while(j<n&&a[j]>a[j-1])
                                j++;
                                cnt=Math.max(cnt,j-i);
                                min=Math.min(num,min);
                                i=j-1;
                            }
                        }
                        if(cnt==-1)
                        {
                            for(int i=0;i<n;i++)
                            pw.print(1);
                        }
                        else
                        {
                            if(n-cnt>min)
                            cnt=Math.min(min,cnt);
                            pw.print(1);
                            for(int i=1;i<n;i++)
                            {
                                if(i<cnt-1)
                                pw.print(0);
                                else
                                pw.print(1);
                            }
                        }
                        pw.println();
                    }
                }
            }
        }
        pw.flush();
        pw.close();
    }
}