import java.util.*;
import java.io.*;
public class Main{
	static int gcd(int a, int b)
    {
      if (b == 0)
        return a;
      return gcd(b, a % b);
    }
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		int t=sc.nextInt();
		while(t-->0) {
			int n=sc.nextInt();
			sc.nextLine();
			String s= sc.nextLine();
			int[] d= new int[n];
			int[] k = new int[n];
			for(int i=0;i<n;i++) {
				if(s.charAt(i)=='D') {
					d[i]=((i>=1)?d[i-1]:0)+1;
					k[i]=((i>=1)?k[i-1]:0);
				}
				else {
					d[i]=((i>=1)?d[i-1]:0);
					k[i]=((i>=1)?k[i-1]:0)+1;
				}
			}
//			int[] ans = new int[n];
			for(int i=0;i<n;i++) {
				int num=gcd(d[i],k[i]);
				if(num>1) {
				for(int tes=num;tes>=1;tes--) {
					if(d[i]%tes==0 && k[i]%tes==0) {
					boolean flag=false;
					int j=-1+(i+1)/tes;
					int D=d[j],K=k[j];
					while(j<=i) {
						j+=(i+1)/tes;
						if(j<=i && D*(k[j]-k[j-((i+1)/tes)])!=K*(d[j]-d[j-(i+1)/tes])) {
							flag=true;
							break;
						}
						
					}
					if(!flag) {
						System.out.print(tes+" ");
						break;
					}
				}}
			}
				else {
					System.out.print(1+" ");
				}
				}
			System.out.println();
	
		}
}
}
