/*     🅻🅴🅰🆁🅽🅸🅽🅶      */
//✅ // 
	import java.util.*;
	import java.io.*;
	import java.lang.*;
	import java.math.BigInteger;
	
	public class A {
	
	static FastReader		sc	= new FastReader();
	static PrintWriter	out	= new PrintWriter(System.out);
	
	public static void main(String[] args) throws Exception {


		int t =sc.nextInt();
		while (t-- > 0) {
			A.go();
		}
		out.flush();
	}
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Code Starts <<<<<<<<<<<<<<<<<<<<<<<<<<<<< //

	static void go() {
		int n=sc.nextInt();
		char s[]=sc.next().toCharArray();
		int pre[][]=new int[n+1][2];
		for(int i=1;i<=n;i++) {
			if(i==1) {
				if(s[i-1]=='D') {
					pre[i][0]=1;
				}else {
					pre[i][1]=1;
				}
			}else
			if(s[i-1]=='D') {
				pre[i][0]=1+pre[i-1][0];
				pre[i][1]=pre[i-1][1];
			}else {
				pre[i][1]=1+pre[i-1][1];
				pre[i][0]=pre[i-1][0];
			}
		}

		int ans[]=new int[n+1];
		for(int i=1;i*i<=n+1;i++) {
			for(int j=i;j<=n;j+=i) {
				if(pre[j-i][0]*pre[j][1]==pre[j][0]*pre[j-i][1]) {
					ans[j]=Math.max(ans[j],1+ans[j-i]);
				}else {
					ans[j]=Math.max(ans[j],1);
				}
			}

		}
		
	for(int i=1;i<ans.length;i++) {
		out.print(ans[i]+" ");
	}
	out.println();
		
	}



	
	static void   print(int  a[][]) {
//		out.println("--------");
		for(int i=0;i<a.length;i++) {
			for(int j=0;j<a[i].length;j++) {
//				out.print("("+a[i][j].y+","+a[i][j].z+") ");
				if(j<=i)
				out.print(a[i][j]+" ");
			}
			out.println();
		}
	}
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Code Ends <<<<<<<<<<<<<<<<<<<<<<<<<<<<< //
	static long fact[];
	static long invfact[];
	static long ncr(int n,int k) {
		if(k<0||k>n) {
			return 0;
		}
		long x=fact[n]%mod;
		long y=invfact[k]%mod;
		long yy=invfact[n-k]%mod;
		long ans=(x*y)%mod;
		ans=(ans*yy)%mod;
		return ans;
	}
	static long gcd(long a, long b) {
		if (b==0) {
			return a;
		}return gcd(b, a%b);
	}
	
	static int	prime[]	= new int[200005];
	static int	N				= 200005;
	
	static void sieve() {
	
		for(int i=0;i<N;i++) {
			prime[i]=i;
	}
		for (int i = 2; i * i <= N; i++) {
			if (prime[i] == i) {
				prime[i]=i;
				for (int j = i; j < N; j += i) {
					prime[j] =i;
				}}}
	}
	
	
	static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		}return gcd(b, a % b);
	}
	 static void sort(int[] a) {
		ArrayList<Integer> aa = new ArrayList<>();
		for (Integer i : a) {
			aa.add(i);	}
		Collections.sort(aa);
		for (int i = 0; i < a.length; i++)
			a[i] = aa.get(i);}
	
	 static void sort(long[] a) {
		ArrayList<Long> aa = new ArrayList<>();
		for (Long i : a) {
			aa.add(i);}
		Collections.sort(aa);
		for (int i = 0; i < a.length; i++)
			a[i] = aa.get(i);
	}
	
	static int mod = (int) 1e9 + 7;
	
	static long pow(long x, long y) {
		long res = 1l;
		while (y != 0) {
			if (y % 2 == 1) {
				res = (x%mod * res%mod)%mod;
				}	y /= 2;
				x = (x%mod * x%mod)%mod;
		}
		return res%mod;
	}
	
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Fast IO <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< //
	
	static class FastReader {
		BufferedReader	br;
		StringTokenizer	st;
		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}		}	return st.nextToken();
		}	int nextInt() {
			return Integer.parseInt(next());
		}	long nextLong() {
			return Long.parseLong(next());
		}	double nextDouble() {
			return Double.parseDouble(next());
		}
		int[] intArray(int n) {
			int a[]=new int[n];
			for(int i=0;i<n;i++)a[i]=sc.nextInt();
			return a;
		}
		long[] longArray(int n) {
			long a[]=new long[n];
			for(int i=0;i<n;i++)a[i]=sc.nextLong();
			return a;
		}
		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}	}}