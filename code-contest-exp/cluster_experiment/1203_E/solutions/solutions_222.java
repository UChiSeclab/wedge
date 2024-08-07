import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.PriorityQueue;

public class Solution implements Runnable
	                {
	                    static final long MAX = 464897L;
	                    static class InputReader
	                    {
	                        private InputStream stream;
	                        private byte[] buf = new byte[1024];
	                        private int curChar;
	                        private int numChars;
	                        private SpaceCharFilter filter;
	                        private BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	                 
	                        public InputReader(InputStream stream)
	                        {
	                            this.stream = stream;
	                        }
	                        
	                        public int read()
	                        {
	                            if (numChars==-1) 
	                                throw new InputMismatchException();
	                            
	                            if (curChar >= numChars)
	                            {
	                                curChar = 0;
	                                try 
	                                {
	                                    numChars = stream.read(buf);
	                                }
	                                catch (IOException e)
	                                {
	                                    throw new InputMismatchException();
	                                }
	                                
	                                if(numChars <= 0)                
	                                    return -1;
	                            }
	                            return buf[curChar++];
	                        }
	                     
	                        public String nextLine()
	                        {
	                            String str = "";
	                            try
	                            {
	                                str = br.readLine();
	                            }
	                            catch (IOException e)
	                            {
	                                e.printStackTrace();
	                            }
	                            return str;
	                        }
	                        public int nextInt()
	                        {
	                            int c = read();
	                            
	                            while(isSpaceChar(c)) 
	                                c = read();
	                            
	                            int sgn = 1;
	                            
	                            if (c == '-') 
	                            {
	                                sgn = -1;
	                                c = read();
	                            }
	                            
	                            int res = 0;
	                            do 
	                            {
	                                if(c<'0'||c>'9') 
	                                    throw new InputMismatchException();
	                                res *= 10;
	                                res += c - '0';
	                                c = read();
	                            }
	                            while (!isSpaceChar(c)); 
	                            
	                            return res * sgn;
	                        }
	                        
	                        public long nextLong() 
	                        {
	                            int c = read();
	                            while (isSpaceChar(c))
	                                c = read();
	                            int sgn = 1;
	                            if (c == '-') 
	                            {
	                                sgn = -1;
	                                c = read();
	                            }
	                            long res = 0;
	                            
	                            do 
	                            {
	                                if (c < '0' || c > '9')
	                                    throw new InputMismatchException();
	                                res *= 10;
	                                res += c - '0';
	                                c = read();
	                            }
	                            while (!isSpaceChar(c));
	                                return res * sgn;
	                        }
	                        
	                        public double nextDouble() 
	                        {
	                            int c = read();
	                            while (isSpaceChar(c))
	                                c = read();
	                            int sgn = 1;
	                            if (c == '-') 
	                            {
	                                sgn = -1;
	                                c = read();
	                            }
	                            double res = 0;
	                            while (!isSpaceChar(c) && c != '.') 
	                            {
	                                if (c == 'e' || c == 'E')
	                                    return res * Math.pow(10, nextInt());
	                                if (c < '0' || c > '9')
	                                    throw new InputMismatchException();
	                                res *= 10;
	                                res += c - '0';
	                                c = read();
	                            }
	                            if (c == '.') 
	                            {
	                                c = read();
	                                double m = 1;
	                                while (!isSpaceChar(c)) 
	                                {
	                                    if (c == 'e' || c == 'E')
	                                        return res * Math.pow(10, nextInt());
	                                    if (c < '0' || c > '9')
	                                        throw new InputMismatchException();
	                                    m /= 10;
	                                    res += (c - '0') * m;
	                                    c = read();
	                                }
	                            }
	                            return res * sgn;
	                        }
	                        
	                        public String readString() 
	                        {
	                            int c = read();
	                            while (isSpaceChar(c))
	                                c = read();
	                            StringBuilder res = new StringBuilder();
	                            do 
	                            {
	                                res.appendCodePoint(c);
	                                c = read();
	                            } 
	                            while (!isSpaceChar(c));
	                            
	                            return res.toString();
	                        }
	                     
	                        public boolean isSpaceChar(int c) 
	                        {
	                            if (filter != null)
	                                return filter.isSpaceChar(c);
	                            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	                        }
	                     
	                        public String next() 
	                        {
	                            return readString();
	                        }
	                        
	                        public interface SpaceCharFilter 
	                        {
	                            public boolean isSpaceChar(int ch);
	                        }
	                    }
	                     
	                    public static void main(String args[]) throws Exception
	                    {
	                        new Thread(null, new Solution(),"Solution",1<<26).start();
	                    }   
	                    static long gcd(long a, long b) 
	                    { 
	                      if (b == 0) 
	                        return a; 
	                      return gcd(b, a % b);  
	                    } 
	                    static long lcm(long a,long b) {
	                        return (a*b)/gcd(a,b);
	                    }
	                    int maxn = 1000005;
	                    long MOD = 998244353;
	                    long prime = 29;
	                    ArrayList<Integer> adj[];
	                    HashMap<Integer,Integer> tmap = new HashMap();
	                    int[] val;
	                    int[] ans;
	                	public void run() 
	                    {
	                        InputReader sc = new InputReader(System.in);
	                        PrintWriter w = new PrintWriter(System.out);
	                        int n = sc.nextInt();
	                        PriorityQueue<Integer> pq = new PriorityQueue(Collections.reverseOrder());
	                        for(int i = 0;i < n;i++) {
	                        	pq.add(sc.nextInt());
	                        }
	                        boolean[] pos = new boolean[200005];
	                        while(!pq.isEmpty()) {
	                        	int x = pq.poll();
	                        	if(!pos[x+1]) {
	                        		pos[x+1] = true;
	                        	}else if(!pos[x]) {
	                        		pos[x] = true;
	                        	}else if(!pos[x-1] && x != 1) {
	                        		pos[x-1] = true;
	                        	}
	                        }
	                        int count = 0;
	                        for(int i = 1;i < pos.length;i++) {
	                        	if(pos[i]) {
	                        		count++;
	                        	}
	                        }
	                        w.println(count);
	                        w.close();
	                    } 
	                    static long power(long a,long b,long mod) {
                            long ans = 1;
                            a = a % mod;
                            while(b != 0) {
                                if(b % 2 == 1) {
                                    ans = (ans * a) % mod;
                                }
                                a = (a * a) % mod;
                                b = b/2;
                            }
                            return ans;
                        }
	                    class Pair implements Comparable<Pair>{
	                        int a;
	                        int b;
	                        int c;
	                        Pair(int a,int b,int c){
	                            this.b = b;
	                            this.a = a;
	                            this.c = c;
	                        }
	                        public boolean equals(Object o) {
	                            Pair p = (Pair)o;
	                            return this.a == p.a && this.b == this.b;
	                        }
	                        public int hashCode(){
	                            return Long.hashCode(a)*27 + Long.hashCode(b)*31;
	                        }
	                        public int compareTo(Pair p) {
	                            return Long.compare(this.b,p.b);
	                        }
	                    }
	                    class Pair2 implements Comparable<Pair2>{
	                        int a;
	                        int b;
	                        int c;
	                        Pair2(int a,int b,int c){
	                            this.b = b;
	                            this.a = a;
	                            this.c = c;
	                        }
	                        public boolean equals(Object o) {
	                            Pair p = (Pair)o;
	                            return this.a == p.a && this.b == this.b;
	                        }
	                        public int hashCode(){
	                            return Long.hashCode(a)*27 + Long.hashCode(b)*31;
	                        }
	                        public int compareTo(Pair2 p) {
	                            return Long.compare(p.a,this.a);
	                        }
	                    }
	                }