import java.util.*;
import java.io.*;
public class A {
	public static void main(String args[]) {
		FastScanner in = new FastScanner();
		int n=in.nextInt();
		int a[]=in.nextArray(n);
		HashMap<Integer, Integer> hmap=new HashMap<>();
		ArrayList<Integer> arr=new ArrayList<>();
		hmap.put(a[0], 0);
		for(int i=0;i<n;i++) {
			if(hmap.containsKey(a[i])) {
				hmap.put(a[i], hmap.get(a[i])+1);
			}
			else {
				arr.add(hmap.get(3-a[i]));
				hmap=new HashMap<>();
				hmap.put(a[i], 1);
			}
		}
		arr.add(hmap.get(a[n-1]));
		int ans=0;
//		System.out.println(arr);
		for(int i=0;i<arr.size()-1;i++) {
			ans=Math.max(ans, Math.min(arr.get(i), arr.get(i+1)));
		}
		System.out.println(ans*2);
	}

	///////////////////////////
	static class FastScanner {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");

		String next() {
			while (!st.hasMoreTokens())
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		int[] nextArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = nextInt();
			return a;
		}

		long nextLong() {
			return Long.parseLong(next());
		}
	}
}
