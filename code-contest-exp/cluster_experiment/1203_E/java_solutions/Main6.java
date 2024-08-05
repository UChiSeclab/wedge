    import java.io.BufferedWriter;
    	import java.io.IOException;
    	import java.io.InputStream;
    	import java.io.OutputStream;
    	import java.io.OutputStreamWriter;
    	import java.io.PrintWriter;
    	import java.io.Writer;
    	import java.util.Arrays;
    	import java.util.InputMismatchException;
    	import java.util.*;
    	import java.io.*;
    	import java.math.*;
    	public class Main6{
     
    	static class Pair
    		{ 
    			int x;
    			int y;
    			public Pair(int x,int y) 
    			{	 
    				this.x= x; 
    				this.y= y;
    			}	 
				
			@Override
					public int hashCode() 
					{ 
						final int temp = 14; 
						int ans = 1; 
						ans =x*31+y*13; 
						return ans; 
					} 
			  
					// Equal objects must produce the same 
					// hash code as long as they are equal 
				@Override
				public boolean equals(Object o) 
				{ 
					if (this == o) { 
						return true; 
					} 
					if (o == null) { 
						return false; 
					} 
					if (this.getClass() != o.getClass()) { 
						return false; 
					} 
					Pair other = (Pair)o; 
					if (this.x != other.x || this.y!=other.y) { 
						return false; 
					} 
					return true; 
				} 
					
    		} 
    		static class Pair1
    		{
    			String x;
    			int y;
    			int z;
    			
    		}
    		static class Compare
    		{ 
    			/*static void compare(Pair arr[], int n) 
    			{ 
    				// Comparator to sort the pair according to second element 
    				Arrays.sort(arr, new Comparator<Pair>() { 
    					@Override public int compare(Pair p1, Pair p2) 
    					{ 
    						if(p1.start>p2.start)
    						{
    							return 1;
    						}
    						else if(p1.start==p2.start)
    						{
    							return 0;
    						}
    						else
    						{
    							return -1;
    						}
    					} 
    				}); 
    			}
    			*/
    		}
    		
    		
    		public static long pow(long a, long b)
    		{
    			long result=1;
    			while(b>0)
    			{
    				if (b % 2 != 0)
    				{
    					result=(result*a)%998244353;
    					b--;
    				} 
    				a=(a*a)%998244353;
    				b /= 2;
    			}   
    			return result;
    		}
    		public static long fact(long num)
    		{
    					long value=1;
    					int i=0;
    					for(i=2;i<num;i++)
    					{
    						value=((value%mod)*i%mod)%mod;
    					}
    					return value;
    				}
    				public static long gcd(long a, long b)
    				{
    					if (a == 0)
    						return b;
    					return gcd(b%a, a);
    				}
    				
    		/*		public static long lcm(long a,long b)
    				{
    					return a * (b / gcd(a, b));
    				}
    			*/	public static long sum(int h)
    				{
    					return (h*(h+1)/2);
    				}
    				public static void dfs(int parent,boolean[] visited,int[] dp)
    				{
    					ArrayList<Integer> arr=new ArrayList<Integer>();
    					arr=graph.get(parent);
    					visited[parent]=true;
    					for(int i=0;i<arr.size();i++)
    					{
    						int num=(int)arr.get(i);
    						if(visited[num]==false)
    						{
    							dfs(num,visited,dp);
							}
							dp[parent]=Math.max(dp[num]+1,dp[parent]);
    					}
    				}
    			//	static int flag1=0;
    				static int[] dis;
    				static int mod=1000000007;
    				static ArrayList<ArrayList<Integer>> graph;
    				
    				public static void bfs(int num,int size)
    				{
    					boolean[] visited=new boolean[size+1];
    					Queue<Integer> q=new LinkedList<>();
    					q.add(num);
    					ans[num]=1;
    					visited[num]=true;
    					while(!q.isEmpty())
    					{
    						int x=q.poll();
    						ArrayList<Integer> al=graph.get(x);
    						for(int i=0;i<al.size();i++)
    						{
    							int y=al.get(i);	
    							if(visited[y]==false)
    							{
    								q.add(y);
    								ans[y]=ans[x]+1;
    								visited[y]=true;
    							}
    						}
    					}
    				}
    				static int[] ans;
    				
    				
    				
    				
    				
    				
    				
    				
    				
    			//	static int[] a;			  
    				
    				public static int[] sort(int[] a)
    				{
    					int n=a.length;
    					ArrayList<Integer> ar=new ArrayList<>();
    					for(int i=0;i<a.length;i++)
    					{
    						ar.add(a[i]);
    					}
    					Collections.sort(ar);
    					for(int i=0;i<n;i++)
    					{
    						a[i]=ar.get(i);
    					}
    					return a;
    				}
					static public void main(String args[])throws IOException
    				{
    		//		    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    			/*		boolean[] prime=new boolean[1101];
    					for(int i=2;i*i<=1100;i++)
    					{
    						if(prime[i]==false)
    						{
    							for(int j=2*i;j<=1100;j+=i)
    							{
    								prime[j]=true;
    							}
    						}
    					}*/
						int n=i();
						int[] a=new int[n];
						int[] count=new int[1000000];
						int[] flag=new int[1000000];
						int max=0;
						for(int i=0;i<n;i++)
						{
							a[i]=i();
							count[a[i]]++;
							max=Math.max(a[i],max);
						}
						for(int i=max;i>=1;i--)
						{
							if(count[i]>0)
							{
								if(flag[i+1]==0)
								{
									flag[i+1]=1;
									count[i]--;
								}
								if(flag[i]==0 && count[i]>0)
								{
									flag[i]=1;
									count[i]--;
								}
								if(flag[i-1]==0 && count[i]>0 && i-1>0)
								{
									flag[i-1]=1;
									count[i]--;
								}
							}
						}
						int ans=0;
						for(int i=1;i<=max+1;i++)
						{
							if(flag[i]==1)
								ans++;
						}
						pln(ans+"");
    				}
    	
    					
    				
    				
    				
    				
    				
    				
    				
    				
    				
    				
    				
    				
    				
    				/**/
    				static InputReader in=new InputReader(System.in);
    					static OutputWriter out=new OutputWriter(System.out);
    					public static long l()
    					{
    						String s=in.String();
    						return Long.parseLong(s);
    					}
    					public static void pln(String value)
    					{
    						System.out.println(value);
    					}
    					public static int i()
    					{
    						return in.Int();
    					}
    					public static String s()
    					{
    						return in.String();
    					}
    		}
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    			class InputReader {
    				 
    				private InputStream stream;
    				private byte[] buf = new byte[1024];
    				private int curChar;
    				private int numChars;
    				private SpaceCharFilter filter;
    			 
    				public InputReader(InputStream stream) {
    					this.stream = stream;
    				}
    			 
    				public int read() {
    					if (numChars== -1)
    						throw new InputMismatchException();
    					if (curChar >= numChars) {
    						curChar = 0;
    						try {
    							numChars = stream.read(buf);
    						} catch (IOException e) {
    							throw new InputMismatchException();
    						}
    						if (numChars <= 0)
    							return -1;
    					}
    					return buf[curChar++];
    				}
    			 
    				public int Int() {
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
    			 
    				public String String() {
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
    			 
    				public boolean isSpaceChar(int c) {
    					if (filter != null)
    						return filter.isSpaceChar(c);
    					return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    				}
    			 
    				public String next() {
    					return String();
    				}
    			 
    				public interface SpaceCharFilter {
    					public boolean isSpaceChar(int ch);
    				}
    			}
    			 
    			class OutputWriter {
    				private final PrintWriter writer;
    			 
    				public OutputWriter(OutputStream outputStream) {
    					writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
    				}
    			 
    				public OutputWriter(Writer writer) {
    					this.writer = new PrintWriter(writer);
    				}
    			 
    				public void print(Object...objects) {
    					for (int i = 0; i < objects.length; i++) {
    						if (i != 0)
    							writer.print(' ');
    						writer.print(objects[i]);
    					}
    				}
    			 
    				public void printLine(Object...objects) {
    					print(objects);
    					writer.println();
    				}
    			 
    				public void close() {
    					writer.close();
    				}
    			 
    				public void flush() {
    					writer.flush();
    				}
    			 
    				}
    			 
    				class IOUtils {
    			 
    				public static int[] readIntArray(InputReader in, int size) {
    					int[] array = new int[size];
    					for (int i = 0; i < size; i++)
    						array[i] = in.Int();
    					return array;
    				}
    			 
    				} 