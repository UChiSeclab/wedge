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
		for(int i=1;i<n-1;i++)
		{
		    if(arr[i]==arr[i+1]&&arr[i-1]+1!=arr[i]&&arr[i]-1>0)
		    arr[i]--;
		    else if(arr[i]==arr[i+1])
		    arr[i]++;
		    Arrays.sort(arr);
		}
		Arrays.sort(arr);
		for(int i=1;i<n-1;i++)
		{
		    if(arr[i]==arr[i+1]&&arr[i-1]+1!=arr[i]&&arr[i]-1>0)
		    arr[i]--;
		    else if(arr[i]==arr[i+1])
		    arr[i]++;
		    Arrays.sort(arr);
		}
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
