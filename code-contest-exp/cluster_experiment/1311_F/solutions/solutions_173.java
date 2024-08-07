import java.awt.*;
import java.util.*;
import java.io.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        FastReader sc = new FastReader();
        n=sc.nextInt();
        bit=new long[n+1];
        bit1=new int[n+2];
        Point p[]=new Point[n];
        for (int i=0;i<n;i++)p[i]=new Point(sc.nextInt(),0);
        for (int i=0;i<n;i++)p[i].y=sc.nextInt();
        Arrays.sort(p, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.y-o2.y;
            }
        });
        int x=0;
        int prev=-1;
        for (int i=0;i<n;i++){
            if (i>0 && prev!=p[i].y)x++;
            prev=p[i].y;
            p[i].y=x;
        }
        Arrays.sort(p, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.x-o2.x;
            }
        });
//        HashMap<Integer,Integer> map=new HashMap<>();
//        for (int i=0;i<n;i++)map.put(p[i].x,i);
        long ans=0;
        for (int i=n-1;i>=0;i--){
//            System.out.println(p[i].x+" "+p[i].y);
            long sum=suff(p[i].y);
            long sum1=suff1(p[i].y);
            up(p[i].y,p[i].x);
            up1(p[i].y);
            ans+=Math.abs(sum-p[i].x*sum1);
//            System.out.println(sum+" "+p[i].x*sum1);
        }
        System.out.println(ans);

    }
    static long bit[];
    static int bit1[];
    static int n;
    static void up(int i,int val){
        for (i++;i<=n;i+=i&(-i)){
            bit[i]+=val;
        }
    }
    static void up1(int i){
        for (i++;i<=n;i+=i&(-i)){
            bit1[i]+=1;
        }
    }
    static long sum(int i){
        long res=0;
        for (i++;i>0;i-=i&(-i)){
            res+=bit[i];
        }
        return res;
    }
    static long sum1(int i){
        long res=0;
        for (i++;i>0;i-=i&(-i)){
            res+=bit1[i];
        }
        return res;
    }
    static long suff(int i){
        return sum(n-1)-sum(i-1);
    }
    static long suff1(int i){
        return sum1(n-1)-sum1(i-1);
    }
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