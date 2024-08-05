import java.util.*;
import java.io.*;
public class Global12D{
	public static void main(String[] main) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		PrintWriter out = new PrintWriter(System.out);
		int t = Integer.parseInt(st.nextToken());
		for(int trial = 1; trial <= t; trial++){
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int[] arr = new int[n];
			int[] freq = new int[n];
			boolean perm = true;
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i < n; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
				freq[arr[i]-1]++;
				if(freq[arr[i]-1] > 1)
					perm = false;
			}
			StringJoiner sj = new StringJoiner("");
			if(perm)
				sj.add("1");
			else
				sj.add("0");
			if(freq[0] > 1) {
				for(int i = 0; i < n-2; i++)
					sj.add("0");
				sj.add("1");
			}
			else if(freq[0] == 0) {
				for(int i = 0; i < n-1; i++)
					sj.add("0");
			}
			else {
				int p = 0;
				int l = 0, r = n-1;
				while(p < n && freq[p] == 1) {
					if(arr[l] == p+1) {
						l++;
						p++;
					}
					else if(arr[r] == p+1) {
						r--;
						p++;
					}
					else
						break;
				}
				if(p < n && freq[p] >= 1) {
					for(int i = 0; i < n-p-2; i++)
						sj.add("0");
					for(int i = 0; i < p+1; i++)
						sj.add("1");
				}
				else if(p < n && freq[p] == 0) {
					for(int i = 0; i < n-p-1; i++)
						sj.add("0");
					for(int i = 0; i < p; i++)
						sj.add("1");
				}
				else {
					for(int i = 0; i < p-1; i++)
						sj.add("1");
				}
			}
			out.println(sj);
		}
		out.close();
	}
}