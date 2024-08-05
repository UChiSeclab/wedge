import java.lang.*;
import java.math.*;
import java.util.*;
import java.io.*;
public class Main {
class Node {
    int s;
    int f;
    int c;
    int r;
    public Node(int s,int f,int c,int r){
        this.s=s;
        this.r=r;
        this.c=c;
        this.f=f;
    }
}
    void solve() {
        int n=ni(),m=ni();
        int a[]=new int[n+1];
        for(int i=1;i<=n;i++) a[i]=ni();
        Node p[]=new Node[m];
        for(int i=0;i<m;i++) p[i]=new Node(ni(),ni(),ni(),ni());
        long l=1,r=1000000000;
        r*=r;
        long ans=-1;
       out: while(l<=r){
            long V=(l+r)>>1;
            for(int i=0;i<m;i++){
                long fuel=V;
                int cc=0;
                for(int j=p[i].s+1;j<=p[i].f;j++){
                    if(fuel-(a[j]-a[j-1])*p[i].c<0){
                        l=V+1;
                        continue out;
                    }
                    fuel-=(a[j]-a[j-1])*p[i].c;
                    if(j<p[i].f && fuel-(a[j+1]-a[j])*p[i].c<0){
                        fuel=V;
                        cc++;
                        if(cc>p[i].r){
                            l=V+1;
                            continue out;
                        }
                    }
                }
            }

            ans=V;
           r=V-1;

        }
        pw.println(ans);

    }
    int basis[];
    int LOGN=30;
    void insert(int x){
        for(int i=LOGN-1;i>=0;i--){
            if(((x>>i)&1)==0) continue;
            if(basis[i]==-1){
                basis[i]=x;
                break;
            }else {
                x^=basis[i];
            }
        }
    }

    long M= (long)1e9+7;
    InputStream is;
    PrintWriter pw;
    String INPUT = "";
    void run() throws Exception {
        is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
        pw = new PrintWriter(System.out);
        long s = System.currentTimeMillis();
        solve();
        pw.flush();
        if(!INPUT.isEmpty())tr(System.currentTimeMillis()-s+"ms");

    }
    public static void main(String[] args) throws Exception { new Main().run(); }

    private byte[] inbuf = new byte[1024];
    public int lenbuf = 0, ptrbuf = 0;

    private int readByte() {
        if(lenbuf == -1)throw new InputMismatchException();
        if(ptrbuf >= lenbuf){
            ptrbuf = 0;
            try { lenbuf = is.read(inbuf); } catch (IOException e) { throw new InputMismatchException(); }
            if(lenbuf <= 0)return -1;
        }
        return inbuf[ptrbuf++];
    }

    private boolean isSpaceChar(int c) { return !(c >= 33 && c <= 126); }
    private int skip() { int b; while((b = readByte()) != -1 && isSpaceChar(b)); return b; }

    private double nd() { return Double.parseDouble(ns()); }
    private char nc() { return (char)skip(); }

    private String ns() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while(!(isSpaceChar(b))){ // when nextLine, (isSpaceChar(b) && b != ' ')
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }

    private char[] ns(int n) {
        char[] buf = new char[n];
        int b = skip(), p = 0;
        while(p < n && !(isSpaceChar(b))){
            buf[p++] = (char)b;
            b = readByte();
        }
        return n == p ? buf : Arrays.copyOf(buf, p);
    }

    private char[][] nm(int n, int m) {
        char[][] map = new char[n][];
        for(int i = 0;i < n;i++)map[i] = ns(m);
        return map;
    }

    private int[] na(int n) {
        int[] a = new int[n];
        for(int i = 0;i < n;i++)a[i] = ni();
        return a;
    }

    private int ni() {
        int num = 0, b;
        boolean minus = false;
        while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
        if(b == '-'){
            minus = true;
            b = readByte();
        }

        while(true){
            if(b >= '0' && b <= '9'){
                num = num * 10 + (b - '0');
            }else{
                return minus ? -num : num;
            }
            b = readByte();
        }
    }

    private long nl() {
        long num = 0;
        int b;
        boolean minus = false;
        while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
        if(b == '-'){
            minus = true;
            b = readByte();
        }

        while(true){
            if(b >= '0' && b <= '9'){
                num = num * 10 + (b - '0');
            }else{
                return minus ? -num : num;
            }
            b = readByte();
        }
    }
    private boolean oj = System.getProperty("ONLINE_JUDGE") != null;
    private void tr(Object... o) { if(INPUT.length() > 0)System.out.println(Arrays.deepToString(o)); }

}