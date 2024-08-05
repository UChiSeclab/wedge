import java.io.*;
import java.util.*;

public class Template {

	static int[] count = new int[150000 +10];
	public static void main(String[] args) {
		FastScanner sc = new FastScanner();
		int yo = 1;
		while (yo-- > 0) {
			int n = sc.nextInt();
			int[] a = sc.readArray(n);
			
			for(int e : a) {
				count[e]++;
			}
			
			ruffleSort(a);
			
//			for(int e : a) {
//				System.out.print(e + " ");
//			}
//			System.out.println();

			for(int i = n-1; i >= 0; i--) {
				if(count[a[i]+1] == 0) {// inc
					count[a[i]]--;
					count[a[i]+1]++;
					a[i]++;
				}
				else if(count[a[i]] == 1) {// rem
					
				}
				else if(a[i] != 1){// dec
					count[a[i]-1]++;
					count[a[i]]--;
					a[i]--;
				}
			}
			
//			for(int e : a) {
//				System.out.print(e + " ");
//			}
//			System.out.println();
			
			Set<Integer> set = new HashSet<>();
			for(int e : a) {
				set.add(e);
			}
			System.out.println(set.size());
		}
	}

	static class Pair {
		int x;
		int y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static void ruffleSort(int[] a) {
		int n = a.length;
		Random r = new Random();
		for (int i = 0; i < a.length; i++) {
			int oi = r.nextInt(n), temp = a[i];
			a[i] = a[oi];
			a[oi] = temp;
		}
		Arrays.sort(a);
	}

	static long gcd(long a, long b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}

	static boolean[] sieve(int n) {
		boolean isPrime[] = new boolean[n + 1];
		for (int i = 2; i <= n; i++) {
			if (isPrime[i])
				continue;
			for (int j = 2 * i; j <= n; j += i) {
				isPrime[j] = true;
			}
		}
		return isPrime;
	}

	static int mod = 1000000007;

	static long pow(int a, long b) {
		if (b == 0) {
			return 1;
		}
		if (b == 1) {
			return a;
		}
		if (b % 2 == 0) {
			long ans = pow(a, b / 2);
			return ans * ans;
		} else {
			long ans = pow(a, (b - 1) / 2);
			return a * ans * ans;
		}

	}

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

		int[] readArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = nextInt();
			return a;
		}

		long nextLong() {
			return Long.parseLong(next());
		}
	}

	//	For Input.txt and Output.txt	
	//	FileInputStream in = new FileInputStream("input.txt");
	//	FileOutputStream out = new FileOutputStream("output.txt");
	//	PrintWriter pw = new PrintWriter(out);
	//	Scanner sc = new Scanner(in);
}
