import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class A {



	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(buf.readLine());

		int n  = Integer.parseInt(st.nextToken());

		int arr[] = new int[n + 1];

		st = new StringTokenizer(buf.readLine());

		for(int i = 0; i < n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		int ini = 0, fin = 0;

		int prev = 0;
		int max = 0;

		while(fin <= n) {
			if(arr[ini] != arr[fin]) {
				max = Math.max(max, Math.min(prev, fin - ini));	
				prev = fin - ini;
				ini = fin;
			}
			fin ++;
		}

		System.out.println(2*max);

	}

}
