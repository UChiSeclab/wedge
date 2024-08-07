import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class cf {
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);
			int n = input.nextInt();
			int arr[] = new int[n];
			for(int i=0;i<n;i++) {
				arr[i] = input.nextInt();
			}
			int arr_v[] = new int[n];
			for(int i=0;i<n;i++) {
				arr_v[i] = input.nextInt();
			}
			int res = 0;
			for(int i=0;i<n;i++) {
				for(int j=i+1;j<n;j++) {
//					System.out.println("x1 = "+arr[i]+" x2 = "+arr[j]+" v1 = "+arr_v[i]+" v2 = "+arr_v[j]);
//					System.out.println(mp(arr[i],arr[j],arr_v[i],arr_v[j]));
					res+=mp(arr[i],arr[j],arr_v[i],arr_v[j]);
				}
			}
			System.out.println(res);
	}
	static int mp(int x1,int x2,int v1,int v2) {
		if((x2>x1 && v2>v1) || (x1>x2 && v1>v2) || (v1==v2)) {
			return Math.abs(x2-x1);
		}
		return 0;
	}
}