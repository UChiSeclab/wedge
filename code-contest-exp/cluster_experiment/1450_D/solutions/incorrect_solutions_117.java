import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.StringTokenizer;

public class GR12D {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int t = scn.nextInt();
		while (t > 0) {
			int n = scn.nextInt();
			int[] arr = scn.readArray(n);
			out.println(func(n, arr));
			t--;
		}
		out.close();
	}

	static String func(int n, int[] arr) {
		// Used to find previous and next smaller
		Stack<Integer> s = new Stack<>();

		// Arrays to store previous and next smaller
		int left[] = new int[n + 1];
		int right[] = new int[n + 1];

		// Initialize elements of left[] and right[]
		for (int i = 0; i < n; i++) {
			left[i] = -1;
			right[i] = n;
		}

		// Fill elements of left[] using logic discussed on
		// https://www.geeksforgeeks.org/next-greater-element/
		for (int i = 0; i < n; i++) {
			while (!s.empty() && arr[s.peek()] > arr[i])
				s.pop();

			if (!s.empty())
				left[i] = s.peek();

			s.push(i);
		}

		// Empty the stack as stack is
// going to be used for right[] 
		while (!s.empty())
			s.pop();

		// Fill elements of right[] using same logic
		for (int i = n - 1; i >= 0; i--) {
			while (!s.empty() && arr[s.peek()] > arr[i])
				s.pop();

			if (!s.empty())
				right[i] = s.peek();

			s.push(i);
		}

		// Create and initialize answer array
		int ans[] = new int[n + 1];
		for (int i = 0; i <= n; i++)
			ans[i] = 0;

		int[] per = new int[n + 1];


		for (int i = 0; i < n; i++) {
			// length of the interval
			int x = arr[i];
			int len = right[i] - left[i] - 1;
			per[x] = Math.max(len, per[x]);
			// arr[i] is a possible answer for this length
			// 'len' interval, check if arr[i] is more than
			// max for 'len'
			ans[len] = Math.max(ans[len], arr[i]);
		}

		boolean flag = true;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= n; ++i) {
			int val = per[i];
			if (!flag) {
				sb.append(0);
			} else if (val == n - i + 1) {
				sb.append(1);
			} else if (val == 0) {
				flag = false;
				sb.append(0);
			} else {
				sb.append(0);
			}
		}

		sb.reverse();
		return sb.toString();

	}

	static FastScanner scn = new FastScanner();
	static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

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
}
