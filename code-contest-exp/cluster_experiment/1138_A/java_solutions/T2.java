import java.util.Scanner;

public class T2 {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int n = 0, m = 0, t = 0, tl = 0, tr = 0, ans = 0;
		n = input.nextInt();
		for(int i = 0; i < n; i++) {
			t = input.nextInt();
			if(i == 0) {
				m = t;
			}
			if(t == m) {
				tr++;
			}
			if(t != m || i == n - 1) {
				if(t != m) {
					tl = Math.max(1, tl);
				}
				ans = Math.max(ans, Math.min(tl, tr) * 2);
				tl = tr;
				m = t;
				tr = 1;
			}
		}
		System.out.println(ans);
	}
}

		      	 			    	  	     			 	