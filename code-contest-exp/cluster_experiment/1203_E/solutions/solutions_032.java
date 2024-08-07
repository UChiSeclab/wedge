import java.util.*;
import java.lang.*;
import java.io.*;
import java.awt.*;

// U KNOW THAT IF THIS DAY WILL BE URS THEN NO ONE CAN DEFEAT U HERE................
//JUst keep faith in ur strengths .................................................. 


// ASCII = 48 + i ;// 2^28 = 268,435,456  > 2* 10^8 // log 10 base 2 = 3.3219 
// odd:: (x^2+1)/2 , (x^2-1)/2 ; x>=3// even:: (x^2/4)+1 ,(x^2/4)-1  x >=4 
// FOR ANY ODD NO N : N,N-1,N-2
//ALL ARE PAIRWISE COPRIME 
//THEIR COMBINED LCM IS PRODUCT OF ALL THESE NOS

// two consecutive odds are always coprime to each other
// two consecutive even have always gcd  = 2 ;

// Rectangle r = new Rectangle(int x , int y,int widht,int height) 
//Creates a rect. with bottom left cordinates as (x, y) and top right as ((x+width),(y+height))

//BY DEFAULT Priority Queue is MIN in nature in java
//to use as max , just push with negative sign and change sign after removal 

// We can make a sieve of max size 1e7 .(no time or space issue) 
// In 1e7 starting nos we have about 66*1e4 prime nos
// In 1e6 starting nos we have about 78,498 prime nos

 public class Main
{
     
    // static int[] arr = new int[100002] ; 
    // static int[] dp = new int[100002] ;  
    
     static PrintWriter out;
    
