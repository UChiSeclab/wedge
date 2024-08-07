import java.util.*;
import java.io.*;
 
public class Main2 {
	static class FastReader{ BufferedReader br;StringTokenizer st;public FastReader(){br = new BufferedReader(new InputStreamReader(System.in));}String next(){while (st == null || !st.hasMoreElements()){try{st = new StringTokenizer(br.readLine());}catch (IOException  e){e.printStackTrace();}}return st.nextToken();}int nextInt(){ return Integer.parseInt(next());}long nextLong(){return Long.parseLong(next());}double nextDouble(){return Double.parseDouble(next());}String nextLine(){String str = ""; try{str = br.readLine(); } catch (IOException e) {e.printStackTrace();} return str; }}
	static long mod = (long)(1e9+7);
//	static long mod = 998244353;
//	static Scanner sc = new Scanner(System.in);
	static FastReader sc = new FastReader();
	static PrintWriter out = new PrintWriter(System.out);
	public static void main (String[] args) {
		int t = 1;
    	t = sc.nextInt();
	    z : for(int tc=1;tc<=t;tc++) {
	    	int n = sc.nextInt();
	    	char a[] = sc.next().toCharArray();
	    	int d[] = new int[n];
	    	int k[] = new int[n];
	    	if(a[0] == 'D') d[0] = 1;
	    	else k[0] = 1;
	    	for(int i=1;i<n;i++) {
	    		if(a[i] == 'D') d[i] = d[i-1] + 1;
	    		else d[i] = d[i-1];
	    		if(a[i] == 'K') k[i] = k[i-1] + 1;
	    		else k[i] = k[i-1];
	    	}
	    	int ans[] = new int[n];
	    	int div[] = new int[n];
	    	for(int i=0;i<n;i++) {
	    		int mcd = d[i], mck = k[i];
	    		int _gcd = (int) gcd(mcd,mck);
	    		for(int gcd = 1; gcd*gcd<=_gcd;gcd++) {
	    			if(_gcd%gcd!=0) continue;
		    		find(mcd,mck,ans,div,d,k,gcd,i);
		    		if(_gcd/gcd!=gcd) find(mcd,mck,ans,div,d,k,_gcd/gcd,i);
	    		}
	    	}
	    	for(int val : ans) out.write(val+" ");
	    	out.write("\n");
	    }
		out.close();
	}
	private static void find(int mcd, int mck, int[] ans, int[] div, int[] d, int[] k, int gcd, int i) {
		int cd = mcd, ck = mck;
		cd /= gcd; ck /= gcd;
		int val = cd + ck;
		int ccd = d[i] - (i-val>=0?d[i-val]:0);
		int cck = k[i] - (i-val>=0?k[i-val]:0);
//	    		System.out.println(cd+" "+ck+" "+ccd+" "+cck+" "+gcd);
		if(ccd == cd && cck == ck) {
			if(i - val < 0 && ans[i]<1) {
				ans[i] = 1;
				div[i] = i+1;
			}
			else if(div[i-val] == val && ans[i]<1+ans[i-val]) {
				ans[i] = 1 + ans[i - val];
				div[i] = val;
			}
			else if(ans[i]<1){
				ans[i] = 1;
				div[i] = i+1;
			}
		}
		else if(ans[i]<1){
			ans[i] = 1;
			div[i] = i+1;
		}
	}
	static long gcd(long a,long b) { if(b==0) return a; return gcd(b,a%b); }
	private static void sort(int[] a) {List<Integer> k = new ArrayList<>();for(int val : a) k.add(val);Collections.sort(k);for(int i=0;i<a.length;i++) a[i] = k.get(i);}
	private static void ini(List<Integer>[] tre2){for(int i=0;i<tre2.length;i++){tre2[i] = new ArrayList<>();}}
	private static void init(List<int[]>[] tre2){for(int i=0;i<tre2.length;i++){tre2[i] = new ArrayList<>();}}
	private static void sort(long[] a) {List<Long> k = new ArrayList<>();for(long val : a) k.add(val);Collections.sort(k);for(int i=0;i<a.length;i++) a[i] = k.get(i);}
}