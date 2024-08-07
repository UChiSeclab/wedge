import java.util.Scanner;
import java.util.Arrays;
import java.lang.Math;
public class Ishu
{
	public static void main(String[] args)
	{
	Scanner scan=new Scanner(System.in);
	int n,i,a,min=0,max=0,ans=0;
	int[] arr=new int[150002];
	n=scan.nextInt();
	for(i=0;i<n;++i)
		{
		a=scan.nextInt();
		++arr[a];
		if(i==0)
			min=max=a;
		else if(a>max)
			max=a;
		else if(a<min)
			min=a;
		}
	for(i=min;i<=max+1;++i)
		{
		if(arr[i]>0)
			++ans;
		if(arr[i]==2)
			{
			if(i==1)
				{
				if(arr[i+1]==0)
					{
					++arr[i+1];
					}
				
				}
			else
				{
				if(arr[i+1]==0)
					arr[i+1]++;
				else if(arr[i-1]==0)
					++ans;
				}
			}
		else if(arr[i]>2)
			{
			if(i==1)
				{
				if(arr[i+1]==0)
					{
					++arr[i+1];
					}
				
				}
			else
				{
				if(arr[i+1]==0)
					arr[i+1]++;
				if(arr[i-1]==0)
					++ans;
				}
			}
		}
	System.out.println(ans);
	}
}