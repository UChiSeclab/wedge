
import java.util.*;



import java.io.*;
 
/* Name of the class has to be "Main" only if the class is public. */
public class final2
{
	
	public static int gcd(int a, int b)
	{
		if (b == 0)
	        return a;
	    return gcd(b, a % b);
	}
	 public static void main (String[] args) throws java.lang.Exception

    {
	    
			//Your Solve
			Scanner sc = new Scanner(System.in);

        int t=sc.nextInt();
        PrintWriter out=new PrintWriter(System.out);
        
        Set<String> st1=new HashSet<String>();
        int i,j,a=1,b=4,c,d,k,l,r;
        
        long count=0;
        int m,n;
        long sum1=1,sum=0;
        long min,max,y,z;
        long x=0;
        int pos=1;  
        String s="",s1="",s2="";
        //long y=0;
        Map<Integer,ArrayList<Integer>> hm=new HashMap<Integer,ArrayList<Integer>>();
        Set<Integer> st=new HashSet<Integer>();
        ArrayList<Integer> ar=new ArrayList<Integer>();
        //out.print("dopne");
       
        for(;t>0;t--)
        {
        	n=sc.nextInt();
        	s=sc.next();
        	int arr[]=new int[n];
        	int brr[]=new int[n];
        	a=0;
        	for(i=0;i<n;i++)
        	{
        		if(s.charAt(i)=='D')
        			a++;
        		arr[i]=a;
        	}
        	Arrays.fill(brr,1);
        	brr[0]=1;
        	if(n>=2)
        	{
        		if(arr[1]==2 || arr[1]==0)
        			brr[1]=2;
        		
        	}
        	m=0;
        	for(i=1;i<=n/2;i++) 
        	{
        		m=arr[i-1];
        		for(j=i*2-1;j<n;j+=i)
        		{
        			if(arr[j]-arr[j-i]==m)
        			{
        				brr[j]=(int) Math.max(brr[j], (j+1)/i);
        			}
        			else
        				break;
        		}
        	}
        	for(i=0;i<n;i++)
        		out.print(brr[i]+" ");
        	out.println();
        }
        	
        
        out.close();
        
    
        
		
        
        
    }
}