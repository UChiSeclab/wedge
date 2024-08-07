import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
public class Main implements Runnable{
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
        new Thread(null, new Main(),"Main",1<<26).start();
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
    long MOD = 1000000007;
    long prime = 29;
    ArrayList<Integer> adj[];
    HashMap<Integer,Integer> tmap = new HashMap();
    static class Node implements Comparable<Node>{
		int destination;
		long weight;
		Node(int destination,long weight){
			this.destination = destination;
			this.weight = weight;
		}
		Node(){
			
		}
		@Override
		public int compareTo(Node n) {
			return Long.signum(weight - n.weight);
		}
	}
    int[] val;
    boolean[] visited;
	public void run() 
    {
        InputReader sc = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int t = 1;
        while(t-- > 0) {
        	int n = sc.nextInt();
        	int[] arr = new int[n];
        	ans = new int[n];
        	for(int i = 0;i < n;i++) {
        		arr[i] = sc.nextInt();
        		ans[i] = -2;
        	}
        	for(int i = 0;i < n;i++) {
        		if(ans[i] == -2) {
        			dfs(i,arr);
        		}
        	}
        	
        	for(int i = 0;i < n;i++) {
        		if(ans[i] == 10000000) {
        			w.print("-1 ");
        		}else {
        			w.print(ans[i] + " ");
        		}
        	}
        }
        w.close();
    } 
	int[] ans;
	int dfs(int index,int[] arr) {
		ans[index] = 10000000;
		if(index - arr[index] >= 0) {
			if(arr[index - arr[index]] % 2 != arr[index] % 2) {
				ans[index] = 1;
			}else if(ans[index - arr[index]] != -2) {
				ans[index] = Math.min(ans[index],1 + ans[index - arr[index]]);
			}else {
				ans[index] = Math.min(ans[index], 1 + dfs(index - arr[index],arr));
			}
		} if(index + arr[index] < arr.length) {
			if(arr[index + arr[index]] % 2 != arr[index] % 2) {
				ans[index] = 1;
			}else if(ans[index + arr[index]] != -2) {
				ans[index] = Math.min(ans[index],1 + ans[index + arr[index]]);
			}else {
				ans[index] = Math.min(ans[index], 1 + dfs(index + arr[index],arr));
			}
		}
		return ans[index];
	}
	static long power(long a,long b,long MOD) {
        long ans = 1;
        a = a % MOD;
        while(b != 0) {
            if(b % 2 == 1) {
                ans = (ans * a) % MOD;
            }
            a = (a * a) % MOD;
            b = b/2;
        }
        return ans;
    }
    class Equal implements Comparable<Equal>{
    	Character a;
    	int b;
    	public Equal(char a,int b){
    		this.a = a;
    		this.b = b;
    	}
		@Override
		public int compareTo(Equal an) {
			// TODO Auto-generated method stub
			return this.a.compareTo(an.a);
		}
    }
    class Pair implements Comparable<Pair>{
        long a;
        long b;
        long c;
        Pair(long a,long b,long c){
            this.b = b;
            this.a = a;
            this.c = c;
        }
        public boolean equals(Object o) {
            Pair p = (Pair)o;
            return this.a == p.a && this.b == p.b && this.c == p.c;
        }
        public int hashCode(){
            return Long.hashCode(a)*27 + Long.hashCode(b)*31;
        }
        public int compareTo(Pair p) {
       	     return Long.compare(this.a,p.a);
        }
    }
    
    class Pair3 implements Comparable<Pair3>{
        int a;
        int b;
        int c;
        Pair3(int a,int b,int c){
            this.b = b;
            this.a = a;
            this.c = c;
        }
        public boolean equals(Object o) {
            Pair3 p = (Pair3)o;
            return this.a == p.a && this.b == p.b && this.c == p.c;
        }
        public int hashCode(){
            return Long.hashCode(a)*27 + Long.hashCode(b)*31;
        }
        public int compareTo(Pair3 p) {
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
            Pair2 p = (Pair2)o;
            return this.a == p.a && this.b == p.b && this.c == p.c;
        }
        public int hashCode(){
            return Long.hashCode(a)*27 + Long.hashCode(b)*31 + Long.hashCode(c)*3;
        }
        public int compareTo(Pair2 p) {
            return Long.compare(p.a,this.a);
        }
    }
}