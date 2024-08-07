import java.util.*;
import java.io.*;
import java.math.*;
public class Main
{
    static class Reader 
    { 
        private InputStream mIs;private byte[] buf = new byte[1024];private int curChar,numChars;public Reader() { this(System.in); }public Reader(InputStream is) { mIs = is;} 
        public int read() {if (numChars == -1) throw new InputMismatchException();if (curChar >= numChars) {curChar = 0;try { numChars = mIs.read(buf);} catch (IOException e) { throw new InputMismatchException();}if (numChars <= 0) return -1; }return buf[curChar++];} 
        public String nextLine(){int c = read();while (isSpaceChar(c)) c = read();StringBuilder res = new StringBuilder();do {res.appendCodePoint(c);c = read();}while (!isEndOfLine(c));return res.toString() ;} 
        public String s(){int c = read();while (isSpaceChar(c)) c = read();StringBuilder res = new StringBuilder();do {res.appendCodePoint(c);c = read();}while (!isSpaceChar(c));return res.toString();} 
        public long l(){int c = read();while (isSpaceChar(c)) c = read();int sgn = 1;if (c == '-') { sgn = -1 ; c = read() ; }long res = 0; do{ if (c < '0' || c > '9') throw new InputMismatchException();res *= 10 ; res += c - '0' ; c = read();}while(!isSpaceChar(c));return res * sgn;} 
        public int i(){int c = read() ;while (isSpaceChar(c)) c = read();int sgn = 1;if (c == '-') { sgn = -1 ; c = read() ; }int res = 0;do{if (c < '0' || c > '9') throw new InputMismatchException();res *= 10 ; res += c - '0' ; c = read() ;}while(!isSpaceChar(c));return res * sgn;} 
        public double d() throws IOException {return Double.parseDouble(s()) ;}
        public boolean isSpaceChar(int c) { return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1; } 
        public boolean isEndOfLine(int c) { return c == '\n' || c == '\r' || c == -1; } 
        public int[] arr(int n){int[] ret = new int[n];for (int i = 0; i < n; i++) {ret[i] = i();}return ret;}
    }
    
    
 
           //       |----|       /\      |    |   -----   |
           //       |   /       /  \     |    |     |     |
           //       |--/       /----\    |----|     |     |
           //       |   \     /      \   |    |     |     |
           //       |    \   /        \  |    |   -----   -------
    
    static int cache[][][][][],n;
    static String s;
    public static int dp(int pos,int changed,int dis,int dom,int dir)
    {
        if(changed>n)
        return -1000000000;
        if(pos==s.length())
        {
            if(changed==n)
            return dis;
            return -1000000000;
        }
        if(cache[pos][changed][dis][dom][dir]!=-1)
        return cache[pos][changed][dis][dom][dir];
        
        if(s.charAt(pos)=='F')
        {
            int aans=-1000000000;
            for(int i=0;i<=100;i+=2)
            {
                int ans=-1000000000;
                if(dir==0)
                {
                    if(dom==0)
                    ans=dp(pos+1,changed+i,dis+1,dom,dir);
                    else
                    {
                        if(dis-1<=0)
                        ans=dp(pos+1,changed+i,Math.abs(dis-1),dom^1,dir);
                        else
                        ans=dp(pos+1,changed+i,dis-1,dom,dir);
                    }
                }
                else
                {
                    if(dom==1)
                    ans=dp(pos+1,changed+i,dis+1,dom,dir);
                    else
                    {
                        if(dis-1<=0)
                        ans=dp(pos+1,changed+i,Math.abs(dis-1),dom^1,dir);
                        else
                        ans=dp(pos+1,changed+i,dis-1,dom,dir);
                    }
                }
                aans=Math.max(ans,aans);
            }
            for(int i=1;i<=100;i+=2)
            aans=Math.max(aans,dp(pos+1,changed+i,dis,dom,dir^1));
            return cache[pos][changed][dis][dom][dir]=aans;
        }
        else
        {
            int aans=-1000000000;
            for(int i=1;i<=100;i+=2)
            {
                int ans=-1000000000;
                if(dir==0)
                {
                    if(dom==0)
                    ans=dp(pos+1,changed+i,dis+1,dom,dir);
                    else
                    {
                        if(dis-1<=0)
                        ans=dp(pos+1,changed+i,Math.abs(dis-1),dom^1,dir);
                        else
                        ans=dp(pos+1,changed+i,dis-1,dom,dir);
                    }
                }
                else
                {
                    if(dom==1)
                    ans=dp(pos+1,changed+i,dis+1,dom,dir);
                    else
                    {
                        if(dis-1<=0)
                        ans=dp(pos+1,changed+i,Math.abs(dis-1),dom^1,dir);
                        else
                        ans=dp(pos+1,changed+i,dis-1,dom,dir);
                    }
                }
                aans=Math.max(ans,aans);
            }
            for(int i=0;i<=100;i+=2)
            aans=Math.max(aans,dp(pos+1,changed+i,dis,dom,dir^1));
            return cache[pos][changed][dis][dom][dir]=aans;
        }
    }
    public static void main(String[] args)throws IOException
    {
        PrintWriter out= new PrintWriter(System.out);
        Reader sc=new Reader();
        s=sc.s();
        n=sc.i();
        cache=new int[s.length()][n+1][s.length()+1][2][2];
        for(int i=0;i<s.length();i++)for(int j=0;j<=n;j++)
        for(int k=0;k<=s.length();k++)for(int l=0;l<2;l++)Arrays.fill(cache[i][j][k][l],-1);
        out.println(dp(0,0,0,0,0));
        out.flush();
    }
}