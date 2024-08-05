import java.util.Scanner;

public class P769D {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		int[] bits = new int[(1 << 14) + 2];
		int size = 0;
		for (int i = 0; i < (1 << 14); i++) {
			if (Integer.bitCount(i) == k)
				bits[size++] = i;
		}
		int[] cnt = new int[10001];
		for (int i = 0; i < n; i++) {
			cnt[scan.nextInt()]++;
		}
		long ans = 0;
		if (k == 0)
		{
			for (int i = 0; i < cnt.length; i++) {
				if (cnt[i] <= 1)
					continue;
				int c = cnt[i];
				ans += (long)c*(c-1)/2;
			}
		}
		else
		{
			for (int i = 0; i < cnt.length; i++) {
				if (cnt[i] == 0)
					continue;
				for (int j = 0; j < size; j++) {
					int xor = bits[j] ^ i;
					if (xor < i)
						ans += (long)cnt[i]*cnt[xor];
				}
			}
		}
		System.out.println(ans);
	}
}
