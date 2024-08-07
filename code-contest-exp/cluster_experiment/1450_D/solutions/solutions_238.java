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
            int a[]=new int[n+2];
            a[0]=0;
            int arr[]=new int[n+1];
            for(int i=1;i<=n;i++)
            {
                a[i]=input.nextInt();
                arr[a[i]]++;
            }
            a[n+1]=0;
            int i=1;
            int l=0;
            int x=0,y=0;
            int max=0;
            while(i<=n)
            {
                if(a[i]>a[i-1])
                {
                    l=i-1;
                    i++;
                }
                else
                {
                    for(int j=l;j>=0;j--)
                    {
                        if(a[j]<a[i])
                        {
                            x=j;
                            break;
                        }
                    }
                    for(int j=i+1;j<=n+1;j++)
                    {
                        if(a[j]<a[i])
                        {
                            y=j;
                            break;
                        }
                    }
                    l=x;
                    if(!(i==x+1 || i==y-1))
                    {
                        max=Math.max(max,y-x-2);
                    }
                    i=y;
                }
            }
            int m1=0;
            for(int j=1;j<=n;j++)
            {
                if(arr[j]==0)
                {
                    m1=Math.max(m1,n-j+1);
                }
                else if(arr[j]>1)
                {
                    m1=Math.max(m1,n-j);
                }
            }
            char ch[]=new char[n];
            for(int j=0;j<n;j++)
            {
                ch[j]='1';
            }
            for(int j=0;j<m1;j++)
            {
                ch[j]='0';
            }
            for(int j=1;j<max;j++)
            {
                ch[j]='0';
            }
            out.println(ch);
        }
        out.close();
    }
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}