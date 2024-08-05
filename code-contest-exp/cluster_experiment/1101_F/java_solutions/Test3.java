import javafx.util.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class Test3 {

    PrintWriter pw = new PrintWriter(System.out); InputStream is = System.in;
    Random rnd = new Random();
    C[] c;
    long[] m;
    int a;

    void run(){
        a = ni();
        int k = ni();
        m = new long[a];
        for(int q=0; q<a; q++) m[q] = ni();
        ArrayList<C> l = new ArrayList<>();
        c = new C[k];
        for(int q=0; q<k; q++) l.add(new C(ni()-1, ni()-1, ni(), ni()));
        Collections.shuffle(l);
        for(int q=0; q<k; q++) c[q] = l.get(q);
        long o = getans(c[0].s, c[0].f, c[0].c, c[0].r);
        for(int q=1; q<k; q++){
            if(check(c[q].s, c[q].f, c[q].c, c[q].r, o)) continue;
            o = getans(c[q].s, c[q].f, c[q].c, c[q].r);
        }
        pw.print(o);
        pw.flush();
    }

    boolean check(int s, int f, long c, long r, long v){
        long inbak = v;
        boolean fl = true;
        for(; s<f && fl; s++){
            long d = (m[s+1]-m[s])*c;
            if(d>v) fl = false;
            if(d<=inbak){
                inbak-=d;
                continue;
            }
            if(r==0) fl = false;
            r--; inbak = v-d;
        }
        return fl;
    }

    long getans(int s, int f, long c, long r){
        long ll = 0, rr = (long)2e18;
        for(; ll+1!=rr;){
            long md = (ll+rr)/2;
            if(check(s,f,c,r,md)) rr=md;
            else ll=md;
        }
        return rr;
    }

    public static void main(String[] args) {
        new Test3().run();
    }

    private byte[] inbuf = new byte[1024];
    public int lenbuf = 0, ptrbuf = 0;

    private int readByte() {
        if (lenbuf == -1) throw new InputMismatchException();
        if (ptrbuf >= lenbuf) {
            ptrbuf = 0;
            try {
                lenbuf = is.read(inbuf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (lenbuf <= 0) return -1;
        }
        return inbuf[ptrbuf++];
    }

    private boolean isSpaceChar(int c) {
        return !(c >= 33 && c <= 126);
    }

    private int skip() {
        int b;
        while ((b = readByte()) != -1 && isSpaceChar(b)) ;
        return b;
    }

    private double nd() {
        return Double.parseDouble(ns());
    }

    private char nc() {
        return (char) skip();
    }

    private String ns() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while (!(isSpaceChar(b))) {
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }

    private char[] ns(int n) {
        char[] buf = new char[n];
        int b = skip(), p = 0;
        while (p < n && !(isSpaceChar(b))) {
            buf[p++] = (char) b;
            b = readByte();
        }
        return n == p ? buf : Arrays.copyOf(buf, p);
    }

    private char[][] nm(int n, int m) {
        char[][] map = new char[n][];
        for (int i = 0; i < n; i++) map[i] = ns(m);
        return map;
    }

    private int[] na(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = ni();
        return a;
    }

    private int ni() {
        int num = 0, b;
        boolean minus = false;
        while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-')) ;
        if (b == '-') {
            minus = true;
            b = readByte();
        }
        while (true) {
            if (b >= '0' && b <= '9') {
                num = num * 10 + (b - '0');
            } else {
                return minus ? -num : num;
            }
            b = readByte();
        }
    }

    private long nl() {
        long num = 0;
        int b;
        boolean minus = false;
        while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-')) ;
        if (b == '-') {
            minus = true;
            b = readByte();
        }
        while (true) {
            if (b >= '0' && b <= '9') {
                num = num * 10 + (b - '0');
            } else {
                return minus ? -num : num;
            }
            b = readByte();
        }
    }
}

class C{

    int s,f;
    long c,r;

    C(int a, int b, int cc, int d){
        s = a;
        f = b;
        c = cc;
        r = d;
    }
}