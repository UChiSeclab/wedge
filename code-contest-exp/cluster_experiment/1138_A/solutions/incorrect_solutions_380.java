import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class sushi {

	
	public static void main(String[] str) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int nb1 = 0, nb0 = 0, s = 0, max =0, r1 = 0, r0 = 0;
		String a = st.nextToken();
		String b = "";
		while(st.hasMoreTokens()) {
			b = st.nextToken();
			if(b.equals(a)) {
				nb1 = 0;
				nb0++;
				r0 = nb0;

			} else {
				nb0 = 0;
				nb1++;	
				r1 = nb1;
			}
			max = Math.max(max, Math.min(r0, r1));
		}
		System.out.println(2*max);
	}
}
