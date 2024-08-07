 import java.util.Arrays;
import java.math.*;
import java.lang.Long;
import java.util.Scanner;
public class ForTesting {

	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		int test=sc.nextInt();
	
			int n=sc.nextInt();
			 int arr[]=new int[n];
	          for(int i=0; i<n; i++)
	        	  arr[i]=sc.nextInt();
	          if(n==2)
	          {
	        	  System.out.println(2);
	          }
	          else {
	        	  int count1=0; int count2=0; int subseg=0;
	          
	          if(arr[0]==1) count1++;
	          else count2++;
	          int result=0;
	          for(int i=1; i<n; i++)
	          {
	        	  if(arr[i-1]!=arr[i]) subseg++;
	        	  if(subseg==2 || i==n-1) 
	        	  {
	        		  subseg=1;
	        		  result=Math.max(result, Math.min(count1, count2));
	        		 
	        		  if(arr[i]==1) count1=0;
	        		  else count2=0;
	        	  }
	        	  if(arr[i]==1) count1++;
	        	  else count2++;
	          }
				if(result==0) System.out.println(2);
				else  System.out.println(result*2);
	          }
		
  }
}