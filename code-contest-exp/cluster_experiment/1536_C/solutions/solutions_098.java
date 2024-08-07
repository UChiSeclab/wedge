import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main{
	
	public static void main(String[] args) {
		
		Scanner s=new Scanner(System.in);
StringBuilder sb=new StringBuilder();
		int t=s.nextInt();
		for(int ie=0;ie<t;ie++) {
			int n=s.nextInt();

			char[] arr=s.next().toCharArray();
			
			
			HashMap<String,Integer> map=new HashMap<>();
			
			int a=0;
			int b=0;
			int[] ans=new int[n];
			for(int i=0;i<n;i++) {
				if(arr[i]=='D') {
					a++;
				}else {
					b++;
				}
				
				if(a==0) {
					ans[i]=b;
				}else if(b==0) {
					ans[i]=a;
				}else {
					int c=gcd(a,b);
					int d=a/c;
					int e=b/c;
					
					if(map.containsKey(d+" "+e)) {
						ans[i]=map.get(d+" "+e)+1;
						map.put(d+" "+e,map.get(d+" "+e)+1);
					}else {
						ans[i]=1;
						map.put(d+" "+e, 1);
					}
					
					
				}
				
			}
			
			
			for(int i=0;i<n;i++) {
				sb.append(ans[i]+" ");
				
			}
			sb.append("\n");
			
		}
		System.out.println(sb);
	}
	public static int gcd(int a,int b) {
		if(b==0) {
			return a;
			
		}
		
		return gcd(b,a%b);
	}
}