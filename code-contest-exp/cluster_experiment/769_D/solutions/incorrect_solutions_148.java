//this won't pass

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
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = Integer.parseInt(tok.nextToken());
		}
		
		int pairs = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i+1; j < n; j++) {
				if (countBits(a[i] ^ a[j]) == k) {
					pairs++;
					System.out.println(i + " " + j);
				}
			}
		}
		System.out.println(pairs);
	}
	
	public static int countBits(int n) {
		int c = 0;
		while (n>0) {
			c += n%2;
			n /= 2;
		}
		return c;
	}
}
