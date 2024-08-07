import java.util.*;

import java.awt.Point;
import java.io.*;
import java.math.BigInteger;
public class Solutions {
	 
	static FastScanner scr=new FastScanner();
	static PrintStream out=new PrintStream(System.out);
//	static ArrayList<ArrayList<Integer>>list=new ArrayList<ArrayList<Integer>>();
	public static void main(String []args) {
//		int t=scr.nextInt();
//		while(t-->0) {
			solve();
//		}
	}
	static int i=0;
	static int j=0;
	static void solve() {
		int n=scr.nextInt();
		int []a=scr.readArray(n);
		Arrays.sort(a);
		HashMap<Integer, Integer>hm=new HashMap<>();
		for(int i=0;i<n;i++) {
			int cn=hm.getOrDefault(a[i], 0);
			hm.put(a[i],cn+1);
		}
		int sum=0;
		HashSet<Integer>hs=new HashSet<>();
		for(Map.Entry<Integer, Integer>m:hm.entrySet()) {
			if(m.getKey()==1) {
				if(m.getValue()>1) {
					sum+=2;
					hs.add(1);
					hs.add(2);
				}else {
					sum+=m.getKey();
					hs.add(1);
				}
			}else {
				if(m.getValue()>=3) {
					if(!hs.contains(m.getKey())) {
						sum++;
						hs.add(m.getKey());
					}
					if(!hs.contains(m.getKey()-1)) {
						sum++;
						hs.add(m.getKey()-1);
					}if(!hs.contains(m.getKey()+1)) {
						sum++;
						hs.add(m.getKey()+1);
					}
				}else if(m.getValue()==2){
					if(!hs.contains(m.getKey())) {
						sum++;
						hs.add(m.getKey());
					}
					if(!hs.contains(m.getKey()-1)) {
						sum++;
						hs.add(m.getKey()-1);
					}else if(!hs.contains(m.getValue()+1)){
						sum++;
						hs.add(m.getKey()+1);
					}
				}else {
					if(hs.contains(m.getKey())==false) {
						sum++;
						hs.add(m.getKey());
					}
				}
			}
		}
		out.println(sum);
	}
	static long parse(String s) {
		return Long.parseLong(s);
	}
	static boolean count(int n) {
		if(n<2) {
			return false;
		}
		for(int i=2;i*i<=n;i++) {
			if(n%i==0) {
				return false;
			}
		}
		return true;
	}
	static boolean check(int a[],int b[],int x,int y) {
		for(int i=0;i<a.length;i++) {
			if(a[i]!=x && a[i]!=y && b[i]!=x && b[i]!=y) {
				return false;
			}
		}
		return true;
	}
	static int gcd(int a,int b) {
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
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		int[] readArray(int n) {
			int[] a=new int[n];
			for (int i=0; i<n; i++) a[i]=nextInt();
			return a;
		}
		long nextLong() {
			return Long.parseLong(next());
		}
	}
	static int MAX=Integer.MAX_VALUE;
	static int MIN=Integer.MIN_VALUE;
}class triplet{
	int x;
	int y;
	int z;
	triplet(int fisrt,int second,int third){
		this.x=fisrt;
		this.y=second;
		this.z=third;
	}
}
class pair{
	int x=0;
	int y=0;
	pair(int first,int second){
		this.x=first;
		this.y=second;
	}
}