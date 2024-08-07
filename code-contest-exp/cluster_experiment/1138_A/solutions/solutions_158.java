import java.io.*;
import java.util.*;
public class test{
   
	public static void main(String[]args) throws Exception
	{
    	  Scanner sc=new Scanner(System.in);
    	  int tc=1;
    	 
    	 for(int t=0;t<tc;t++)
    	 {
        	int n=sc.nextInt();
        	//int k=sc.nextInt();
        	
        	int[]A=new int[n];
        	
        	for(int i=0;i<n;i++)
        	{
        	    A[i]=sc.nextInt();
        	}
        	
        	int ans=0;
        	
        	boolean turn=false;
        	int ones=0;
        	int twos=0;
        	
        	if(A[0]==1)
        	{
        	    turn = true;
        	    ones++;
        	}
        	else
        	{
        	    turn = false;
        	    twos++;
        	}
        	
        	for(int i=1;i<A.length;i++)
        	{
        	    if(turn==true)
        	    {
        	        if(A[i]==2)
        	        {
            	        ans=Math.max(ans,2*Math.min(ones,twos));
            	        twos=1;
            	        turn=false;
        	        }
        	        else
        	        {
        	            ones++;
        	        }
        	    }
        	    else
        	    {
        	        if(A[i]==2)
        	        {
        	            twos++;
        	        }
        	        else
        	        {
        	            ans=Math.max(ans,2*Math.min(ones,twos));
            	        ones=1;
            	        turn=true;
        	        }
        	    }
        	    ans=Math.max(ans,2*Math.min(ones,twos));
        	}
        	
        	System.out.println(ans);
        
    	 }
	     
	}
}