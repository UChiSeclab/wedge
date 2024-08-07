import java.io.*;
import java.util.*;
import java.math.*;
import java.math.BigInteger; 
//import javafx.util.*; 
public final class B
{    
	static PrintWriter out = new PrintWriter(System.out); 
	static StringBuilder ans=new StringBuilder();
	static FastReader in=new FastReader();
	static ArrayList<Integer> g[],og[];
	static long mod=(long)(1e9+7);
	static int D1[],D2[],par[];
	static boolean set[];
	static int value[];
	static long INF=Long.MAX_VALUE;
	static int N,M;
	static int val[],tot[],max[];
	static int min;
	static int path[][],A[];
	static char X[];
	static int sum[];
	public static void main(String args[])throws IOException
	{

		int T=i();
		outer:while(T-->0)
		{
			int N=i();
			char X[]=in.next().toCharArray();
			int len=1;
			int x=0,y=0;
			int r_x[]=new int[N];
			int r_y[]=new int[N];

			int f[]=new int[N];
			if(X[0]=='D')x=1;
			else y=1;
			f[0]=1;
			r_x[0]=x;
			r_y[0]=y;
			for(int i=1; i<N; i++)
			{
				if(X[i]=='D')x+=1;
				else y+=1;
				r_x[i]=x;
				r_y[i]=y;
				if(len==1)
				{
					if(X[i]!=X[0])
					{
						f[i]=1;
						len=i+1;

					}
					else
					{
						f[i]=i+1;
					}
				}
				else
				{
					if((i+1)%len==0)
					{

						int a=0,b=0;
						if(x%r_x[len-1]==0 && y%r_y[len-1]==0)
						{
							a=x/r_x[len-1];
							b=y/r_y[len-1];
							if(a==b)
							{
								f[i]=a;
							}
							else
							{
								
								for(int j=len; j<=i; j++)
								{
									if(x%r_x[j]==0 && y%r_y[j]==0)
									{
										a=x/r_x[j-1];
										b=y/r_y[j-1];
										if(a==b)
										{
											f[i]=a;
											len=j;
											break;
										}
										
									}
								}
								//System.out.println("UIP--> "+len+" "+(i+1));

							}
						}
						else
						{
							f[i]=1;
							for(int j=len; j<=i; j++)
							{
								if(x%r_x[j]==0 && y%r_y[j]==0)
								{
									a=x/r_x[len-1];
									b=y/r_y[len-1];
									if(a==b)
									{
										f[i]=a;
										len=j;
										break;
									}
									
								}
							}
						}
					}
					else
					{
						f[i]=1;
					}
				}
			}
			print(f);
		}
		out.close();
	}
	static double d(long a,long b,long c,long d)
	{
		long x=(a-c);
		long y=(b-d);
		x*=x;
		y*=y;
		return Math.sqrt(x+y);
	}
	static String add(char A[],char B[])
	{
		StringBuilder sb=new StringBuilder();
		return sb.toString();
	}
	static int f(int N,int v)
	{
		//System.out.println("for--> "+v);
		if(v>N)return 1;
		char ch=X[v];		
		if(sum[v]==-1)
		{
			if(ch=='?')
			{
				return sum[v]=f(N,2*v)+f(N,2*v+1);
			}
			else if(ch=='1')
			{
				return sum[v]=f(N,2*v);
			}
			else
			{
				return sum[v]=f(N,2*v+1);
			}
		}
		return sum[v];
	}
	static void update(int N,int v)
	{
		if(v<1)return ;
		if(X[v]=='?')
		{
			sum[v]=f(N,v*2)+f(N,v*2+1);
		}
		else if(X[v]=='1')sum[v]=f(N,v*2);
		else sum[v]=f(N,2*v+1);
		//System.out.println("sum--> "+v+ " "+sum[v]);
		update(N,v/2);
	}
	static boolean isPalin(char X[])
	{
		int l=0,r=X.length-1;
		while(l<=r)
		{
			if(X[l]!=X[r])return false;
			l++;
			r--;
		}
		return true;
	}

	static boolean isSorted(int A[])
	{
		int N=A.length;
		for(int i=0; i<N-1; i++)
		{
			if(A[i]>A[i+1])return false;
		}

		return true;		
	}
	static int f1(int x,ArrayList<Integer> A)
	{
		int l=-1,r=A.size();
		while(r-l>1)
		{
			int m=(l+r)/2;
			int a=A.get(m);
			if(a<x)l=m;
			else r=m;
		}		
		return l;
	}


	static int ask(int t,int i,int j,int x)
	{
		System.out.println("? "+t+" "+i+" "+j+" "+x);
		return i();
	}

	static int ask(int a)
	{
		System.out.println("? 1 "+a);
		return i();
	}

	static int max(int a,int b,int c)
	{
		return Math.max(a, Math.max(c, b));
	}
	static int value(char X[],char Y[])
	{
		int c=0;
		for(int i=0; i<7; i++)
		{
			if(Y[i]=='1' && X[i]=='0')return -1;
			if(X[i]=='1' && Y[i]=='0')c++;
		}
		return c;
	}
	static boolean isValid(int i,int j)
	{
		if(i<0 || i>=N)return false;
		if(j<0 || j>=M)return false;
		return true;
	}

