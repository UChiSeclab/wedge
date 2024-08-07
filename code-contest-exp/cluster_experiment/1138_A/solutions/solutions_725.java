
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class SushiforTwo {

	private static int[] a;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner k = new Scanner(System.in);
		int x = k.nextInt();
		a = new int[x];
		for (int i = 0; i < a.length; i++) {
			a[i] = k.nextInt();
		}
		
		int counta = 0;
		int coountb = 0;
		if (a[0] == 1) {
			counta++;
		} else {
			coountb++;
		}
		int []one=new int[a.length];
		int index=0;
		for (int i = 1; i < a.length; i++) {
			if (a[i] == 1 && a[i - 1] != a[i]) {
				one[index]=coountb;
				coountb=0;
				index++;
				counta++;
			} else if (a[i] == 1 && a[i - 1] == a[i]) {
				counta++;
			} else if (a[i] == 2 && a[i - 1] != a[i]) {
				one[index]=counta;
				counta = 0;
				index++;
				coountb++;
				
			} else {
				coountb++;
			}

		}
		if (coountb>counta) {
			one[index]=coountb;
		}
		else{
			one[index]=counta;
		}
		index++;
		int max=0;
		for (int i = 1; i < index; i++) {
		max=Math.max(max, Math.min(one[i], one[i-1]));
		}
		System.out.println(max*2);
		// while (max > min) {
		// int mid = (max + min) / 2;
		// if (ok(mid)) {
		// min = mid + 1;
		// } else {
		// max = mid - 1;
		// }
		// }
		// }
		//
		// private static boolean ok(int mid) {
		// int min=0;
		// int one=0;
		// int two=0;
		// for (int i = 0; i < a.length; i++) {
		// if (a[i]==1) {
		// one++;
		// }
		// }
		// return false;
	}

}
