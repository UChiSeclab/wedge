import java.io.*;
import java.util.*;
import java.lang.*;


public class Main{

    public void solve () throws IOException  {

        InputReader in = new InputReader(System.in);
        PrintWriter pw = new PrintWriter(System.out);
        int t = in.nextInt();
        while(t-- > 0){
            int n = in.nextInt();
            char s[] = in.nextLine().toCharArray();
            int totalD = 0;
            HashMap<Pair , Integer> hm = new HashMap<>();
            for(int i = 0 ; i < n ; i++){
                if(s[i] == 'D'){
                    totalD ++;
                }
                int totalK = i + 1 - totalD;
                if(totalK == 0){
                    int val = hm.get(new Pair(n + 1 , n + 1)) == null ? 0 : hm.get(new Pair(n + 1 , n + 1));
                    hm.put(new Pair(n + 1 , n + 1) , val + 1);
                    pw.print((val + 1)+" ");
                }else if(totalD == 0){
                    int val = hm.get(new Pair(0, 0)) == null ? 0 : hm.get(new Pair(0 , 0));
                    hm.put(new Pair(0 , 0) , val + 1);
                    pw.print((val + 1)+" ");
                }
                else{
                    int gcd = GCD(totalD , totalK);
                    Pair p = new Pair(totalD / gcd , totalK / gcd);
                    int val = hm.get(p) == null ? 0 : hm.get(p);
                    hm.put(p , val + 1);
                    pw.print((val + 1)+" ");
                }
            }
            pw.println();
        }
        pw.flush();
        pw.close();
    }

    public int GCD(int x , int y){
        if(y == 0){
            return x;
        }else{
            return GCD(y , x % y);
        }
    }

    public void debug(Object...o){
        System.out.println(Arrays.deepToString(o));
    }

    public static class Pair implements Comparable<Pair>{
        int x;
        int y;
        public Pair(int x , int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Pair o) {
            if(o.x > x) return  -1;
            if(o.x < x) return  1;
            return Integer.compare(y, o.y);
        }

        @Override
        public boolean equals(Object o){
            if(o instanceof Pair){
                Pair p = (Pair)o;
                return p.x == x && p.y == y;
            }
            return false;
        }

        @Override
        public String toString(){
            return x+" "+y;
        }

        @Override
        public int hashCode(){
            return Integer.hashCode(x) * 31 + Integer.hashCode(y);
        }
    }

    public static void main(String[] args) throws Exception {


        new Thread(null,new Runnable() {
            public void run() {
                try {
                    new Main().solve();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },"1",1<<26).start();


    }

    static class InputReader
    {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream)
        {
            this.stream = stream;
        }
        public int snext()
        {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars)
            {
                curChar = 0;
                try
                {
                    snumChars = stream.read(buf);
                }
                catch (IOException e)
                {
                    throw new InputMismatchException();
                }
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt()
        {
            int c = snext();
            while (isSpaceChar(c))
            {
                c = snext();
            }
            int sgn = 1;
            if (c == '-')
            {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do
            {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public long nextLong()
        {
            int c = snext();
            while (isSpaceChar(c))
            {
                c = snext();
            }
            int sgn = 1;
            if (c == '-')
            {
                sgn = -1;
                c = snext();
            }
            long res = 0;
            do
            {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public int[] nextIntArray(int n)
        {
            int a[] = new int[n];
            for (int i = 0; i < n; i++)
            {
                a[i] = nextInt();
            }
            return a;
        }

        public String readString()
        {
            int c = snext();
            while (isSpaceChar(c))
            {
                c = snext();
            }
            StringBuilder res = new StringBuilder();
            do
            {
                res.appendCodePoint(c);
                c = snext();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public String nextLine()
        {
            int c = snext();
            while (isSpaceChar(c))
                c = snext();
            StringBuilder res = new StringBuilder();
            do
            {
                res.appendCodePoint(c);
                c = snext();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c)
        {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private boolean isEndOfLine(int c)
        {
            return c == '\n' || c == '\r' || c == -1;
        }

        public interface SpaceCharFilter
        {
            public boolean isSpaceChar(int ch);
        }
    }

}

