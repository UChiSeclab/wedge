import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;
public class Main extends Thread  {
    boolean[] prime;
    FastScanner sc;
    PrintWriter pw;
    MathF mf;
    final class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner() {
            try {
                br = new BufferedReader(new InputStreamReader(System.in));
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public long nlo() {
            return Long.parseLong(next());
        }

        public String next() {
            if (st.hasMoreTokens()) return st.nextToken();
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return st.nextToken();
        }

        public int ni() {
            return Integer.parseInt(next());
        }

        public String nli() {
            String line = "";
            if (st.hasMoreTokens()) line = st.nextToken();
            else try {
                return br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (st.hasMoreTokens()) line += " " + st.nextToken();
            return line;
        }

        public double nd() {
            return Double.parseDouble(next());
        }
    }
    final class MathF {
        long grtdiv(long x)
        {
            if(isPrime(x))
                return 1;
            else
            {
                long ans=0;
                long y=(long)Math.sqrt(x)+1;
                for(;y>=2;y--)
                {
                    if(x%y==0)
                        ans=Math.max(ans,Math.max(y,x/y));
                }
                return ans;
            }
        }
        boolean isPrime(long x)
        {
            for(int i=2;i*i<=x;i++)
            {
                if(x%i==0)
                {
                    return false;
                }
            }
            return true;
        }
        long[][] mul(long[][] arr,long[][] brr,int a,int b,int c,int d)
        {
            long[][] crr=new long[a][d];
            for(int i=0;i<a;i++)
            {
                for(int j=0;j<d;j++)
                {
                    for(int k=0;k<b;k++)
                    {
                        crr[i][j]+=(((arr[i][k]%1000000007)*(brr[k][j]%1000000007))%1000000007);
                    }
                }
            }
            return crr;
        }
        int sod(long a)
        {
            int ans=0;
            while(a!=0)
            {
                ans+=(a%10);
                a=a/10;
            }
            return ans;
        }
        long pow(long a,long b)
        {
            if(b==0)
                return 1;
            else
            {
                long x=pow(a,b/2);
                if(b%2==1)
                    return (x*x*a);
                else
                    return (x*x);
            }
        }
        public  long gcd(long a, long b) {
            return (b == 0 ? a : gcd(b, a % b));
        }
        public long exponent(long a,long b)
        {
            if(b==0)
                return 1;
            else
            {
                long x=exponent(a,b/2);
                x*=x;
                if(b%2==1)
                    x*=a;
                return x;
            }
        }
        public  int nod(long x) {
            int i = 0;
            while (x != 0) {
                i++;
                x = x / 10;
            }
            return i;
        }

        public  int nob(long x) {
            if(x==0)
                return 1;
            return (int) Math.floor(Math.log(x) / Math.log(2)) + 1;
        }

        public int[] FastradixSort(int[] f) {
            int[] to = new int[f.length];
            {
                int[] b = new int[65537];
                for (int i = 0; i < f.length; i++) b[1 + (f[i] & 0xffff)]++;
                for (int i = 1; i <= 65536; i++) b[i] += b[i - 1];
                for (int i = 0; i < f.length; i++) to[b[f[i] & 0xffff]++] = f[i];
                int[] d = f;
                f = to;
                to = d;
            }
            {
                int[] b = new int[65537];
                for (int i = 0; i < f.length; i++) b[1 + (f[i] >>> 16)]++;
                for (int i = 1; i <= 65536; i++) b[i] += b[i - 1];
                for (int i = 0; i < f.length; i++) to[b[f[i] >>> 16]++] = f[i];
                int[] d = f;
                f = to;
                to = d;
            }
            return f;
        }

        public  void Primesieve(int n) {
            prime=new boolean[n];
            for (int i = 0; i < n; i++)
                prime[i] = true;
            for (int p = 3; p * p < n; p+=2) {
                if (prime[p] == true) {
                    for (int i = p * p; i < n; i += p)
                        prime[i] = false;
                }
            }
        }
    }
    public Main(ThreadGroup t,Runnable r,String s,long d )
    {
        super(t,r,s,d);
    }
    public void run()
    {
        sc=new FastScanner();
        pw=new PrintWriter(System.out);
        mf=new MathF();
        solve();
        pw.flush();
        pw.close();
    }
    public static void main(String[] args)
    {
        new Main(null,null,"",1<<28).start();
    }

    /////////////----------------------------------------/////////////
    ////////////------------------MAIN-LOGIC-------------/////////////
    ////////////----------Remember dfs reqire ----------/////////////
    ///////////---------- thread  for stack size -------/////////////
    ////////////--------------------------------------///////////////

    public void solve(){
        long t=1;
        while(t-->0) {
            int n=sc.ni();
            int[] arr=new int[ 150002];
            int[] add=new int[150002];
            for(int i=0;i<n;i++)
            {
                arr[sc.ni()]++;
            }
            if(arr[1]>1)
            {
                add[2]++;
                arr[1]--;
            }
            for(int i=2;i<=15;i++)
            {
                if(arr[i]>=2)
                {
                    if(arr[i-1]==0&&add[i-1]==0)
                        add[i-1]++;
                    else
                        add[i+1]++;
                    arr[i]--;
                }
                if(arr[i]>=2)
                {
                    add[i+1]++;
                    arr[i]--;
                }
              //  pw.println(arr[i-1]+" "+arr[i]+" "+arr[i+1]);
            }
            long ans=0;
            for(int i=0;i<=150001;i++)
                if((arr[i]+add[i])>0)
                    ans++;
            pw.println(ans);
        }
    }
}