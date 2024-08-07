import java.util.*;
import java.io.*;
public class Solution
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
    static final long mod=(long)1e9+7;
    public static long pow(long a,int p)
    {
        long res=1;
        while(p>0)
        {
            if(p%2==1)
            {
                p--;
                res*=a;
                res%=mod;
            }
            else
            {
                a*=a;
                a%=mod;
                p/=2;
            }
        }
        return res;
    }
    static class Pair
    {
        int u,v;
        Pair(int u,int v)
        {
            this.u=u;
            this.v=v;
        }
    }
    /*static class Pair implements Comparable<Pair>
    {
        int v,l;
        Pair(int v,int l)
        {
            this.v=v;
            this.l=l;
        }
        public int compareTo(Pair p)
        {
            return l-p.l; 
        }
    }*/
    static int gcd(int a,int b)
    {
        if(b%a==0)
        return a;
        return gcd(b%a,a);
    }
    public static class comp implements Comparator<Integer>
    {
        public int compare(Integer o1,Integer o2)
        {
            return o1%2-o2%2;
        }
    }
    public static void main(String args[])throws Exception
    {
        FastReader fs=new FastReader();
        PrintWriter pw=new PrintWriter(System.out);
        int tc=fs.nextInt();
        while(tc-->0)
        {
            int n=fs.nextInt();
            char c[]=fs.nextLine().toCharArray();
            int d=0,k=0;
            TreeMap<String,Integer> tmap=new TreeMap<>();
            for(int i=0;i<n;i++)
            {
                if(c[i]=='D')d++;
                else k++;
                int g=1;
                if(d>0&&k>0)
                g=gcd(d,k);
                else
                g=Math.max(d,k);
                int td=d/g,tk=k/g;
                tmap.put(td+" "+tk,tmap.getOrDefault(td+" "+tk,0)+1);
                pw.print(tmap.get(td+" "+tk)+" ");
            }
            pw.println();
        }
        pw.flush();
        pw.close();
    }
}