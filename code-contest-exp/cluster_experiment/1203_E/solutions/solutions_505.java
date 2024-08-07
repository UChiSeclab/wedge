import java.io.*;
import java.util.*;

public class A {
    static FastReader f = new FastReader();

    public static void main(String[] args) {
        int n = f.nextInt();
        int[] arr = new int[150005];
        for(int i=0;i<n;i++) {
            arr[f.nextInt()]++;
        }
        boolean[] bol = new boolean[150005];

        for(int i=1;i<150001;i++) {
            if(arr[i] > 0) {
                if(i > 1 && !bol[i-1]) {
                    bol[i-1] = true;
                    arr[i]--;
                    i--;
                } else if(!bol[i]) {
                    bol[i] = true;
                    arr[i]--;
                    i--;
                } else if(!bol[i+1]) {
                    bol[i+1] = true;
                    arr[i]--;
                    i--;
                }
            }
        }

        int cnt = 0;
        for(boolean b : bol) {
            if(b) {
                cnt++;
            }
        }

        System.out.println(cnt);
    }



    //fast input reader
    static class FastReader
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
}


