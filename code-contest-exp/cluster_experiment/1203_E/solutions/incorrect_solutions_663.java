import javax.print.attribute.standard.PrinterMessageFromOperator;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
public class Main {
    public static void main(String[] args) throws Exception {
        new Main().run();}

//    int[] h,ne,to,wt;
//    int ct = 0;
//    int n;
//    void graph(int n,int m){
//        h = new int[n];
//        Arrays.fill(h,-1);
////        sccno = new int[n];
////        dfn = new int[n];
////        low = new int[n];
////        iscut = new boolean[n];
//        ne = new int[2*m];
//        to = new int[2*m];
//        wt = new int[2*m];
//        ct = 0;
//    }
//    void add(int u,int v,int w){
//        to[ct] = v;
//        ne[ct] = h[u];
//        wt[ct] = w;
//        h[u] = ct++;
//    }
//
//    int color[],dfn[],low[],stack[] = new int[1000000],cnt[];
//    int sccno[];
//    boolean iscut[];
//    int time = 0,top = 0;
//    int scc_cnt = 0;
//
//    // 有向图的强连通分量
//    void tarjan(int u) {
//        low[u] = dfn[u]= ++time;
//        stack[top++] = u;
//        for(int i=h[u];i!=-1;i=ne[i]) {
//            int v = to[i];
//            if(dfn[v]==0) {
//                tarjan(v);
//                low[u]=Math.min(low[u],low[v]);
//            } else if(sccno[v]==0) {
//                // dfn>0 but sccno==0, means it's in current stack
//                low[u]=Math.min(low[u],low[v]);
//            }
//        }
//
//        if(dfn[u]==low[u]) {
//            sccno[u] = ++scc_cnt;
//            while(stack[top-1]!=u) {
//                sccno[stack[top-1]] = scc_cnt;
//                --top;
//            }
//            --top;
//        }
//    }
//
//    //缩点, topology sort
//    int[] h1,to1,ne1;
//    int ct1 = 0;
//    void point(){
//        for(int i=0;i<n;i++) {
//            if(dfn[i]==0) tarjan(i);//有可能图不连通，所以要循环判断。
//        }
//        // 入度
//        int du[] = new int[scc_cnt+1];
//        h1 = new int[scc_cnt+1];
//        Arrays.fill(h1, -1);
//        to1 = new int[scc_cnt*scc_cnt];
//        ne1 = new int[scc_cnt*scc_cnt];
//        // scc_cnt 个点
//
//        for(int i=1;i<=n;i++) {
//            for(int j=h[i]; j!=-1; j=ne[j]) {
//                int y = to[j];
//                if(sccno[i] != sccno[y]) {
//                    // add(sccno[i],sccno[y]);  // 建新图
//                    to1[ct1] = sccno[y];
//                    ne1[ct1] = h[sccno[i]];
//                    h[sccno[i]] = ct1++;
//                    du[sccno[y]]++; //存入度
//                }
//            }
//        }
//
//        int q[] = new int[100000];
//        int end = 0;
//        int st = 0;
//        for(int i=1;i<=scc_cnt;++i){
//            if(du[i]==0){
//                q[end++] = i;
//            }
//        }
//
//        int dp[] = new int[scc_cnt+1];
//        while(st<end){
//            int cur = q[st++];
//            for(int i=h1[cur];i!=-1;i=ne1[i]){
//                int y = to[i];
//                // dp[y] += dp[cur];
//                if(--du[y]==0){
//                    q[end++] = y;
//                }
//            }
//        }
//    }
//
//
//
//
//    int fa[];
//    int faw[];
//
//    int dep = -1;
//    int pt = 0;
//    void go(int rt,int f,int dd){
//
//        int p = 0;
//        stk[p] = rt;
//        lk[p] = 0;
//        fk[p] = f;p++;
//        while(p>0) {
//            int cur = stk[p - 1];
//            int fp = fk[p - 1];
//            int ll = lk[p - 1];
//            p--;
//
//
//            if (ll > dep) {
//                dep = ll;
//                pt = cur;
//            }
//            for (int i = h[cur]; i != -1; i = ne[i]) {
//                int v = to[i];
//                if (fp == v) continue;
//
//                stk[p] = v;
//                lk[p] = ll + wt[i];
//                fk[p] = cur;
//                p++;
//            }
//        }
//    }
//    int pt1 = -1;
//    void go1(int rt,int f,int dd){
//
//        int p = 0;
//        stk[p] = rt;
//        lk[p] = 0;
//        fk[p] = f;p++;
//        while(p>0) {
//            int cur = stk[p - 1];
//            int fp = fk[p - 1];
//            int ll = lk[p - 1];
//            p--;
//
//
//            if (ll > dep) {
//                dep = ll;
//                pt1 = cur;
//            }
//
//            fa[cur] = fp;
//            for (int i = h[cur]; i != -1; i = ne[i]) {
//                int v = to[i];
//                if (v == fp) continue;
//                faw[v] = wt[i];
//                stk[p] = v;
//                lk[p] = ll + wt[i];
//                fk[p] = cur;
//                p++;
//            }
//        }
//    }
//
//    int r = 0;
//    int stk[] = new int[301];
//    int fk[] = new int[301];
//    int lk[] = new int[301];
//    void ddfs(int rt,int t1,int t2,int t3,int l){
//
//
//        int p = 0;
//        stk[p] = rt;
//        lk[p] = 0;
//        fk[p] = t3;p++;
//        while(p>0){
//            int cur = stk[p-1];
//            int fp = fk[p-1];
//            int ll = lk[p-1];
//            p--;
//            r = Math.max(r,ll);
//            for(int i=h[cur];i!=-1;i=ne[i]){
//                int v = to[i];
//                if(v==t1||v==t2||v==fp) continue;
//                stk[p] = v;
//                lk[p] = ll+wt[i];
//                fk[p] = cur;p++;
//            }
//        }
//
//
//
//    }


