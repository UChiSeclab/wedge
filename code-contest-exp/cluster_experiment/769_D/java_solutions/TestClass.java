import java.io.*;
import java.math.*;
import java.security.KeyStore.Entry;
import java.util.*;

public class TestClass {
	private static InputStream stream;
	private static byte[] buf = new byte[1024];
	private static int curChar;
	private static int numChars;
	private static SpaceCharFilter filter;
	private static PrintWriter pw;
	
	private static void soln(){
		int max = 10001;
		boolean ans[] = new boolean[max];
		int n = nI(),k = nI();
		for(int i=0;i<10001;i++){
			int temp = i,cou = 0;
			while(temp!=0){
				temp&=(temp-1);
				cou++;
			}
			if(cou==k)
				ans[i] = true;
		}
		long arr[] = new long[max];
		while(n-->0)
			arr[nI()]++;
		if(k==0){
			long nm = 0;
			for(int i=0;i<max;i++)
				nm+=(arr[i]*(arr[i]-1))/2;
			pw.println(nm);
			return ;
		}
		long nm = 0;
		for(int i=0;i<max;i++){
			for(int j=i+1;j<max;j++){
				if((i^j)<max&&ans[i^j])
					nm+=arr[i]*arr[j];
			}
		}
		pw.println(nm);
	}
	
	public static void main(String[] args) {
		InputReader(System.in);
		pw = new PrintWriter(System.out);
		soln();
		pw.close();
	}

	// To Get Input
	// Some Buffer Methods
	public static void InputReader(InputStream stream1) {
		stream = stream1;
	}

	private static boolean isWhitespace(int c) {
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}

	private static boolean isEndOfLine(int c) {
		return c == '\n' || c == '\r' || c == -1;
	}

	private static int read() {
		if (numChars == -1)
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

	private static int nI() {
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

	private static long nL() {
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

	private static String nextToken() {
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

	private static String nLi() {
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

	private static boolean isSpaceChar(int c) {
		if (filter != null)
			return filter.isSpaceChar(c);
		return isWhitespace(c);
	}

	private interface SpaceCharFilter {
		public boolean isSpaceChar(int ch);
	}
}