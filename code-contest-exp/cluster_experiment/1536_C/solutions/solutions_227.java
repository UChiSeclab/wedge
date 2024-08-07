import java.util.*;
import java.io.*;


public class Main{
    public static int gcd(int a, int b)
    {
        if (a == 0)
            return b;

        return gcd(b%a, a);
    }
    public static void main(String[] args) throws java.io.IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int t=Integer.parseInt(br.readLine());
        while(t-->0)
        {
            int n=Integer.parseInt(br.readLine());
            String str=br.readLine();
            HashMap<ArrayList<Integer>,Integer> map=new HashMap<>();
            int[] dp=new int[n];
            int d_count=0;
            int k_count=0;
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<n;++i)
            {
                if(str.charAt(i)=='D')
                    d_count++;
                else
                    k_count++;
                int gcd=gcd(k_count,d_count);
                int cur_d=d_count/gcd;
                int cur_k=k_count/gcd;
                ArrayList<Integer> list=new ArrayList<>();
                list.add(cur_d);
                list.add(cur_k);
                int old=map.getOrDefault(list,0)+1;
                map.put(list,old);
                dp[i]=old;
                sb.append(dp[i]+" ");
            }
            System.out.println(sb.toString());
        }
    }
}
// 1 0 1 1 0 0 1