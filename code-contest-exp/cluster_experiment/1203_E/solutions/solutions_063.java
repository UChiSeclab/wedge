import java.io.*;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.KeyStore.Entry;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

public class Main
{  
   static long mod=(long)(1e+9 + 7);
   static int[] sieve;
   static ArrayList<Integer> primes;
   static int[] visited;
   static String ans;

   public static void main(String[] args) throws java.lang.Exception
   {	  	
	     fast s = new fast();
	     PrintWriter out=new PrintWriter(System.out);
         
	     int n=s.nextInt();
	     
	     Integer a[]=new Integer[n];
	     for(int i=0;i<n;i++)
	    	 a[i]=s.nextInt();
	    
	     HashSet<Integer> hm=new HashSet<Integer>();
	     
	     Arrays.sort(a);
	     
	     for(int i=0;i<a.length;i++)
	    	 a[i]+=1;
	     
	     hm.add(a[n-1]);
	     
	     for(int i=n-2;i>=0;i--)
	    	 {
	    		 if(hm.contains(a[i]))
	    			 {
	    			  if(!hm.contains(a[i]-1) && a[i]-1>0)
	    			    hm.add(a[i]-1);
	    			  else if(a[i]-2>0)
	    				hm.add(a[i]-2);
	    			 }
	    		 else
	    			 hm.add(a[i]);
	    	 }
	     
	     System.out.println(hm.size());
         
   }
  
 

static class fast {   
	private InputStream i;
		private byte[]  buf = new byte[1024];
		private int curChar;
		private int numChars;
		
		//Return floor log2n
		public static long log2(long bits) // returns 0 for bits=0
			{
			    int log = 0;
			    if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
			    if( bits >= 256 ) { bits >>>= 8; log += 8; }
			    if( bits >= 16  ) { bits >>>= 4; log += 4; }
			    if( bits >= 4   ) { bits >>>= 2; log += 2; }
			    return log + ( bits >>> 1 );
			}
		
		public static boolean next_permutation(int a[])
		   {
			   int i=0,j=0;int index=-1;	   
			   int n=a.length;
			   
			   for(i=0;i<n-1;i++)
				   if(a[i]<a[i+1]) index=i;
			   
			   if(index==-1) return false;
			   i=index;

			  
			   for(j=i+1;j<n && a[i]<a[j];j++);

			   int temp=a[i];
			   a[i]=a[j-1];
			   a[j-1]=temp;
			   	   
			   for(int p=i+1,q=n-1;p<q;p++,q--)
			   {
				   temp=a[p];
				   a[p]=a[q];
				   a[q]=temp;
			   }
			   
			   return true;
		   }
		   
		   
		public static void division(char ch[],int divisor)
		   {
			   int div=Character.getNumericValue(ch[0]); int mul=10;int remainder=0;
			   StringBuilder quotient=new StringBuilder("");
			   for(int i=1;i<ch.length;i++)
			   {
				   div=div*mul+Character.getNumericValue(ch[i]);	
				   if(div<divisor) {quotient.append("0");continue;}
				   quotient.append(div/divisor);
				   div=div%divisor;mul=10;
				   
			   } 
			   remainder=div;
			   while(quotient.charAt(0)=='0')quotient.deleteCharAt(0);	   
			   System.out.println(quotient+" "+remainder);	  
		   }
		
		public static void sieve(int size)
		{
			sieve=new int[size+1];
			
			primes=new ArrayList<Integer>();
			
			sieve[1]=1;
			
			for(int i=2;i<=Math.sqrt(size);i++)
			{
				if(sieve[i]==0)
				 {
					for(int j=i*i;j<size;j+=i) {sieve[j]=1;}
				 }
			}
			
			for(int i=2;i<=size;i++)
			{
				if(sieve[i]==0) primes.add(i);
			}
		}
		
		public static long pow(long n, long b, long MOD)
		   {
		       long x=1;long y=n; 
		       while(b > 0)
		       {
		           if(b%2 == 1)
		           {
		               x=x*y;
		               if(x>MOD) x=x%(MOD);
		           }
		           y = y*y;
		           if(y>MOD) y=y%(MOD); 
		           b >>= 1;
		       }
		       return x;
		   }


		public static long mod_inv(long n,long mod)
		{
			return pow(n,mod-2,mod);
		}
		
		//Returns index of highest number less than or equal to key
		 public static int upper(long[] a,int length,long key)
		 {
			    int low = 0;
		        int high = length-1;
		        int ans=-1;
		        
		        while (low <= high) {
		            int mid = (low + high) / 2;
		            if (key >= a[mid]) {
		                ans=mid;
		            	low = mid+1;
		            } else if(a[mid]>key){
		                high = mid - 1;
		            }
		        }
		        return ans;
		}
		
		//Returns index of least number greater than or equal to key
		 public static int lower(long[] a,int length,long key)
		 {
			    int low = 0;
		        int high = length-1;
		        int ans=-1;
		        
		        while (low <= high) {
		            int mid = (low + high) / 2;
		            if (key<=a[mid]) {
		                ans=mid;
		            	high = mid-1;
		            }
		            else{
		            		low=mid+1;
		            	}
		        }
		        return ans;
		} 
		   
		public long gcd(long r,long ans)
		{
			if(r==0) return ans;
			return gcd(ans%r,r);
		}
		public fast() {
			this(System.in);
		}
		public fast(InputStream is) {
			i = is;
		}
		public int read() {
			if (numChars == -1)
				throw new InputMismatchException();
			if (curChar >= numChars) {
				curChar = 0;
				try {
					numChars = i.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}
			return buf[curChar++];
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
		public String nextString() {
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
		public long nextLong() {
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
		public int nextInt() {
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
		public boolean isSpaceChar(int c) {
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
		public boolean isEndOfLine(int c) {
			return c == '\n' || c == '\r' || c == -1;
		}

	}	
}