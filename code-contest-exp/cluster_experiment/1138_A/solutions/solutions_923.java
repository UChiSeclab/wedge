import java.util.*;
import java.io.*;
import java.math.*;
 
public class Main {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(System.out);
        //Scanner sc = new Scanner();
        Reader in = new Reader();
        Main solver = new Main();
        solver.solve(out, in);
        out.flush();
        out.close();
 
    }
 
    //<>
    //
    
    static int maxn = (int)1e5*2;
    static long mod=998244353 ;
    static int n,m,t,k;
    
    static int[] arr;
    
    void solve(PrintWriter out, Reader in) throws IOException{   
        n = in.nextInt();
        
        arr = new int[n];
        for(int i=0;i<n;i++) arr[i] = in.nextInt();
        
        int prev=arr[0],now=1,cnt=0;
        int i =1;
        while(arr[i]==prev){
            now++;
            i++;
        }
        
        int ans=0;
        for(;i<n;i++){
            if(arr[i]!=prev){
                ans = Math.max(ans,Math.min(now,cnt)*2);
                cnt = now;
                now =1;
                prev = arr[i];
            }else{
                now++;
            }
        }
        ans = Math.max(ans,Math.min(cnt,now)*2);
        out.println(ans);
        
    }
    
    static int bs(int x){
        int lo=1,hi=x,mid,res=x;
        
        while(lo<=hi){
            mid = (lo+hi)/2;
            if(arr[x-1]-arr[mid-1]<=5){
                res = mid;
                hi = mid-1;
            }else{
                lo = mid+1;
            }
        }
        
        return res;
    }
    
    static class Node implements Comparable<Node>{
        int idx,v;
        
        Node(int idx,int v){
            this.idx = idx;
            this.v = v;
        }
        
        public int compareTo(Node o){
            return this.v-o.v;
        }
    }
    
    static class Edge {
        int d,w;
        
        Edge(int d,int w){
            this.d = d;
            this.w = w;
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
}