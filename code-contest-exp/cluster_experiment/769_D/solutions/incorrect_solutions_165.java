import java.util.*;

public class Main {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		int k = sc.nextInt();
		
		if (k > 0) {
			
			int x = 0;
			int[] m = new int[100001];
		
			for (int i=0; i<n; i++) m[sc.nextInt()]++;
			
			for (int i=0; i<16384; i++) {
				if (Integer.bitCount(i) == k) {
					for (int j=0; j<10002; j++) {
						int t = i ^ j;
						if (t >= 0 && t < 10002) {
							x += m[t] * m[j];
						}
					}
				}
			}
			System.out.println(x/2);
		
		} else {
			
			int x = 0;
			int[] m = new int[100001];
		
			for (int i=0; i<n; i++) {
				int w = sc.nextInt();
				if (m[w] > 0) {
					x += m[w];
					m[w]++;
				} else {
					m[w] = 1;
				}
			}
			System.out.println(x);
		}
	}
}