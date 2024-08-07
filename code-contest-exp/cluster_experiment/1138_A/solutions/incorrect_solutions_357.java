import java.util.Scanner;
import java.util.Arrays;
public class GFG 
{
	public static void main (String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		long n = sc.nextLong();
		byte oldT = sc.nextByte();
    	byte newT = sc.nextByte();
    	int index1 = 1;
    	int index2 = 1;
    	int oldBig = 0;
    	int newBig = 0;
    	
		for (int i = 0; i < n; i++)
		{
    		if(oldT == newT)
    		{
    		    while (newT == oldT)
    		    {
    		        oldT = newT;
    		        newT = sc.nextByte();
    		        index1++;
    		    }
    		    oldT = newT;
    		    newT = sc.nextByte();
		        while (newT == oldT)
    		    {
    		        oldT = newT;
    		        newT = sc.nextByte();
    		        index2++;
    		    }
    		    if (index1 <= index2)
    		    {
    		        newBig = index1*2;
        		    if (newBig >= oldBig)
        		    {
        		        oldBig = newBig;
        		    }
    		    }
    		    if (index2 <= index1)
    		    {
    		        newBig = index2*2;
        		    if (newBig >= oldBig)
        		    {
        		        oldBig = newBig;
        		    }
    		    }
    		}
        }
        System.out.println(newBig);
	}
}