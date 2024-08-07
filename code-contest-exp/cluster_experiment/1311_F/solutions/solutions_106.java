import java.util.Arrays;
import java.util.Scanner;
import java.util.Comparator;
 
 
public class R624_F {
	private static long sum (long[] t, int pos) {
		long result = 0;
		while (pos >= 0) {
			result += t[pos];
			pos = (pos & (pos + 1)) - 1;
		}
		return result;
	}
	
	private static void upd (long[] t, int pos, int delta) {
		while (pos < t.length) {
			t[pos] += delta;
			pos = pos | (pos + 1);
		}
	}
	public static void main(String[] args) {
		Scanner in = new Scanner (System.in);
		int n = in.nextInt();
		int[][] p = new int[n][2];
		int[][] v = new int[n][2];
		
		for (int i = 0; i < n; i++)
			p[i][0] = in.nextInt();
		
		for (int i = 0; i < n; i++) {
			p[i][1] = in.nextInt();
			v[i][0] = p[i][1];
			v[i][1] = i;
		}
		in.close();
		
		Arrays.sort(v, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[0] - b[0];
			}
		});
		int idx = 0;
		int[][] ret = new int[n][2];
		
		for (int i = 0; i < n; i++) {
			if (i > 0 && v[i][0] != v[i-1][0])
				idx++;
			ret[i][0] = idx;
			ret[i][1] = v[i][1];
		}
		Arrays.sort(ret, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[1] - b[1];
			}
		});
		
		for (int i = 0; i < n; i++) {
			p[i][1] = ret[i][0];
		}
		Arrays.sort(p, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[0] - b[0];
			}
		});
				
		long[] xs = new long[n];
		long[] cnt = new long[n];
		long ans = 0;
		
		for (int i = 0; i < n; i++) {
			int pos = p[i][1];
			ans += sum(cnt, pos) * p[i][0] - sum (xs, pos);
			upd(cnt, pos, 1);
			upd(xs, pos, p[i][0]);
		}
		System.out.println(ans);
 
	}
}