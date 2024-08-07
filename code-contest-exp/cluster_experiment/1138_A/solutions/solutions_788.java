


import java.util.*;
import java.lang.*;
import java.lang.reflect.Array;
import java.io.*;
import java.math.*;
import java.util.HashMap;
import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.StringTokenizer; 
import java.io.DataInputStream; 
import java.io.FileInputStream; 
import java.text.DecimalFormat;
import java.util.BitSet;
public class Prac{   
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
    static PrintWriter w = new PrintWriter(System.out);
    public static void main(String[] args) throws IOException {   
        InputReader sc=new InputReader(System.in);
        
        
        int n=sc.ni();
        int arr[]=new int[n];
        int count[]=new int[n];
        for(int i=0;i<n;i++)
            arr[i]=sc.ni();
        int one=0,two=0,max=0,countone=0,counttwo=0;
        int j=0;
        for(int i=0;i<n;i++){
            if(arr[i]==1){
                one++;
                if(two>0)
                  count[j++]=two;
                two=0;
            }
            else {
                two++;
                if(one>0)
                    count[j++]=one;
                one=0;
            }
           
        }
        count[j]=Math.max(two,one);
        for(int i=1;i<n;i++)
            if(count[i]==count[i-1]&&count[i]>max)
                max=count[i];
            else if(count[i]>count[i-1]&&count[i-1]>max)
                max=count[i-1];
            else if(count[i]<count[i-1]&&count[i]>max)
                max=count[i];
        w.println(2*max);
//        int i=0,po=0,ne=0;
//        int s=(int)Math.ceil((double)n/2);
//        for(i=0;i<n;i++){
//            int a=sc.ni();
//            if(a>0)
//                po++;
//            else if(a<0)
//                ne++;
//            
//        }
//        if(po>=s)
//            w.println(1);
//        else if(ne>=s)
//            w.println(-1);
//        else
//            w.println(0);
            
        
        
        
//        int n=sc.ni();
//        int m=sc.ni();
//        String arr[]=new String[n];
//        for(int i=0;i<n;i++)
//            arr[n-i-1]=sc.nextLine();
//        int dp[][]=new int[n][m+2];
//        
//        for(int i=0;i<n;i++){
//            for(int j=0;j<m+2;j++)
//                dp[i][j]=arr[i].charAt(j)-48;
//        }
//        int last=0,ans=0;
//        int i=0,j=0;
//        boolean left=true,right=false;
//        while(i<n){
//            if(left){
//                last=0;
//                for(i=0;i<m;i++){
//                    if(dp[i][j]==1){
//                        ans=ans+j-last;
//                        last=j;
//                    }
//                }
//            }
//        }
        w.close();
    }
}
