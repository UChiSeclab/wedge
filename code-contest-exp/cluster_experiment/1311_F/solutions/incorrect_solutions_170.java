import java.util.*;
import java.io.*;
public class Codeforces
{
    static InputReader in=new InputReader(System.in);
    static OutputWriter out=new OutputWriter(System.out);    
    static StringBuilder sb=new StringBuilder();
    static long MOD = (long)(998244353);    
    // Main Class Starts Here    
    
    static boolean ip[];
    public static void pr() {
    	ip = new boolean[200001];
    	Arrays.fill(ip, true);
    	ip[0] = false;
    	ip[1] = false;
    	for(int i=2;i<=200000;i++) {
    		if(!ip[i])
    			continue;
    		for(int j= 2*i;j<=200000;j+=i) ip[j] = false;
    	}
    }
    
    public static void main(String args[])throws IOException
    {
    	// Write your code.    	
    	int t=1;
    	while(t-->0) {
    		int n=in();
    		long x[]=lan(n);
    		long v[]=lan(n);
    		Vector<Long> l = new Vector<>();
    		Vector<Long> r = new Vector<>();    		
    		for(int i=0;i<n;i++) {
    			if(v[i] < 0)
    				l.add(x[i]);
    			else
    				r.add(x[i]);
    		}
    		Collections.sort(l);
    		Collections.sort(r);
    		int rs = r.size();
    		int ls = l.size();
    		long sum = 0L;
    		for(long p:r) {
    			sum+=p;
    		}
    		long ans = 0L;
    		for(int i=0;i<ls;i++) {
    			long c = l.get(i) * rs;
    			c -= (sum);
    			ans+=c;
    		}
    		app(ans+"\n");
    	}    	    	    	
    	out.printLine(sb);
    	out.close();
    }
     static class Point{
    	int x;
    	int y;
    	public Point(int x,int y) {
    		this.x=x;
    		this.y=y;
    	}
    }
               
    public static long rev(long n) {
    	if(n<10L)
    		return n;
    	long rev = 0L;
    	while(n != 0L) {
    		long a = n % 10L;
    		rev = a + rev*10L;
    		n /= 10L;
    	}
    	return rev;
    }
    
    public static long pow(long a,long b) {
    	if(b==0)
    		return 1L;
    	if(b==1)
    		return a % MOD;
    	long f = a * a;
    	if(f > MOD)
    		f %= MOD;
    	long val = pow(f, b/2) % MOD;
    	if(b % 2 == 0)
    		return val;
    	else {
    		long temp = val * (a % MOD);
    		if(temp > MOD)
    			temp = temp % MOD;
    		return temp;
    	}
    }
    
    public static long lcm(long a,long b) {
    	long lcm = (a*b)/gcd(a,b);
    	return lcm;
    }
    
    public static long gcd(long a,long b) {
    	if(b==0)
    		return a;
    	return gcd(b, a%b);
    }
    
    public static int[] an(int n) {
    	int ar[]=new int[n];
    	for(int i=0;i<n;i++)
    		ar[i]=in();
    	return ar;
    }

    public static long[] lan(int n) {
    	long ar[]=new long[n];
    	for(int i=0;i<n;i++)
    		ar[i]=lin();
    	return ar;
    }    
    
    public static String atos(Object ar[]) {
    	return Arrays.toString(ar);
    }
    
    public static int in() {
    	return in.readInt();
    }
    
    public static long lin() {
    	return in.readLong();
    }    
    
    public static String sn() {
    	return in.readString();
    }
    
    public static void prln(Object o) {    	
    	out.printLine(o);
    }
    
    public static void prn(Object o) {    	
    	out.print(o);
    }
    
    public static int getMax(int a[]) {
    	int n = a.length;
    	int max = a[0];
    	for(int i=1; i < n; i++) {
    		max = Math.max(max, a[i]);
    	}
    	return max;
    }
    
    public static int getMin(int a[]) {
    	int n = a.length;
    	int min = a[0];
    	for(int i=1; i < n; i++) {
    		min = Math.min(min, a[i]);
    	}
    	return min;
    }
    
    public static void display(int a[]) {
    	out.printLine(Arrays.toString(a));
    }    
    
    public static HashMap<Integer,Integer> hm(int a[]){
    	HashMap<Integer,Integer> map=new HashMap<>();
    	for(int i=0; i < a.length;i++) {
    		int keep = (int)map.getOrDefault(a[i],0);
    		map.put(a[i],++keep);
    	}
    	return map;
    }
    
    public static void app(Object o) {
    	sb.append(o);
    }
    
    public static long calcManhattanDist(int x1,int y1,int x2,int y2) {
    	long xa = Math.abs(x1-x2);
    	long ya = Math.abs(y1-y2);
    	return (xa+ya);
    }
    
    
    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1)
                throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public long readLong() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next() {
            return readString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object...objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0)
                    writer.print(' ');
                writer.print(objects[i]);
            }
        }

        public void printLine(Object...objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }
    } 
}