    static long mul(long a, long b, long p)
    {
        long res=0,base=a;
        while(b>0)
        {
            if((b&1L)>0)
                res=(res+base)%p;
            base=(base+base)%p;
            b>>=1;
        }
        return res;
    }

    static long mod_pow(long k,long n,long p){
        long res = 1L;
        long temp = k;
        while(n!=0L){
            if((n&1L)==1L){
                res = (res*temp)%p;
            }
            temp = (temp*temp)%p;
            n = n>>1L;
        }
        return res%p;
    }
    int ct = 0;
    int f[]  =new int[200001];
    int b[]  =new int[200001];
    int str[]  =new int[200001];

    void go(int rt,List<Integer> g[]){
        str[ct] = rt;
        f[rt] = ct;
        for(int cd:g[rt]){
            ct++;
            go(cd,g);
        }
        b[rt] = ct;
    }
    int add =0;

    void sort(long a[]) {
        Random rd = new Random();
        for (int i = 1; i < a.length; ++i) {
            int p = rd.nextInt(i); long x = a[p]; a[p] = a[i]; a[i] = x;
        }
        Arrays.sort(a);
    }

    void dfs(int from,int k){

    }
    void add(int u,int v){
        to[ct] = u;
        ne[ct] = h[v];
        h[v] = ct++;
    }
    int r =0;
    void dfs1(int c,int ff){
        clr[c][aa[c]]++;
        for(int j=h[c];j!=-1;j=ne[j]){
            if(to[j]==ff) continue;
            dfs1(to[j],c);
            clr[c][1] += clr[to[j]][1];
            clr[c][2] += clr[to[j]][2];
            if(clr[to[j]][1]==s1&&clr[to[j]][2]==0||clr[to[j]][2]==s2&&clr[to[j]][1]==0){
                r++;
            }
        }
    }

    int[] h,ne,to,fa;
    int clr[][];
    int aa[];
    int s1 = 0;
    int s2 = 0;

    boolean f(int n){
        int c = 0;
        while(n>0){
            c += n%10;
            n /=10;
        }
        return (c&3)==0;
    }

    int[][] next(String s){
        int len = s.length();
        int ne[][] = new int[len+1][26];
        Arrays.fill(ne[len], -1);
        for(int i=len-1;i>=0;--i){
            ne[i] = ne[i+1].clone();
            ne[i][s.charAt(i)-'a'] = i+1;
        }
        return ne;
    }


