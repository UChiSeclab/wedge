import java.io.*;
import java.util.*;

public class C {

	public static int gcd(int a, int b) {

		if(a == 0)
			return b;

		return gcd(b%a, a);
	}

	public static void main(String[] args) throws IOException{

		FastScanner scan = new FastScanner();
		int t = scan.nextInt();
		for(int tt = 0;tt<t;tt++) {
			int n = scan.nextInt();
			char arr[] = scan.next().toCharArray();

			HashMap<ArrayList<Integer>,Integer> map = new HashMap<>();
			StringBuilder str = new StringBuilder();
			int countD = 0, countK = 0;
			for(int i = 0;i<n;i++) {
				if(arr[i]=='D') countD++;
				if(arr[i]=='K') countK++;
				int gcd = gcd(countD, countK);
				//Pair p = new Pair(countD/gcd, countK/gcd);
				ArrayList<Integer> p = new ArrayList<>();
				p.add(countD/gcd);
				p.add(countK/gcd);
				if(!map.containsKey(p)) {
					map.put(p, 1);
				}
				else {
					map.replace(p, map.get(p)+1);
				}
				str.append(map.get(p)+" ");
			}
			System.out.println(str);
			//System.out.println(map);
		}

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
		
		double nextDouble() {
			return Double.parseDouble(next());
		}
	}
	
}