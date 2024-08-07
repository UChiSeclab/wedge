import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

public final class CF_2020_GlobalRound12_D
{
	static boolean verb=true;
	static void log(Object X){if (verb) System.err.println(X);}
	static void log(Object[] X){if (verb) {for (Object U:X) System.err.print(U+" ");System.err.println("");}}
	static void log(int[] X){if (verb) {for (int U:X) System.err.print(U+" ");System.err.println("");}}
	static void logWln(int[] X){if (verb) {for (int U:X) System.err.print(U+" ");}}
	static void log(int[] X,int L){if (verb) {for (int i=0;i<L;i++) System.err.print(X[i]+" ");System.err.println("");}}
	static void log(long[] X){if (verb) {for (long U:X) System.err.print(U+" ");System.err.println("");}}

	static void logWln(Object X){if (verb) System.err.print(X);}
	static void info(Object o){	System.out.println(o);}
	static void output(Object o){outputWln(""+o+"\n");	}
	static void outputFlush(Object o){try {out.write(""+ o+"\n");out.flush();} catch (Exception e) {}}
	static void outputWln(Object o){try {out.write(""+ o);} catch (Exception e) {}}


	static void logBin(int[] tm) {for (int x:tm) logWln(bin(8,x)+" ");log("");}

	static String bin(int L,int x) {
		String s=Integer.toBinaryString(x);
		while (s.length()<L) s="0"+s;
		return s;
	}

	static long powerMod(long b,long e,long m){
		long x=1;
		while (e>0) {
			if (e%2==1)
				x=(b*x)%m;
			b=(b*b)%m;
			e=e/2;
		}
		return x; 
	}


	static class Composite implements Comparable<Composite>{

		int val;
		int idx;


		public int compareTo(Composite X) {
			if (val!=X.val)
				return val-X.val;
			return idx-X.idx;
		}


		public Composite(int val, int idx) {
			this.val = val;
			this.idx = idx;
		}







	}


	static int[][] dd={{0,1},{1,0},{1,1},{-1,1}};

	static String[] dnames= {"hor","vert","diag"};




	// Global vars
	static BufferedWriter out;
	static InputReader reader;

	static long MX=1000000001;

	static long mod=1000000007;

	static Scanner sc;


	static boolean ok(int[] a,int k) {
		//log("checking for k:"+k);
		int n=a.length;
		TreeSet<Integer> raoul=new TreeSet<Integer>();
		TreeSet<Integer> lst=new TreeSet<Integer>();
		for (int j=0;j<=k;j++) {
			lst.add(a[j]);
		}
		for (int i=0;i+k<n;i++) {
			if (i>0)
				lst.remove(a[i-1]);
			lst.add(a[i+k]);
			int min=lst.first();
			raoul.add(min);
		}
		if (raoul.size()==n-k && raoul.first()==0 && raoul.last()==n-k-1) 
			return true;
		return false;
		
	}

	
	static String solveBourrin(int[] a) {
		int n=a.length;
		
		char[] ans=new char[n];
		for (int k=0;k<n;k++) {
			ans[k]='0';
			TreeSet<Integer> raoul=new TreeSet<Integer>();
			for (int i=0;i+k<n;i++) {
				int min=n;
				for (int j=i;j<=i+k;j++) {
					min=Math.min(min, a[j]);
				}
				raoul.add(min);
			}
			log("k:"+k+" "+raoul);
			if (raoul.size()==n-k && raoul.first()==0 && raoul.last()==n-k-1) 
				ans[k]='1';
			
				
			
			
		}
		return new String(ans);
		
	}
	
	
	static int time;
	static int[] last;
	static int[] cnt;
	
