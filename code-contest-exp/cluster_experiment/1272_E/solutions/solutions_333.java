import java.io.*;
import java.math.BigInteger;
import java.util.*;

// written by luchy0120

public class Main {
    public static void main(String[] args) throws Exception {

        new Main().run();
    }


//    int get_room(int i,int j){
//        return i/3*3 + j/3;
//    }

//    int a[][] = new int[9][9];
//    int space = 0;
//
//    boolean vis_row[][] = new boolean[9][10];
//    boolean vis_col[][] = new boolean[9][10];
//    boolean vis_room[][] = new boolean[9][10];
//    int val[][][] =new int[9][9][];
//    int prepare[][];
//
//    void dfs(int rt){
//
//    }





    void solve(){



    int n = ni();
    int a[] = na(n);

        int q1[] = new int[10000000];
        int d[] = new int[n];
        Arrays.fill(d,n+2);

        List<Integer> from[] = new List[n];
        for (int i = 0; i < n; ++i) {
            from[i] = new ArrayList<>();
        }

        for(int u=0;u<2;++u) {
            int s = 0;
            int e = 0;

            for (int i = 0; i < n; ++i) {
                if (a[i] % 2 == u) {
                    int ck1 = a[i] + i;

                    if (ck1 < n ){
                        if(a[ck1] % 2 == 1-u) {
                            d[i] = 1;
                        }else{
                            from[ck1].add(i);
                        }
                    }
                    int ck2 = i - a[i];
                    if (ck2 >= 0 ) {
                        if(a[ck2] % 2 == 1-u) {
                            d[i] = 1;
                        }else{
                            from[ck2].add(i);
                        }
                    }
                    q1[e++] = i;
                }
            }

            while (s < e) {
                int c = q1[s++];
                for(int ck1:from[c]) {
                    if (d[ck1] > d[c] + 1) {
                        q1[e++] = ck1;
                        d[ck1] = d[c] + 1;
                    }
                }
            }
        }
        for(int i=0;i<n;++i){
            if(d[i]==n+2){
                d[i] = -1;
            }
        }
        printArray(d,0);


















    }
    public static String roundS(double result, int scale){
        String fmt = String.format("%%.%df", scale);
        return String.format(fmt, result);
    }








//    void solve() {
//
//        for(int i=0;i<9;++i) {
//            for (int j = 0; j < 9; ++j) {
//                int v = ni();
//                a[i][j] = v;
//                if(v>0) {
//                    vis_row[i][v] = true;
//                    vis_col[j][v] = true;
//                    vis_room[get_room(i, j)][v] = true;
//                }else{
//                    space++;
//                }
//            }
//        }
//
//
//        prepare = new int[space][2];
//
//        int p = 0;
//
//        for(int i=0;i<9;++i) {
//            for (int j = 0; j < 9; ++j) {
//                if(a[i][j]==0){
//                    prepare[p][0] = i;
//                    prepare[p][1]= j;p++;
//                    List<Integer> temp =new ArrayList<>();
//                    for(int k=1;k<=9;++k){
//                        if(!vis_col[j][k]&&!vis_row[i][k]&&!vis_room[get_room(i,j)][k]){
//                            temp.add(k);
//                        }
//                    }
//                    int sz = temp.size();
//                    val[i][j] = new int[sz];
//                    for(int k=0;k<sz;++k){
//                        val[i][j][k] = temp.get(k);
//                    }
//                }
//            }
//        }
//        Arrays.sort(prepare,(x,y)->{
//           return Integer.compare(val[x[0]][x[1]].length,val[y[0]][y[1]].length);
//        });
//        dfs(0);
//
//
//
//
//
//
//
//
//
//
//    }




    InputStream is;
    PrintWriter out;

    void run() throws Exception {
        is = System.in;
        out = new PrintWriter(System.out);
        solve();
        out.flush();
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

    private char ncc() {
        int b = readByte();
        return (char) b;
    }

    private String ns() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b != ' ')
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

    private String nline() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while (!isSpaceChar(b) || b == ' ') {
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }

    private char[][] nm(int n, int m) {
        char[][] a = new char[n][];
        for (int i = 0; i < n; i++) a[i] = ns(m);
        return a;
    }

    private int[] na(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = ni();
        return a;
    }

    private long[] nal(int n) {
        long[] a = new long[n];
        for (int i = 0; i < n; i++) a[i] = nl();
        return a;
    }

    private int ni() {
        int num = 0, b;
        boolean minus = false;
        while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-')) {
        }
        ;
        if (b == '-') {
            minus = true;
            b = readByte();
        }
        while (true) {
            if (b >= '0' && b <= '9') num = (num << 3) + (num << 1) + (b - '0');
            else return minus ? -num : num;
            b = readByte();
        }
    }

    private long nl() {
        long num = 0;
        int b;
        boolean minus = false;
        while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-')) {
        }
        ;
        if (b == '-') {
            minus = true;
            b = readByte();
        }
        while (true) {
            if (b >= '0' && b <= '9') num = num * 10 + (b - '0');
            else return minus ? -num : num;
            b = readByte();
        }
    }

    void print(Object obj) {
        out.print(obj);
    }

    void println(Object obj) {
        out.println(obj);
    }

    void println() {
        out.println();
    }

    void printArray(int a[],int from){
        int l = a.length;
        for(int i=from;i<l;++i){
            print(a[i]);
            if(i!=l-1){
                print(" ");
            }
        }
        println();
    }

    void printArray(long a[],int from){
        int l = a.length;
        for(int i=from;i<l;++i){
            print(a[i]);
            if(i!=l-1){
                print(" ");
            }
        }
        println();
    }
}