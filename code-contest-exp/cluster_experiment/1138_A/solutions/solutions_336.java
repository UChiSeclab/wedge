import java.util.Scanner;
 
public class Main {
public static void main(String[]args){
	Scanner in=new Scanner(System.in);
	while(in.hasNext()){
		int n=in.nextInt();
		long arr[]=new long[100005];
		long b[]=new long[100005];
		for(int i=0;i<n;i++){
			arr[i]=in.nextInt();
			b[i]=1;
		}
		int j=0;
		for(int i=1;i<n;i++){
			if(arr[i]==arr[i-1]){
				b[j]++;
				
			}else{
				j++;
			}
		}
		long sum=0;
		for(int i=1;i<=j;i++){
			sum=Math.max(sum,Math.min(b[i],b[i-1]));
		}
		System.out.println(sum*2);
	}
}
}