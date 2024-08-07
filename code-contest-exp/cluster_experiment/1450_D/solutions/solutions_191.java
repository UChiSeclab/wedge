import java.io.*;
import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.*;








	//import javafx.util.Pair; 
	//import java.util.concurrent.LinkedBlockingDeque;







		
public  class Codeforces {
		
			
			public static long mod = 1000000007 ;
			public static double epsilon=0.00000000008854;//diffue of epsilon
			public static InputReader sc = new InputReader(System.in);
			public static PrintWriter pw = new PrintWriter(System.out);
			public static Random rn=new Random();
			
			public static int firstNode(int a,int p[]){
				if(a!=p[a]){
					p[a]=firstNode(p[a], p);
				}
				return p[a];
			}
			public static void Union(int a,int b,int p[]){
				 a=firstNode(a, p);
				 b=firstNode(b, p);
				
				if(a!=b)
				p[a]=b;//if no rank than 
			}
			public static long[][] multiply(long a[][], long b[][]) 
			{ 
				long mul[][] = new long[a.length][b[0].length]; 
				for (int i = 0; i < a.length; i++) 
				{ 
					for (int j = 0; j < b[0].length; j++) 
					{ 
						mul[i][j] = 0; 
						for (int k = 0; k < a[0].length; k++) 
							mul[i][j] += (a[i][k] * b[k][j]);
					} 
				} 
			
				return mul;
			
			} 
			public static String backtrack(long dp[][],int i,int j,long a[][]){
				if(i==j){
					return "A"+(i+1);
				}
				if(i+1==j){
					return "(A"+(i+1)+" x "+"A"+(j+1)+")";
				}
				String ans="";
				for(int p=i;p<j;p++){
					long val=dp[i][p]+dp[p+1][j]+a[i][0]*a[p][1]*a[j][1];
					if(val==dp[i][j]){
						ans= "("+backtrack(dp, i, p, a)+" x "+backtrack(dp, p+1, j, a)+")";
						break;
					}
				}
				return ans;


			}
			public static boolean fun(int a[],int k){
				int n=a.length;
				Deque<Integer> qu=new LinkedList<>();
				int t=0;
				for(int i=0;i<k;i++){
					while(qu.size()>0&&a[qu.peekLast()]>a[i]){
						qu.removeLast();
					}
					qu.add(i);
				}
				HashSet<Integer> ans=new HashSet<>();
				ans.add(a[qu.peekFirst()]);
				for(int i=k;i<n;i++,t++){
					if(qu.peek()==t){
						qu.remove();
					}
					while(qu.size()>0&&a[qu.peekLast()]>a[i]){
						qu.removeLast();
					}
					qu.add(i);
		
					ans.add(a[qu.peekFirst()]);
				}
				//pw.println(ans+" "+k);
				for(int i=1;i<=n-k+1;i++){
					if(!ans.contains(i))return false;
				}
				return true;
			}
			
			public static void solve(){
				int n=sc.nextInt();
				int a[]=scanArray(n);
				int l=2,r=n;
				int ans=n+1;
				while(l<=r){
					int mid=(l+r)/2;
					if(fun(a, mid)){
						ans=mid-1;
						r=mid-1;
					}
					else{
						l=mid+1;
					}
					
				}
				//pw.println(ans);
				for(int i=0;i<n;i++){
					if(i==0){
						if(fun(a, 1))pw.print(1);
						else pw.print(0);
					}
					else if(i<ans)pw.print(0);
					else pw.print(1);
				}
				pw.print("\n");
				

				

			}

			
			
			
			public static void main(String[] args) throws Exception {
				// code starts.

				int q=sc.nextInt();
				while(q-->0){
					solve();
				}
				// Code ends...
				pw.flush();
				pw.close();
			}
			public static Comparator<Integer[]> MOquery(int block){
				return
				new Comparator<Integer[]>(){
					@Override
					public int compare(Integer a[],Integer b[]){
						int a1=a[0]/block;
						int b1=b[0]/block;
						if(a1==b1){
							if((a1&1)==1)
							return a[1].compareTo(b[1]);
							else{
								return b[1].compareTo(a[1]);
							}
						}
						return a1-b1;
					}
				};
			}
		