	static class FastReader{
		BufferedReader br;
		StringTokenizer st;
		public FastReader(){
			br=new BufferedReader(new InputStreamReader(System.in));
			out=new PrintWriter(System.out);
		}
		String next(){
			while(st==null || !st.hasMoreElements()){
				try{
					st= new StringTokenizer(br.readLine());
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}
		int nextInt(){
			return Integer.parseInt(next());
		}
		long nextLong(){
			return Long.parseLong(next());
		}
		double nextDouble(){
			return Double.parseDouble(next());
		}
		String nextLine(){
			String str = "";
			try{
				str=br.readLine();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			return str;
		}
	}
	


////////////////////////////////////////////////////////////////////////////////////
 public static int countDigit(long n) 
    { 
        return (int)Math.floor(Math.log10(n) + 1); 
    } 

///////////////////////////////////////////////////////////////////////////////////////// 
 
 public static int sumOfDigits(long n)
 {
  
  if( n< 0)return -1 ;
  
  int sum = 0;
  
  while( n > 0)
  {
      sum = sum + (int)( n %10) ;
      
      n /= 10 ;
  }
     
  return sum ;  
 
 
 
 }
 
 //////////////////////////////////////////////////////////////////////////////////////////////////

public static long arraySum(int[] arr , int start , int end)
{
    long ans = 0 ;
    
    for(int i = start ; i <= end  ; i++)ans += arr[i] ;
    
    return ans  ;
}

/////////////////////////////////////////////////////////////////////////////////



////////////////////////////////////////////////////////////////////////////////

public static void swapArray(int[] arr , int start , int end)
{
    while(start < end)
    {
        int temp = arr[start] ;
        arr[start] = arr[end];
        arr[end] = temp;
        start++ ;end-- ;
    }
}


//////////////////////////////////////////////////////////////////////////////////

static long factorial(long a)
{
    if(a== 0L || a==1L)return 1L ;
    
    return a*factorial(a-1L) ;
}

///////////////////////////////////////////////////////////////////////////////


public static int[][] rotate(int[][] input){

int n =input.length;
int m = input[0].length ;
int[][] output = new int [m][n];

for (int i=0; i<n; i++)
	for (int j=0;j<m; j++)
		output [j][n-1-i] = input[i][j];
return output;
}
///////////////////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////// ////////////////////////////////////////////////   

public static boolean isPowerOfTwo(long n) 
{ 
    if(n==0) 
    return false; 
  
if(((n ) & (n-1)) == 0 ) return true ;
else return false  ;

} 

/////////////////////////////////////////////////////////////////////////////////////

 
/////////////////////////////////////////////////////////////////////////////////// 

public static String reverse(String input)
{
  StringBuilder str  = new StringBuilder("") ;
   
    for(int i =input.length()-1 ; i >= 0  ; i-- )
    {
        str.append(input.charAt(i));
    }
    
return str.toString() ;
}
///////////////////////////////////////////////////////////////////////////////////////////



////////////////////////////////////////////////////////////////////////////////////////////////////
public static boolean isPossibleTriangle(int a  ,int b , int c)
{
      if( a + b > c && c+b > a && a +c > b)return true  ;
      else return false  ;
}


////////////////////////////////////////////////////////////////////////////////////////////
static long xnor(long num1, long num2) {
		if (num1 < num2) {
			long temp = num1;
			num1 = num2;
			num2 = temp;
		}
		num1 = togglebit(num1);
		return num1 ^ num2;
	}

	static long togglebit(long n) {
		if (n == 0)
			return 1;
		long i = n;
		n |= n >> 1;
		n |= n >> 2;
		n |= n >> 4;
		n |= n >> 8;
		n |= n >> 16;
		return i ^ n;
	}

///////////////////////////////////////////////////////////////////////////////////////////////

public static int xorOfFirstN(int n)
{
 
 
 if( n % 4 ==0)return n ;
 
 else if( n % 4 == 1)return 1 ;
 
 else if( n % 4 == 2)return n+1 ;
 
 else return 0 ;
 
    
}
//////////////////////////////////////////////////////////////////////////////////////////////

public static int gcd(int a, int b )
{

if(b==0)return a ;

else return gcd(b,a%b) ; 

}


public static long gcd(long a, long b )
{

if(b==0)return a ;

else return gcd(b,a%b) ; 

}

////////////////////////////////////////////////////////////////////////////////////

public static int lcm(int a, int b ,int c , int d )
{

int temp = lcm(a,b , c) ;


 
 int ans = lcm(temp ,d ) ;

return ans  ;


}

///////////////////////////////////////////////////////////////////////////////////////////

public static int lcm(int a, int b ,int c )
{

int temp = lcm(a,b) ;

int ans =  lcm(temp ,c) ;

return ans  ;


}

////////////////////////////////////////////////////////////////////////////////////////
    
public static int lcm(int a , int b )
{

int gc = gcd(a,b);

return (a/gc)*b ;
}


public static long lcm(long a , long b )
{

long gc = gcd(a,b);

return (a/gc)*b;
}


///////////////////////////////////////////////////////////////////////////////////////////
static boolean isPrime(long n)
{
      if(n==1)
      {
            return false  ;
      }
      
      boolean ans =  true  ;
      
      for(long i = 2L; i*i <= n ;i++)
      {
            if(n% i ==0)
            {
                  ans = false  ;break ;
            }
      }
      
      
      return ans  ;
} 

static boolean isPrime(int n)
{
      if(n==1)
      {
            return false  ;
      }
      
      boolean ans =  true  ;
      
      for(int i = 2; i*i <= n ;i++)
      {
            if(n% i ==0)
            {
                  ans = false  ;break ;
            }
      }
      
      
      return ans  ;
}      


///////////////////////////////////////////////////////////////////////////

static int sieve =  1000000 ;

 
static boolean[] prime =  new boolean[sieve + 1] ;

public static void sieveOfEratosthenes() 
    { 
        // FALSE == prime
        
        // TRUE ==  COMPOSITE
        
        // FALSE== 1
       
        
        // time complexity = 0(NlogLogN)== o(N)
        
        // gives prime nos bw 1 to N
        
        for(int i = 4; i<= sieve ; i++)
        {
            prime[i] = true  ;
            i++ ;
        }
        
        for(int p = 3; p*p <= sieve; p++) 
        { 
           
            if(prime[p] == false) 
            { 
                
                for(int i = p*p; i <= sieve; i += p) 
                    prime[i] = true; 
            } 
            
            p++ ;
        } 
          
       
       
    
    } 
 
///////////////////////////////////////////////////////////////////////////////////


public static void sortD(int[] arr , int s  , int e)
{
      sort(arr ,s , e) ;
      
      int i =s ; int j = e  ;
      
      while( i < j)
      {
            int temp = arr[i] ;
            arr[i] =arr[j] ;
            arr[j] = temp ;
            i++ ; j-- ;
      }
      
      
      
      return ;
}


/////////////////////////////////////////////////////////////////////////////////////////

public static long countSubarraysSumToK(long[] arr ,long sum )
    {
      HashMap<Long,Long> map = new HashMap<>() ;
       
      int n = arr.length ;
       
      long prefixsum = 0 ;
       
      long count = 0L ;
      for(int i  = 0; i < n ; i++)
      {
          prefixsum  = prefixsum +  arr[i] ;
           
          if(sum == prefixsum)count = count+1 ;
           
          if(map.containsKey(prefixsum -sum))
          {
              count = count + map.get(prefixsum -sum) ;
          }
          
          
          if(map.containsKey(prefixsum ))
          {
              map.put(prefixsum , map.get(prefixsum) +1 );
          }
          
          else{
              map.put(prefixsum , 1L );
          }
           
           
      }
        
        
        
      return count  ;  
        
    }

///////////////////////////////////////////////////////////////////////////////////////////////


// KMP ALGORITHM : TIME COMPL:O(N+M) 
// FINDS THE OCCURENCES OF PATTERN AS A  SUBSTRING IN STRING
//RETURN THE ARRAYLIST OF INDEXES 
// IF SIZE OF LIST IS ZERO MEANS PATTERN IS NOT PRESENT IN STRING


public static ArrayList<Integer> kmpAlgorithm(String str , String pat)
     {
        ArrayList<Integer> list =new ArrayList<>();
        
        int n = str.length() ;
        int m = pat.length() ;
        
        String q = pat + "#" + str ;
        
        int[] lps  =new int[n+m+1] ;
        
         longestPefixSuffix(lps, q,(n+m+1)) ;
         
         
         for(int i =m+1 ; i < (n+m+1) ; i++ )
         {
             if(lps[i] == m)
             {
                 list.add(i-2*m) ;
             }
         }
        
        return list ; 
         
        
    }
    

public static void longestPefixSuffix(int[] lps ,String str , int n)
    {
        lps[0] =  0 ;
        
        for(int i = 1  ; i<= n-1; i++)
        {
          int l = lps[i-1] ;
           
          while( l > 0 && str.charAt(i) != str.charAt(l))
          {
              l = lps[l-1] ;
          }
           
          if(str.charAt(i) == str.charAt(l))
          {
              l++ ;
          }
            
           
          lps[i] = l ; 
        }
        
    }
    
     

///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

 // CALCULATE TOTIENT Fn FOR ALL VALUES FROM 1 TO n
    // TOTIENT(N) = count of nos less than n and grater than 1 whose gcd with n is 1 
    // or n and the no  will be coprime in nature
    //time : O(n*(log(logn)))
    
    public static void eulerTotientFunction(int[] arr ,int n )
    {
      
      for(int i = 1; i <= n  ;i++)arr[i] =i  ;
      
      
      for(int i= 2 ; i<= n ;i++)
      {
          if(arr[i] == i)
          {
              arr[i] =i-1 ;
              
              for(int j =2*i ; j<= n  ; j+= i )
              {
                  arr[j] = (arr[j]*(i-1))/i ;
              }
              
          }
      }
        
      return  ;  
        
    }
	
/////////////////////////////////////////////////////////////////////////////////////////////
public static long nCr(int n,int k)
{
    long ans=1L;
    k=k>n-k?n-k:k;
    int j=1;
    for(;j<=k;j++,n--)
    {
        if(n%j==0)
        {
            ans*=n/j;
        }else
        if(ans%j==0)
        {
            ans=ans/j*n;
        }else
        {
            ans=(ans*n)/j;
        }
    }
    return ans;
}

///////////////////////////////////////////////////////////////////////////////////////////

public static ArrayList<Integer> allFactors(int n)
{   
      ArrayList<Integer> list = new ArrayList<>() ;
      
    for(int i = 1; i*i <= n ;i++)
    {
          if( n % i == 0)
          {
              if(i*i == n)
              {
                    list.add(i) ;
              }
              else{
                    list.add(i) ;
                    list.add(n/i) ;
                    
              }
          }
    }
      
     return list ; 
      
      
}


public static ArrayList<Long> allFactors(long n)
{   
      ArrayList<Long> list = new ArrayList<>() ;
      
    for(long i = 1L; i*i <= n ;i++)
    {
          if( n % i == 0)
          {
              if(i*i == n)
              {
                    list.add(i) ;
              }
              else{
                    list.add(i) ;
                    list.add(n/i) ;
                    
              }
          }
    }
      
     return list ; 
      
      
}
////////////////////////////////////////////////////////////////////////////////////////////////////

  static final int MAXN = 1000001; 
       
    
    static int spf[] = new int[MAXN]; 
   
    static void sieve() 
    { 
        spf[1] = 1; 
        for (int i=2; i<MAXN; i++) 
       
           
            spf[i] = i; 
       
       
        for (int i=4; i<MAXN; i+=2) 
            spf[i] = 2; 
       
        for (int i=3; i*i<MAXN; i++) 
        { 
           
            if (spf[i] == i) 
            { 
               
                for (int j=i*i; j<MAXN; j+=i) 
       
                    if (spf[j]==j) 
                        spf[j] = i; 
            } 
        } 
    } 
       

    static ArrayList<Integer> getPrimeFactorization(int x) 
    { 
        ArrayList<Integer> ret = new ArrayList<Integer>(); 
        while (x != 1) 
        { 
            ret.add(spf[x]); 
            x = x / spf[x]; 
        } 
        return ret; 
    } 
       
 //////////////////////////////////////////////////////////////////////////////////////////////////
 //////////////////////////////////////////////////////////////////////////////////////////////////
   
public static void merge(int arr[], int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
 
        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];
 
       //Copy data to temp arrays
        for (int i=0; i<n1; ++i)
            L[i] = arr[l + i];
        for (int j=0; j<n2; ++j)
            R[j] = arr[m + 1+ j];
 
 
        /* Merge the temp arrays */
 
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2)
        {
            if (L[i] <= R[j])
            {
                arr[k] = L[i];
                i++;
            }
            else
            {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
 
        /* Copy remaining elements of L[] if any */
        while (i < n1)
        {
            arr[k] = L[i];
            i++;
            k++;
        }
 
        /* Copy remaining elements of R[] if any */
        while (j < n2)
        {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
 
    // Main function that sorts arr[l..r] using
    // merge()
  public static void sort(int arr[], int l, int r)
    {
        if (l < r)
        {
            // Find the middle point
            int m = (l+r)/2;
 
            // Sort first and second halves
            sort(arr, l, m);
            sort(arr , m+1, r);
 
            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

public static void sort(long arr[], int l, int r)
    {
        if (l < r)
        {
            // Find the middle point
            int m = (l+r)/2;
 
            // Sort first and second halves
            sort(arr, l, m);
            sort(arr , m+1, r);
 
            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }


public static void merge(long arr[], int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
 
        /* Create temp arrays */
        long L[] = new long[n1];
        long R[] = new long[n2];
 
        //Copy data to temp arrays
        for (int i=0; i<n1; ++i)
            L[i] = arr[l + i];
        for (int j=0; j<n2; ++j)
            R[j] = arr[m + 1+ j];
 
 
        /* Merge the temp arrays */
 
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2)
        {
            if (L[i] <= R[j])
            {
                arr[k] = L[i];
                i++;
            }
            else
            {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
 
        /* Copy remaining elements of L[] if any */
        while (i < n1)
        {
            arr[k] = L[i];
            i++;
            k++;
        }
 
        /* Copy remaining elements of R[] if any */
        while (j < n2)
        {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
 

 /////////////////////////////////////////////////////////////////////////////////////////
 
	public static long knapsack(int[] weight,long value[],int maxWeight){

        
        int n=  value.length ;
        
	
	//dp[i] stores the profit with KnapSack capacity "i" 
  long []dp = new long[maxWeight+1]; 
      
    //initially profit with 0 to W KnapSack capacity is 0 
    Arrays.fill(dp, 0); 
  
    // iterate through all items 
    for(int i=0; i < n; i++)  
      
        //traverse dp array from right to left 
        for(int j = maxWeight; j >= weight[i]; j--) 
            dp[j] = Math.max(dp[j] , value[i] + dp[j - weight[i]]); 
              
    /*above line finds out maximum of dp[j](excluding ith element value) 
    and val[i] + dp[j-wt[i]] (including ith element value and the 
    profit with "KnapSack capacity - ith element weight") */
    return dp[maxWeight]; 
	}

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////


// to return max sum of any subarray in given array
public static long kadanesAlgorithm(long[] arr)
{
    
    if(arr.length == 0)return  0 ;
    
      long[] dp = new long[arr.length] ;
      
      dp[0] = arr[0] ;
      long max =  dp[0] ;
      
      
      for(int i = 1; i <  arr.length ; i++)
      {
            if(dp[i-1] > 0)
            {
                  dp[i] = dp[i-1] + arr[i] ;
            }
            else{
                  dp[i] = arr[i] ;
            }
            
            if(dp[i] >  max)max = dp[i] ;
            
      }
      
      return max  ;
      
}
/////////////////////////////////////////////////////////////////////////////////////////////
public static long kadanesAlgorithm(int[] arr)
{
     if(arr.length == 0)return  0 ;
    
      long[] dp = new long[arr.length] ;
      
      dp[0] = arr[0] ;
      long max =  dp[0] ;
      
      
      for(int i = 1; i <  arr.length ; i++)
      {
            if(dp[i-1] > 0)
            {
                  dp[i] = dp[i-1] + arr[i] ;
            }
            else{
                  dp[i] = arr[i] ;
            }
            
            if(dp[i] >  max)max = dp[i] ;
            
      }
      
      return max  ;
      
}

      
///////////////////////////////////////////////////////////////////////////////////////



/////////////////////////////////////////////////////////////////////////////////////////



//////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

//TO GENERATE ALL(DUPLICATE ALSO EXIST) PERMUTATIONS OF A STRING


// JUST CALL generatePermutation( str,  start,  end) start :inclusive ,end : exclusive 

//Function for swapping the characters at position I with character at position j  
    public static String swapString(String a, int i, int j) {  
        char[] b =a.toCharArray();  
        char ch;  
        ch = b[i];  
        b[i] = b[j];  
        b[j] = ch;  
        return String.valueOf(b);  
    }  
      
//Function for generating different permutations of the string  
    public static void generatePermutation(String str, int start, int end)  
    {  
        //Prints the permutations  
        if (start == end-1)  
            System.out.println(str);  
        else  
        {  
            for (int i = start; i < end; i++)  
            {  
                //Swapping the string by fixing a character  
                str = swapString(str,start,i);  
                //Recursively calling function generatePermutation() for rest of the characters   
                generatePermutation(str,start+1,end);  
                //Backtracking and swapping the characters again.  
                str = swapString(str,start,i);  
            }  
        }  
    }  



////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////

public static long factMod(long n, long mod) {
    if (n <= 1) return 1;
    long ans = 1;
    for (int i = 1; i <= n; i++) {
      ans = (ans * i) % mod;
    }
    return ans;
  }


/////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

public static long power(int a  ,int b)
    {
        //time comp : o(logn) 
        
        long x = (long)(a) ;
         long n = (long)(b) ;
        
        if(n==0)return 1 ;
        if(n==1)return x;
        
        long ans =1L  ;
       
      while(n>0)
      {
          if(n % 2 ==1)
          {
              ans = ans *x ;
          }
           
          n = n/2L ;
           
          x =  x*x ;
           
      }
       
      return ans ;
    }
    
    public static long power(long a  ,long b)
    {
        //time comp : o(logn) 
        
        long x = (a) ;
         long n = (b) ;
        
        if(n==0)return 1L ;
        if(n==1)return x;
        
        long ans =1L  ;
       
      while(n>0)
      {
          if(n % 2 ==1)
          {
              ans = ans *x ;
          }
           
          n = n/2L ;
           
          x =  x*x ;
           
      }
       
      return ans ;
    }

    
    
    

////////////////////////////////////////////////////////////////////////////////////////////////////
public static long powerMod(long x, long n, long mod) {
    //time comp : o(logn)
    
    if(n==0)return 1L ;
        if(n==1)return x;
        
    
    long ans = 1;
    while (n > 0) {
      if (n % 2 == 1) ans = (ans * x) % mod;
      x = (x * x) % mod;
      n /= 2;
    }
    return ans;
  }
 
//////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////

/*
lowerBound - finds largest element equal or less than value paased
upperBound - finds smallest element equal or more than value passed

if not present return -1;

*/

public static long lowerBound(long[] arr,long k)
	{
		long ans=-1;
		
		int start=0;
		int end=arr.length-1;
		
		while(start<=end)
		{
			int mid=(start+end)/2;
			
			if(arr[mid]<=k)
			{
				ans=arr[mid];
				start=mid+1;
			}
			else
			{
				end=mid-1;
			}
			
		}
		
		return ans;
		
	}
	
	public static int lowerBound(int[] arr,int k)
	{
		int ans=-1;
		
		int start=0;
		int end=arr.length-1;
		
		while(start<=end)
		{
			int mid=(start+end)/2;
			
			if(arr[mid]<=k)
			{
				ans=arr[mid];
				start=mid+1;
			}
			else
			{
				end=mid-1;
			}
			
		}
		
		return ans;
		
	}
	
	
	public static long upperBound(long[] arr,long k)
	{
		long ans=-1;
		
		int start=0;
		int end=arr.length-1;
		
		while(start<=end)
		{
			int mid=(start+end)/2;
			
			if(arr[mid]>=k)
			{
				ans=arr[mid];
				end=mid-1;
			}
			else
			{
				start=mid+1;
			}
			
		}
		
		return ans;
	}
	
	
	public static int upperBound(int[] arr,int k)
	{
		int ans=-1;
		
		int start=0;
		int end=arr.length-1;
		
		while(start<=end)
		{
			int mid=(start+end)/2;
			
			if(arr[mid]>=k)
			{
				ans=arr[mid];
				end=mid-1;
			}
			else
			{
				start=mid+1;
			}
			
		}
		
		return ans;
	}
	

//////////////////////////////////////////////////////////////////////////////////////////

public static void printArray(int[] arr , int si ,int ei)
{
    for(int i = si  ; i  <= ei ; i++)
    {
        out.print(arr[i] +" ") ;
    }
    
}

public static void printArrayln(int[] arr , int si ,int ei)
{
    for(int i = si  ; i  <= ei ; i++)
    {
        out.print(arr[i] +" ") ;
    }
    out.println() ;
}


public static void printLArray(long[] arr , int si , int ei)
{
    for(int i = si ; i  <= ei ; i++)
    {
        out.print(arr[i] +" ") ;
    }
   
}




public static void printLArrayln(long[] arr , int si , int ei)
{
    for(int i = si ; i  <= ei ; i++)
    {
        out.print(arr[i] +" ") ;
    }
    out.println() ;
   
}

public static void printtwodArray(int[][] ans)
{
    for(int i = 0; i< ans.length ; i++)
    {
        for(int j  = 0 ; j <  ans[0].length ; j++)out.print(ans[i][j] +" ");
        out.println() ;
    }
    out.println() ;
   
}

  
   static long modPow(long a, long x, long p) {
    //calculates a^x mod p in logarithmic time.
    
    a = a % p ;
    
    if(a == 0)return 0L ;
    
    
    long res = 1L;
    while(x > 0) {
        if( x % 2 != 0) {
            res = (res * a) % p;
        }
        a = (a * a) % p;
        x =x/2;
    }
    return res;
}
 
 
 
  static long modInverse(long a, long p) {
    //calculates the modular multiplicative of a mod p.
    //(assuming p is prime).
    return modPow(a, p-2, p);
}
 
 static long[] factorial =  new long[1000001] ;
 
 static void modfac(long mod)
 {
     factorial[0]=1L ; factorial[1]=1L ;
     
     for(int i = 2; i<= 1000000 ;i++)
     {
         factorial[i] = factorial[i-1] *(long)(i) ;
          factorial[i] = factorial[i] % mod ;
     }
    
     
 }
 

 
 
 
static long modBinomial(long n, long r, long p) {
// calculates C(n,r) mod p (assuming p is prime).
 
  if(n < r) return 0L ; 
 
    long num = factorial[(int)(n)] ;
    
    long den = (factorial[(int)(r)]*factorial[(int)(n-r)]) %  p ;
    
    
    long ans  = num*(modInverse(den,p)) ;
    
    ans = ans % p ;
    
    return ans  ;
 
    
}
 
 
 static void update(int val , long[] bit ,int n)
 {
     for( ; val <= n ; val += (val &(-val)) )
     {
         bit[val]++ ;
     }
    
     
 }
 
 
 static long query(int val , long[] bit , int n)
 {
     long ans = 0L; 
     for( ; val >=1 ; val-=(val&(-val))  )ans += bit[val];
     
     return ans ;
 }



static int countSetBits(long n) 
    { 
        int count = 0; 
        while (n > 0) { 
            n = (n) & (n - 1L); 
            count++; 
        } 
        return count; 
    } 


static int abs(int x)
{
    if(x < 0)x = -1*x ;
    
    return x ;
}


static long abs(long x)
{
    if(x < 0)x = -1L*x ;
    
    return x ;

}

////////////////////////////////////////////////////////////////////////////////////////////////
static void p(int val)
{
    out.print(val) ;
}

static void p()
{
    out.print(" ") ;
}

static void pln(int val)
{
    out.println(val) ;
}

static void pln()
{
    out.println() ;
}


static void p(long val)
{
    out.print(val) ;
}



static void pln(long val)
{
    out.println(val) ;
}


static void yes()
{
    out.println("YES") ;
}
static void no()
{
    out.println("NO") ;
}

////////////////////////////////////////////////////////////////////////////////////////////

 // calculate total no of nos greater than or equal to key in  sorted array arr
 
static  int bs(int[] arr, int s ,int e  ,int key)
{
    if( s> e)return 0 ;
    
     int mid = (s+e)/2 ;
        
        if(arr[mid] <key)
        {
            return bs(arr ,mid+1,e , key) ;
            
        }
        
        
        else{
            
             return bs(arr ,s ,mid-1, key)  + e-mid+1;
            
            
        }
}
 
// static ArrayList<Integer>[] adj ;
// static int mod= 1000000007 ;


 



//////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////

public static void solve()
{
FastReader scn = new FastReader() ;

//Scanner scn = new Scanner(System.in);
//int[] store = {2 ,3, 5 , 7  ,11 , 13 , 17 , 19 , 23 , 29 , 31 , 37 } ;

// product of first 11 prime nos is greater than 10 ^ 12;
//sieve() ;
//ArrayList<Integer> arr[] = new ArrayList[n] ;
ArrayList<Integer> list = new ArrayList<>() ;
ArrayList<Long> lista = new ArrayList<>() ;
ArrayList<Long> listb = new ArrayList<>() ;
// ArrayList<Integer> lista = new ArrayList<>() ;
// ArrayList<Integer> listb = new ArrayList<>() ;
//ArrayList<String> lists = new ArrayList<>() ;

HashMap<Integer,Integer> map = new HashMap<>() ;
//HashMap<Long,Long> map = new HashMap<>() ;
HashMap<Integer,Integer> mapx  = new HashMap<>() ;
HashMap<Integer,Integer> mapy  = new HashMap<>() ;
//HashMap<String,Integer> maps = new HashMap<>() ;
//HashMap<Integer,Boolean> mapb = new HashMap<>() ;
//HashMap<Point,Integer> point = new HashMap<>() ; 

 Set<Integer> set = new HashSet<>() ;
 Set<Integer> setx = new HashSet<>() ;
 Set<Integer> sety = new HashSet<>() ;

StringBuilder sb =new StringBuilder("") ;

//Collections.sort(list);

//if(map.containsKey(arr[i]))map.put(arr[i] , map.get(arr[i]) +1 ) ;
//else map.put(arr[i],1) ;

// if(map.containsKey(temp))map.put(temp , map.get(temp) +1 ) ;
// else map.put(temp,1) ;

//int bit  =Integer.bitCount(n);
// gives total no of set bits in n;

// Arrays.sort(arr, new Comparator<Pair>() {
// 			@Override
// 			public int compare(Pair a, Pair b) {
// 				if (a.first != b.first) {
// 					return a.first - b.first; // for increasing order of first
// 				}
// 				return a.second - b.second ; //if first is same then sort on second basis
// 			}
// 		});


int testcase = 1;
 //testcase = scn.nextInt() ;
for(int testcases =1  ; testcases <= testcase ;testcases++)
{
    
 //if(map.containsKey(arr[i]))map.put(arr[i],map.get(arr[i])+1) ;else map.put(arr[i],1) ;
 //if(map.containsKey(temp))map.put(temp,map.get(temp)+1) ;else map.put(temp,1) ;
 
 //adj = new ArrayList[n] ;

// for(int i = 0; i< n; i++)
// {
//     adj[i] = new ArrayList<Integer>();
// }

// long n = scn.nextLong() ;
//String s = scn.next() ;

int n= scn.nextInt() ;
int[] arr= new int[n] ;

for(int i=0; i < n;i++)
{
    arr[i]= scn.nextInt();
}

sort(arr, 0 ,n-1) ;

int x= 0;int y = n-1 ;

while( x < y)
{
    int temp = arr[x] ;
    arr[x] =arr[y] ;
    arr[y] = temp ;
    
    x++ ;y-- ;
}

int last = arr[0]+2 ;int ans = 0 ;

for( int i = 0 ; i < n ;i++)
{
    
    int cur  =-1  ;
    
    for(int dx =1; dx >=-1 ;dx--)
    {
        if(arr[i] +dx > 0 && arr[i] + dx < last)
        {
            cur  = arr[i] + dx ;break ;
        }
    }
    
    
    
    if(cur == -1)
    {
        continue  ;
    }
    
    
    ans++ ;
    
    last = cur  ;
}

pln(ans) ;






















//out.println(ans) ;
//out.println(ans+" "+in) ;

//out.println("Case #" + testcases + ": "  + ans ) ;
//out.println("@") ;
set.clear() ;
sb.delete(0 , sb.length()) ;
list.clear() ;lista.clear() ;listb.clear() ;
map.clear() ;
mapx.clear() ;
mapy.clear() ;
setx.clear() ;sety.clear() ;

} // test case  end loop


out.flush() ;  
} // solve fn ends


public static void main (String[] args) throws java.lang.Exception
{
  

solve() ;
      
}


}
  




 class Pair 
{
  int first ;
  
int second  ;
  
  public  Pair(int x, int y)
  {
      this.first = x ;this.second = y ;
  }
  
  @Override
 public boolean equals(Object obj)
 {
     if(obj == this)return true  ;
     
     if(obj == null)return false  ;
     
     if(this.getClass() != obj.getClass())
     {
         return false ;
     }
     
     
     Pair other  = (Pair)(obj) ;
     
     if(this.first != other.first)return false  ;
     
     
     if(this.second != other.second)return false  ;
     
     return true  ;
     
 }
    
    @Override
    
    public int hashCode()
    {
        return this.first^this.second  ;
    }
      
@Override
	public String toString() {
	
	String ans = "" ;
	ans  += this.first ;
	ans += " ";
	ans += this.second ;
	
	return ans  ;
	}


}



