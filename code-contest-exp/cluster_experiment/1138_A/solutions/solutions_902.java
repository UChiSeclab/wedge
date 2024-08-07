
import java.util.Scanner;

public class Sushif {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] arr = new int[n];
		for(int i = 0; i < n; i++)
			arr[i] = sc.nextInt();
		int last = arr[0];
		int cnt1 = 0;
		int cnt2 = 0; int ans = 0;
		for(int i = 0; i < n; i++) {
			if(arr[i]!=last) {
				cnt2 = cnt1;
				cnt1 = 0;
				last = arr[i];
			}
			cnt1++;
			ans = Math.max(ans, Math.min(cnt1, cnt2));
		}
		System.out.println(ans*2);
	}

}