    void solve() {
        int  n  =ni();
        int a[] = na(n);
        Arrays.sort(a);

        int ct = 0;
        int cur = a[0] - 1;
        boolean f[] = new boolean[1500002];
        if(cur!=0) {
            f[cur] = true;ct++;
        }
        ot:for(int i=1;i<n;++i){
            for(int c=-1;c<=1;++c){
                if(!f[a[i]+c]&&a[i]+c>0){
                    f[a[i]+c] = true;ct++;
                    continue ot;
                }
            }
        }
        println(ct);












        //N , M , K , a , b , c , d . 其中N , M是矩阵的行列数；K 是上锁的房间数目，(a, b)是起始位置，(c, d)是出口位置


//        int n = ni();
//        int m = ni();
//        int k = ni();
//        int a = ni();
//        int b = ni();
//        int c = ni();
//        int d = ni();
//
//
//        char cc[][] = nm(n,m);
//        char keys[][] = new char[n][m];
//
//        char ky = 'a';
//        for(int i=0;i<k;++i){
//            int x = ni();
//            int y = ni();
//            keys[x][y] = ky;
//            ky++;
//        }
//        int f1[] = {a,b,0};
//
//        int dd[][] = {{0,1},{0,-1},{1,0},{-1,0}};
//
//        Queue<int[]> q =  new LinkedList<>();
//        q.offer(f1);
//        int ts = 1;
//
//        boolean vis[][][] = new boolean[n][m][33];
//
//        while(q.size()>0){
//            int sz = q.size();
//            while(sz-->0) {
//                int cur[] = q.poll();
//                vis[cur[0]][cur[1]][cur[2]] = true;
//
//                int x = cur[0];
//                int y = cur[1];
//
//                for (int u[] : dd) {
//                       int lx = x +  u[0];
//                       int ly = y +  u[1];
//                       if (lx >= 0 && ly >= 0 && lx < n && ly < m && (cc[lx][ly] != '#')&&!vis[lx][ly][cur[2]]){
//                            char ck =cc[lx][ly];
//                            if(ck=='.'){
//                                if(lx==c&&ly==d){
//                                    println(ts); return;
//                                }
//                                if(keys[lx][ly]>='a'&&keys[lx][ly]<='z') {
//                                    int cao = cur[2] | (1 << (keys[lx][ly] - 'a'));
//                                    q.offer(new int[]{lx, ly, cao});
//                                    vis[lx][ly][cao] = true;
//                                }else {
//
//                                    q.offer(new int[]{lx, ly, cur[2]});
//                                }
//
//                            }else if(ck>='A'&&ck<='Z'){
//                                int g = 1<<(ck-'A');
//                                if((g&cur[2])>0){
//                                    if(lx==c&&ly==d){
//                                        println(ts); return;
//                                    }
//                                    if(keys[lx][ly]>='a'&&keys[lx][ly]<='z') {
//
//                                        int cao = cur[2] | (1 << (keys[lx][ly] - 'a'));
//                                        q.offer(new int[]{lx, ly, cao});
//                                        vis[lx][ly][cao] = true;;
//                                    }else {
//
//                                        q.offer(new int[]{lx, ly, cur[2]});
//                                    }
//                                }
//                            }
//                       }
//                }
//
//                }
//            ts++;
//        }
//        println(-1);




//        int n = ni();
//
//        HashSet<String> st = new HashSet<>();
//        HashMap<String,Integer> mp = new HashMap<>();
//
//
//        for(int i=0;i<n;++i){
//            String s  = ns();
//            int id= 1;
//            if(mp.containsKey(s)){
//                int u = mp.get(s);
//                id = u;
//
//            }
//
//            if(st.contains(s)) {
//
//                while (true) {
//                    String ts = s + id;
//                    if (!st.contains(ts)) {
//                        s = ts;
//                        break;
//                    }
//                    id++;
//                }
//                mp.put(s,id+1);
//            }else{
//                mp.put(s,1);
//            }
//            println(s);
//            st.add(s);
//
//        }

//        int t = ni();
//
//        for(int i=0;i<t;++i){
//            int n = ni();
//            long w[] = nal(n);
//
//            Map<Long,Long> mp = new HashMap<>();
//            PriorityQueue<long[]> q =new PriorityQueue<>((xx,xy)->{return Long.compare(xx[0],xy[0]);});
//
//            for(int j=0;j<n;++j){
//                q.offer(new long[]{w[j],0});
//                mp.put(w[j],mp.getOrDefault(w[j],0L)+1L);
//            }
//
//            while(q.size()>=2){
//                long f[] = q.poll();
//                long y1 = f[1];
//                if(y1==0){
//                    y1 = mp.get(f[0]);
//                    if(y1==1){
//                        mp.remove(f[0]);
//                    }else{
//                        mp.put(f[0],y1-1);
//                    }
//                }
//                long g[] = q.poll();
//                long y2 = g[1];
//                if(y2==0){
//                    y2 = mp.get(g[0]);
//                    if(y2==1){
//                        mp.remove(g[0]);
//                    }else{
//                        mp.put(g[0],y2-1);
//                    }
//                }
//                q.offer(new long[]{f[0]+g[0],2L*y1*y2});
//
//            }
//            long r[] = q.poll();
//            println(r[1]);
//
//
//
//
//        }
        //  int o= 9*8*7*6;
        //  println(o);


//       int t = ni();
//       for(int i=0;i<t;++i){
//           long a = nl();
//           int k  = ni();
//           if(k==1){
//               println(a);
//               continue;
//           }
//
//           int f = (int)(a%10L);
//           int s = 1;
//           int j = 0;
//           for(;j<30;j+=2){
//               int u = f-j;
//               if(u<0){
//                   u = 10+u;
//               }
//               s = u*s;
//               s = s%10;
//               if(s==k){
//                   break;
//               }
//           }
//
//           if(s==k) {
//               println(a - j - 2);
//           }else{
//               println(-1);
//           }
//
//
//
//
//       }

//     int m = ni();
//     h = new int[n];
//     to = new int[2*(n-1)];
//     ne = new int[2*(n-1)];
//     wt = new int[2*(n-1)];
//
//     for(int i=0;i<n-1;++i){
//         int u = ni()-1;
//         int v = ni()-1;
//
//     }
        //  long a[] = nal(n);


//        int n = ni();
//        int k = ni();
//        t1 = new long[200002];
//
//        int p[][] = new int[n][3];
//
//        for(int i=0;i<n;++i){
//            p[i][0]  = ni();
//            p[i][1]  = ni();
//            p[i][2]  = i+1;
//        }
//        Arrays.sort(p, new Comparator<int[]>() {
//            @Override
//            public int compare(int[] x, int[] y) {
//                if(x[1]!=y[1]){
//                    return Integer.compare(x[1],y[1]);
//                }
//                return Integer.compare(y[0], x[0]);
//            }
//        });
//
//        for(int i=0;i<n;++i){
//            int ck = p[i][0];
//
//        }

















    }









//    int []h,to,ne,wt;

