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
	    ArrayList<Integer> a = new ArrayList<Integer>();
	    
	    for (int x=0;x<N;x++)
	    a.add(in.nextInt());
	    
	    Collections.sort(a);
	    
	    int min=Math.max(1,a.get(0)-1);
	    int max=Math.min(150001,a.get(N-1)+1);
	    int k=min;
	    int x=0;
	    int ans=0;
	    while (k<=max && x<N)
	    {
	        if (Math.abs(a.get(x)-k)<=1)
	        {k++;ans++;x++;}
	        else
	        {
	            if (k<a.get(x))
	            k++;
	            else
	            x++;
	        }
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