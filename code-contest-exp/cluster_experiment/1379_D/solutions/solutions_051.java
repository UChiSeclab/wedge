import java.util.*;
import java.io.*;
import java.math.*;
 
public class Main {
    static PrintWriter out;
    static Reader in;
    public static void main(String[] args) throws IOException {
        //out = new PrintWriter(new File("out.txt"));
        //PrintWriter out = new PrintWriter(System.out);
        //in = new Reader(new FileInputStream("in.txt"));
        //Reader in = new Reader();
        input_output();
        Main solver = new Main();
        solver.solve();
        out.flush();
        out.close();
 
    }
 
    static int INF = (int)2e9+5;
    static int maxn = (int)1e5+5;
    static int mod=(int)1e9+7 ;
    static int n, m, t, q, k;

    void solve() throws IOException{
        n = in.nextInt();
        int h = in.nextInt(),
            m = in.nextInt();
        k = in.nextInt();

        int[] hi = new int[n];
        int[] mi = new int[n];
        for (int i = 0; i < n; i++) {
            hi[i] = in.nextInt();
            mi[i] = in.nextInt();
        }

        ArrayList<Node> arr = new ArrayList<Node>();
        TreeSet<Integer> set = new TreeSet<>();

        int ans = 0;
        int th, tm, l, r, tmp, cur = 0;
        for (int i = 0; i < n; i++) {
            th = hi[i]; tm = mi[i];

            if (tm >= m/2) {
                if (m-k < tm) {
                    l = 0;
                    r = tm-(m-k)-1;
                    if (l > r) continue;
                    cur++;
                    set.add(i+1);
                    if (l != 0) arr.add(new Node (l, i, 0));
                    arr.add(new Node (r+1, i, 1));
                }
                l = tm-m/2+1;
                r = tm-(m/2-k)-1;
                if (l > r) continue;
                if (l == 0) {
                    cur++;
                    if(!set.contains(i+1))set.add(i+1);
                }
                if (l != 0) arr.add(new Node (l, i, 0));
                arr.add(new Node (r+1, i, 1));
            } else {
                if (m/2-k < tm) {
                    l = 0;
                    r = tm-(m/2-k)-1;
                    if (l > r) continue;
                    cur++;
                    set.add(i+1);
                    if (l != 0) arr.add(new Node (l, i, 0));
                    arr.add(new Node (r+1, i, 1));
                }
                l = tm+1;
                r = tm+k-1;
                if (l > r) continue;
                if (l == 0) {
                    cur++;
                    if (!set.contains(i+1)) set.add(i+1);
                }
                if (l != 0) arr.add(new Node (l, i, 0));
                arr.add(new Node (r+1, i, 1));
            }
        }

        Collections.sort(arr);
        Node e;
        int start = 0, remember = cur;
        ans = cur;
        for (int i = 0; i < arr.size(); i++) {
            e = arr.get(i);
            if (e.s >= m/2) break;
            if (e.t == 1) cur--;
            else cur++;

            if (i != arr.size()-1 && arr.get(i+1).s == e.s) continue;
            if (cur < ans) {
                ans = cur;
                start = e.s;
            }
        }
        
        out.println(ans +" "+start);
        cur = remember;
        if (cur == ans && start == 0) {
            for (int elm:set) out.print(elm+" ");
            out.println();
            return;
        }
        for (int i = 0; i < arr.size(); i++) {
            e = arr.get(i);
            if (e.s >= m/2) break;
            if (e.t == 1) {
                cur--;
                set.remove(e.id+1);
            } else {
                cur++;
                set.add(e.id+1);
            }

            if (i != arr.size()-1 && arr.get(i+1).s == e.s) continue;
            if (cur == ans && e.s == start) {
                break;
            }
        }

        for (int elm:set) out.print(elm+" ");
        out.println();
    }
 
    //<>

    static class Node implements Comparable<Node>{
        int s, id, t;

        Node (int s, int id, int t) {
            this.s = s;
            this.id = id;
            this.t = t;
        }

        public int compareTo(Node o) {
            if (this.s != o.s) return this.s - o.s;
            if (this.id == o.id) return this.t - o.t;
            return o.t - this.t;
        }
    }
    
    static class Reader {
 
        private InputStream mIs;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
    
        public Reader() {
            this(System.in);
        }
    
        public Reader(InputStream is) {
            mIs = is;
        }
    
        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
    
        }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = mIs.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }
    
        public String nextLine() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isEndOfLine(c));
            return res.toString();
        }
    
        public String next() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }
    
        double nextDouble()
        {
            return Double.parseDouble(next());
        }
    
        public long nextLong() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }
    
        public int nextInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }
    
        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }
    
        public boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
        }
 
    }
    static void input_output() throws IOException {
        File f = new File("in.txt");
        if(f.exists() && !f.isDirectory()) { 
            in = new Reader(new FileInputStream("in.txt"));
        } else in = new Reader();
        f = new File("out.txt");
        if(f.exists() && !f.isDirectory()) {
            out = new PrintWriter(new File("out.txt"));
        } else out = new PrintWriter(System.out);
    }
}