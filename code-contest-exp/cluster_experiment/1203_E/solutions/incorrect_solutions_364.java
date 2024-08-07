import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.BigInteger;
 
public class Codechef
{
    static int A=Integer.MAX_VALUE;
    static int B=0;
    static int C=0;
    static int a=0;
    static int b=0;
    static int c=0;
    static void divide(long N)
    {
        for (int x=(int)Math.floor(Math.sqrt(N));x>=1;x--)
        {
            if (N%x==0)
            {
                a=x;
                divide1(N/x);
               if (a+b+c<=A+B+C)
               {A=a;B=b;C=c;}
            }
        }
        return ;
    }
    static void divide1(long N)
    {
        for (int x=1;x*x<=N;x++)
        {
            if (N%x==0)
            {
                if (check(N/x))
                {c=x;break;}
                else if(check(x))
                {c=(int)(N/x);//System.out.println (c+"gdfg");
                break;}
                else
                continue;
            }
        }
        return ;
    }
    static boolean check(double N)
    {
        double k=1+(double)Math.sqrt(1+8*N);
        k=k/2;
        if (Math.ceil(k)==Math.floor(k))
        {b=(int)k;return (true);}
        else
        return (false);
    }
	public static void main (String[] args) throws java.lang.Exception
	{
	    Reader in = new Reader();
	    int N=in.nextInt();
	    int a[] = new int[N];
	    int f[] = new int[150002];
	    for (int x=0;x<N;x++)
	    {
	        a[x]=in.nextInt();
	        f[a[x]]++;
	    }
	    int p[] = new int[150002];
	    for (int x=1;x<150002;x++)
	    {
	        if (x==1 && f[x]>0)
	        {
	            p[1]=1;
	            if (f[x]>=2)
	            p[2]=1;
	        }
	        else if (x>1 && f[x]>0)
	        {
	            if (f[x]==1 && p[x]!=1)
	            p[x]=1;
	            else if (f[x]==1 && p[x]==1)
	            p[x+1]=1;
	            if (f[x]==2)
	            {
	                if (p[x]!=1)
	                {
	                    p[x]=1;
	                    if (p[x-1]==1)
	                    p[x+1]=1;
	                    else
	                    p[x-1]=1;
	                }
	                else
	                {p[x+1]=1;p[x-1]=1;}
	            }
	            else if (f[x]>2)
	            {p[x]=1;p[x+1]=1;p[x-1]=1;}
	        }
	    }
	    int ans=0;
	    for (int x=1;x<150002;x++)
	    {
	        ans=ans+p[x];
	      //  if (p[x]==1)
	       // System.out.println (x);
	    }
	    System.out.println (ans);
	}
    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;
 
        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
 
        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
 
        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }
 
        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');
 
            if (neg)
                return -ret;
            return ret;
        }
 
        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }
 
        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
 
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
 
            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }
 
            if (neg)
                return -ret;
            return ret;
        }
 
        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }
 
        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }
 
        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }
}