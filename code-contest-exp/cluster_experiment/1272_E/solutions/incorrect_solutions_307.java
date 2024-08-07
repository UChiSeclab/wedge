import java.io.*;
import java.util.*;

public class practicing {
    static class tri {
        int l;
        int last;
        int beg;

        public tri(int m, int b,int ma) {
            l = m;
            last = ma;
            beg=b;
        }
    }

    static long pow(long n, int e) {
        long res = 1;
        while (e-- > 0) {
            res *= n;
        }
        return res;
    }
    static int returnvalidAns(int left,int right){
        if(left==right){
            if(left==0||left==-1)
                return -1;
            return 1+left;
        }
        int max=Math.max(left,right);
        int min=Math.min(left, right);
        if(max==0)
            return -1;
        if(min==0||min==-1)
            return 1+max;
        return 1+min;
    }

   static int dp(int i,int[]a,int[]mem,boolean[]vis){
        if(i<0||i>=a.length)
            return -1;
       if(vis[i])
           return 0;
       vis[i]=true;
        if(mem[i]!=0) {
            vis[i]=false;
            return mem[i];
        }
        int left=dp(i-a[i],a,mem,vis);
        int right=dp(i+a[i],a,mem,vis);
        vis[i]=false;
        mem[i]=(returnvalidAns(left,right));
        return mem[i];
   }

    static boolean diffP(int a,int b) {
       if(a%2==0&&b%2==1)
           return true;
        if(b%2==0&&a%2==1)
            return true;
       return false;
    }

    public static void main(String[] args) throws IOException {
        //BufferedReader br = new BufferedReader(new FileReader("folding.in"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        //PrintWriter out = new PrintWriter("folding.out");
        PrintWriter out = new PrintWriter(System.out);
        int n = Integer.parseInt(br.readLine());
        int[]a=new int[n];
        st=new StringTokenizer(br.readLine());
        for (int i = 0; i <n ; i++) {
            a[i]=Integer.parseInt(st.nextToken());
        }
        int[]mem=new int[n];
        boolean flag=false;
        for (int i = 0; i <n ; i++) {
            int now=a[i];
            if(i+now<n){
                if(diffP(now,a[i+now])) {
                    mem[i] = 1;
                    flag=true;
                }
            }
            if(i-now>=0){
                if(diffP(now,a[i-now])) {
                    mem[i] = 1;
                    flag=true;
                }
            }
        }


        if(flag){
            for (int i = 0; i <n ; i++) {
                if(mem[i]==0) {
                    boolean[]vis=new boolean[n];
                    dp(i, a,mem,vis);

                }
            }
        }
        else {
            Arrays.fill(mem,-1);
        }
        for (int i = 0; i <n ; i++) {
            if(i<n-1)
                out.print(mem[i]+" ");
            else out.println(mem[i]);
        }
        out.flush();

    }
}