	static String solveSubtil(int[] a) {
		int n=a.length;
		boolean single=true;
		int max=0;
		int min=n;
		time++;
		
		for (int i=0;i<n;i++) {
			int x=a[i];
		if (last[x]<time) {
				last[x]=time;
				cnt[x]=0;
			}
			cnt[x]++;
			if (cnt[x]>1)
				single=false;
			if (x>max)
				max=x;
			if (x<min)
				min=x;
		}
		// if no 0, no good
		char[] ans=new char[n];
		Arrays.fill(ans,'0');
		int target=0;
		if (last[0]==time) {
			ans[n-1]='1';
			// obvious case
			
			// do we have perm ?
			if (min==0 && max==n-1 && single==true) {
				ans[0]='1';
			} else {
				//log(min+" "+max+" "+single);
				//log("no perm");
			}
			
			int l=0;
			int r=n-1;
			int cur=0;
			
			while (l<r && cnt[cur]==1 && (a[l]==cur || a[r]==cur)) {
			
				if (a[l]==cur)
					l++;
				else
					r--;
				cur++;
			}
			//log("stopping at l:"+l+" r:"+r);
			int d=r-l+1;
			// it's ko for everything >=2 and <d
			// now is it correct for d ?
			if (ok(a,d-1))
				ans[d-1]='1';
			
			for (int e=d;e<n;e++)
				ans[e]='1';
			
			
		}

		return new String(ans);
		


	}
	
	static void test() {
		
		log("testing");
		Random r=new Random();
		int NTESTS=10000;
		int NMAX=6;
		for (int t=0;t<NTESTS;t++) {
			int n=r.nextInt(NMAX)+1;
			int[] a=new int[n];
			int[] b=new int[n];
			
			for (int i=0;i<n;i++) {
				a[i]=r.nextInt(n);
				b[i]=a[i]+1;
			}
			String good=solveBourrin(a);
			String test=solveSubtil(a);
			if (!good.contentEquals(test)) {
				log("error");
				
				log(1);
				log(n);
				
				log(b);
				log(a);
				log(good);
				log(test);
				
				return;
			}
			
		}
		
		log("testing done");
	}

	static void process() throws Exception {

		out = new BufferedWriter(new OutputStreamWriter(System.out));
		//reader = new InputReader(System.in);
		sc=new Scanner(System.in);
		log("hello");
		
	
		int CX=26;
 		time=1;

 		int NX=300000;

		last=new int[NX];
		cnt=new int[NX];

		//test();
		
		int T=sc.nextInt();
	for (int t=0;t<T;t++) {
			int n=sc.nextInt();
			time++;
			int[] a=new int[n];
			boolean single=true;
			for (int i=0;i<n;i++) {
				int x=sc.nextInt()-1;
				a[i]=x;
			}
			String s=solveSubtil(a);
			//log(solveBourrin(a));
			output(s);
		}

		try {
			out.close();
		} catch (Exception Ex) {
		}


	}




	public static void main(String[] args) throws Exception {
		process();

	}

	static final class InputReader {
		private final InputStream stream;
		private final byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public InputReader(InputStream stream) {
			this.stream = stream;
		}

		private int read() throws IOException {
			if (curChar >= numChars) {
				curChar = 0;
				numChars = stream.read(buf);
				if (numChars <= 0) {
					return -1;
				}
			}
			return buf[curChar++];
		}

		public final String readString() throws IOException {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			StringBuilder res = new StringBuilder();
			do {
				res.append((char) c);
				c = read();
			} while (!isSpaceChar(c));
			return res.toString();
		}

		public final String readString(int L) throws IOException {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			StringBuilder res = new StringBuilder(L);
			do {
				res.append((char) c);
				c = read();
			} while (!isSpaceChar(c));
			return res.toString();
		}

		public final int readInt() throws IOException {
			int c = read();
			boolean neg = false;
			while (isSpaceChar(c)) {
				c = read();
			}
			char d = (char) c;
			// log("d:"+d);
			if (d == '-') {
				neg = true;
				c = read();
			}
			int res = 0;
			do {
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			// log("res:"+res);
			if (neg)
				return -res;
			return res;

		}

		public final long readLong() throws IOException {
			int c = read();
			boolean neg = false;
			while (isSpaceChar(c)) {
				c = read();
			}
			char d = (char) c;
			// log("d:"+d);
			if (d == '-') {
				neg = true;
				c = read();
			}
			long res = 0;
			do {
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			// log("res:"+res);
			if (neg)
				return -res;
			return res;

		}

		private boolean isSpaceChar(int c) {
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
	}

}