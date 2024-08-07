
import java.lang.*;
import java.util.*;
import java.io.*;

public class pblm2 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] ar = new int[n];
		for(int i=0;i<n;i++)ar[i]=sc.nextInt();
		
		int i=0,c=1,max=0,last=ar[0];
		
		while(i<n-1)
		{
			last=ar[i];
			if(last==ar[i+1])
			{
				c++;
				i++;
			}
			else
			{
				int  j=0;
				for(j=i+1;j<n;j++) {
					if(ar[j]==last)break;}
				if(i+1+c <= j)max = Math.max(c*2, max);
				//System.out.println(j+" "+c);
				i++;
				c=1;
			}
		}
		System.out.println(max);
			
	}

}
