import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int n=in.nextInt();
		int m=in.nextInt();
		int[] a=new int[n];
		int[] s=new int[m];
		int[] f=new int[m];
		int[] c=new int[m];
		int[] r=new int[m];
		for (int i=0;i<n;++i) a[i]=in.nextInt();
		for (int i=0;i<m;++i) {
			s[i]=in.nextInt();
			f[i]=in.nextInt();
			c[i]=in.nextInt();
			r[i]=in.nextInt();
			--s[i];
			--f[i];
		}
	
		int[][][] dp=new int[n][n][n];
		int inf=0x3f3f3f3f;
		for (int i=0;i<n;++i) {
			for (int j=i;j<n;++j) dp[i][j][0]=a[j]-a[i];
			for (int k=1;k<n;++k) {
				int l=i;
				for (int j=i+k+1;j<n;++j) {
					while (l+1<j&&Math.max(dp[i][l][k-1],a[j]-a[l])>Math.max(dp[i][l+1][k-1],a[j]-a[l+1]))
						++l;
					dp[i][j][k]=Math.max(dp[i][l][k-1],a[j]-a[l]);
				}
			}
		}
		long res=0;
		for (int j=0;j<m;++j) res=Math.max(res,(long)c[j]*dp[s[j]][f[j]][Math.min(r[j],f[j]-s[j]-1)]);
		System.out.println(res);
	}
}
	  	 			    	 	   	  	 	 		 			