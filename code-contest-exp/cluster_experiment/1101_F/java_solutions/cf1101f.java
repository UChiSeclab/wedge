import java.io.*;
import java.util.*;
public class cf1101f {

	public static void main(String[] args)throws IOException {
		BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer st=new StringTokenizer(bf.readLine());
		int n=Integer.parseInt(st.nextToken());
		int m=Integer.parseInt(st.nextToken());
		st=new StringTokenizer(bf.readLine());
		int[] a=new int[n];
		for(int i=0;i<n;i++) {
			a[i]=Integer.parseInt(st.nextToken());
		}
		int[] s=new int[m];
		int[] f=new int[m];
		int[] c=new int[m];
		pair[] r=new pair[m];
		for(int i=0;i<m;i++) {
			st=new StringTokenizer(bf.readLine());
			s[i]=Integer.parseInt(st.nextToken())-1;
			f[i]=Integer.parseInt(st.nextToken())-1;
			c[i]=Integer.parseInt(st.nextToken());
			r[i]=new pair(Integer.parseInt(st.nextToken()),i);
		}
		Arrays.sort(r);
		int[][] dp=new int[n][n];
		int[][] dp2=new int[n][n];
		long v=0;
		for(int i=0;i<n;i++) {
			for(int j=i+1;j<n;j++) {
				dp[i][j]=a[j]-a[i];
			}
		}
		int cur=0;
		for(int i=0;i<=n;i++) {
			while(cur<m&&r[cur].a==i) {
				int x=r[cur].b;
				v=Math.max(v, (long)dp[s[x]][f[x]]*c[x]);
				cur++;
			}
			for(int j=0;j<n;j++) {
				int l=j;
				for(int k=j+1;k<n;k++) {
					while(l<k&&dp[j][l]<a[k]-a[l]) {
						l++;
					}
					dp2[j][k]=Math.min(dp[j][l], a[k]-a[l-1]);
				}
			}
			int[][] swap=dp;
			dp=dp2;
			dp2=swap;
		}
		out.println(v);
		out.close();
	}
	static class pair implements Comparable<pair>{
		int a;int b;
		public pair(int x, int y) {
			a=x;b=y;
		}
		public int compareTo(pair p) {
			return a-p.a;
		}
	}
}
