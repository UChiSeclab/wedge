import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Boxers {
    public static void main(String[] args) {
        FastReader input=new FastReader();
        int n=input.nextInt();
        int a[]=new int[n];
        for(int i=0;i<n;i++)
        {
            a[i]=input.nextInt();
        }
        mergeSort(a,0,n-1);
        HashSet<Integer> set=new HashSet<>();
        int last=Integer.MAX_VALUE;
        for(int i=0;i<a.length;i++)
        {
            if(a[i]+1<last && !set.contains(a[i]+1))
            {
                set.add(a[i]+1);
                last=a[i]+1;
            }
            else if(!set.contains(a[i]))
            {
                set.add(a[i]);
                last=a[i];
            }
            else if(a[i]!=1 && !set.contains(a[i]-1))
            {
                set.add(a[i]-1);
                last=a[i]-1;
            }
        }
        System.out.println(set.size());
    }
    public static void mergeSort(int a[],int p,int r)
    {
        if(p<r)
        {
            int q=(p+r)/2;
            mergeSort(a,p,q);
            mergeSort(a,q+1,r);
            merge(a,p,q,r);
        }
    }
    public static void merge(int a[],int p,int q,int r)
    {
        int n1=q-p+2;
        int L[]=new int[n1];
        int n2=r-q+1;
        int R[]=new int[n2];
        for(int i=p;i<=q;i++)
        {
            L[i-p]=a[i];
        }
        L[n1-1]=Integer.MIN_VALUE;
        for(int i=q+1;i<=r;i++)
        {
            R[i-q-1]=a[i];
        }
        R[n2-1]=Integer.MIN_VALUE;
        int x=0,y=0;
        for(int i=p;i<=r;i++)
        {
            if(L[x]>=R[y])
            {
                a[i]=L[x];
                x++;
            }
            else
            {
                a[i]=R[y];
                y++;
            }
        }
    }
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}

