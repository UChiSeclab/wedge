import java.util.*;
import java.io.*;
import java.text.*;
public class Main{
    //SOLUTION BEGIN
    //Into the Hardware Mode
    void pre() throws Exception{}
    void solve(int TC)throws Exception {
        int n = ni();
        TreeSet<Long> v = new TreeSet<>();
        long[][] p = new long[n][2];
        for(int i = 0; i< n; i++)p[i][0] = nl();for(int i = 0; i< n; i++){p[i][1] = nl();v.add(p[i][1]);}
        HashMap<Long, Integer> mp = new HashMap<>();
        int c = 1;
        for(long l:v)mp.put(l, c++);
        Arrays.sort(p, (long[] l1, long[] l2) -> Long.compare(l1[0], l2[0]));
        for(int i = 0; i< n; i++)p[i][1] = mp.get(p[i][1]);
        SegTree le = new SegTree(c+1);
        long ans = 0;
        for(int i = 0; i< n; i++){
            long[] o =  le.sum(0, (int)p[i][1]);
            ans += p[i][0]*o[1]-o[0];
            le.u((int)p[i][1], p[i][0]);
        }
        pn(ans);
    }
    class SegTree{
        int m = 1;
        long[] t, cnt;
        public SegTree(int n){
            while(m<n)m<<=1;
            t = new long[m<<1];
            cnt=  new long[m<<1];
        }
        void u(int p, long a){
            t[p+=m] += a;
            cnt[p] ++;
            for(p>>=1; p>0; p>>=1){
                t[p] = t[p<<1]+t[p<<1|1];
                cnt[p] = cnt[p<<1]+cnt[p<<1|1];
            }
        }
        long[] sum(int l, int r){
            long ans = 0, cc = 0;
            for(l += m, r += m+1; l< r; l>>=1, r>>=1){
                if((l&1)==1){ans += t[l];cc += cnt[l++];}
                if((r&1)==1){ans += t[--r];cc += cnt[r];}
            }
            return new long[]{ans, cc};
        }
    }
    //SOLUTION END
    void hold(boolean b)throws Exception{if(!b)throw new Exception("Hold right there, Sparky!");}
    void exit(boolean b){if(!b)System.exit(0);}
    long IINF = (long)1e15;
    final int INF = (int)1e9+2, MX = (int)2e6+5;
    DecimalFormat df = new DecimalFormat("0.00000000000");
    double PI = 3.141592653589793238462643383279502884197169399, eps = 1e-8;
    static boolean multipleTC = false, memory = false, fileIO = false;
    FastReader in;PrintWriter out;
    void run() throws Exception{
        long ct = System.currentTimeMillis();
        if (fileIO) {
            in = new FastReader("");
            out = new PrintWriter("");
        } else {
            in = new FastReader();
            out = new PrintWriter(System.out);
        }
        //Solution Credits: Taranpreet Singh
        int T = (multipleTC) ? ni() : 1;
        pre();
        for (int t = 1; t <= T; t++) solve(t);
        out.flush();
        out.close();
        System.err.println(System.currentTimeMillis() - ct);
    }
    public static void main(String[] args) throws Exception{
        if(memory)new Thread(null, new Runnable() {public void run(){try{new Main().run();}catch(Exception e){e.printStackTrace();}}}, "1", 1 << 28).start();
        else new Main().run();
    }
    int find(int[] set, int u){return set[u] = (set[u] == u?u:find(set, set[u]));}
    int digit(long s){int ans = 0;while(s>0){s/=10;ans++;}return ans;}
    long gcd(long a, long b){return (b==0)?a:gcd(b,a%b);}
    int gcd(int a, int b){return (b==0)?a:gcd(b,a%b);}
    int bit(long n){return (n==0)?0:(1+bit(n&(n-1)));}
    void p(Object o){out.print(o);}
    void pn(Object o){out.println(o);}
    void pni(Object o){out.println(o);out.flush();}
    String n()throws Exception{return in.next();}
    String nln()throws Exception{return in.nextLine();}
    int ni()throws Exception{return Integer.parseInt(in.next());}
    long nl()throws Exception{return Long.parseLong(in.next());}
    double nd()throws Exception{return Double.parseDouble(in.next());}

    class FastReader{
        BufferedReader br;
        StringTokenizer st;
        public FastReader(){
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws Exception{
            br = new BufferedReader(new FileReader(s));
        }

        String next() throws Exception{
            while (st == null || !st.hasMoreElements()){
                try{
                    st = new StringTokenizer(br.readLine());
                }catch (IOException  e){
                    throw new Exception(e.toString());
                }
            }
            return st.nextToken();
        }

        String nextLine() throws Exception{
            String str;
            try{
                str = br.readLine();
            }catch (IOException e){
                throw new Exception(e.toString());
            }
            return str;
        }
    }
}