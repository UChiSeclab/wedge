import java.io.*;
import java.lang.*;
import java.util.*;
public class Program
{
	public static void main(String[] args) {
	    Scanner scan=new Scanner(System.in);
		int n=scan.nextInt();
		int c=0;
		int c1=0;
		int max=0;
		int s=scan.nextInt();
		int[]lst=new int[n];
		int[]lst1=new int[n];
		int st=0;
		if(s==1)lst[0]=1;
		else{st=1; lst1[0]=1;}
		while(c<n-1){
		    int t=scan.nextInt();
		    if(t==s&&t==1){lst[c1]++;c++;}
		    else if(t==s&&t==2){lst1[c1]++;c++;}
		    else if(t==1){lst[++c1]++;s=t;c++;}
		    else{lst1[++c1]++;s=t;c++;}
		}
		if(st==0){
		    for(int i=0;i<n-1;i++){
    		    max=Math.max(max,Math.min(lst[i],lst1[i])*2);
		        max=Math.max(max,Math.min(lst[i+1],lst1[i])*2);
		    }
		}else{
		    for(int i=0;i<n-1;i++){
		        max=Math.max(max,Math.min(lst[i],lst1[i])*2);
		        max=Math.max(max,Math.min(lst[i],lst1[i+1])*2);
		    }
		}
		System.out.println(max);
	}
}