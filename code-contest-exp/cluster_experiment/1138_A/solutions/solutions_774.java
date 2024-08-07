import java.util.Arrays;
import java.util.Scanner;

public class dan {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int a[]=new int[n];
		int ans[]=new int[n];
		for(int i=0;i<n;i++) {
			a[i]=sc.nextInt();
		}
//		Arrays.sort(a);
		int sign=a[0];
		int count=0;
		for(int i=0;i<n;i++) {
			if(sign==a[i]) {
				ans[count]++;
			}else {
				count++;
				ans[count]=1;
				sign=a[i];
			}
		}
		int Max=0;
		for(int i=1;i<=count;i++) {
			int s=Math.min(ans[i],ans[i-1]);
			Max=Math.max(Max, s);
			
		}
		System.out.println(Max*2);
			
		
		

	}

}