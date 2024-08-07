import java.io.*;
import java.util.*;
public class Boxers
{
	public static void main(String args[])throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int arr[] = new int[n];
		for(int i=0;i<n;i++)
			arr[i] = Integer.parseInt(st.nextToken());
		Arrays.sort(arr);
		int count=1;
		TreeSet<Integer> uniq = new TreeSet<>();
		uniq.add(arr[0]);
		uniq.add(0);
		for(int i=1;i<n;i++)
		{
			if(arr[i]==arr[i-1]||uniq.contains(arr[i]))
			{
				if(!uniq.contains(arr[i]-1))
				{
					count++;
					arr[i]--;
					uniq.add(arr[i]);
				}
				else if(!uniq.contains(arr[i]+1))
				{
					count++;
					arr[i]++;
					uniq.add(arr[i]);
				}
			}
			else{
				count++;
				uniq.add(arr[i]);
			}
		}
		System.out.println(count);
	}
}