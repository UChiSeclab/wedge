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
		int[] v = new int[n];
		
		for (int i = 0; i < n; i++)
			p[i][0] = in.nextInt();
		
		for (int i = 0; i < n; i++) {
			p[i][1] = in.nextInt();
			v[i] = p[i][1] + 100000000;
		}
		in.close();
		v = shrink(v);
		
		for (int i = 0; i < n; i++) {
			p[i][1] = v[i];
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
	
	//code copied from submission 71787218 made by uwi (great thanks to him!)
	public static long[] radixSort(long[] f){ return radixSort(f, f.length); }
	public static long[] radixSort(long[] f, int n)
	{
		long[] to = new long[n];
		{
			int[] b = new int[65537];
			for(int i = 0;i < n;i++)b[1+(int)(f[i]&0xffff)]++;
			for(int i = 1;i <= 65536;i++)b[i]+=b[i-1];
			for(int i = 0;i < n;i++)to[b[(int)(f[i]&0xffff)]++] = f[i];
			long[] d = f; f = to;to = d;
		}
		{
			int[] b = new int[65537];
			for(int i = 0;i < n;i++)b[1+(int)(f[i]>>>16&0xffff)]++;
			for(int i = 1;i <= 65536;i++)b[i]+=b[i-1];
			for(int i = 0;i < n;i++)to[b[(int)(f[i]>>>16&0xffff)]++] = f[i];
			long[] d = f; f = to;to = d;
		}
		{
			int[] b = new int[65537];
			for(int i = 0;i < n;i++)b[1+(int)(f[i]>>>32&0xffff)]++;
			for(int i = 1;i <= 65536;i++)b[i]+=b[i-1];
			for(int i = 0;i < n;i++)to[b[(int)(f[i]>>>32&0xffff)]++] = f[i];
			long[] d = f; f = to;to = d;
		}
		{
			int[] b = new int[65537];
			for(int i = 0;i < n;i++)b[1+(int)(f[i]>>>48&0xffff)]++;
			for(int i = 1;i <= 65536;i++)b[i]+=b[i-1];
			for(int i = 0;i < n;i++)to[b[(int)(f[i]>>>48&0xffff)]++] = f[i];
			long[] d = f; f = to;to = d;
		}
		return f;
	}
 
 
	
	public static int[] shrink(int[] a) {
		int n = a.length;
		long[] b = new long[n];
		for (int i = 0; i < n; i++)
			b[i] = (long) a[i] << 32 | i;
		b = radixSort(b);
		int[] ret = new int[n];
		int p = 0;
		for (int i = 0; i < n; i++) {
			if (i > 0 && (b[i] ^ b[i - 1]) >> 32 != 0)
				p++;
			ret[(int) b[i]] = p;
		}
		return ret;
	}
}
