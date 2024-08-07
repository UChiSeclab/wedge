import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String s = in.next();
		int n = in.nextInt();
		int t;
		char[] a = s.toCharArray();
		char[] tmp;
		int max = -1;
		int r;
		for (int i = 0; i < a.length; i++) {
			tmp = a.clone();
			t = n;
			for (int j = i; j < a.length; j++) {
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
		int max = -1;
		int cnt = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 'F')
				cnt++;
			else {
				max = Math.max(cnt, max);
				cnt = 0;
			}
		}

		return Math.max(max, cnt);
	}
}