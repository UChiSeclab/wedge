//package graphs;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class PW {
	
	private static BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
	public static int arr[];
	public static int memo[][];
	public static FastReader s = new FastReader();
	public static void main(String[] args)  throws IOException{
		// TODO Auto-generated method stub
	 
		FastScanner sc=new FastScanner();
		int t=sc.nextInt();
		//int k=1;
		
		while(t-- >0)
		{
			
			int n=sc.nextInt();
			String str=sc.next();
			
			HashMap<Double,Integer> map=new HashMap<>();
			int c1=0;
			
			int c2 = 0;

			  for (int i = 0; i < n; i++)
			  {
			    if (str.charAt(i)=='D')
			    {
			      c1++;
			    } else c2++;
			    int l =gcd(c1, c2);
			    int cd = c1 / l;
			    int ck = c2 / l;
			   
			    double temp=Integer.MAX_VALUE;
			    if(ck!=0)
			    	temp=((1.0)*(cd))/ck;
			    if(map.containsKey(temp))
			    	map.put(temp, map.get(temp)+1);
			    else
			    	map.put(temp, 1);
			    int ans = map.get(temp);
			    output.append(ans + " ");
		        }
		        output.append("\n");
		        output.flush();
			  
			
		}
		
	
	}
	
	public static void solve(String str,HashSet<String> set)
	{
		if(str.length()>4)
			return ;
		
		for(int i=0;i<26;i++)
		{
			solve(str+ (char)('a'+i),set);
		}
		
		if(str.length()>0)
			set.add(str);
		
	}
	
	public static long consecutiveNumbersSum(long N) {
		  if(N==1)return 1;
		  long cnt = 0;
		  // int D =
		  for(long  i = 1 ; (i*(i+1)/2) <=N ; i++){
		    long rem = N - ((i)*(i-1))/2;
		    if(rem >0 && rem%i==0){
		      cnt++;
		    }
		  }
		  return cnt;
		}
	
	
	public static  void solve()
	{
		
		 
	    
	}
	
	public static boolean helper(int arr[], int i, int sum){
	     if(sum == 0){
	          return true;
	     }
	     if(i>=arr.length || sum < 0){
	          return false;
	     }
	     if(memo[i][sum] != -1){
	          return memo[i][sum]==0?false:true;
	     }
	     boolean ans1 = helper(arr,i+1,sum-arr[i]);
	     boolean ans2 = helper(arr,i+1,sum);
	     boolean fin=ans1||ans2;
	     if(fin==true)
	    	 memo[i][sum]=1;
	     else
	    	 memo[i][sum]=0;
	     return fin ;
	}
	
