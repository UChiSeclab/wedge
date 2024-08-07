
import java.lang.*;
import java.util.*;
import java.io.*;

public class pblm2 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] ar = new int[n];
		for(int i=0;i<n;i++)ar[i]=sc.nextInt();
		
		int i=0,c=1,max=2,last=ar[0],fl=0;
		
		while(i<n-1)
		{
			int c1=1;
			while(i<n-1 && ar[i]==ar[i+1]) { i++;c1++;}
			int j=i+1,c2=1;
			while(j<n-1 && ar[j]==ar[j+1]) { j++;c2++; }
			i++;
			
			max = Math.max(max ,  Math.min(c1, c2)*2);
		}
		System.out.println(max);
			
	}

}
