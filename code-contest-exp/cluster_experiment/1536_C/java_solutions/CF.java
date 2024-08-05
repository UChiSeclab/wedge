
import java.util.*;

import java.lang.*;
import java.io.*;
import java.math.BigInteger;


public class CF {
	private static FS sc = new FS();
	
	private static class FS {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");
		String next() {
			while (!st.hasMoreTokens())
				try {
					st=new StringTokenizer(br.readLine());				               
	            } catch (IOException e) {}
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		long nextLong() {
			return Long.parseLong(next());
		}
	}
	
	private static class extra {
		
		static int[] intArr(int size) {
			int[] a = new int[size];
			for(int i = 0; i < size; i++) a[i] = sc.nextInt();
			return a;
		}
		
		static long[] longArr(int size) {
			long[] a = new long[size];
			for(int i = 0; i < size; i++) a[i] = sc.nextLong();
			return a;
		}
		
		static long intSum(int[] a) {
			long sum = 0; 
			for(int i = 0; i < a.length; i++) {
				sum += a[i];
			}
			return sum;
		}
		
		static long longSum(long[] a) {
			long sum = 0; 
			for(int i = 0; i < a.length; i++) {
				sum += a[i];
			}
			return sum;
		}
		
		static LinkedList[] graphD(int vertices, int edges) {
			LinkedList[] temp = new LinkedList[vertices+1];
			for(int i = 0; i <= vertices; i++) temp[i] = new LinkedList<>();
			for(int i = 0; i < edges; i++) {
				int x = sc.nextInt();
				int y = sc.nextInt();
				temp[x].add(y);
			}
			return temp;
		}
		
		static LinkedList[] graphUD(int vertices, int edges) {
			LinkedList[] temp = new LinkedList[vertices+1];
			for(int i = 0; i <= vertices; i++) temp[i] = new LinkedList<>();
			for(int i = 0; i < edges; i++) {
				int x = sc.nextInt();
				int y = sc.nextInt();
				temp[x].add(y);
				temp[y].add(x);
			}
			return temp;
		}
		
		static void printG(LinkedList[] temp) {
			for(LinkedList<Integer> aa:temp) System.out.println(aa);
		}
		
	}
	
	static class sieve {
		static final int MAXN = (int) (Math.pow(10, 7)+5);
		
	    static int spf[] = new int[MAXN];
	    
	    static void sieve() {
	        spf[1] = 1;
	        for (int i=2; i<MAXN; i++) spf[i] = i;
	        for (int i=4; i<MAXN; i+=2) spf[i] = 2;
	        for (int i=3; i*i<MAXN; i++) if (spf[i] == i) for (int j=i*i; j<MAXN; j+=i) if (spf[j]==j) spf[j] = i;
	    }
	      
	    static long get(int x) {
	    	ArrayList<Integer> ret = new ArrayList<>();
	        while (x != 1) {
	            ret.add(spf[x]);
	            x = x / spf[x];
	        }
	        HashMap<Integer, Integer> temp = new HashMap<>();
	        for(int aa:ret) temp.put(aa, (temp.getOrDefault(aa, 0)+1)%2);
	        long pro = 1;
	        for(int aa:temp.keySet()) pro *= Math.pow(aa, temp.get(aa));
	        return pro;
	    }
	    
	}
	
	static LinkedList[] temp;
	static int mod = (int)Math.pow(10, 9) + 7;
	
	public static void main(String[] args) {
		int t = sc.nextInt();
//		int t = 1;
		StringBuilder ret = new StringBuilder();
		while(t-- > 0) {
			int n = sc.nextInt();
			String s = sc.next();
			int d = 0, k = 0, j = 0;
			int[] ans = new int[n];
			HashMap<String, Integer> temp = new HashMap<>();
			for(int i = 0; i < n; i++) {
				if(s.charAt(i) == 'K') k++;
				else d++;
				if(d == 0 || k == 0) {
					temp.put("0", temp.getOrDefault("0", 0)+1);
					ans[j++] = temp.get("0");
					continue;
				}
				int gcd = gcd(d, k);
				String x = d/gcd + ":" + k/gcd;
				temp.put(x, temp.getOrDefault(x, 0)+1);
				ans[j++] = temp.get(x);
			}
//			System.out.println(temp);
			for(int aa:ans) ret.append(aa + " ");
			ret.append("\n");
		}
		System.out.println(ret);
	}
	
	static int gcd(int a, int b) { return b == 0 ? a:gcd(b, a%b); }
	
}

