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
		int arr[]=new int[150001];
		for(int j=0;j<n;j++)
		{
			int temp=Integer.parseInt(ss[j]);
			arr[temp-1]++;
		}
		/*for(int i=0;i<174;i++)
			System.out.print(arr[i]+" ");
		System.out.println();*/
		int priv=0;
		for(int i=0;i<150001;i++)
		{
			if(arr[i]==0)
			{
				if(priv==1)
				{
					arr[i]=1;
					priv=0;
					continue;
				}
				if(i+1<150001&&arr[i+1]>0)
				{
					arr[i]=1;
					arr[i+1]--;
					continue;
				}
			}
			else
			{
				if(arr[i]>1)
				{
					priv=1;
					arr[i]--;
				}
			}
		}
		int coun=0;
		for(int i=0;i<150001;i++)
		{
			//System.out.print(arr[i]+" ");
			if(arr[i]>0)
				coun++;
		}
		//System.out.println();
		System.out.println(coun);
	}
}
