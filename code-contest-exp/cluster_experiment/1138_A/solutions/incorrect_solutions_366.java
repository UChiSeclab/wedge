import java.util.*;
public class SushiForTwo	{
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int count1 = 0;
		int max1 = 0;
		int count2 =0;
		int max2 = 0;
		for(int i =0;i<n;i++)
		{
			int curr = sc.nextInt();
			if(curr ==1)
			{
				count2=0;
				count1++;
				if(max1<count1)
					max1=count1;
			}
			else
			{
				count1 = 0;
				count2++;
				if(max2<count2)
					max2=count2;
			}
		}
		if(max1<max2)
			System.out.println(2*max1);
		else
			System.out.println(2*max2);
	}
}