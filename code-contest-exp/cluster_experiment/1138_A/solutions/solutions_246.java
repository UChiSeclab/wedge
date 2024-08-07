import java.util.Scanner;
public class sushi_for_two {
	public static void main(String[] args) {
	       Scanner sc = new Scanner(System.in);
	       int n=sc.nextInt();
	       int t[]=new int[100010];
	       int a[]=new int[100010];
	       for(int i=0;i<n;i++)
	       {
	    	   t[i]=sc.nextInt();
	       }
	       int s=t[0];
	       int count=0;
	       for(int i=0;i<n;i++)
	       {
	    	   if(s==t[i])
	    	   {
	    		   a[count]++;
	    	   }
	    	   else
	    	   {
	    		   count++;
	    		   a[count]=1;
	    		   s=t[i];
	    	   }
	       }
	       int max=0;
	       for(int i=1;i<=count;i++)
	       {
	    	   int s1=Math.min(a[i], a[i-1]);
	    	   max=Math.max(max, s1);
	       }
	       System.out.println(2*max);
	}
}