			public static Comparator<Long[]> column(int i){
				return 
				new Comparator<Long[]>() {
					@Override
					public int compare(Long[] o1, Long[] o2) {
						//return o1[i].compareTo(o2[i]);//for ascending
						return o2[i].compareTo(o1[i]);//for descending
					}
				};
			}
			public static Comparator<Integer[]> column(){
				return 
				new Comparator<Integer[]>() {
					@Override
					public int compare(Integer[] o1, Integer[] o2) {
						//return o1[i].compareTo(o2[i]);//for ascending
						//return o2[i].compareTo(o1[i]);//for descending
						
						for(int i=0;i<o1.length;i++){
							if(o1[i].compareTo(o2[i])!=0)
							return o1[i].compareTo(o2[i]);
						}
						return 0;
					}
				};
			}
			public static Comparator<Integer[]> column2(){
				return 
				new Comparator<Integer[]>() {
					@Override
					public int compare(Integer[] o1, Integer[] o2) {
						//return o1[i].compareTo(o2[i]);//for ascending
						//return o2[i].compareTo(o1[i]);//for descending
						int l1=0,l2=0;
						for(int i=0;i<o1.length-1;i++){
							l1+=o1[i];
							l2+=o2[i];
						}
						return l2-l1;
					}
				};
			}
			public static Comparator<Integer[]> dis(int k,Integer a[][]){
				return 
				new Comparator<Integer[]>() {
					@Override
					public int compare(Integer[] o1, Integer[] o2) {
						//return o1[i].compareTo(o2[i]);//for ascending
						//return o2[i].compareTo(o1[i]);//for descending
						int l1=0,l2=0;
						for(int i=0;i<o1.length-1;i++){
							l1+=Math.abs(a[k][i]-o1[i]);
							l2+=Math.abs(a[k][i]-o2[i]);
						}
						return l2-l1;
					}
				};
			}
			public static Comparator<Integer[]> col(int i){
				return 
				new Comparator<Integer[]>() {
					@Override
					public int compare(Integer[] o1, Integer[] o2) {
						//if(o1[i]-o2[i]!=0)
						return o1[i].compareTo(o2[i]);//for ascending
						//return o1[i+1].compareTo(o2[i+1]);
						//return o2[i].compareTo(o1[i]);//for descending
					}
				};
			}
			public static Comparator<Integer> des(){
				return 
				new Comparator<Integer>() {
					@Override
					public int compare(Integer o1, Integer o2) {
						//return o1[i].compareTo(o2[i]);//for ascending
						return o2.compareTo(o1);//for descending
					}
				};
			}
			public static Comparator<Integer> des(int dp[]){
				return 
				new Comparator<Integer>() {
					@Override
					public int compare(Integer o1, Integer o2) {
						//return o1[i].compareTo(o2[i]);//for ascending
						return dp[o2]-dp[o1];//for descending
					}
				};
			}
			
			
			
			
			public static String reverseString(String s){
				StringBuilder input1 = new StringBuilder(); 
				input1.append(s);  
				input1 = input1.reverse();
				return input1.toString();
			}
			public static int[] scanArray(int n){
				int a[]=new int [n];
				//Random x=new Random();
				for(int i=0;i<n;i++){
					a[i]=sc.nextInt();
				}
				
		
				return a;
			}
			public static long[] scanLongArray(int n){
				long a[]=new long [n];
				for(int i=0;i<n;i++)
				a[i]=sc.nextLong();
		
				return a;
			}
			public static String [] scanStrings(int n){
				String a[]=new String [n];
				for(int i=0;i<n;i++)
				a[i]=sc.nextLine();
		
				return a;
			}
			public static class InputReader {
		
				private final InputStream stream;
				private final byte[] buf = new byte[8192];
				private int curChar, snumChars;
				private SpaceCharFilter filter;
		
				public InputReader(InputStream stream) {
					this.stream = stream;
				}
		
				public int snext() {
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
		
				public int nextInt() {
					int c = snext();
					while (isSpaceChar(c)) {
						c = snext();
					}
					int sgn = 1;
					if (c == '-') {
						sgn = -1;
						c = snext();
					}
					int res = 0;
					do {
						if (c < '0' || c > '9')
							throw new InputMismatchException();
						res *= 10;
						res += c - '0';
						c = snext();
					} while (!isSpaceChar(c));
					return res * sgn;
				}
		
				public long nextLong() {
					int c = snext();
					while (isSpaceChar(c)) {
						c = snext();
					}
					int sgn = 1;
					if (c == '-') {
						sgn = -1;
						c = snext();
					}
					long res = 0;
					do {
						if (c < '0' || c > '9')
							throw new InputMismatchException();
						res *= 10;
						res += c - '0';
						c = snext();
					} while (!isSpaceChar(c));
					return res * sgn;
				}
		
				public int[] nextIntArray(int n) {
					int a[] = new int[n];
					for (int i = 0; i < n; i++) {
						a[i] = nextInt();
					}
					return a;
				}
		
				public String readString() {
					int c = snext();
					while (isSpaceChar(c)) {
						c = snext();
					}
					StringBuilder res = new StringBuilder();
					do {
						res.appendCodePoint(c);
						c = snext();
					} while (!isSpaceChar(c));
					return res.toString();
				}
		
				public String nextLine() {
					int c = snext();
					while (isSpaceChar(c))
						c = snext();
					StringBuilder res = new StringBuilder();
					do {
						res.appendCodePoint(c);
						c = snext();
					} while (!isEndOfLine(c));
					return res.toString();
				}
		
				public boolean isSpaceChar(int c) {
					if (filter != null)
						return filter.isSpaceChar(c);
					return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
				}
		
				private boolean isEndOfLine(int c) {
					return c == '\n' || c == '\r' || c == -1;
				}
		
				public interface SpaceCharFilter {
					public boolean isSpaceChar(int ch);
				}
			}
			
		}
		
		class Pair{
			int r;int w;
			Pair(int node,int weight){
				r=node;w=weight;
			}
		}
		class pfactor{
			long p=0,c=0;//p is prime number & c is its count
			pfactor(long p1,long c1){
				p=p1;
				c=c1;
			}
		}