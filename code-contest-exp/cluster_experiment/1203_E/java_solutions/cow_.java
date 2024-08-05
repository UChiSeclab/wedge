
//package cf;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
public class cow_ {
    static int p=1000000007;
    public static void main(String[] args) throws Exception{
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(java.io.FileDescriptor.out), "ASCII"), 512);
        FastReader sc=new FastReader();
        int n = sc.nextInt();
        Integer ar[]=new Integer[n];
        String s[]=sc.nextLine().split(" ");
        for(int i=0;i<n;i++)
        {
            ar[i]=Integer.parseInt(s[i]);//sc.nextInt();
        }
        Arrays.sort(ar);
        ar[n-1]++;
        for(int i=n-2;i>=0;i--) {
            if (ar[i] + 1 < ar[i + 1])
            {
                ar[i]++;
            }
            else if (ar[i] == ar[i + 1]) {
                //    if(ar[i]+1<ar[i+1])
                //      ar[i]++;
               //  if(ar[i]-1>0)
                ar[i]--;

            }
        }
        Set<Integer> set=new HashSet<>();
        for(int i:ar)
        {
            if(i!=0)
            set.add(i);
        }
       // System.out.println(set);
        System.out.println(set.size());
        // StringBuilder sb=new StringBuilder();
        //   out.write(sb.toString());
        out.flush();
    }






    ///////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////
    static class FastReader {

        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
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
