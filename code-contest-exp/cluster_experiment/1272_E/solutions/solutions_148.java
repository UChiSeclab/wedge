import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import java.math.*;
 
public class Myclass{
    static ArrayList[] adj=new ArrayList[200001];
    static boolean visited[] = new boolean [200001];
    static int ans[] = new int [200001];
    static void bfs(PriorityQueue<pair>pq) {
    		while(!pq.isEmpty()) {
    			pair p = pq.poll();
    			int x = p.y;
    			for (int i = 0 ; i < adj[x].size() ; i++) {
    				int idx = (int) adj[x].get(i);
    				if(!visited[idx]) {
    					ans [idx] = ans[x] + 1;
    					pq.add(new pair(ans[idx] , idx));
    					visited[idx] = true;
    				}
    			}
    		}
    }
     public void solve () throws FileNotFoundException  {
        InputReader in = new InputReader(System.in);
        PrintWriter pw = new PrintWriter(System.out);
        int n = in.nextInt();
        int a[] = new int [n];
        for(int i = 0 ; i < n ;i++) {
        	a[i] = in.nextInt();
        	adj[i] = new ArrayList<Integer>();
        }
        Arrays.fill(ans, -1);
        PriorityQueue<pair> pq1 = new PriorityQueue<>();
        PriorityQueue<pair> pq2 = new PriorityQueue<>();
        for(int i = 0 ;i < n; i++) {
        	if(i + a[i] < n) {
        		if(a[i + a[i]] % 2 == a[i] % 2) {
        			adj[i + a[i]].add(i); 
        		}
        		else {
        			if(a[i + a[i]] % 2 == 0) {
        				pq1.add(new pair(1,i));
        			}
        			else
        				pq2.add(new pair(1,i));
    				visited[i] = true; ans[i] = 1;
        		}
        	}
        	if(i - a[i] >= 0) {
        		if(a[i - a[i]] % 2 == a[i] % 2) {
        			adj[i - a[i]].add(i);
        		}
        		else {
        			if(a[i - a[i]] % 2 == 0) {
        				pq1.add(new pair(1,i));
        			}
        			else
        				pq2.add(new pair(1,i));
    				visited[i] = true; ans[i] = 1;
 
        		}
        	}
        }
        
        bfs(pq1);
        bfs(pq2);
        for(int i = 0 ;i < n ;i++) {
        	pw.print(ans[i] +" ");
        }
        
        pw.flush();
        pw.close();
    }
    public static void main(String[] args) throws Exception {
        
        
        new Thread(null,new Runnable() {
            public void run() {
                    try {
                        new Myclass().solve();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }
        },"1",1<<26).start();
        
        
    }
   
    static void debug(Object... o) {
        System.out.println(Arrays.deepToString(o));
        }
        
        static class InputReader 
        {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;
        private SpaceCharFilter filter;
 
        public InputReader(InputStream stream) 
        {
            this.stream = stream;
        }
        public int snext() 
        {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) 
            {
                curChar = 0;
                try 
                {
                    snumChars = stream.read(buf);
                } 
                catch (IOException e) 
                {
                    throw new InputMismatchException();
                }
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }
 
        public int nextInt() 
        {
            int c = snext();
            while (isSpaceChar(c)) 
            {
                c = snext();
            }
            int sgn = 1;
            if (c == '-')
            {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do 
            {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }
 
        public long nextLong()
        {
            int c = snext();
            while (isSpaceChar(c)) 
            {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') 
            {
                sgn = -1;
                c = snext();
            }
            long res = 0;
            do 
            {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }
 
        public int[] nextIntArray(int n) 
        {
            int a[] = new int[n];
            for (int i = 0; i < n; i++) 
            {
                a[i] = nextInt();
            }
            return a;
        }
 
        public String readString()
        {
            int c = snext();
            while (isSpaceChar(c)) 
            {
                c = snext();
            }
            StringBuilder res = new StringBuilder();
            do 
            {
                res.appendCodePoint(c);
                c = snext();
            } while (!isSpaceChar(c));
            return res.toString();
        }
 
        public String nextLine() 
        {
            int c = snext();
            while (isSpaceChar(c))
                c = snext();
            StringBuilder res = new StringBuilder();
            do 
            {
                res.appendCodePoint(c);
                c = snext();
            } while (!isEndOfLine(c));
            return res.toString();
        }
 
        public boolean isSpaceChar(int c) 
        {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }
 
        private boolean isEndOfLine(int c) 
        {
            return c == '\n' || c == '\r' || c == -1;
        }
 
        public interface SpaceCharFilter
        {
            public boolean isSpaceChar(int ch);
        }
    }
        public static long mod = 1000000007;
        public static int d;
        public static int p;
        public static int q;
        public void extended(int a,int b) {
            if(b==0) {
                d=a;
                p=1;
                q=0;
            }
            else
            {
                extended(b,a%b);
                int temp=p;
                p=q;
                q=temp-(a/b)*q;
            }
        }
       
        
        public static long binaryExponentiation(long x,long n)
        {
            long result=1;
            while(n>0)
            {
                if(n % 2 ==1)
                    result=result * x;
                x=x*x;
                n=n/2;
            }
            return result;
        }
        
        public static long[] shuffle(long[] a,Random gen)
        {
            int n = a.length;
            for(int i=0;i<n;i++)
            {
                int ind = gen.nextInt(n-i)+i;
                long temp = a[ind];
                a[ind] = a[i];
                a[i] = temp;
            }
            return a;
        }
        
        public static void swap(int a, int b){
            int temp = a;
            a = b;
            b = temp;
        }
        
        public static HashSet<Integer> primeFactorization(int n)
        {
            HashSet<Integer> a =new HashSet<Integer>();
            for(int i=2;i*i<=n;i++)
            {
                while(n%i==0)
                {
                    a.add(i);
                    n/=i;
                }
            }
            if(n!=1)
                a.add(n);
            return a;
        }
        
        public static void sieve(boolean[] isPrime,int n)
        {
            for(int i=1;i<n;i++)
                isPrime[i] = true;
            
            isPrime[0] = false;
            isPrime[1] = false;
            
            for(int i=2;i*i<n;i++)
            {
                if(isPrime[i] == true)
                {
                    for(int j=(2*i);j<n;j+=i)
                        isPrime[j] = false;
                }
            }
        }
        
        public static long GCD(long n,long m)
        {
            if(m==0)
                return n;
            else
                return GCD(m,n%m);
        }
        public static long LCM (long a,long b) {
            return ((a*b)/(GCD(a,b)));
        }
        
        static class pair implements Comparable<pair>
        {
            Integer x ,y;
            
            pair(int x ,int y)
            {
                this.x=x;
                this.y=y;
            }

           
            public int compareTo(pair o) {
                int result = x.compareTo(o.x);
                if(result==0)
                    result = y.compareTo(o.y);
                
                return result;
            }  
            
            public String toString()
            {
                return x+" "+y;
            }
            
            public boolean equals(Object o)
            {
                if (o instanceof pair)
                {
                    pair p = (pair)o;
                    return p.x == x && p.y == y ;
                }
                return false;
            }
            
            public int hashCode()
            {
                return new Double(x).hashCode()*31 + new Long(y).hashCode();
            }
        }
        public static int binaryExponentiation(int x,int n)
        {
            int result=1;
            while(n>0)
            {
                if(n % 2 ==1)
                    result=result * x;
                x=x*x;
                n=n/2;
            }
            return result;
        }
        
       
        
        public static int modularExponentiation(int x,int n,int M)
        {
            int result=1;
            while(n>0)
            {
                if(n % 2 ==1)
                    result=(result * x)%M;
                x=(x%M*x)%M;
                n=n/2;
            }
            return result;
        }
        
        public static long modularExponentiation(long x,long n,long M)
        {
            long result=1;
            while(n>0)
            {
                if(n % 2 ==1)
                    result=(result%M * x%M)%M;
                x=(x%M * x%M)%M;
                n=n/2;
            }
            return result;
        }
        
        public static long modInverse(int A,int M)
        {
            return modularExponentiation(A,M-2,M);
        }
        
        public static long modInverse(long A,long M)
        {
            return modularExponentiation(A,M-2,M);
        }
        

        
         
}
class dsu{
	int parent[];
	ArrayList a[];
	long b [];
	static int d;
	static long ans = 0;
	dsu(int n,long c[] ,int d){
		parent=new int[n+1];
		a=new ArrayList[n+1];
		b = c.clone();
		this.d = d;
		for(int i=1;i<=n;i++)
		{
			parent[i]=i;
			a[i]=new ArrayList<Long>();
			a[i].add(b[i]);
		}
	}
	int root(int n) {
		while(parent[n]!=n)
		{ 
			parent[n]=parent[parent[n]];
			n=parent[n];
		}
		return n;
	}
	void union(int _a,int _b) {
		int p_a=root(_a);
		int p_b=root(_b);
		if(a[p_a].size()>a[p_b].size()) {
			parent[p_b]=p_a;
			for(int i=0;i<a[p_b].size();i++) {
				long get = (long) a[p_b].get(i);
				int l = ub(p_a , get);
				int r = lb(p_a, get); 
				if(l <= r)
					ans += (r - l + 1);
			}
			merge(p_a, p_b);
			//a[p_b].clear();
		}
		else
		{
			parent[p_a]=p_b;
			for(int i=0;i<a[p_a].size();i++) {
				long get = (long) a[p_a].get(i);
				int l = ub(p_b , get);
				int r = lb(p_b, get); 
				if(l <= r)
					ans += (r - l + 1);
			}
			merge(p_b, p_a);
		//	a[p_a].clear();
		}
	}
	 void  merge(int x,int y) {
		int i = 0 ; int j = 0;
		ArrayList<Long>tmp = new ArrayList<>();
		while(i < a[x].size() && j < a[y].size() ) {
			long get1 = (long) a[x].get(i);
			long get2 = (long) a[y].get(j);
			if(get1 <= get2){ 
				tmp.add(get1); i++;
			}
			else
			{
				tmp.add(get2); j++;
			}
		}
		while(i < a[x].size()) {
			long get1 = (long) a[x].get(i);
			tmp.add(get1); i++;
		}
		while(j < a[y].size()) {
			long get2 = (long) a[y].get(j);
			tmp.add(get2); j++;
		}
		a[x] = (ArrayList<Long>) tmp.clone();
	}
	boolean find(int a,int b) {
		if(root(a)==root(b))
			return true;
		else
			return  false;
	}
	int size(int n) {
		return (a[n].size());
	}
	public int ub(int x ,long y ) {
		int start = 0;
		int end = a[x].size() - 1;
		int ans  = -1;
		while(start <= end) {
			int mid = (start + end)/2;
			long got = (long) a[x].get(mid);
			if(y - got <= d) {
				ans = mid;
				end = mid - 1;
			}
			else
				start = mid + 1;
		}
		if(ans == -1) {
			return  a[x].size();
		}
		else
			return ans;
		
	}
	public int lb(int x ,long y ) {
		int start = 0;
		int end = a[x].size() - 1;
		int ans  = -1;
		while(start <= end) {
			int mid = (start + end)/2;
			long got = (long) a[x].get(mid);
			if(got - y <= d) {
				ans = mid;
				start = mid + 1;
			}
			else
				end = mid - 1;
		}
		if(ans == -1) {
			return   -1;
		}
		else
			return ans;
		
	}
	static void debug(Object... o) {
        System.out.println(Arrays.deepToString(o));
    }
	
}
