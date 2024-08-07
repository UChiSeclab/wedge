/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;




/* Name of the class has to be "Main" only if the class is public. */
public class p6
{
         static class Input {
        private StringTokenizer tokenizer = null;
        private BufferedReader reader;
 
        public Input(InputStream inputStream) {
            reader = new BufferedReader(new InputStreamReader(inputStream));
        }
 
        public String nextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(nextLine());
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
        public long nextLong() {
            return Long.parseLong(next());
        }
 
        public int[] nextIntArray(int n, int add) {
            int[] result = new int[n];
            for (int i = 0; i < n; i++) {
                result[i] = nextInt() + add;
            }
            return result;
        }
 
        public long[] nextLongArray(int n, long add) {
            long[] result = new long[n];
            for (int i = 0; i < n; i++) {
                result[i] = nextLong() + add;
            }
            return result;
        }
 
        public int[] nextIntArray(int n) {
            return nextIntArray(n, 0);
        }
 
    }

    static long get(long a[],int in)
    {
        long sum=0;
        while(in>0)
        {
            sum+=a[in];
            in-=in&(-in);
        }
        return sum;
    }

    static void upd(long a[],int in ,long val)
    {
        int n=a.length-1;
        while(in<=n)
        {
            a[in]+=val;
            in+=in&(-in);
        }
    }
static PrintWriter out=new PrintWriter(System.out);
    static int search(long a[],long e)
    {
        //out.println("ele"+e);
        int l=0,r=a.length-1,mid,ans=r;
        while(l<=r)
        {
            mid=(l+r)/2;
         //  out.println(l+" "+r+" "+a[mid]);
            if(a[mid]>=e)
            {
                ans=Math.min(ans,mid);
                r=mid-1;
            }
            else
            l=mid+1;
        }
       // out.println(ans);
        return ans;
    }
    
	public static void main (String[] args) throws java.lang.Exception
	{
		Input s=new Input(System.in);
		int t=1;
		
		
		while(t-->0)
		{
                int n=s.nextInt();
                int a[]=s.nextIntArray(n);
                int b[]=s.nextIntArray(n);

                myclass m1[]=new myclass[n];
                TreeSet<Integer> hs=new TreeSet<>();
                for(int i=0;i<n;i++)
                {
                    m1[i]=new myclass(a[i],b[i]);
                    hs.add(b[i]);
                }

                Arrays.sort(m1,new myclass());
                long c[]=new long[hs.size()];
                int i1=0;
                for(Integer el:hs)
                c[i1++]=el;
                long p1[]=new long[hs.size()+1];
                long p2[]=new long[hs.size()+1];
                long ans=0;
                for(int i=0;i<n;i++)
                {
                    int in=search(c,(long)m1[i].b);
                    //out.println(get(p1,in+1)+" "+get(p2,in+1));
                    ans+=get(p1,in+1)*(long)m1[i].a-get(p2,in+1);
                    upd(p1,in+1,1);
                    upd(p2,in+1,m1[i].a);
                   // out.println(m1[i].b+" "+ans+" "+in);
                }

                out.println(ans);

        }
        out.flush();
	}
}


class myclass implements Comparator<myclass>
{
    int a,b;
    myclass(int a,int b)
    {
        this.a=a;
        this.b=b;
    }
    myclass(){}

    public int compare(myclass m1,myclass m2)
    {
        if(m1.a!=m2.a)
        return m1.a-m2.a;
        else
        return m1.b-m2.b;
    }

}