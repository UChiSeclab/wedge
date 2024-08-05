
import java.util.*;
import java.lang.*;
import java.lang.reflect.Array;
import java.io.*;
import java.math.*;
import java.text.DecimalFormat;
public class Prac{     
    static class InputReader { 
        private final InputStream stream;
	private final byte[] buf = new byte[8192];
	private int curChar, snumChars;
 	public InputReader(InputStream st) {
            this.stream = st;
	} 
	public int read() {
            if (snumChars == -1)
	 	throw new InputMismatchException();
            if (curChar >= snumChars) {
		curChar = 0;
                try {
                    snumChars = stream.read(buf);
		} 
                catch (IOException e) {
                    throw new InputMismatchException();
           	}
		if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
	}
        public int ni() {
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
		res *= 10;
		res += c - '0';
		c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
	} 
	public long nl() {
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
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
	} 
	public int[] nia(int n) {
            int a[] = new int[n];
            for (int i = 0; i < n; i++) {
		a[i] = ni();
            }
            return a;
	} 
	public String rs() {
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
	public String nextLine() {
            int c = read();
            while (isSpaceChar(c))
		c = read();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isEndOfLine(c));
            return res.toString();
        } 
	public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}
 
	private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
	} 
    }
    static PrintWriter w = new PrintWriter(System.out);
    static long mod=998244353L;
    public static class Key {
 
    private final int x;
    private final int y;
 
    public Key(int x, int y) {
        this.x = x;
        this.y = y;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return x == key.x && y == key.y;
    }
 
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
 
}
    static int jump[],n,mov[];
    static boolean v[];
    static ArrayList<Integer> arr[],revarr[];   
    public static void main (String[] args)throws IOException{
        InputReader sc=new InputReader(System.in);
        n=sc.ni();
        mov=new int[n+1];
        Arrays.fill(mov,-1);
        v=new boolean[n+1];
        jump=new int[n+1];
        arr=new ArrayList[n+1];
        revarr=new ArrayList[n+1];
        for(int i=0;i<=n;i++){
            mov[i]=Integer.MAX_VALUE;
            arr[i]=new ArrayList<>();
            revarr[i]=new ArrayList<>();
        }
        for(int i=1;i<=n;i++){
            jump[i]=sc.ni();
            if(i+jump[i]<=n){
                arr[i].add(i+jump[i]);
                revarr[i+jump[i]].add(i);
            }
            if(i-jump[i]>0){
                arr[i].add(i-jump[i]);
                revarr[i-jump[i]].add(i);
            }
        }
        Queue<Integer> q=new LinkedList<>();
        for(int i=1;i<=n;i++){
            for(int j:arr[i]){
                if(jump[i]%2!=jump[j]%2){
                    mov[i]=1;
                    q.add(i);
                }
            }
        }
        while(!q.isEmpty()){
            int x=q.poll();
            for(int j:revarr[x]){
                if(mov[j]>mov[x]+1){
                    mov[j]=mov[x]+1;
                    q.add(j);
                }
                
            }
        }
        for(int i=1;i<=n;i++)w.print((mov[i]>=(int)1e7)?-1+" ":mov[i]+" ");
        w.println();
        w.close();
    }
}