/*
    Idea from Blogewoosh #6
 */

//created by Whiplash99
import java.io.*;
import java.util.*;
public class F
{
    private static final int max=(int)(1e9);
    private static int[] a,s,f,c,r,order;
    private static void shuffleArray(int[] arr)
    {
        int n = arr.length;
        Random rnd = new Random();
        for(int i=0; i<n; ++i)
        {
            int tmp = arr[i];
            int randomPos = i + rnd.nextInt(n-i);
            arr[i] = arr[randomPos];
            arr[randomPos] = tmp;
        }
    }
    private static boolean check(int id, int V)
    {
        long used=0; int count=r[id];
        for(int i=s[id]+1;i<=f[id];i++)
        {
            long cur=(long)c[id]*(a[i]-a[i-1]);
            used+=cur;

            if(used>V)
            {
                if(count==0) return false;
                count--; used=cur;
                if(used>V) return false;
            }
        }
        return true;
    }
    private static int bSearch(int id, int cur)
    {
        int l=cur,r=max,mid;
        while (l<=r)
        {
            mid=(l+r)/2;
            if(check(id,mid))
            {
                cur=mid;
                r=mid-1;
            }
            else l=mid+1;
        }
        return cur;
    }
    public static void main(String[] args) throws IOException
    {
        Reader reader=new Reader();
        int i,N,ans=0;

        N=reader.nextInt();
        int M=reader.nextInt();

        a=new int[N]; s=new int[M]; f=new int[M];
        c=new int[M]; r=new int[M]; order=new int[M];

        for(i=0;i<N;i++) a[i]=reader.nextInt();
        for(i=0;i<M;i++)
        {
            s[i]=reader.nextInt()-1;
            f[i]=reader.nextInt()-1;
            c[i]=reader.nextInt();
            r[i]=reader.nextInt();

            order[i]=i;
        }

        shuffleArray(order);
        for(i=0;i<M;i++)
        {
            if(check(order[i],ans)) continue;
            ans=bSearch(order[i],ans+1);
        }
        System.out.println(ans);
    }
    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
        public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;
        }public Reader(String file_name) throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;
        }public String readLine() throws IOException{byte[] buf=new byte[64];int cnt=0,c;while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);
        }public int nextInt() throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;
        }public long nextLong() throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;
        }public double nextDouble() throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;
        }private void fillBuffer() throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;
        }private byte read() throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];
        }public void close() throws IOException{if(din==null) return;din.close();}
    }
}