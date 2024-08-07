import java.util.Scanner;
 
public class Cls {
 
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt() , ans = 0;
		int[] a = new int[100001];
		int[] t = new int[100001];
		t[0] = 0;
		a[0] = 0;
		for(int i = 1 ; i <= n ; i++) {
			a[i] = sc.nextInt();
			if(a[i] == a[i - 1])
				t[i] = t[i - 1] + 1;
			
				t[i] = 1;
			ans = Math.max(ans , Math.min(t[i] , t[i - t[i]]));
			//System.out.println(t[i]);
		}
		System.out.println(2 * ans);
		
	}
 
}