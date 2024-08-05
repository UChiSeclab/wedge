// practice with kaiboy
import java.io.*;
import java.util.*;

public class CF1138A extends PrintWriter {
	CF1138A() { super(System.out, true); }
	Scanner sc = new Scanner(System.in);
	public static void main(String[] $) {
		CF1138A o = new CF1138A(); o.main(); o.flush();
	}

	void main() {
		int n = sc.nextInt();
		int[] aa = new int[n];
		for (int i = 0; i < n; i++)
			aa[i] = sc.nextInt();
		int ans = 0, k = 0;
		for (int i = 0, j; i < n; i = j) {
			j = i + 1;
			while (j < n && aa[j] == aa[i])
				j++;
			ans = Math.max(ans, Math.min(k, j - i));
			k = j - i;
		}
		println(ans * 2);
	}
}
