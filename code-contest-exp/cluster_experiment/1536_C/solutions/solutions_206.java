import java.util.*;
import java.lang.*;
import java.io.*;

public class Codechef {
    static PrintWriter out = new PrintWriter(System.out);
    static Reader reader= new Reader();
    public static void main(String[] args) throws java.lang.Exception {
        int t = reader.nextInt();
        while (t-- > 0) {
            int n = reader.nextInt();
            HashMap<Pair,Integer> map = new HashMap<>();
            int d=0,k=0;
            String s = reader.nextLine();
            for(int i =0;i<n;i++) {
               if(s.charAt(i)=='D')
                   d++;
               else
                   k++;
               int g = gcd(d,k);
               Pair p = new Pair(d/g,k/g);
               int ret = 0;
               if(map.containsKey(p)){
                   ret = map.get(p);
                   map.put(p,map.get(p)+1);
               } else {
                   map.put(p,1);
               }
               out.print(ret+1+" ");
           }
            out.println();
        }
        out.close();
    }
    static int gcd(int a, int b){
        if(a==0)
            return b;
        return gcd(b%a,a);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    static class Reader
    {
        BufferedReader br;
        StringTokenizer st;

        public Reader()
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
    static class Pair{
        int x;
        int y;

        public Pair(int x, int y) {
            this.x=x;
            this.y=y;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) {
                return true;
            }
            if (o == null) {
                return false;
            }
            if (this.getClass() != o.getClass()) {
                return false;
            }
            Pair other = (Pair)o;
            return this.x == other.x && this.y == other.y;
        }
        public int hashCode() {
            // name's hashCode is multiplied by an arbitrary prime number (13)
            // in order to make sure there is a difference in the hashCode between
            // these two parameters:
            //  name: a  value: aa
            //  name: aa value: a
            return x * 13 + y;
        }
    }
}