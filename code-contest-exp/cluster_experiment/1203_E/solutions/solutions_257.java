import java.util.Scanner;
public class E_579{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] A = new int[150002];
		int counter = 0;
		int maxVal = 0;
		for (int i = 0 ; i < n ; i++)
		{
			int num = sc.nextInt();
			A[num]++;
			if (num > maxVal) maxVal = num;
			if (A[num]==1) counter++;
		}
		int res = 0;
		for (int i = 1 ; i <= maxVal ; i++)
		{
			if (A[i]==0)
				res = 1;
			if (A[i]>1 && A[i-1]==0 && i>1)
			{
				A[i-1]++;
				A[i]--;
				res = 0;
				counter++;
			}
			else if (A[i]>1 && A[i-1]==1 && res == 1 && i > 1)
			{
				A[i]--;
				counter++;
				res = 0;
			}
			if (A[i]>1){
				A[i+1]++;
				A[i]--;
				if (A[i+1] ==1) counter++;
			}
		}
		System.out.println(counter);
	}
}