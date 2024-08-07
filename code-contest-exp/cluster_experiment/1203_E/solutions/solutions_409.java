import java.util.Arrays;
import java.util.Scanner;


public class E {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		Integer arr[] = new Integer[n];
		for(int i=0;i<n;i++) {
			arr[i]=sc.nextInt();
		}
		Arrays.sort(arr);
		int lastElement=arr[0]>1?arr[0]-1:arr[0];
		int count = 1;
		for(int i=1;i<n;i++) {
			int temp=-1;
			for(int j=1;j>=-1;j--) {
				if(arr[i]+j>lastElement && arr[i]+j>0) {
					temp=arr[i]+j;
				}
			}
			if(temp!=-1) {
				count++;
				lastElement=temp;
			}
		}
		System.out.println(count);
		sc.close();

	}

}
