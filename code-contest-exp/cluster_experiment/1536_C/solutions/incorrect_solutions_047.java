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
				
				System.out.print(gcd(d[i],k[i])+" ");
			}
			System.out.println();
	
		}
}
}