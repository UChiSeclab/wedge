import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class D769 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer tok = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(tok.nextToken());
		int k = Integer.parseInt(tok.nextToken());
		tok = new StringTokenizer(br.readLine());
		long[] a = new long[10001];
		for (int i = 0; i < n; i++) {
			a[Integer.parseInt(tok.nextToken())]++;
		}

		long pairs = 0;
		if (k==0) {
			for (int i = 0; i <= 10000; i++) {
				pairs += a[i]*(a[i]-1)/2;
			}
		} else {
			for (int i = 0; i <= 10000; i++) {
				if (a[i] > 0) {
					for (int j = i+1; j <= 10000; j++) {
						if (a[j] > 0 && Integer.bitCount(i ^ j) == k) {
							pairs += a[i]*a[j];
						}
					}
				}
			}
		}
		System.out.println(pairs);
	}
}
