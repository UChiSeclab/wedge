//https://codeforces.com/contest/1536/problem/C
//C. Diluc and Kaeya
import java.util.*;
import java.io.*;
public class CF_1536_C{
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int t = Integer.parseInt(br.readLine());
		while(t-->0){
			int n = Integer.parseInt(br.readLine());
			int d[] = new int[n+1];
			int k[] = new int[n+1];
			String s = br.readLine();
			for(int i=1;i<=n;i++){
				d[i] = d[i-1];
				k[i] = k[i-1];
				if(s.charAt(i-1)=='D')
					d[i]++;
				else
					k[i]++;
			}

			int ans[] = new int[n+1];
			Arrays.fill(ans, 1);
			for(int i=1;i<=n;i++){
				long A = d[i];
				long B = k[i];
				for(int j=i;j<=n;j+=i){
					long C = d[j]-d[j-i];
					long D = k[j]-k[j-i];
					if(A*D==B*C)
						ans[j] = Math.max(ans[j], j/i);
					else
						break;
				}
			}

			for(int i=1;i<=n;i++)
				sb.append(ans[i]).append(" ");
			sb.append("\n");
		}

		pw.print(sb);
		pw.flush();
		pw.close();
	}
}