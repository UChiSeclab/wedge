import java.util.Scanner;


public class E {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.next();
		int T = 0;
		int L = s.length();
		for (int i = 0; i < L; i++) {
			if (s.charAt(i)=='T')
				T++;
		}
		
		int n = sc.nextInt();
		if (T <= n) {
			int k = n-T;
			if (k % 2==0)
				System.out.println(L);
			else
				System.out.println(L-1);
			return;
		}
		int[]a = new int[L+1];
		for (int i = 1; i <= L; i++) {
			if (s.charAt(i-1)=='T')
				a[i] = 1;
			a[i] += a[i-1];
		}
		int k = 0, max = 0;
		for (int i = 1; i < L; i++) {
			for (int j = i+1; j <= L; j++) {
				if (a[j]-a[i-1] <= n) {
					if (j-i+1 > max) {
						max = j-i+1;
						k = a[j]-a[i-1];
					}
				}
			}
		}
		if (k % 2==0)
			System.out.println(max);
		else
			System.out.println(max-1);
	}
}
