//This code is written by प्रविण शंखपाळ 

//package wizard;

import java.io.*;
import java.util.*;

public class Dobby {

	static int gcd(int a, int b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}

	public static void main(String[] args) {

		try {

			FastReader fr = new FastReader();
			PrintWriter pt = new PrintWriter(System.out);

			int t = fr.nextInt();

			while (t > 0) {

				int n = fr.nextInt();
				String str = fr.next();
				int d = 0, k = 0;

				ArrayList<Pair> x = new ArrayList<>();

				long dp[] = new long[n + 1];

				for (int i = 0; i < n; i++) {

					if (str.charAt(i) == 'D') {
						d++;
					} else if (str.charAt(i) == 'K') {
						k++;
					}
					if (d == 0) {
						Pair temp = new Pair(0, k);
						x.add(temp);
					} else if (k == 0) {
						Pair temp = new Pair(d, 0);
						x.add(temp);
					} else {
						int gc = gcd(d, k);
						Pair temp = new Pair((int) (d / gc), (int) (k / gc));
						x.add(temp);
					}

					if (d == 0) {
						dp[i] = k;
					} else if (k == 0) {
						dp[i] = d;
					} else {
						int gc = gcd(d, k);
//						pt.println(gc);
						boolean flag = true;
						int index = x.get(i).a + x.get(i).b;

						for (int j = i; j >= 0; j -= index) {
							if (!(x.get(j).a == x.get(i).a && x.get(j).b == x.get(i).b)) {
								flag = false;
							}
						}

						dp[i] = flag ? gc : 1;
					}

				}

				for (int i = 0; i < n; i++) {
					pt.print(dp[i] + " ");
				}
				pt.println();

				t--;
			}

			pt.close();

		} catch (

		Exception e) {
			return;
		}
	}

	static class Triple implements Comparable<Triple> {
		int a, b, c;

		Triple(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

		public int compareTo(Triple o) {
			// TODO Auto-generated method stub
			if (this.a != o.a)
				return Long.compare(this.a, o.a);
			else
				return Long.compare(this.b, o.b);
			// return 0;
		}

		public boolean equals(Object o) {
			if (o instanceof Pair) {
				Pair p = (Pair) o;
				return p.a == a && p.b == b;
			}
			return false;
		}

	}

	static class Pairx<Pairx> {

		int a;
		String b;

		Pairx(int a, String b) {
			this.a = a;
			this.b = b;
		}

	}

	static void merge(long arr[], int l, int m, int r) {

		int n1 = m - l + 1;
		int n2 = r - m;

		long L[] = new long[n1];
		long R[] = new long[n2];

		for (int i = 0; i < n1; ++i)
			L[i] = arr[l + i];
		for (int j = 0; j < n2; ++j)
			R[j] = arr[m + 1 + j];

		int i = 0, j = 0;

		int k = l;
		while (i < n1 && j < n2) {
			if (L[i] <= R[j]) {
				arr[k] = L[i];
				i++;
			} else {
				arr[k] = R[j];
				j++;
			}
			k++;
		}

		while (i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}

		while (j < n2) {
			arr[k] = R[j];
			j++;
			k++;
		}
	}

	static void sort(long arr[], int l, int r) {
		if (l < r) {

			int m = l + (r - l) / 2;

			sort(arr, l, m);
			sort(arr, m + 1, r);

			merge(arr, l, m, r);
		}
	}

	static class Pair implements Comparable<Pair> {
		int a, b;

		Pair(int a, int b) {
			this.a = a;
			this.b = b;
		}

		public int compareTo(Pair o) {
			// TODO Auto-generated method stub
			if (this.a != o.a)
				return Long.compare(this.a, o.a);
			else
				return Long.compare(this.b, o.b);
			// return 0;
		}

		public boolean equals(Object o) {
			if (o instanceof Pair) {
				Pair p = (Pair) o;
				return p.a == a && p.b == b;
			}
			return false;
		}

	}

	static int binarySearch(long arr[], int first, int last, long key) {
		int mid = (first + last) / 2;
		while (first <= last) {
			if (arr[mid] < key) {
				first = mid + 1;
			} else if (arr[mid] == key) {
				return mid;
			} else {
				last = mid - 1;
			}
			mid = (first + last) / 2;
		}
		return -1;
//		return mid;
//		return mid if want to find key greater than or less than arr[mid] if exact value is not given
	}

	static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}