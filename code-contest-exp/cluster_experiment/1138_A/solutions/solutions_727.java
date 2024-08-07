import java.util.*;

import java.awt.Point;
import java.io.*;
import java.math.BigInteger;
public class Solutions {
	 
static int MAX=Integer.MAX_VALUE;
		static int MIN=Integer.MIN_VALUE;
//static ArrayList<ArrayList<Integer>>list=new ArrayList<ArrayList<Integer>>();
	static FastScanner scr=new FastScanner();
	static PrintStream out=new PrintStream(System.out);
	static ArrayList<Integer>list[];
	public static void main(String []args) {
//		int t=scr.nextInt();
//		while(t-->0) {
			solve();
//		}
	}
	static void solve() {
		int n = scr.nextInt();
        int[] a = scr.readArray(n);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int cnt1 = 0, cnt2 = 0;
            int j;
            for (j = i; j <n; j++) {
                if (a[i] != a[j])
                    break;
                cnt1++;
            }
            i = j;
            for (; j < n; j++) {
                if (a[i] != a[j])
                    break;
                cnt2++;
            }
            i--;
            ans = Math.max(ans, 2 * Math.min(cnt1, cnt2));
        }
        out.println(ans);
	}
	static int gcd(int a,int b){
		if(b==0) {
			return a;
		}
		
		return gcd(b,a%b);
	}
	static long modPow(long base,long exp,long mod) {
		if(exp==0) {
			return 1;
		}
		if(exp%2==0) {
			long res=(modPow(base,exp/2,mod));
			return (res*res)%mod;
		}
		return (base*modPow(base,exp-1,mod))%mod;
	}
	static long gcd(long a,long b) {
		if(b==0) {
			return a;
		}
		return gcd(b,a%b);
	}
	static class FastScanner {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st=new StringTokenizer("");
	 	String next() {
	 		while (!st.hasMoreTokens())
	 			try {
	 				st=new StringTokenizer(br.readLine());
	 			} catch (IOException e) {
	 				e.printStackTrace();
	 			}
	 			return st.nextToken();
		}
		int[] sort(int a[]) {
			Arrays.sort(a);
			return a;
		}
		int []reverse(int a[]){
			int b[]=new int[a.length];
			int index=0;
			for(int i=a.length-1;i>=0;i--) {
				b[index]=a[i];
			}
			return b;
		}
		int nextInt() {
			return Integer.parseInt(next());
		}
		int[] readArray(int n) {
			int[] a=new int[n];
			for (int i=0; i<n; i++) a[i]=nextInt();
			return a;
		}
		long[] readLongArray(int n) {
			long  [] a=new long  [n];
			for (int i=0; i<n; i++) a[i]=nextLong();
			return a;
		}
		long nextLong() {
			return Long.parseLong(next());
		}
	}

}
