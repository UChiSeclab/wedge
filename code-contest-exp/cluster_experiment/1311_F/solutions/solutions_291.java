import java.io.*;
import java.util.*;
public class Main {
    public static void main(String args[])
    {
        FastReader input=new FastReader();
        PrintWriter out=new PrintWriter(System.out);
        int T=1;
        while(T-->0)
        {
            int n=input.nextInt();
            int p[][]=new int[n][2];
            for(int i=0;i<n;i++)
            {
                p[i][0]=input.nextInt();
            }
            int v[]=new int[n];
            HashSet<Integer> set=new HashSet<>();
            for(int i=0;i<n;i++)
            {
                p[i][1]=input.nextInt();
                v[i]=p[i][1];
            }
            mergeSort(p,0,n-1);
            mergeSort(v,0,n-1);
            long xs[]=new long[n+1];
            HashMap<Integer,Integer> map=new HashMap<>();
            for(int i=0;i<n;i++)
            {
                map.put(v[i],(i+1));
            }
            long cnt[]=new long[n+1];
            long ans=0;
            for(int i=0;i<n;i++)
            {
                int ve=p[i][1];
                int in=map.get(ve);
                long sum=sum(xs,in);
                long count=sum(cnt,in);
                long val=p[i][0]*count-sum;
                ans+=val;
                update(xs,in,p[i][0],n);
                update(cnt,in,1,n);
            }
            out.println(ans);
        }
        out.close();
    }
    public static void mergeSort(int a[],int p,int r)
    {
        if(p<r)
        {
            int q=(p+r)/2;
            mergeSort(a,p,q);
            mergeSort(a,q+1,r);
            merge(a,p,q,r);
        }
    }
    public static void merge(int a[],int p,int q,int r)
    {
        int n1=q-p+2;
        int L[]=new int[n1];
        int n2=r-q+1;
        int R[]=new int[n2];
        for(int i=p;i<=q;i++)
        {
            L[i-p]=a[i];
        }
        L[n1-1]=Integer.MAX_VALUE;
        for(int i=q+1;i<=r;i++)
        {
            R[i-q-1]=a[i];
        }
        R[n2-1]=Integer.MAX_VALUE;
        int x=0,y=0;
        for(int i=p;i<=r;i++)
        {
            if(L[x]<=R[y])
            {
                a[i]=L[x];
                x++;
            }
            else
            {
                a[i]=R[y];
                y++;
            }
        }
    }
    public static void mergeSort(int a[][],int p,int r)
    {
        if(p<r)
        {
            int q=(p+r)/2;
            mergeSort(a,p,q);
            mergeSort(a,q+1,r);
            merge(a,p,q,r);
        }
    }
    public static void merge(int a[][],int p,int q,int r)
    {
        int n1=q-p+2;
        int L[][]=new int[n1][2];
        int n2=r-q+1;
        int R[][]=new int[n2][2];
        for(int i=p;i<=q;i++)
        {
            L[i-p]=a[i];
        }
        L[n1-1][0]=Integer.MAX_VALUE;
        for(int i=q+1;i<=r;i++)
        {
            R[i-q-1]=a[i];
        }
        R[n2-1][0]=Integer.MAX_VALUE;
        int x=0,y=0;
        for(int i=p;i<=r;i++)
        {
            if(L[x][0]<=R[y][0])
            {
                a[i]=L[x];
                x++;
            }
            else
            {
                a[i]=R[y];
                y++;
            }
        }
    }
    public static long sum(long xs[],int k)
    {
        long sum=0;
        while(k>0)
        {
            sum+=xs[k];
            k-=k&-k;
        }
        return sum;
    }
    public static void update(long xs[],int k,int d,int n)
    {
        while(k<=n)
        {
            xs[k]+=d;
            k+=k&-k;
        }
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