    long t1[];
    //    long t2[];
    void update(long[] t,int i,long v){
        for(;i<t.length;i+=(i&-i)){
            t[i] += v;
        }
    }
    long get(long[] t,int i){
        long s = 0;
        for(;i>0;i-=(i&-i)){
            s += t[i];
        }
        return s;
    }

    int equal_bigger(long t[],long v){
        int s=0,p=0;
        for(int i=Integer.numberOfTrailingZeros(Integer.highestOneBit(t.length));i>=0;--i) {
            if(p+(1<<i)< t.length && s + t[p+(1<<i)] < v){
                v -= t[p+(1<<i)];
                p |= 1<<i;
            }
        }
        return p+1;
    }









    static class S{
        int l = 0;
        int r = 0 ;
        long le = 0;
        long ri = 0;
        long tot = 0;
        long all = 0;

        public S(int l,int r) {
            this.l = l;
            this.r = r;
        }
    }

    static S a[];
    static int[] o;

    static void init(int[] f){
        o = f;
        int len = o.length;
        a = new S[len*4];
        build(1,0,len-1);
    }

    static void build(int num,int l,int r){
        S cur = new S(l,r);
        if(l==r){
            a[num] = cur;
            return;
        }else{
            int m = (l+r)>>1;
            int le = num<<1;
            int ri = le|1;
            build(le, l,m);
            build(ri, m+1,r);
            a[num] = cur;
            pushup(num, le, ri);
        }
    }



