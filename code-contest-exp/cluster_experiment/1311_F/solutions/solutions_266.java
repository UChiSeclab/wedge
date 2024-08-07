import java.awt.*;
import java.io.*;
import java.io.IOException;
import java.util.*;
import java.text.DecimalFormat;
 
public class Exam {
	
	public static long mod = (long)Math.pow(10, 9)+7 ;
	public static double epsilon=0.00000000008854;//value of epsilon
	public static InputReader sc = new InputReader(System.in);
	public static PrintWriter pw = new PrintWriter(System.out);
	public static long gcd(long a,long b){
		while(b>0){
			long t=b;
			b=a%b;
			a=t;
		}
		return a;
    }
	
	static void dfs(Point p[],int v,boolean vis[]){
        if (vis[v])return;
        vis[v]=true;
        for (int i=0;i<p.length;i++){
            if (p[i].x==p[v].x || p[i].y==p[v].y){
                dfs(p,i,vis);
            }
        }
	}
	public static long bSearch(Pair[]d, int l, int r, int a) {
		if(d[l].d > a)
			return 0;
		if(l == r)
			return d[l].g;
		int m = (l + r + 1) / 2;
		if(d[m].d > a)
			return bSearch(d, l, m - 1, a);
		return bSearch(d, m, r, a);
	}
	static class Pair implements Comparable<Pair> {
		int d, i;
		long g;
		public Pair(int D, long G, int I) {
			d = D;
			g = G;
			i = I;
		}
		public int compareTo(Pair p) {
			if(d == p.d)
				return i - p.i;
			return d - p.d;
		}
	 }
	 
	
	public static long pow(long x,long y,long mod){
		long ans=1;
		while(y>0){
			if((y&1)==1){
				ans=(ans*x)%mod;
			}
			y=y>>1;
			x=(x*x)%mod;
		}
		return ans;
	} 
	public static boolean isPalindrome(String s1,String s2){
		s2=reverseString(s2);
		
		if(s1.equals(s2)){
		return true;	
		}
		else 
		return false;
		
		/*
		boolean f=true;
		for(int i=0;i<s.length();i++){
			if(i>s.length()-i-1)
			break;
			if(s.charAt(i)!=s.charAt(s.length()-i-1)){
				f=false;
				break;
			}
		}
		return f;*/
	}
	
	public static HashSet<Long> query(int root,int l,int r,int st,int en,ArrayList<HashSet<Long>>s){
		if(l>=st&&r<=en){
			return s.get(root);
		}
		else if(r<st||l>en)
		return new HashSet<Long>();
		else{
			HashSet<Long> h1=query(2*root+1, l, (l+r)/2, st, en, s);
		HashSet<Long> h2=query(2*root+2, (l+r)/2+1, r, st, en, s);
		HashSet<Long> h=new HashSet<>();
		h.addAll(h1);h.addAll(h2);
		return h;
		}
	}
	 
	public static void main(String[] args) {
		// code starts..
		//int q=sc.nextInt();
		//while(q-->0){
			int n=sc.nextInt();
			//int m=sc.nextInt();
			Long a[][]=new Long[n][3];
			for(int i=0;i<n;i++){
				a[i][0]=sc.nextLong();
			}
			for(int i=0;i<n;i++){
				a[i][1]=sc.nextLong();
			}
			Arrays.sort(a,column(0));
			for(int i=0;i<n;i++){
				a[i][2]=(long)i+1;
			}
			Arrays.sort(a,column());
			Bit co=new Bit(1000000);
			Bit sum=new Bit(1000000);
			long tot=0;
			for(int i=0;i<n;i++){
				//if(i>0&&a[i][1]==a[i-1][1]){
				//	if(a[i][0]>a[i-1][0])
				//	tot-=a[i][0]-a[i-1][0];
				//}
				long count=co.query(a[i][2]);
				long s=sum.query(a[i][2]);
				tot+=(long)count*a[i][0]-s;
				co.update(a[i][2], 1);
				sum.update(a[i][2], a[i][0]);
			}
		
			pw.println(tot);
		//}
		// Code ends...
		pw.flush();
		pw.close();
	}
	static class tripletL implements Comparable<tripletL> {
		Long x, y, z;
 
		tripletL(long x, long y, long z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
 
		public int compareTo(tripletL o) {
			int result = x.compareTo(o.x);
			if (result == 0)
				result = y.compareTo(o.y);
			if (result == 0)
				result = z.compareTo(o.z);
 
			return result;
		}
 
		public boolean equlas(Object o) {
			if (o instanceof tripletL) {
				tripletL p = (tripletL) o;
				return (x - p.x == 0) && (y - p.y ==0 ) && (z - p.z == 0);
			}
			return false;
		}
 
		public String toString() {
			return x + " " + y + " " + z;
		}
 
		public int hashCode() {
			return new Long(x).hashCode() * 31 + new Long(y).hashCode() + new Long(z).hashCode();
		}
	}
	public static String Doubleformate(double a,int n){
		String s="";
		while(n-->0){
			s+='0';
		}
		DecimalFormat f =new DecimalFormat("#0."+s);
		return f.format(a);
	}
 
	public static Comparator<Long[]> column(int i){
		return 
		new Comparator<Long[]>() {
			@Override
			public int compare(Long[] o1, Long[] o2) {
				return o1[i].compareTo(o2[i]);//for ascending
				//return o2[i].compareTo(o1[i]);//for descending
			}
		};
	}
	public static Comparator<Long[]> column(){
		return 
		new Comparator<Long[]>() {
			@Override
			public int compare(Long[] o1, Long[] o2) {
				if(o1[1]!=o2[1])
				return o1[1].compareTo(o2[1]);//for ascending
				//return o2[i].compareTo(o1[i]);//for descending
				else return o1[0].compareTo(o2[0]);
			}
		};
	}
	public static Comparator<Long[]> pair(){
		return 
		new Comparator<Long[]>() {
			@Override
			public int compare(Long[] o1, Long[] o2) {
				int result=o1[0].compareTo(o2[0]);
				if(result==0)
				result=o1[1].compareTo(o2[1]);
				return result;
			}
		};
	}
	public static Comparator<Long[]> Triplet(){
		return 
		new Comparator<Long[]>() {
			@Override
			public int compare(Long[] o1, Long[] o2) {
			
					for(int i=0;i<3;i++){
						for(int j=i+1;j<3;j++){
							for(int k=0;k<3;k++){
								for(int p=k+1;p<3;p++){
									if((o1[i]==o2[k]&&o1[j]==o2[p])||(o1[j]==o2[k]&&o1[i]==o2[p])){
 
									}
								}
							}
						}
					}
					int result=o1[0].compareTo(o2[0]);
					if(result==0)
					result=o1[1].compareTo(o2[1]);
					if(result==0)
					result=o1[2].compareTo(o2[2]);
					return result;
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
		for(int i=0;i<n;i++)
		a[i]=sc.nextInt();
 
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
	static class InputReader {
 
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
class Bit{//1...n
	long a[];
	Bit(int n){
		a=new long[n+1];
	}
	void update(long f,long delta){
		int i=(int)f;
		while(i<a.length){
			a[i]+=delta;
			i+=i&(-i);
		}
	}
	long query(long f){
		int  i=(int)f;
		long sum=0;
		while(i>0){
			sum+=a[i];
			i-=i&(-i);
		}
		return sum;
	}
}
