import java.util.*;
import java.io.*;
public class boxers {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static String[] line;
	static PrintWriter pw = new PrintWriter(System.out);
	
	static int n, max = 150001 + 1;
	static Integer[] arr;
	public static void main(String[] args) throws IOException {
		n = p(br.readLine());
		arr = new Integer[n];
		l();
		for(int i = 0; i < n; ++i) arr[i] = p(line[i]);
		Arrays.sort(arr);
		
		boolean[] used = new boolean[max];
		used[0] = true;
		int out = 0;
		for(int curr : arr) {
			for(int i = -1; i <= 1; ++i) {
				if(!used[curr + i]) {
					used[curr + i] = true;
					++out;
					break;
				}
			}
		}
		
		System.out.println(out);
	}
	static void l() throws IOException {
		line = br.readLine().split(" ");
	}
	static int p(String in) {
		return Integer.parseInt(in);
	}
}