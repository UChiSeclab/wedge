import java.util.*;
public class Main
{
    static class comp implements Comparator<long []>
    {
        public int compare(long a1[],long a2[])
        {
            if(a1[1]!=a2[1])
            {
                if(a1[1]>a2[1])
                return 1;
                else
                return -1;
            }
            if(a1[0]>a2[0])
                return 1;
            else
                return -1;
        }
    }
    public static void update(long val,int in,long b[],int i,int cnt[])
    {
        for(int j=i;j<in;j+=(j&(-j)))
        {
            b[j]+=val;
            cnt[j]++;
        }
    }
    public static long query(int i,long b[],long val,int cnt[])
    {
        long sum=0;
        for(int j=i-1;j>0;j-=(j&(-j)))
        sum=sum+val*cnt[j]-b[j];
        return sum;
    }
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        long arr[][]=new long[n][2];
        TreeSet<Long> ts=new TreeSet<>();
        for(int i=0;i<n;i++)
        {
            arr[i][0]=sc.nextLong();
            ts.add(arr[i][0]);
        }
        TreeMap<Long,Integer> ind=new TreeMap<>();
        long b[]=new long[ts.size()+1];
        int cnt[]=new int[ts.size()+1];
        int in=1;
        while(!ts.isEmpty())
        {
            long node=ts.pollFirst();
            ind.put(node,in);
            in++;
        }
        for(int i=0;i<n;i++)
        arr[i][1]=sc.nextLong();
        Arrays.sort(arr,new comp());
        long ans=0;
        for(int i=0;i<n;i++)
        {
            ans=ans+query(ind.get(arr[i][0]),b,arr[i][0],cnt);
            update(arr[i][0],in,b,ind.get(arr[i][0]),cnt);
        }
        System.out.println(ans);
    }
}