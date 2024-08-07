import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.util.*;
import java.lang.*;
import java.util.HashMap;
import java.util.PriorityQueue;
public class templ implements Runnable{
    static class pair implements Comparable
    {
        int f;
        int s;
        pair(int fi,int se)
        {
            f=fi;
            s=se;
        }
        public int compareTo(Object o)//ascending order
        {
            pair pr=(pair)o;
            if(s>pr.s)
                return 1;
            if(s==pr.s)
            {
                if(f>pr.f)
                    return 1;
                else
                    return -1;
            }
            else
                return -1;
        }
        public boolean equals(Object o)
        {
            pair ob=(pair)o;
            int ff;
            int ss;
            if(o!=null)
            {
                ff=ob.f;
                ss=ob.s;
                if((ff==this.f)&&(ss==this.s))
                    return true;
            }
            return false;
        }
        public int hashCode()
        {
            return (this.f+" "+this.s).hashCode();
        }
    }
    public class triplet implements Comparable
    {
        int f,t;
        int s;
        triplet(int f,int s,int t)
        {
            this.f=f;
            this.s=s;
            this.t=t;
        }
        public boolean equals(Object o)
        {
            triplet ob=(triplet)o;
            int ff;
            int ss;
            int tt;
            if(o!=null)
            {
                ff=ob.f;
                ss=ob.s;
                tt=ob.t;
                if((ff==this.f)&&(ss==this.s)&&(tt==this.t))
                    return true;
            }
            return false;
        }
        public int hashCode()
        {
            return (this.f+" "+this.s+" "+this.t).hashCode();
        }
        public int compareTo(Object o)//ascending order
        {
            triplet tr=(triplet)o;
            if(t>tr.t)
                return 1;
            else
                return -1;
        }
    }
    void merge1(int arr[], int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;
        int L[] = new int [n1];
        int R[] = new int [n2];
        for (int i=0; i<n1; ++i)
            L[i] = arr[l + i];
        for (int j=0; j<n2; ++j)
            R[j] = arr[m + 1+ j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2)
        {
            if (L[i]<=R[j])
            {
                arr[k] = L[i];
                i++;
            }
            else
            {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1)
        {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2)
        {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    void sort1(int arr[], int l, int r)
    {
        if (l < r)
        {
            int m = (l+r)/2;
            sort1(arr, l, m);
            sort1(arr , m+1, r);
            merge1(arr, l, m, r);
        }
    }
    public static void main(String args[])throws Exception
    {
        new Thread(null,new templ(),"templ",1<<27).start();
    }
    public void run()
    {
        try
        {
            InputReader in = new InputReader(System.in);
            PrintWriter out = new PrintWriter(System.out);
            int n=in.ni();
            int a[]=new int[200000];
            for(int i=1;i<=n;i++)
            {
                int x=in.ni();
                a[x]++;
            }
            for(int i=1;i<=150000;i++)
            {
                if(a[i]==0)
                    continue;
                if(a[i]==1 && i>1)
                {
                    if(a[i-1]==0)
                    {
                        a[i-1]++;
                        a[i]--;
                    }
                    continue;
                }
                else if(i==1)
                {
                    a[i]--;
                    a[i+1]++;
                }
                else
                {
                    if(a[i-1]==0)
                    {
                        a[i]--;
                        a[i-1]++;
                    }
                    if(a[i]>1)
                    {
                        a[i]--;
                        a[i+1]++;
                    }
                }
            }
            int ans=0;
            for(int i=1;i<=15;i++)
            {
                if(a[i]>=1)
                    ans++;
            }
            out.println(ans);
            out.close();
        }
        catch(Exception e){
            return;
        }
    }
    static class InputReader {

        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                try {
                    snumChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int ni() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public long nl() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            long res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public int[] nextIntArray(int n) {
            int a[] = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = ni();
            }
            return a;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public String nextLine() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
        }

    }
}