	static long fact(long N)
	{
		long num=1L;
		while(N>=1)
		{
			num=((num%mod)*(N%mod))%mod;
			N--;
		}
		return num;
	}
	static boolean reverse(long A[],int l,int r)
	{
		while(l<r)
		{
			long t=A[l];
			A[l]=A[r];
			A[r]=t;
			l++;
			r--;
		}
		if(isSorted(A))return true;
		else return false;
	}
	static boolean isSorted(long A[])
	{
		for(int i=1; i<A.length; i++)if(A[i]<A[i-1])return false;
		return true;
	}
	static boolean isPalindrome(char X[],int l,int r)
	{
		while(l<r)
		{
			if(X[l]!=X[r])return false;
			l++; r--;
		}
		return true;
	}

	static long min(long a,long b,long c)
	{
		return Math.min(a, Math.min(c, b));
	}

	static void print(int a)
	{
		System.out.println("! "+a);
	}
	static int ask(int a,int b)
	{
		System.out.println("? "+a+" "+b);
		return i();
	}
	static int find(int a)
	{
		if(par[a]<0)return a;
		return par[a]=find(par[a]);
	}
	static void union(int a,int b)
	{
		a=find(a);
		b=find(b);
		if(a!=b)
		{
			par[a]+=par[b]; //transfers the size
			par[b]=a;       //changes the parent
		}
	}
	static void swap(char A[],int a,int b)
	{
		char ch=A[a];
		A[a]=A[b];
		A[b]=ch;
	}

	static void sort(long[] a) //check for long
	{
		ArrayList<Long> l=new ArrayList<>();
		for (long i:a) l.add(i);
		Collections.sort(l);
		for (int i=0; i<a.length; i++) a[i]=l.get(i);
	}
	static void setGraph(int N)
	{
		g=new ArrayList[N+1];		
		og=new ArrayList[N+1];

		val=new int[N+1];
		for(int i=0; i<=N; i++)
		{
			og[i]=new ArrayList<Integer>();			
			g[i]=new ArrayList<Integer>();
		}
	}

	static  long pow(long a,long b)
	{
		long mod=1000000007;
		long pow=1;
		long x=a;
		while(b!=0)
		{
			if((b&1)!=0)pow=(pow*x)%mod;
			x=(x*x)%mod;
			b/=2;
		}
		return pow;
	}
	static long toggleBits(long x)//one's complement || Toggle bits
	{
		int n=(int)(Math.floor(Math.log(x)/Math.log(2)))+1;

		return ((1<<n)-1)^x;
	}
	static int countBits(long a)
	{
		return (int)(Math.log(a)/Math.log(2)+1);
	}
	static void sort(int[] a) {
		ArrayList<Integer> l=new ArrayList<>();
		for (int i:a) l.add(i);
		Collections.sort(l);
		for (int i=0; i<a.length; i++) a[i]=l.get(i);
	}
	static boolean isPrime(long N)
	{
		if (N<=1)  return false; 
		if (N<=3)  return true; 
		if (N%2 == 0 || N%3 == 0) return false; 
		for (int i=5; i*i<=N; i=i+6) 
			if (N%i == 0 || N%(i+2) == 0) 
				return false; 
		return true; 
	}
	static long GCD(long a,long b) 
	{
		if(b==0)
		{
			return a;
		}
		else return GCD(b,a%b );
	}

	//Debugging Functions Starts

	static void print(char A[])
	{
		for(char c:A)System.out.print(c+" ");
		System.out.println();
	}
	static void print(boolean A[])
	{
		for(boolean c:A)System.out.print(c+" ");
		System.out.println();
	}
	static void print(int A[])
	{
		for(int a:A)System.out.print(a+" ");
		System.out.println();	
	}
	static void print(long A[])
	{
		for(long i:A)System.out.print(i+ " ");
		System.out.println();

	}
	static void print(ArrayList<Integer> A)
	{
		for(int a:A)System.out.print(a+" ");
		System.out.println();
	}

	//Debugging Functions END
	//----------------------
	//IO FUNCTIONS STARTS
	static HashMap<Integer,Integer> Hash(int A[])
	{
		HashMap<Integer,Integer> mp=new HashMap<>();
		for(int a:A)
		{
			int f=mp.getOrDefault(a,0)+1;
			mp.put(a, f);
		}
		return mp;
	}
	static int i()
	{
		return in.nextInt();
	}

	static long l()
	{
		return in.nextLong();
	}

	static int[] input(int N){
		int A[]=new int[N];
		for(int i=0; i<N; i++)
		{
			A[i]=in.nextInt();
		}
		return A;
	}

	static long[] inputLong(int N)     {
		long A[]=new long[N];
		for(int i=0; i<A.length; i++)A[i]=in.nextLong();
		return A;
	}

	//IO FUNCTIONS END

}

class node implements Comparable<node>
{
	long x,y,t;
	double p;
	node(long x,long y,long t,double p)
	{
		this.x=x;
		this.y=y;
		this.t=t;
		this.p=p;
	}
	public int compareTo(node x)
	{
		if(this.t<x.t)return -1;
		if(this.t>x.t)return 1;			
		return 0;
	}

}

//Code For FastReader
//Code For FastReader
//Code For FastReader
//Code For FastReader
class FastReader
{
	BufferedReader br;
	StringTokenizer st;
	public FastReader()
	{
		br=new BufferedReader(new InputStreamReader(System.in));
	}

	String next()
	{
		while(st==null || !st.hasMoreElements())
		{
			try
			{
				st=new StringTokenizer(br.readLine());
			}
			catch(IOException e)
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
	//gey
	double nextDouble()
	{
		return Double.parseDouble(next());
	}

	String nextLine()
	{
		String str="";
		try
		{
			str=br.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return str;
	}

}