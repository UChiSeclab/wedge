import java.util.Scanner;

public class D {
	public static String complete(String b, int l) {
		String z = "";
		for (int i = 0; i < l - b.length(); i++) {
			z = z + '0';
		}
		return z + b;
	}
	public static boolean compare(String a, String b, int d) {
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != b.charAt(i)) {
				d--;
				if (d < 0) {
					return false;
				}
			}
		}
		return true;
	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		int[] d = new int[n];
		String[] b = new String[n];
		int c = 0;
		int max = 0;
		for (int i = 0; i < n; i++) {
			d[i] = in.nextInt();
			int bin = Integer.toBinaryString(d[i]).length();
			if (bin > max) {
				max = bin;
			}
		}
		for (int i = 0; i < n; i++) {
			b[i] = complete(Integer.toBinaryString(d[i]), max);
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n - (i + 1); j++) {
				if (compare(b[i], b[i + j + 1], k) == true) {
					c++;
				}
			}
		}
		System.out.println(c);
	}
}