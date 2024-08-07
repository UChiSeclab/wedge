import java.util.*;
import java.lang.*;
import java.io.*;
//****Use Integer Wrapper Class for Arrays.sort()****
public class AC5 {
    public static void main(String[] Args){
        FastReader scan=new FastReader();
        int n=scan.nextInt();
        Map<Integer,Integer> f=new HashMap<>();
        for(int i=0;i<n;i++){
            int num=scan.nextInt();
            f.putIfAbsent(num,0);
            f.put(num,f.get(num)+1);
        }
        ArrayList<Integer> u=new ArrayList<>();
        for(Integer i:f.keySet()){
            u.add(i);
        }
        Collections.sort(u);
        n=u.size();
        int prev=0;
        int ans=0;
        for(int i=0;i<n;i++){
            int cur=u.get(i);
            int lf=f.get(cur);
            if(cur==1){
                if(lf==1){
                    prev=1;
                    ans++;
                }
                else{
                    prev=2;
                    ans+=2;
                }
            }
            else{
                if(prev<cur-1){
                    prev=cur-1;
                    ans++;
                    lf--;
                }
                if(prev<cur&&lf>0){
                    prev=cur;
                    ans++;
                    lf--;
                }
                if(lf>0){
                    prev=cur+1;
                    ans++;
                }
            }
        }
        System.out.println(ans);
    }
    static class FastReader {
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

}
