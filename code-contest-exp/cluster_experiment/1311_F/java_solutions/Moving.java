import java.util.Scanner;

public class Moving {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int [] x = new int [n];
		int [] v = new int [n];
		
		for (int i = 0; i < n; i++) {
			x[i] = scan.nextInt();
		}
		
		for (int i = 0; i < n; i++) {
			v[i] = scan.nextInt();
		}
		
		scan.close();
		
		int sum = 0;
		
		for (int i = 0; i < n-1; i++) {
			for (int k = i+1; k < n ; k++) {
				if (x[k] > x[i]) {
					if (v[k] - v[i] >= 0) {
						sum+=(x[k]-x[i]);
					}
				} else {
					if (v[i] - v[k] >= 0) {
						sum+=(x[i]-x[k]);
					}
				}
			}
		}
		
		System.out.print(sum);
		
		
	}

}
