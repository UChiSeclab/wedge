//package learning;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

import java.awt.*;
 
public class TryC
{
    
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
				} catch (IOException e) {
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
	

	
	
	
    static long mod=1000000007;
    static BigInteger bigInteger = new BigInteger("1000000007");
    static int n = (int)1e6;
    static boolean[] prime;
    static ArrayList<Integer> as;
     static ArrayList<Integer> []as1;
    static void sieve() {
			Arrays.fill(prime	, true);
			prime[0] = prime[1] = false;
			for(int i = 2 ; i * i <= n ; ++i) {
				if(prime[i]) {
					for(int k = i * i; k< n ; k+=i) {
						prime[k] = false;
					}
				}
			}
			
		}
		static PrintWriter w = new PrintWriter(System.out);
                static int p = 0;
 static char [][]sol;
    public static void main(String[] args)
    {
        
        InputReader sc = new InputReader(System.in);
        //PrintWriter w = new PrintWriter(System.out);
    
        
        prime  = new boolean[n + 1];
		sieve();
		prime[1] = false;
		
		/*
		as = new ArrayList<>();
		for(int i=2;i<=1000000;i++)
		{
		    if(prime[i])
		    as.add(i);
		}
		*/
		
       /* 
		
	long a = sc.nl();
	 
            BigInteger ans = new BigInteger("1");
            for (long i = 1; i < Math.sqrt(a); i++) {
                if (a % i == 0) {
                    if (a / i == i) {
                        ans = ans.multiply(BigInteger.valueOf(phi(i)));
                        
                    } else {
                        ans = ans.multiply(BigInteger.valueOf(phi(i)));
                        ans = ans.multiply(BigInteger.valueOf(phi(a / i)));
 
                        
                    }
 
 
                }
            }
            w.println(ans.mod(bigInteger));
            */
          
         // MergeSort ob = new MergeSort();
       // ob.sort(arr, 0, arr.length-1);
       
    // Student []st = new Student[x];    
   // st[i] = new Student(i,d[i]);
     //Arrays.sort(st,(p,q)->p.diff-q.diff);
     //PriorityQueue<Point> pq=new PriorityQueue<Point>(new multipliers());
 int x = sc.ni();
 Integer []a = new Integer[x];
 for(int i=0;i<x;i++)
 {
     a[i] = sc.ni();
 }
 Arrays.sort(a);
 int []c  =new int[1500000];
 for(int i=0;i<x;i++)
 {
     if(a[i]-1 > 0)
     {
         if(c[a[i]-1] == 0)
         {
             a[i]--;
             c[a[i]] = 1;
         }
         else
         {
             if(c[a[i]]==0)
             {
                 c[a[i]] = 1;
                 
             }
             else
             {
                 a[i]++;
                 c[a[i]] = 1;
             }
         }
         
     }
     else
     {
         
         if(c[a[i]]==0)
             {
                 c[a[i]] = 1;
                 
             }
             else
             {
                 a[i]++;
                 c[a[i]] = 1;
             }
     }
     
 }
 HashSet<Integer> hs = new HashSet<>();
 for(int i=0;i<x;i++)
 {
     hs.add(a[i]);
 }
 w.println(hs.size());
   
     
        
  
   w.close();
        
  
    
    }
   
    
   
        
   
   static long  get(long x,int t) {
		if (x<t) return 0;
		return (x-t)/3+1;
	}
    static long getwhite(long x1,long y1,long x2,long y2)
{
    long sum=(x2-x1+1)*(y2-y1+1);
		if(sum%2==0)return sum/2;
		if((x1+y1)%2==0)return sum/2+1;
		return sum/2;
}
    static long getblack(long x1,long y1,long x2,long y2)
    {
        if(x2<x1||y2<y1)return 0;
		long sum=(x2-x1+1)*(y2-y1+1);
		sum-=getwhite(x1,y1,x2,y2);
		return sum;
    }
    static long log2(long value) {
    return Long.SIZE-Long.numberOfLeadingZeros(value);
}
   
     static class Student
    {
        
        int first;
        int sec;
      //  int index;
        Student(int first,int sec)
        {
            this.first = first;
            this.sec = sec;
           // this.index = index;             
            
        }
        
    }
     
     public static long modMultiply(long one, long two) {
 
		return (one % mod * two % mod) % mod;
	}
    static long fx(int m)
    {
        long re = 0;
        for(int i=1;i<=m;i++)
        {
            re += (long) (i / gcd(i,m));
        }
        return re;
    }
     static int gcd(int a, int b)
    {
	 if (a == 0)
	  return b;
				         
	return gcd(b%a, a);
    }
    
   
    
     static long phi(long nx)
    {
        // Initialize result as n
        double result = nx; 
        
     
        // Consider all prime factors of n and for 
        // every prime factor p, multiply result
        // with (1 - 1/p)
        for (int p = 0; as.get(p) * as.get(p) <= nx; ++p)
        {
            // Check if p is a prime factor.
            if (nx % as.get(p) == 0)
            {
                // If yes, then update n and result
                while (nx % as.get(p) == 0)
                    nx /= as.get(p);
                result *= (1.0 - (1.0 / (double) as.get(p)));
            }
        }
     
        // If n has a prime factor greater than sqrt(n)
        // (There can be at-most one such prime factor)
        if (nx > 1)
            result *= (1.0 - (1.0 / (double) nx));
     
        return (long)result;
    
        
        //return(phi((long)result,k-1));
        
    }
    
    public static void primeFactors(int n,int x)
    {
        as = new ArrayList<>();
       
        // Print the number of 2s that divide n
        while (n%2==0)
        {
            
        
            as.add(2);
            //System.out.print(2 + " ");
            n /= 2;
            
        }
 
        // n must be odd at this point.  So we can
        // skip one element (Note i = i +2)
        for (int i = 3; i <= Math.sqrt(n); i+= 2)
        {
            // While i divides n, print i and divide n
            while (n%i == 0)
            {
            
               // System.out.print(i + " ");
                
                as.add(i);
                n /= i;
             
                
            }
            
        }
 
        // This condition is to handle the case whien
        // n is a prime number greater than 2
        if (n >= 2)
        {
          
            as.add(n);
        }
           
    }
    static int digitsum(int x)
    
    {    
 
        int sum = 0;
        while(x > 0)
        {
            int temp = x % 10;
            sum += temp;
            x /= 10;
        }
        return sum;
        
    }
    static int countDivisors(int n)
    {
        int cnt = 0;
        for (int i = 1; i*i <=n; i++)
        {
             if (n % i == 0 && i<=1000000)
             {
                // If divisors are equal,
                // count only one
                if (n / i == i)
                   cnt++;
      
                else // Otherwise count both
                    cnt = cnt + 2;
             }
         }
        return cnt;
    }
   
    static boolean isprime(long n)
    {
    
    if(n == 2)
        return true;
    if(n == 3)
        return true;
    if(n % 2 == 0)
        return false;
    if(n % 3 == 0)
        return false;
 
    long i = 5;
   long w = 2;
 
    while(i * i <= n)
    {
        if(n % i == 0)
            return false;
    
 
        i += w;
        w = 6 - w;
    }
 
    return true;
    }
    
   
 
 
    
    
    static boolean binarysearch(int []arr,int p,int n)
    {
        //ArrayList<Integer> as = new ArrayList<>();
        //as.addAll(0,at);
        //Collections.sort(as);
        boolean re = false;
        int st = 0;
        int end = n-1;
        
        
        
        while(st <= end)
        { 
            int mid = (st+end)/2;
        
            if(p > arr[mid])
            {
                st = mid+1;
                
            }
            else if(p < arr[mid])
            {
                end = mid-1;
                
            }
            else if(p == arr[mid])
            {
            
            re = true;
            break;
            }
           
            
            
        }
         return re;
        
    }
    
   
    
    /* Java program for Merge Sort */
static class MergeSort
{
    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    void merge(int arr[], int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
 
        /* Create temp arrays */
        int L[] = new int [n1];
        int R[] = new int [n2];
 
        /*Copy data to temp arrays*/
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
    void sort(int arr[], int l, int r)
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
 
    /* A utility function to print array of size n */
    
 
   
}
    

    
    
    
    public static int ip(String s){
		return Integer.parseInt(s);
	}
	 public static String ips(int s){
		return Integer.toString(s);
	}
	
        

        public static int lowerBound(int[] array, int length, int value) {
        int low = 0;
        int high = length-1;
        while (low < high) {
            final int mid = (low + high) / 2;
            if (value <= array[mid]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
  }
  public static int upperBound(int[] array, int length, int value) {
        int low = 0;
        int high = length-1;
        while (low < high) {
            final int mid = (low + high) / 2;
            if (value >= array[mid]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
 static class multipliers implements Comparator<Point>{
  
  public int compare(Point a,Point b) {
   if(a.y<b.y)
    return -1;
   else if(b.y<a.y)
    return 1;
   else
    return 0;
  }
 }
	
	
		
	
}



    
					 