    //    static int query(int num,int l,int r){
//
//        if(a[num].l>=l&&a[num].r<=r){
//            return a[num].tot;
//        }else{
//            int m = (a[num].l+a[num].r)>>1;
//            int le = num<<1;
//            int ri = le|1;
//            pushdown(num, le, ri);
//            int ma = 1;
//            int mi = 100000001;
//            if(l<=m) {
//                int r1 = query(le, l, r);
//                ma = ma*r1;
//
//            }
//            if(r>m){
//                int r2 = query(ri, l, r);
//                ma = ma*r2;
//            }
//            return ma;
//        }
//    }
    static long dd = 10007;
    static void update(int num,int l,long v){
        if(a[num].l==a[num].r){
            a[num].le = v%dd;
            a[num].ri = v%dd;
            a[num].all = v%dd;
            a[num].tot = v%dd;
        }else{
            int m = (a[num].l+a[num].r)>>1;
            int le = num<<1;
            int ri = le|1;
            pushdown(num, le, ri);
            if(l<=m){
                update(le,l,v);
            }
            if(l>m){
                update(ri,l,v);
            }
            pushup(num,le,ri);
        }
    }

    static void pushup(int num,int le,int ri){
        a[num].all = (a[le].all*a[ri].all)%dd;
        a[num].le = (a[le].le + a[le].all*a[ri].le)%dd;
        a[num].ri = (a[ri].ri + a[ri].all*a[le].ri)%dd;
        a[num].tot = (a[le].tot + a[ri].tot + a[le].ri*a[ri].le)%dd;


        //a[num].res[1] = Math.min(a[le].res[1],a[ri].res[1]);
    }
    static void pushdown(int num,int le,int ri){

    }

    long gcd(long a,long b){ return b==0?a: gcd(b,a%b);}
    int gcd(int a,int b){ return b==0?a: gcd(b,a%b);}
    InputStream is;PrintWriter out;
    void run() throws Exception {is = System.in;out = new PrintWriter(System.out);solve();out.flush();}
    private byte[] inbuf = new byte[2];
    public int lenbuf = 0, ptrbuf = 0;
    private int readByte() {
        if (lenbuf == -1) throw new InputMismatchException();
        if (ptrbuf >= lenbuf) {
            ptrbuf = 0;
            try {lenbuf = is.read(inbuf);} catch (IOException e) {throw new InputMismatchException();}
            if (lenbuf <= 0) return -1;
        }
        return inbuf[ptrbuf++];}
    private boolean isSpaceChar(int c) {return !(c >= 33 && c <= 126);}
    private int skip() {int b;while((b = readByte()) != -1 && isSpaceChar(b));return b;}
    private double nd() {return Double.parseDouble(ns());}
    private char nc() {return (char) skip();}
    private char ncc() {int b;while((b = readByte()) != -1 && !(b >= 32 && b <= 126));return (char)b;}
    private String ns() {int b = skip();StringBuilder sb = new StringBuilder();
        while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b != ' ')
            sb.appendCodePoint(b);b = readByte(); }
        return sb.toString();}
    private char[] ns(int n) {char[] buf = new char[n];int b = skip(), p = 0;
        while (p < n && !(isSpaceChar(b))) { buf[p++] = (char) b;b = readByte(); }
        return n == p ? buf : Arrays.copyOf(buf, p);}
    private String nline() {int b = skip();StringBuilder sb = new StringBuilder();
        while (!isSpaceChar(b) || b == ' ') { sb.appendCodePoint(b);b = readByte(); }
        return sb.toString();}
    private char[][] nm(int n, int m) {char[][] a = new char[n][];for (int i = 0; i < n; i++) a[i] = ns(m);return a;}
    private int[] na(int n) {int[] a = new int[n];for (int i = 0; i < n; i++) a[i] = ni();return a;}
    private long[] nal(int n) { long[] a = new long[n];for (int i = 0; i < n; i++) a[i] = nl();return a;}
    private int ni() { int num = 0, b; boolean minus = false;
        while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-')){};
        if (b == '-') { minus = true; b = readByte(); }
        while (true) {
            if (b >= '0' && b <= '9') num = (num << 3) + (num << 1) + (b - '0');
            else return minus ? -num : num;
            b = readByte();}}
    private long nl() { long num = 0; int b;
        boolean minus = false;
        while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-')){};
        if (b == '-') { minus = true; b = readByte(); }
        while (true) {
            if (b >= '0' && b <= '9')  num = num * 10 + (b - '0');
            else return minus ? -num : num;
            b = readByte();}}
    void print(Object obj){out.print(obj);}
    void println(Object obj){out.println(obj);}
    void println(){out.println();}
}