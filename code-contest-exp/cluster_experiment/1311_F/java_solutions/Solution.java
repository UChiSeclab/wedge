import java.util.*;
import java.io.*;
import java.math.BigInteger;
public class Solution
{
     static class Pair<A,B>{
        A parent;
        B rank;
        Pair(A parent,B rank)
        {
            this.rank=rank;
            this.parent=parent;
        }
    }
//    static int find(Pair pr[],int i)
//    {
//        if(i==pr[i].parent)
//            return i;
//        else
//        {
//            pr[i].parent=find(pr,pr[i].parent);
//            return pr[i].parent;
//        }
//    }
//    static void join(Pair[] pr,int i,int j)
//    {
//        i=find(pr,i);
//        j=find(pr,j);
//        if(i==j)
//            return;
//        if(pr[i].rank>=pr[j].rank)
//        {
//            pr[j].parent=i;
//            pr[i].rank+=pr[j].rank;
//        }
//        else
//        {
//            pr[i].parent = j;
//            pr[j].rank+=pr[i].rank;
//        }
//    }
    static void swap(char ar[],int i,int j)
    {
        char f = ar[i];
        ar[i]=ar[j];
        ar[j]=f;
    }
    static ArrayList<Integer> graph[] ;
    static TreeSet<Long> ts;
    public static void main (String[] args) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        FastReader s1 = new FastReader();
             int n=s1.I();
             int xr[] = new int[n];
             int v[] = new int[n];
             for(int i=0;i<n;i++)
             {
                 xr[i]=s1.I();
             }
             for(int i=0;i<n;i++)
             {
                 v[i]=s1.I();
             }
             long ans=0;
             for(int i=0;i<n;i++)
             {
                 for(int j=i+1;j<n;j++)
                 {
                     long c=xr[i]-xr[j];
                     long T=v[i]-v[j];
                     ans+=mindis(c,T);
//                     System.out.println(ans+" "+c+" "+T);
                 }
             }
             sb.append(ans+"\n");
        System.out.println(sb);
    }
    static long mindis(long c,long t)
    {
        if(c==0)
            return c;
        if(t==0)
            return Math.abs(c);
        
        if(c*t>0)
        {
            return Math.abs(c);
        }
        else
        {
            c=Math.abs(c);
            t=Math.abs(t);
            long temp=(c/t);
            long min=Math.abs(c-t*temp);
            long demo=Math.abs(c-t*(temp+1));
//            System.out.println(temp+" "+min+" "+demo+" "+c+" "+t);
            return Math.min(min,demo);
        }
    }
    static boolean print(int h,int b,int a)
    {
        if(h*h==(a*a+b*b))
            return true;
        return false;
    }
    static long min(long no,long temp)
    {
        if(no<temp)
        {
            return -1;
        }
        long ans=no-temp;
        long max=-1;
        int n = (int)(Math.log(no)/Math.log(2));
        long curr=temp;
        for(int i=n;i>=0;i--)
        {
            long demo=(long)Math.pow(2, i);
            long h=(demo|curr);
            if(h<=no && h>max)
            {
                max=h;
                curr=h;
            }
        }
        return max;
    }
    static int dayofweek(long d, int m, long y) 
{ 
    int t[] = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 }; 
    y -= (m < 3) ? 1 : 0; 
    return (int)( y + y/4 - y/100 + y/400 + t[(m-1)] + d) % 7; 
}
        static String preprocess(String s)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("@#");
        for(int i=0;i<s.length();i++)
        {
            sb.append(s.charAt(i)).append("#");
        }
        sb.append("$");
        return sb.toString();
    }
    static String LPS(String s)
    {
        String snew = preprocess(s);
        int n = snew.length();
//        System.out.println(snew);
        int c = 0,r = 0;
        int maxlen = 0;
        int index=0;
        int len[] = new int[n];
        for(int i=1;i<n-1;i++)
        {
            int mirr = c-(i-c);
            if(i<r)
            {
                len[i]=Math.min(len[mirr],r-i);
            }
      //      System.out.println(i+" ");
            while(snew.charAt(i+len[i])==snew.charAt(i-len[i]))
                len[i]++;
            if(i+len[i]>r)
            {
                c = i;
                r = i+len[i];
            }
//            System.out.println(c+" "+i+" "+snew.charAt(i)+" "+len[i]+" "+maxlen+" "+index);
            if(len[i]>maxlen)
            {
//                System.out.println("inside");
                maxlen = len[i];
                index = i;
            }
        }
        maxlen--;
        int left = (index-maxlen-1)/2;
        int right = left+maxlen;
        return s.substring(left, right);
    }
    static long phi(long n)
    {
        long result=n;
        for(int i=2;i<=Math.sqrt(n);i++)
        {
            if(n%i==0)
            {
                result-=result/i;
            }
            while(n%i==0)
            {
                n=n/i;
            }
        }
        if(n>1)
        {
            result-=(result/n);
        }
        return result;
    }
    static ArrayList<Integer> primeFactor(int n)
    {
        ArrayList<Integer> ans = new ArrayList<>();
        if(n%2==0)
            ans.add(2);
        while (n%2==0)
        {   
            n /= 2;
        }
        for (int i = 3; i <= Math.sqrt(n); i+= 2)
        {
            if(n%i==0)
                ans.add(i);
            while (n%i == 0)
            {
                n /= i;
            }
        }
        if (n > 2)
            ans.add(n);
        return ans;
    }
    static int longestSubSeg(int a[], int n,int k)
    {
        int cnt0 = 0;
        int l = 0;
        int max_len = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] == 0)
                cnt0++;
            while (cnt0 > k) {
                if (a[l] == 0)
                    cnt0--;
                l++;
            }
            max_len = Math.max(max_len, i - l + 1);
        }
        
        return max_len;
    }
    static void dfs(int index,boolean ar[])
    {
        ar[index]=true;
        for(int i=0;i<graph[index].size();i++)
        {
            if(!ar[graph[index].get(i)])
            {
                dfs(graph[index].get(i),ar);
            }
        }
    }
    static int binary(int left,int right,int ar[],int no)
    {
        int mid=(left+right)/2;
        if(Math.abs(right-left)<=1)
        {
            if(no>=ar[right])
            {
                return right+1;
            }
            else if(no<ar[left])
            {
                return left;
            }
            else
            {
                return left+1;
            }
        }
        if(ar[mid]>no)
        {
            right=mid-1;
            return binary(left, right, ar, no);
        }
        else{
            left=mid;
            return binary(left, right, ar, no);
        }
    }
    static class FastReader{
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
        
        int I()
        {
            return Integer.parseInt(next());
        }
        
        long L()
        {
            return Long.parseLong(next());
        }
        
        double D()
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
    static long gcd(long a,long b)
    {
        if(a%b==0)
            return b;
        return gcd(b,a%b);
    }
    static float power(float x, int y)
    {
        float temp;
        if( y == 0)
            return 1;
        temp = power(x, y/2);
        
        if (y%2 == 0)
            return temp*temp;
        else
        {
            if(y > 0)
                return x * temp * temp;
            else
                return (temp * temp) / x;
        }
    }
    static long pow(long x, long y)
    {
        int p = 1000000007;
        long res = 1;
        x = x % p;
        while (y > 0)
        {
            if((y & 1)==1)
                res = (res * x) % p;
            y = y >> 1;
            x = (x * x) % p;
        }
        return res;
    }
    static ArrayList<Integer> sieveOfEratosthenes(int n)
    {
        ArrayList<Integer> arr=new ArrayList<Integer>();
        boolean prime[] = new boolean[n+1];
        for(int i=2;i<n;i++)
            prime[i] = true;
        
        for(int p = 2; p*p <=n; p++)
        {
            if(prime[p] == true)
            {
                arr.add(p);
                for(int i = p*p; i <= n; i += p)
                    prime[i] = false;
            }
        }
        return arr;
    }
}
