import java.io.*;
import java.util.*;
public class solution1 {
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int[] arr=new int[n+10];
		for(int i=0;i<n;i++){
			arr[i]=sc.nextInt();
        }
		int[] a=new int[n+10];
		int[] b=new int[n+10];
		int cnt1=1,cnt2=1;
		int flag1=0,flag2=0;
		for(int i=0;i<n+10;i++){
			if(arr[i]==1){
				if(flag2==1){
					cnt1++;
					flag2=0;
					flag1=1;
				}
				a[cnt1]++;
				flag1=1;
			}else if(arr[i]==2){
				if(flag1==1){
					cnt2++;
					flag1=0;
					flag2=1;
					
				}
				b[cnt2]++;
			} 
		}
		int min=0;

		for(int i=0;i<n+10;i++){
			System.out.print(a[i]+" ");
		}
System.out.println();
		for(int i=0;i<n+10;i++){
			System.out.print(b[i]+" ");
		}
		for(int i=0;i<n+10;i++){
			if((a[i]!=0 || (b[i]!=0))){
				if(a[i]==0&&b[i]!=0){
					min=(min<Math.min(a[i-1],b[i])?Math.min(a[i-1],b[i]):min);
				}
				else if(b[i]==0&&a[i]!=0){
					min=(min<Math.min(a[i],b[i-1])?Math.min(a[i],b[i-1]):min);
				}
				else 
				min=min<(Math.min(a[i],b[i]))?Math.min(a[i],b[i]):min;
			}
		}
		System.out.println(min*2);
	}
}

