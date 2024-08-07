import java.util.*;
import java.io.*;
import java.lang.*;

public class CodeChef {

	public static void main (String[] args) throws java.lang.Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		int t=Integer.parseInt(br.readLine());
		while(t-->0)
		{
			int n=Integer.parseInt(br.readLine());
			String str=br.readLine();
			Map<Double,Integer>map=new HashMap();
			double d=0,k=0;
			int ans[]=new int[str.length()];
			for(int i=0;i<str.length();i++)
			{
				if(str.charAt(i)=='D')d++;
				else k++;
				if(k==0)ans[i]=(int)d;
				else
				{
					double ratio=d/k;
					//System.out.println(i+" "+ratio);
					if(map.containsKey(ratio))ans[i]=map.put(ratio,map.get(ratio)+1);
					else map.put(ratio,1);
					ans[i]=map.get(ratio);
		
				}
			}
			for(int i=0;i<n;i++)System.out.print(ans[i]+" ");
			System.out.println();
		}	
	}
	
}
