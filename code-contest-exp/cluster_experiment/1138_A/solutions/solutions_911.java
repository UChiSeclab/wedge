import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SushiforTwo {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		InputStreamReader read = new InputStreamReader(System.in) ;
		BufferedReader in = new BufferedReader(read) ;
		
		int n = Integer.parseInt(in.readLine()) ;
		String s = in.readLine() ;
		String ss[] = s.split("\\s+");
		int arr[] = new int[n];
		
		for(int i = 0 ; i < n ; i++)
		{
			arr[i] = Integer.parseInt(ss[i]);
		}
		ArrayList<Integer> freq = new ArrayList<>() ;
		int count = 0 ;
		count = 1;
		for(int i = 1 ; i < n ;i++)
		{
			if(i==n-1)
			{
				if(arr[i]==arr[i-1]) {
					count++;
					freq.add(count) ;
				}
				else
				{
					freq.add(count);
					freq.add(1);
				}
				continue;
			}
			if(arr[i]!=arr[i-1])
			{
				freq.add(count);
				count=0 ;
			}			
			count++;			
		}
//		System.out.println(freq);
		int max = Integer.MIN_VALUE ;
		for(int i = 1 ; i < freq.size() ; i++)
		{
			int c = 0 ;
			
			c = Math.min(freq.get(i),freq.get(i-1));
			
			max = Math.max(max, c);
			
		}
		
		System.out.println(2*max);

	}

}
