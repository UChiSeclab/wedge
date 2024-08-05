import java.util.Scanner;

public class file {
	public static int Sushi(int[]array) {
		int prev=0;
		int curr=array[0];
		int cnt1=0;
		int cnt2=0;
		int ans=0;
		for (int i=0;i<array.length;i++)
		{
			curr=array[i];
			if (array[i]==1)
			{
				if (prev==curr)
					cnt1++;
				else
					cnt1=1;
			}
			else
			{
				if (prev==curr)
					cnt2++;
				else
					cnt2=1;
			}
			if (cnt1==cnt2 && 2*cnt1>ans)
				ans=2*cnt1;
			prev=array[i];
		}
		return ans;
	}
	public static void main(String[] args) {
		Scanner s=new Scanner (System.in);
		int n=s.nextInt();
		int[]array=new int[n];
		for (int i=0;i<n;i++)
			array[i]=s.nextInt();
		s.close();
		System.out.print(Sushi(array));
	}
}
