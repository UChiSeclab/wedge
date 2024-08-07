import java.util.*;
import java.lang.*;
import java.io.*;  
public class Main 
{
/*
    HashMap<> map=new HashMap<>();
    TreeMap<> map=new TreeMap<>();
    map.put(p,map.getOrDefault(p,0)+1);
    for(Map.Entry<> mx:map.entrySet()){
          int v=mx.getValue(),k=mx.getKey();
    }for (int i = 1; i <= 1000; i++)
    fac[i] = fac[i - 1] * i % mod;
    ArrayList<Pair<Character,Integer>> l=new ArrayList<>();
    ArrayList<> l=new ArrayList<>();
    HashSet<> has=new HashSet<>();
    PriorityQueue<Integer> pq=new PriorityQueue<>(new Comparator<Integer>(){
                public int compare(Integer a,Integer b){
                      return Integer.compare(b,a);
                }
                });
    Arrays.sort(ar,new Comparator<int[]>() {
    public int compare(int[] a, int[] b) {
        return Double.compare(a[0], b[0]);
    }
    });*/ 
    PrintWriter out;
    FastReader sc;
    long mod=(long)(1e9+7);
    long maxlong=Long.MAX_VALUE;
    long minlong=Long.MIN_VALUE;
    ArrayList<Integer> even,odd;
    ArrayList<Integer>[] l;
    int[] dp;
    void mbfs(ArrayList<Integer> a,ArrayList<Integer> b,int n){
          Queue<Integer> q=new LinkedList<>();
          int[] ar=new int[n+1];
          int inf=Integer.MAX_VALUE;
          Arrays.fill(ar,inf);
          for(int i:a){
                q.add(i);
                ar[i]=0;
          }
          while(!q.isEmpty()){
                int v=q.poll();
                for(int i:l[v]){
                      if(ar[i]==inf){
                          ar[i]=ar[v]+1;  
                          q.add(i);
                      }
                }
          }for(int i:b){
                if(ar[i]!=inf)dp[i]=ar[i];
          }
    }
    /******************************************************************************************
     *****************************************************************************************/
    public void sol(){
          int n=ni();
          int[] ar=new int[n+1];
          for(int i=1;i<=n;i++){
                ar[i]=ni();
          }l=new ArrayList[n+1];
          for(int i=1;i<=n;i++)l[i]=new ArrayList<>();
          for(int i=1;i<=n;i++){
                if(i-ar[i]>=1){
                      l[i-ar[i]].add(i);
                }if(i+ar[i]<=n){
                      l[i+ar[i]].add(i);
                }
          }
          even=new ArrayList<>();
          odd=new ArrayList<>();
          for(int i=1;i<=n;i++){
                if(ar[i]%2==0){
                      even.add(i);
                }else odd.add(i);
          }
      //     pl(odd);
      //     pl(even);
          dp=new int[n+1];
          Arrays.fill(dp,-1);
          mbfs(even,odd,n);
      //     for(int i:dp)pl(i);
          mbfs(odd,even,n);
          StringBuilder sb=new StringBuilder();
          for(int i=1;i<=n;i++)sb.append(dp[i]+" ");
          pl(sb);
    }
    public static void main(String[] args) 
	{ 
	      Main g=new Main();
		g.out=new PrintWriter(System.out);
		g.sc=new FastReader();
		int t=1;
// 		t=g.ni();
		while(t-->0)
		g.sol();
		g.out.flush();
	}
	
	/****************************************************************************************
     *****************************************************************************************/
	static class FastReader 
	{ 
		BufferedReader br; 
		StringTokenizer st; 
 
		public FastReader() 
		{ 
			br = new BufferedReader(new
					InputStreamReader(System.in)); 
		} 
 
		String next() 
		{ 
			while (st == null || !st.hasMoreElements()) 
			{ 
				try
				{ 
					st = new StringTokenizer(br.readLine()); 
				} 
				catch (IOException e) 
				{ 
					e.printStackTrace(); 
				} 
			} 
			return st.nextToken(); 
		} 
 
		int nextInt() 
		{ 
			return Integer.parseInt(next()); 
		} 
 
		long nextLong() 
		{ 
			return Long.parseLong(next()); 
		} 
 
		double nextDouble() 
		{ 
			return Double.parseDouble(next()); 
		} 
 
