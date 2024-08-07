import java.io.*;
import java.util.*;
//import javafx.util.Pair;
import java.math.*;
import java.math.BigInteger;
import java.util.LinkedList; 
import java.util.Queue; 
public final  class CodeForces
{    
    static StringBuilder ans=new StringBuilder();
    static FastReader in=new FastReader();
    static ArrayList<ArrayList<Integer>> g;
    static long mod=998244353 ;
    static boolean set[];   
    static int A[];
    static int seg[];
    public static void main(String args[])throws IOException
    {
        int t=i();
        while(t-->0)
        {
            int N=i();
            A=new int[N+1];
            seg=new int[4*N+2];
            int f[]=new int[N+1];

            boolean one=false;
            int c=0;
            for(int i=1; i<=N; i++)
            {
                A[i]=i();
                if(f[A[i]]==0)
                {
                    c++;

                }
                f[A[i]]++;
                if(A[i]==1)one=true;
            }
            //  create(1,1,N);
            // for(int i=0; i<4*N; i++)System.out.print(seg[i]+" ");
            //System.out.println();
            int l=1,r=N;
            int ind=N;
            boolean X[]=new boolean[N+1];

            for(int i=1; i<N; i++)
            {
                if(f[i]==0)break;
                X[ind]=true;
                ind--;

                if(A[l]==i)
                {
                    l++;

                    int min=f[i];
                    System.out.println(min+" "+i);
                    if(min>1)break;

                }
                else if(A[r]==i)
                {
                    //System.out.println(i+" "+A[i]+" R"+r);
                    r--;
                    int min=f[i];
                    if(min>1)break;
                }
                else 
                {

                    break;
                }

            }
            X[1]=(c==N);
            X[N]=one;
            for(int i=1; i<=N; i++)ans.append(X[i]?"1":"0");
            ans.append("\n");
        }
        System.out.println(ans);
    }

    static void create(int v,int l,int r)
    {
        if(l==r)seg[v]=A[l];
        else
        {
            int m=(l+r)/2;
            create(2*v,l,m);
            create(2*v+1,m+1,r);
            seg[v]=Math.min(seg[2*v],seg[2*v+1]);
        }
    }

    static int find(int v,int tl,int tr,int l,int r)
    {
        if(l>r)return 0;
        if(l==tl && r==tr)return seg[v];
        int tm=(tl+tr)/2;
        int x=find(v*2,tl,tm,l,Math.min(r,tm));
        int y=find(v*2+1,tm+1,tr,Math.max(l,tm+1),r);
        if(x==0 || y==0)return Math.max(x,y);
        else return Math.min(x,y);
    }

    static void print(int A[])
    {
        for(int i:A)System.out.print(i+" ");
        System.out.println();
    }

    // cre
    static  long pow(long a,long b)
    {
        //long mod=1000000007;
        long pow=1;
        long x=a;
        while(b!=0)
        {
            if((b&1)!=0)pow=(pow*x)%mod;
            x=(x*x)%mod;
            b/=2;
        }
        return pow;
    }

    static void sort(long[] a) //check for long
    {
        ArrayList<Long> l=new ArrayList<>();
        for (long i:a) l.add(i);
        Collections.sort(l);
        for (int i=0; i<a.length; i++) a[i]=l.get(i);
    }

    static String swap(String X,int i,int j)
    {
        char ch[]=X.toCharArray();
        char a=ch[i];
        ch[i]=ch[j];
        ch[j]=a;
        return new String(ch);
    }

    static int sD(long n)  
    {  

        if (n % 2 == 0 )  
            return 2;  

        for (int i = 3; i * i <= n; i += 2) {  
            if (n % i == 0 )  
                return i;  
        }  

        return (int)n;  
    }  

    static void setGraph(int N)
    {
        set=new boolean[N+1];
        g=new ArrayList<ArrayList<Integer>>();
        for(int i=0; i<=N; i++)
            g.add(new ArrayList<Integer>());
    }

    static void DFS(int N,int d)
    {
        set[N]=true;
        d++;
        for(int i=0; i<g.get(N).size(); i++)
        {
            int c=g.get(N).get(i);
            if(set[c]==false)
            {
                DFS(c,d);
            }
        }
    }

    static long toggleBits(long x)//one's complement || Toggle bits
    {
        int n=(int)(Math.floor(Math.log(x)/Math.log(2)))+1;

        return ((1<<n)-1)^x;
    }

    static int countBits(long a)
    {
        return (int)(Math.log(a)/Math.log(2)+1);
    }

    static long fact(long N)
    { 
        long n=2; 
        if(N<=1)return 1;
        else
        {
            for(int i=3; i<=N; i++)n=(n*i)%mod;
        }
        return n;
    }

    static int kadane(int A[])
    {
        int lsum=A[0],gsum=A[0];
        for(int i=1; i<A.length; i++)
        {
            lsum=Math.max(lsum+A[i],A[i]);
            gsum=Math.max(gsum,lsum);
        }
        return gsum;
    }

    static void sort(int[] a) {
        ArrayList<Integer> l=new ArrayList<>();
        for (int i:a) l.add(i);
        Collections.sort(l);
        for (int i=0; i<a.length; i++) a[i]=l.get(i);
    }

    static boolean isPrime(long N)
    {
        if (N<=1)  return false; 
        if (N<=3)  return true; 
        if (N%2 == 0 || N%3 == 0) return false; 
        for (int i=5; i*i<=N; i=i+6) 
            if (N%i == 0 || N%(i+2) == 0) 
                return false; 
        return true; 
    }

    static int i()
    {
        return in.nextInt();
    }

    static long l()
    {
        return in.nextLong();
    }

    static int[] input(int N){
        int A[]=new int[N];
        for(int i=0; i<N; i++)
        {
            A[i]=in.nextInt();
        }
        return A;
    }

    static long[] inputLong(int N)     {
        long A[]=new long[N];
        for(int i=0; i<A.length; i++)A[i]=in.nextLong();
        return A;
    }

    static long GCD(long a,long b) 
    {
        if(b==0)
        {
            return a;
        }
        else return GCD(b,a%b );
    }

}
//Code For FastReader
//Code For FastReader
//Code For FastReader
//Code For FastReader
class FastReader
{
    BufferedReader br;
    StringTokenizer st;
    public FastReader()
    {
        br=new BufferedReader(new InputStreamReader(System.in));
    }

    String next()
    {
        while(st==null || !st.hasMoreElements())
        {
            try
            {
                st=new StringTokenizer(br.readLine());
            }
            catch(IOException e)
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