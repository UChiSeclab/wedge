import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

public class Main {
    static class pair implements Comparable<pair> {
        long x;
        long y;

        public pair(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(pair o) {
            if (x > o.x)
                return 1;
            if (x == o.x)
                return 0;
            return -1;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        PrintWriter out = new PrintWriter(System.out);
        int n=Integer.parseInt(br.readLine());
        int beg1=-1;int beg2=-1;int max=0;
        int []a=new int[n];
        st=new StringTokenizer(br.readLine());
        for (int i = 0; i <n ; i++) {
            a[i]=Integer.parseInt(st.nextToken());
        }
        int last=a[0];
        int lenlast=0;
        int lenafter=0;
        int ans=0;
        for (int i = 0; i <n ; i++) {
           if(a[i]==last) {
               if(lenafter==0)
               lenlast++;
               else{
                   ans=Math.max(ans,2*Math.min(lenlast,lenafter));
                   last=last==1?2:1;
                   lenlast=lenafter;
                   lenafter=1;
               }
           }
           else{
               lenafter++;
           }
        }
        ans=Math.max(ans,2*Math.min(lenlast,lenafter));
        out.println(ans);
        out.flush();

    }
}