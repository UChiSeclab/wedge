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
import java.util.TreeSet;

public final class CF_724_D2_C
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



	static long mod=1000000007;



	// Global vars
	static BufferedWriter out;
	static InputReader reader;

	static int pgcd(int a,int b){
		if (a<b)
			return pgcd(b,a);
		while (b!=0){
			int c=b;
			b=a%b;
			a=c;
		}
		return a;
	}



	static void process() throws Exception {


		out = new BufferedWriter(new OutputStreamWriter(System.out));
		reader = new InputReader(System.in);

		int T=reader.readInt();
		int CX=26;
		for (int t=0;t<T;t++) {
			int n=reader.readInt();
			String s=reader.readString();
			int[] cnt=new int[n];
			int D=0;
			int K=0;
			HashMap<String,ArrayList<Integer>> hm=new HashMap<String,ArrayList<Integer>>();
			for (int i=0;i<n;i++) {
				if (i>0)
					cnt[i]=cnt[i-1];
				if (s.charAt(i)=='D') {
					cnt[i]++;
					D++;
				} else {
					K++;
				}
				int p=pgcd(D,K);
				int d=D/p;
				int k=K/p;
				String ss=d+":"+k;
				ArrayList<Integer> lst=hm.get(ss);
				if (lst==null) {
					lst=new ArrayList<Integer>();
					hm.put(ss,lst);
				}
				lst.add(i);
				outputWln(lst.size()+" ");
				
			}
			output("");
		
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