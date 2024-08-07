import java.io.BufferedReader;
import java.io.InputStreamReader;
//import java.util.*;
public class cdf579e
{
	public static void main(String args[])throws Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		int n=Integer.parseInt(br.readLine());
		String s=br.readLine();
		String ss[]=s.split(" ");
		int arr[]=new int[15001];
		for(int j=0;j<n;j++)
		{
			int temp=Integer.parseInt(ss[j]);
			arr[temp-1]++;
		}
		for(int i=0;i<n;i++)
		{
			if(i-1>=0&&arr[i]==0&&arr[i-1]>1)
			{
				arr[i-1]--;
				arr[i]++;
			}
			if(i+1<n&&arr[i]==0&&arr[i+1]>1)
			{
				arr[i+1]--;
				arr[i]++;
			}
		}
		int coun=0;
		for(int i=0;i<n;i++)
		{
			if(arr[i]>0)
				coun++;
		}
		System.out.println(coun);
	}
}
