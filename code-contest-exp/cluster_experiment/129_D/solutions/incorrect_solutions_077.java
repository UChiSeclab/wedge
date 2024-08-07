import java.io.*;
import java.lang.Math;
import java.util.*;

public class Main
{
	public static Scanner in;
	public static PrintStream out;
	
	public static void test()
	{
		String s = in.nextLine();
		int k = in.nextInt();
		int n = s.length();
		
		int cnt[] = new int[27];
		int substr_cnt[] = new int[27];
		int pos[][] = new int[27][];
		
		
		int[] c_pos = new int[n+1];
		int c_count = 0;
		
		int i,j;
		for (i=0; i<27; i++)
		{
			cnt[i] = 0;
			pos[i] = new int[n+1];
		}
		
		int c, h;
		for (i=0; i<n; i++)
		{
			c = s.charAt(i)-'a';
			pos[c][cnt[c]++] = i;
		}
		
		int p = 0;
		
		String pref = "";
		
		while (true)
		{
			/*out.println("=====");
			out.println(k);
			out.println("=====");*/
			for (i=0; i<26; i++)
			{
				substr_cnt[i] = 0;
				for (j=0; j<cnt[i]; j++)
				{
					substr_cnt[i] += (n - pos[i][j]);
				}
				
				/*out.print(pref + (char)(i+'a') + ": ");
				out.println(substr_cnt[i]);				*/
			}
			
			i = 0;
			while ((i<26)&&(substr_cnt[i]<k))
			{
				k -= substr_cnt[i];
				i++;
			}
			
			if (i==26)
			{
				out.println("No such line.");
				return;
			}
			
			pref =  pref + (char)(i+'a');
			p++;
			
			if (k<=cnt[i])
			{
				out.println(pref);
				return;
			}
			
			k-= cnt[i];
			
			c = i;
			
			c_count = cnt[i];
			for (i=0;i<c_count;i++)
			{
				c_pos[i] = pos[c][i];
			}
			
			for (i=0; i<26;i++)
			{
				cnt[i] = 0;
			}
			
			for (j=0; j<c_count;j++)
			{
				if (c_pos[j]+p < n)
				{
					h = s.charAt(c_pos[j]+p) - 'a';
					pos[h][cnt[h]++] = c_pos[j];
				}
			}
		}
	}
       
	public static void main(String args[])
	{
		try
		{
			in = new Scanner(System.in);
			out = System.out;
			
			/*in = new Scanner(new File("in.txt"));
			out = new PrintStream(new File("out.txt"));*/
			
		}
		catch (Exception e)
		{
			return;
		}
	   
		/*int t = in.nextInt();
		for (int i=0; i<t; i++)*/
		{
			test();
		}
	}
}