//	public static long fastExpo(long a, long n, long mod) {
//		  long result = 1;
//		  while (n > 0) {
//		    if ((n & 1)>0)
//		      result = (result * a) % mod;
//		    a = (a * a) % mod;
//		    n >>= 1;
//		  }
//		  return result;
//		}
//	1  4   8
	
	public static int digit(int n)
	{
		int c=0;
		while(n!=0)
		{
			n/=10;
			c++;
		}
		return c;
	}
	 
	public static int rev(String n)
	{
		String ans="";
		for(int i=n.length()-1;i>=0;i--)
			ans=ans+n.charAt(i);
		
		return Integer.valueOf(ans);
	}
	 
	public static boolean solve(HashMap<Integer,Integer> mp1,HashMap<Integer,Integer> mp2)
	{
		for(int i:mp2.keySet())
		{
			if(!mp1.containsKey(i))
				return false;
			
			if(mp1.get(i)<mp2.get(i))
				return false;
		}
		
		return true;
		
	}
	
	
	public static int count(int b[]){
		  int s = 0;
		  for (int i = 0; i < 32; i++) {
		    if(b[i]>0){
		      s |= (1<<i);
		    }
		  }
		  return s;
		}
	
	public static void remove(int b[], int val){
		  for (int i = 0; i < 32; i++) {
		    if(((val>>i)&1)==1)
		      b[i]--;
		  }
		}
	
	public static void add(int b[], int val){
		  for (int i = 0; i < 32; i++) {
		    if(((val>>i)&1)==1)
		      b[i]++;
		  }
		}
	    public static String largestNumber( List<String> ab) {
	        
	       // List<String> ab= new ArrayList<>();
	       // for(int i=0;i<A.size();i++)
	       // {
	       //     ab.add(String.valueOf(A.get(i)));
	       // }
	        
	        Collections.sort(ab, new Comparator<String>(){ 
	            
	            public int compare(String X, String Y) { 
	          
	        // first append Y at the end of X 
	        String XY=X + Y; 
	          
	        // then append X at the end of Y 
	        String YX=Y + X; 
	          
	        // Now see which of the two formed numbers  
	        // is greater 
	        return XY.compareTo(YX)>0?-1:1; 
	            
	                
	            }
	            
	            
	            
	        });
	        StringBuilder abc= new StringBuilder();
	        
	        
	        for(int i=0;i<ab.size();i++)
	        {
	           abc.append(ab.get(i));
	            
	        }
	        if(abc.length()==0)
	            return abc.toString();
	        
	        if(abc.charAt(0)=='0')
	        return "0";
	        else
	        return abc.toString();
	        
	    }
	
	public static boolean pal(String s)
	{
		int i=0;
		int j=s.length()-1;
		while(i<=j)
		{
			if(s.charAt(i)!=s.charAt(j))
				return false;
			i++;
			j--;
		}
		return true;
	}
	
	
	
	
	static class FastReader {
        BufferedReader br;
        StringTokenizer st;
 
        public FastReader()
        {
            br = new BufferedReader(
                new InputStreamReader(System.in));
        }
 
        String next()
        {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
 
        int nextInt() { return Integer.parseInt(next()); }
 
        long nextLong() { return Long.parseLong(next()); }
 
        double nextDouble()
        {
            return Double.parseDouble(next());
        }
 
        String nextLine()
        {
            String str = "";
            try {
                str = br.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

	
	public static long solve(int n, int r)
    {


        long p = 1, k = 1;
        if (n - r < r) {
            r = n - r;
        }
 
        if (r != 0) {
            while (r > 0) {
                p *= n;
                k *= r;
 
                long m = __gcd(p, k);
 
                
                p /= m;
                k /= m;
 
                n--;
                r--;
            }
 
           
        }
        else {
            p = 1;
        }
 
        
        //System.out.println(p);
        return p;
    }
	
	
	public static int gcd(int a,int b)
	{
		if(a==0||b==0)
			return a+b;
		return gcd(b,(a%b));
	}
	
	public static long __gcd(long n1, long n2)
    {
        long gcd = 1;
 
        for (int i = 1; i <= n1 && i <= n2; ++i) {
            // Checks if i is factor of both integers
            if (n1 % i == 0 && n2 % i == 0) {
                gcd = i;
            }
        }
        return gcd;
    }
	
	public static boolean prime(int n)
	{
		if(n<=2)
			return true;
		
		for(int i=2;i<=Math.sqrt(n);i++)
		{
			if(n%i==0)
				return false;
		}
		
		return true;
		
	}
	public static long fastExpo(long a,long n,long mod){
        if (n == 0)
            return 1;
        else{
            long x = fastExpo(a,n/2,mod);
            if ((n&1) == 1){
                return (((a*x)%mod)*x)%mod;
            }
            else{
                return (((x%mod)*(x%mod))%mod)%mod;
            }
        }
    }
}
class pair{
	//public:
		int f;
		long s;
		pair(int x,long y)
		{
			f=x;
			s=y;
		}
        
}
 class FastScanner {
    BufferedReader br;
    StringTokenizer st;

    public FastScanner()
    {
        br = new BufferedReader(
                new InputStreamReader(System.in));
    }

    String next()
    {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() { return Integer.parseInt(next()); }

    long nextLong() { return Long.parseLong(next()); }

    double nextDouble()
    {
        return Double.parseDouble(next());
    }

    String nextLine()
    {
        String str = "";
        try {
            str = br.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}


