import java.io.*;
import java.util.*;
public class Main
{
	public static void main(String[] args) throws Exception
	{
	    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	    int n=Integer.parseInt(br.readLine());
	    String s=br.readLine();
	    String ss[]=s.split(" ");
	    int arr[]=new int[n];
	    for(int i=0;i<n;i++)
	    arr[i]=Integer.parseInt(ss[i]);
		Arrays.sort(arr);
		arr[n-1]++;
		for(int i=n-2;i>0;i--)
		{
		    if((arr[i]+1)!=arr[i+1])
		    arr[i]++;
		    else if((arr[i]+1)==arr[i+1]&&arr[i]==arr[i-1])
		    continue;
		    else
		    arr[i]--;
		}
		Arrays.sort(arr);
		int c=0;
		for(int i=0;i<n;)
		{
		    int j;
	        for(j=i;j<n;j++)
	        {
	            if(arr[j]!=arr[i])
	            break;
	        }
	        i=j;
	        c++;
		}
		System.out.println(c);
	}
}