		String nextLine() 
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
	} public int ni(){
	      return sc.nextInt();
	}public long nl(){
	      return sc.nextLong();
	}public double nd(){
	      return sc.nextDouble();
	}public char[] rl(){
	      return sc.nextLine().toCharArray();
	}public String rl1(){
	      return sc.nextLine();
	}
	public void pl(Object s){
	      out.println(s);
	}public void ex(){
	      out.println();
	}
	public void pr(Object s){
	      out.print(s);
	}public String next(){
	      return sc.next();
	}public long abs(long x){
	      return Math.abs(x);
	}
	public int abs(int x){
	      return Math.abs(x);
	}
	public double abs(double x){
	      return Math.abs(x);
	}
      public long pow(long x,long y){
            return (long)Math.pow(x,y);
      }
      public int pow(int x,int y){
            return (int)Math.pow(x,y);
      }
      public double pow(double x,double y){
            return Math.pow(x,y);
      }public long min(long x,long y){
            return (long)Math.min(x,y);
      }
      public int min(int x,int y){
            return (int)Math.min(x,y);
      }
      public double min(double x,double y){
            return Math.min(x,y);
      }public long gcd(long a, long b) {
		if (a == 0)
			return b;
		return gcd(b % a, a);
	}public long lcm(long a, long b) {
            return (a / gcd(a, b)) * b;
      }
       void sort1(int[] a) {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : a) {
			l.add(i);
		}
		Collections.sort(l,Collections.reverseOrder());
		for (int i = 0; i < a.length; i++) {
			a[i] = l.get(i);
		}
	}
	 void sort(int[] a) {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : a) {
			l.add(i);
		}
		Collections.sort(l);
		for (int i = 0; i < a.length; i++) {
			a[i] = l.get(i);
		}
	}void sort(long[] a) {
		ArrayList<Long> l = new ArrayList<>();
		for (long i : a) {
			l.add(i);
		}
		Collections.sort(l);
		for (int i = 0; i < a.length; i++) {
			a[i] = l.get(i);
		}
	}void sort1(long[] a) {
		ArrayList<Long> l = new ArrayList<>();
		for (long i : a) {
			l.add(i);
		}
		Collections.sort(l,Collections.reverseOrder());
		for (int i = 0; i < a.length; i++) {
			a[i] = l.get(i);
		}
	}
	void sort(double[] a) {
		ArrayList<Double> l = new ArrayList<>();
		for (double i : a) {
			l.add(i);
		}
		Collections.sort(l);
		for (int i = 0; i < a.length; i++) {
			a[i] = l.get(i);
		}
	}int  swap(int a,int b){
	      return a;
	}long  swap(long a,long b){
	      return a;
	}double  swap(double a,double b){
	      return a;
	}
	boolean isPowerOfTwo (int x) 
      { 
            return x!=0 && ((x&(x-1)) == 0);     
      }boolean isPowerOfTwo (long x) 
      { 
            return x!=0 && ((x&(x-1)) == 0);     
      }public long max(long x,long y){
            return (long)Math.max(x,y);
      }
      public int max(int x,int y){
            return (int)Math.max(x,y);
      }
      public double max(double x,double y){
            return Math.max(x,y);
      }long sqrt(long x){
            return (long)Math.sqrt(x);
      }int sqrt(int x){
            return (int)Math.sqrt(x);
      }void input(int[] ar,int n){
            for(int i=0;i<n;i++)ar[i]=ni();
      }void input(long[] ar,int n){
            for(int i=0;i<n;i++)ar[i]=nl();
      }void fill(int[] ar,int k){
            Arrays.fill(ar,k);
      }void yes(){
            pl("YES");
      }void no(){
            pl("NO");
      }
    int[] sieve(int n) 
    { 
        boolean prime[] = new boolean[n+1]; 
        int[] k=new int[n+1];
        for(int i=0;i<=n;i++) {
            prime[i] = true; 
            k[i]=i;
        }
          
        for(int p = 2; p <=n; p++) 
        { 
            if(prime[p] == true) 
            { 
               // sieve[p]=p;
                for(int i = p*2; i <= n; i += p) {
                    prime[i] = false; 
                  //  sieve[i]=p;
                    while(k[i]%(p*p)==0){
                          k[i]/=(p*p);
                    }
                }
            } 
        }return k;
    }
    int strSmall(int[] arr, int target)  
    {  
        int start = 0, end = arr.length-1;  
        int ans = -1;  
        while (start <= end) {  
            int mid = (start + end) / 2;   
            if (arr[mid] >= target) {  
                end = mid - 1;  
            }   
            else {  
                ans = mid;  
                start = mid + 1;  
            }  
        }  
        return ans;  
    } int strSmall(ArrayList<Integer> arr, int target)  
    {  
        int start = 0, end = arr.size()-1;  
        int ans = -1;  
        while (start <= end) {  
            int mid = (start + end) / 2;   
            if (arr.get(mid) > target) {  
                start = mid + 1; 
                ans=start;
            }   
            else {
                end = mid - 1;  
            }  
        }  
        return ans;  
    }long mMultiplication(long a,long b) 
    { 
        long res = 0;    
        a %= mod; 
  
        while (b > 0)  
        {  
            if ((b & 1) > 0)  
            { 
                res = (res + a) % mod; 
            }  
            a = (2 * a) % mod; 
  
            b >>= 1;  
        } 
        return res; 
    }long nCr(int n, int r,
                             long p)
    {
 
          if (n<r) 
              return 0;
        if (r == 0)
            return 1;
            long[] fac = new long[n + 1];
        fac[0] = 1;
 
        for (int i = 1; i <= n; i++)
            fac[i] = fac[i - 1] * i % p;
        return (fac[n] * modInverse(fac[r], p)
                % p * modInverse(fac[n - r], p)
                % p)
            % p;
    }long power(long x, long y, long p)
    {
        long res = 1;
        x = x % p;
 
        while (y > 0) {
            if (y % 2 == 1)
                res = (res * x) % p;
            y = y >> 1; 
            x = (x * x) % p;
        }
 
        return res;
    }long modInverse(long n, long p)
    {
        return power(n, p - 2, p);
    }
      public static class Pair implements Comparable<Pair> {
		int x;
		int y;
 
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
 
		public String toString() {
			return x + "," + y;
		}
 
		public boolean equals(Object o) {
			if (o instanceof Pair) {
				Pair p = (Pair) o;
				return p.x == x && p.y == y;
			}
			return false;
		}
 
		public int hashCode() {
			return new Double(x).hashCode() * 31 + new Double(y).hashCode();
		}
 
		public int compareTo(Pair other) {
			if (this.x == other.x) {
				return Long.compare(this.y, other.y);
			}
			return Long.compare(this.x, other.x);
		}
      }
	 
} 