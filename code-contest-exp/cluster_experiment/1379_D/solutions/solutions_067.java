import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.Buffer;
import java.util.*;

public class Main {
    public static class FastReader
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
                catch (IOException e)
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
    public static class pair{
        int mi,id;
        public pair(){}
        public pair(int mi,int id){
            this.mi=mi;
            this.id=id;
        }
    }

    public static FastReader r=new FastReader();
    public static void main(String[] args){
        int n=r.nextInt();
        int h=r.nextInt();
        int m=r.nextInt();
        int k=r.nextInt();
        m/=2;
        ArrayList<pair> trains=new ArrayList<>();
        for(int i=0;i<n;++i){
            int hi=r.nextInt();
            int mi=r.nextInt();
            trains.add(new pair(mi%m,i));
            trains.add(new pair(mi%m+m,i));
        }
        Collections.sort(trains, new Comparator<pair>() {
            @Override
            public int compare(pair o1, pair o2) {
                if(o1.mi>o2.mi) return 1;
                else if(o1.mi==o2.mi){
                    if(o1.id>o2.id) return 1;
                    else if(o1.id==o2.id) return 0;
                    else return -1;
                }
                else return -1;
            }
        });
        pair ans=new pair(n,n);
        for(int run=0,i=n;i<n*2;++i){
            while(trains.get(run).mi<=trains.get(i).mi-k) ++run;
            if(ans.mi>i-run){
                ans=new pair(i-run,trains.get(i).mi);
            }
        }
        System.out.println(ans.mi+" "+(ans.id-m));
        for(int i=0;i<2*n;++i){
            if(trains.get(i).mi<=ans.id-k||trains.get(i).mi>=ans.id) continue;
            System.out.print((trains.get(i).id+1)+" ");
        }
    }
}
