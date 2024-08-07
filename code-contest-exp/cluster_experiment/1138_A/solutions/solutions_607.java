import java.util.*;
import java.io.*;
import java.math.*;
public class Main1
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
    static int n;
    public static void main(String[] args)throws IOException
    {
        PrintWriter out= new PrintWriter(System.out);
        Reader sc=new Reader();
        n=sc.i();
        int arr[]=new int[n+3];
        for(int i=1;i<=n;i++)
        arr[i]=sc.i();
        int freq[][]=new int[n+3][2];
        for(int i=1;i<=n;i++)
        {
            int f=0;
            if(arr[i]==1)
            f=1;
            freq[i][0]+=freq[i-1][0]+f;
            f^=1;
            freq[i][1]+=freq[i-1][1]+f;
        }
        int ans=0;
        for(int i=1;i<=n;i++)
        {
            int low=i;
            int high=n;
            int c=0;
            if(arr[i]==2)
            c=1;
            while(low<high)
            {
                int mid=(low+high+1)/2;
                if(freq[mid][c^1]-freq[i-1][c^1]!=0)
                high=mid-1;
                else
                low=mid;
            }
            int low2=low+1;
            int high2=n;
            while(low2<high2)
            {
                int mid=(low2+high2+1)/2;
                if(freq[mid][c]-freq[low][c]!=0)
                high2=mid-1;
                else
                low2=mid;
            }
            ans=Math.max(ans,2*Math.min(low-i+1,low2-low));
        }
        out.println(ans);
        out.flush();
    }
}