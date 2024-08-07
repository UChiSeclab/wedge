import java.util.*;
import java.io.*;

public class Main {
    public static void main(String args[]) {new Main().run();}

    FastReader in = new FastReader();
    PrintWriter out = new PrintWriter(System.out);
    void run(){
        work();
        out.flush();
    }
    long mod=1000000007;
    long gcd(long a,long b) {
        return a==0?b:gcd(b%a,a);
    }
    void work() {
        int n=ni(),h=ni(),m=ni(),k=ni();;
        HashMap<Integer,Integer> map=new HashMap<>();
        int[][] A=new int[n][2];
        int[] B=new int[n*2];
        for(int i=0;i<n;i++){
            A[i]=nia(2);
            B[i]=A[i][1]%(m/2);
            B[i+n]=(A[i][1]%(m/2))+m/2;
        }
        int r=m/2-k;
        int ret=0;
        int s=0;
        int cur=0;
        Arrays.sort(B);
        for(int i=0,j=0;i<n;i++,cur--){
            int e=B[i]+r;
            while(j<2*n&&B[j]<=e){
                j++;
                cur++;
            }
            if(cur>ret){
                ret=cur;
                s=B[i];
            }
        }
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<n;i++){
            if(!check(A[i][1]%(m/2),s,s+r)&&!check((A[i][1]%(m/2))+m/2,s,s+r)){
                list.add(i+1);
            }
        }
        out.println(list.size()+" "+s);
        for(int l:list){
            out.print(l+" ");
        }
    }

    private boolean check(int v, int s, int e) {
        return s<=v&&v<=e;
    }

    //input
    @SuppressWarnings("unused")
    private ArrayList<Integer>[] ng(int n, int m) {
        ArrayList<Integer>[] graph=(ArrayList<Integer>[])new ArrayList[n];
        for(int i=0;i<n;i++) {
            graph[i]=new ArrayList<>();
        }
        for(int i=1;i<=m;i++) {
            int s=in.nextInt()-1,e=in.nextInt()-1;
            graph[s].add(e);
            graph[e].add(s);
        }
        return graph;
    }

    private ArrayList<long[]>[] ngw(int n, int m) {
        ArrayList<long[]>[] graph=(ArrayList<long[]>[])new ArrayList[n];
        for(int i=0;i<n;i++) {
            graph[i]=new ArrayList<>();
        }
        for(int i=1;i<=m;i++) {
            long s=in.nextLong()-1,e=in.nextLong()-1,w=in.nextLong();
            graph[(int)s].add(new long[] {e,w,i});
            graph[(int)e].add(new long[] {s,w});
        }
        return graph;
    }

    private int ni() {
        return in.nextInt();
    }

    private long nl() {
        return in.nextLong();
    }

    private String ns() {
        return in.next();
    }

    private long[] na(int n) {
        long[] A=new long[n];
        for(int i=0;i<n;i++) {
            A[i]=in.nextLong();
        }
        return A;
    }
    private int[] nia(int n) {
        int[] A=new int[n];
        for(int i=0;i<n;i++) {
            A[i]=in.nextInt();
        }
        return A;
    }
}

class FastReader
{
    BufferedReader br;
    StringTokenizer st;

    public FastReader()
    {
        br=new BufferedReader(new InputStreamReader(System.in));
    }


    public String next()
    {
        while(st==null || !st.hasMoreElements())//回车，空行情况
        {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    public int nextInt()
    {
        return Integer.parseInt(next());
    }

    public long nextLong()
    {
        return Long.parseLong(next());
    }
}