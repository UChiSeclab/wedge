import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;
public class Main extends Thread  {
    boolean[] prime;
    FastScanner sc;
    PrintWriter pw;
    long startTime = System.currentTimeMillis();
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
    public Main(ThreadGroup t,Runnable r,String s,long d )
    {
        super(t,r,s,d);
    }
    public void run()
    {
        sc=new FastScanner();
        pw=new PrintWriter(System.out);
        solve();
        pw.flush();
        pw.close();
    }
    public static void main(String[] args)
    {
        new Main(null,null,"",1<<26).start();
    }


    /////////////------------------------------------//////////////
    ////////////------------------Main-Logic--------//////////////
    ///////////-------------------------------------//////////////
    public static class Pair implements Comparable<Pair>{
        long x;
        long v;
        Pair(long a)
        {
            x=a;
        }
        public int compareTo(Pair b)
        {
            long p=x-b.x;
            if(p<=0)
            return -1;
            return 1;
        }
    }
    public void solve() {
            int t=1;
            while(t-->0)
            {
                ArrayList<Pair> list =new ArrayList<Pair>();
                int n=sc.ni();
                for(int i=0;i<n;i++)
                list.add(new Pair(sc.nlo()));
                TreeSet<Long> lrr=new TreeSet();
                for(Pair p:list)
                {long x=sc.nlo();p.v=x;lrr.add(x);}
                Collections.sort(list);
                HashMap<Long,Integer> map=new HashMap();
                int k=0;
                while(lrr.size()>0)
                {
                    long p=lrr.first();
                    map.put(p,k++);
                    lrr.remove(p);
                }
                long[] seg=new long[4*k+1];
                long[] seg1=new long[4*k+1];
                update(list.get(n-1).x,map.get(list.get(n-1).v),1,seg,0,k-1);
                update(1,map.get(list.get(n-1).v),1,seg1,0,k-1);
             
                long ans=0;
                for(int i=n-2;i>=0;i--)
                {
                    int x=map.get(list.get(i).v);
                    long y=query(x,k-1,1,seg,0,k-1);
                    long z=query(x,k-1,1,seg1,0,k-1);
                    ans+=(y-(z*list.get(i).x));
                    update(list.get(i).x,x,1,seg,0,k-1);
                    update(1,x,1,seg1,0,k-1);
                  
                }
                pw.println(ans);
            }
    }
    public static void update(long x,int p,int ind,long[] seg,int s,int e)
    {
       
        if(p<s||p>e)
        return;
        if((s==e)&&(p==s))
        seg[ind]+=x;
        else {
            if(p<=(s+e)/2)
            update(x,p,2*ind,seg,s,(e+s)/2);
            else
            update(x,p,2*ind+1,seg,(s+e)/2+1,e);
            seg[ind]=seg[2*ind]+seg[2*ind+1];
        }
    }
    public static long query(int qs,int qe,int ind,long[] seg,int s,int e)
    {
        if(s>qe||e<qs)
        return 0;
        if((qs<=s)&&(qe>=e))
        return seg[ind];
        return query(qs,qe,2*ind,seg,s,(s+e)/2)+query(qs,qe,2*ind+1,seg,(s+e)/2+1,e);
    }
}