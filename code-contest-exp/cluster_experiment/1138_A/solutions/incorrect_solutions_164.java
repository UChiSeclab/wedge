import java.io.*;
import java.util.*;
import java.math.*;

public class Solution
{
    public static void main (String[] args) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		
		int longest1 = 0;
		int longest2 = 0;
		
		int currentLongest = 0;
		
		int in;
		
		in = sc.nextInt();
		
		if (in == 1)    longest1++;
		else    longest2++;
		
		int previous = in;
		
		int max = 0;
		
		for (int i = 1; i < n; i++)
		{
		    in = sc.nextInt();
		    
		    if (in == 1)
		    {
		        if (in == previous)   
		        {
		            longest1++;
		        }
		        else
		        {
		            longest1 = 1;
		        }
		    }
		    else
		    {
		        if (in == previous)   
		        {
		            longest2++;
		        }
		        else
		        {
		            longest2 = 1;
		        }
		    }
		    previous = in;
		    max = Math.min(longest1, longest2);
		}
		
		max *= 2;
		
		System.out.println(max);
    }
}