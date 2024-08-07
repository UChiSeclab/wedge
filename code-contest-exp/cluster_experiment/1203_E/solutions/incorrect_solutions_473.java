import java.io.*;
import java.util.*;
public class Solution2 {
	public static void main(String args[]) throws Exception {
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);
		int n = Integer.parseInt(br.readLine());
		Set<Integer> set =  new HashSet<>();
		for(String s : br.readLine().split("\\s+"))
		{
			int x = Integer.parseInt(s);
			if(set.contains(x))
			{
			    if(set.contains(x+1))
			    {
			        if(x-1>0)
			        {
			            if(!set.contains(x-1))
			            {
			                set.add(x-1);
			            }
			        }
			    }
			    else
			    {
			        set.add(x+1);
			    }
			}
			else
			{
			    set.add(x);
			}
		/*	if(set.add(x)==false)
			{
				if(set.add(x+1)==false)
				{
					if(x-1>0)
					{
						set.add(x-1);
					}
				}
			}*/
		}
		System.out.println(set.size());
	}
}