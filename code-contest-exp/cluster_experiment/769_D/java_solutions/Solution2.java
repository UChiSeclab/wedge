	                                                    import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.PrintWriter;
	import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
	import java.util.HashMap;
	import java.util.InputMismatchException;
	import java.util.Iterator;
	import java.util.List;
	import java.util.Random;
	import java.util.TreeMap;
	import java.util.TreeSet;
	                 
	                 
	                 
	                 
	                 
	                public class Solution2 implements Runnable
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
	                        new Thread(null, new Solution2(),"Solution2",1<<26).start();
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
	                    TreeMap<Integer,ArrayList<Integer>> tmap = new TreeMap();
	                   
	                    public void run() 
	                    {
	                        InputReader sc = new InputReader(System.in);
	                        PrintWriter w = new PrintWriter(System.out);
	                        int n = sc.nextInt();
	                        int m = sc.nextInt();
	                        int[] arr = new int[n];
	                        int[] freq = new int[10005];
	                        for(int i = 0;i < n;i++) {
	                        	arr[i] = sc.nextInt();
	                        	freq[arr[i]]++;
	                        }
	                        ArrayList<Integer>[][] ar = new ArrayList[16][16];
	                        for(int i = 0;i < 16;i++) {
	                        	for(int j = 0;j < 16;j++) {
	                        		ar[i][j] = new ArrayList();
	                        	}
	                        	
	                        }
	                        for(int i = 0;i < 16;i++) {
	                        	ar[i][0].add(0);
	                        }
	                        for(int i = 1;i <= 15;i++) {
	                        	for(int j = 1;j <= i;j++) {
	                        		for(Integer val: ar[i-1][j]) {
	                        			ar[i][j].add(val);
	                        		}
	                        		for(Integer val: ar[i-1][j-1]) {
	                        			int temp = val + (1 << (i-1));
	                        			if(temp <= 10000) {
	                        				ar[i][j].add(temp);
	                        			}
	                        		}
	                        	}
	                        }
	                        if(m == 0) {
	                        	long ans = 0;
	                        	for(int i = 0;i <= 10000;i++) {
	                        		ans += (freq[i]) * (freq[i] - 1)/2;
	                        	}
	                        	w.println(ans);
	                        	w.close();
	                        	System.exit(0);
	                        }
	                        long ans = 0;
	                        for(int i = 0;i <= 10000;i++) {
	                        	for(Integer x: ar[14][m]) {
	                        		if((x ^ i) <= 10000) {
	                        			ans += freq[i] * freq[x ^ i];
	                        		}
	                        	}
	                        }
	                        w.println(ans/2);
	                        w.close();
	                    }
	                    StringBuilder build = new StringBuilder();
	                    int ptr = 0;
	                    int[] ar = new int[5*5*4*3*2*1];
	                    void sort(int[] arr) {
	                    	int n = arr.length;
	                    	boolean f = false;
	                    	while(!f) {
	                    		for(int i = 0;i < n;i++) {
	                    			ar[ptr++] = arr[i];
	                    		}
	                    		int i;
	                    		for( i = n-2;i >= 0;i--) {
	                    			if(arr[i] < arr[i+1]) {
	                    				break;
	                    			}
	                    		}
	                    		if(i == -1) {
	                    			f = true;
	                    		}else {
	                    			int ceilIndex = findCeil(arr, arr[i], i+1, n-1);
	                    			int temp = arr[i];
	                    			arr[i] = arr[ceilIndex];
	                    			arr[ceilIndex] = temp;
	                    			reverse(arr, i+1, n-1);
	                    		}
	                    	}
	                    }
	                    void reverse(int arr[], int l, int h) 
	                    { 
	                       while (l < h) 
	                       { 
	                           int temp = arr[l];
	                           arr[l] = arr[h];
	                           arr[h] = temp;
	                           l++; 
	                           h--; 
	                       } 
	                    } 
	                    long[] fact = new long[1000005];
                        void pre() {
                            fact[0] = 1;
                            for(int i = 1;i < fact.length;i++) {
                                fact[i] = (fact[i-1] * i) % MOD;
                            }
                        }
	                    int findCeil (int str[],  int first, int l, int h) 
	                    { 
	                        // initialize index of ceiling element 
	                        int ceilIndex = l; 
	                      
	                        // Now iterate through rest of the elements and find 
	                        // the smallest character greater than 'first' 
	                        for (int i = l+1; i <= h; i++) 
	                          if (str[i] > first && str[i] < str[ceilIndex]) 
	                                ceilIndex = i; 
	                      
	                        return ceilIndex; 
	                    } 
	                    long calc(long a,long num) {
	                    	long oc = num/a - 1;
	                    	long ans = (oc * (oc + 1)/2) * a + oc;
	                    	return ans;
	                    }
	                    int[][] generator(int seed,int n,int m) {
	                    	Random generator = new Random(seed);
	                    	int[][] matrix = new int[n][m];
	                    	for(int i = 0;i < n;i++) {
	                    		for(int j = 0;j < m;j++) {
	                    			matrix[i][j] = generator.nextInt(2);
	                    		}
	                    	}
	                    	return matrix;
	                    }
	                    int rec(int[][] matrix,int n,int m) {
	                    	int ans = 1;
	                    	for(int i = 0;i < n;i++) {
	                    		for(int j = 0;j < m;j++) {
	                    			if(matrix[i][j] != 1) {
	                    				boolean res = false;
	                    				if(i - 1 >= 0) {
	                    					res = res | (matrix[i-1][j] != 1);
	                    				}
	                    				if(i + 1 < n) {
	                    					res = res | (matrix[i+1][j] != 1);
	                    				}
	                    				if(j - 1 >= 0) {
	                    					res = res | (matrix[i][j-1] != 1);
	                    				}
	                    				if(j + 1 < m) {
	                    					res = res | (matrix[i][j+1] != 1);
	                    				}
	                    				if(res) {
	                    					matrix[i][j] = 1;
	                    					int pos = i * m + (j);
	                    					
	                    				    int grundy = rec(matrix,n,m);
	                    					ans = ans & grundy;
	                    					
	                    					matrix[i][j] = 0;
	                    				}
	                    			}
	                    		}
	                    	}
	                    	ans = ans ^ 1;
	                    	return ans;
	                    }
	                    ArrayList<Integer> list[];
	                    HashMap<List<Integer>,Integer> M = new HashMap();
	                    int ID = 1;
	                    void dfs(int v,int[] my,int prev) {
	                    	list[v] = new ArrayList();
	                    	for(int i = 0;i < adj[v].size();i++) {
	                    		int u = adj[v].get(i);
	                    		if(u == prev) {
	                    			continue;
	                    		}
	                    		dfs(u,my,v);
	                    		list[v].add(my[u]);
	                    	}
	                    	Collections.sort(list[v]);
	                    	if(M.containsKey(list[v])) {
	                    		my[v] = M.get(list[v]);
	                    	}
	                    	else {
	                    		my[v] = ID;
	                    		M.put(list[v], ID);
	                    		ID++;
	                    	}
	                    }
	                    static boolean isPrime(long n) 
	                    { 
	                        // Corner cases 
	                        if (n <= 1) return false; 
	                        if (n <= 3) return true; 
	                      
	                        // This is checked so that we can skip  
	                        // middle five numbers in below loop 
	                        if (n % 2 == 0 || n % 3 == 0) return false; 
	                      
	                        for (int i = 5; i * i <= n; i = i + 6) 
	                            if (n % i == 0 || n % (i + 2) == 0) 
	                            return false; 
	                      
	                        return true; 
	                    } 
	                    static class SegTreeLazy{
	                		long[] segTree;
	                		long[] lazy;
	                		public SegTreeLazy(int n){
	                			segTree = new long[4*n];
	                			lazy = new long[4*n];
	                		}
	                		void build(int l,int r,int pos,long[] arr) {
	                			if(l == r) {
	                				segTree[pos] = arr[l];
	                				return;
	                			}
	                			int mid = (l + r)/2;
	                			build(l, mid, 2*pos+1, arr);
	                			build(mid+1, r, 2*pos+2, arr);
	                			segTree[pos] = segTree[2*pos+1] + segTree[2*pos+2];
	                		}
	                		void update(int l,int r,int pos,int ind,long val) {
	                			if(l > ind || r < ind) {
	                				return;
	                			}
	                			if(l == r) {
	                				segTree[pos] = val;
	                				return;
	                			}
	                			int mid = (l + r)/2;
	                			update(l, mid, 2*pos+1, ind, val);
	                			update(mid+1, r, 2*pos+2, ind, val);
	                			segTree[pos] = segTree[2*pos+1] + segTree[2*pos+2];
	                		}
	                		long query(int l,int r,int start,int end,int pos) {
	                			if(l > r || l > end || r < start) {
	                				return 0;
	                			}
	                			if(l >= start && r <= end) {
	                				return segTree[pos];
	                			}
	                			int mid = (l + r)/2;
	                			long p1 = query(l, mid, start, end, 2*pos+1);
	                			long p2 = query(mid+1, r, start, end, 2*pos+2);
	                			return p1 + p2;
	                		}
	                	}
	                    int count = 0;
	                   
	                    boolean[] visited;
	                    int[] parent;
	                    void dfs(int a) {
	                    	boolean leaf=  false;
	                    	visited[a] = true;
	                    	Iterator<Integer> it = adj[a].iterator();
	                    	while(it.hasNext()) {
	                    		int x = it.next();
	                    		if(!visited[x]) {
	                    			parent[x] = a;
	                    			leaf = true;
	                    			dfs(x);
	                    		}
	                    	}
	                    	if(!leaf) {
	                    		count++;
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
	                            return this.a == p.a && this.b == this.b;
	                        }
	                        public int hashCode(){
	                            return Long.hashCode(a)*27 + Long.hashCode(b)*31;
	                        }
	                        public int compareTo(Pair p) {
	                            return Long.compare(this.a,p.a);
	                        }
	                    }
	                    
	                }