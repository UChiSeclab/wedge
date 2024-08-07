import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String s = in.next();
		int n = in.nextInt();
		int t;
		char[] tmp;
		int max = -1;
		int r;
		for (int i = 0; i < s.length(); i++) {
			tmp = s.toCharArray();
			t = n;
			for (int j = i; j < s.length(); j++) {
				if (tmp[j] == 'T') {
					t--;
					tmp[j] = 'F';
				}
				if (t == 0)
					break;
			}
			r = getMax(tmp);
			if (t != 0) {
				if (r == tmp.length) {
					if (t % 2 != 0)
						r--;
				}
			}
			max = Math.max(max, r);
		}
		System.out.println(max);
	}

	public static int getMax(char[] a) {
		int cnt = 0;
		boolean f = true;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 'F') {
				if (f)
					cnt++;
				else
					cnt--;
			} else {
				f = !f;
			}
		}
		return cnt;
	}
}