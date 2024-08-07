import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class sushi {

	
	public static void main(String[] str) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int nb1 = 0;
		int nb0 = 1;
		String a = st.nextToken();
		//System.out.println("a ======"+a+"\n");

		String b = "";
		int s = 0;
		int max = 0;
		for(int i=2;i<n;i++) {
			b = st.nextToken();
			while((b.equals(a)) && (i < n)) {
				nb0++;
				b = st.nextToken();	
				i++;
				//System.out.println("nb0 ======"+nb0+"\n");
			}
			//System.out.println("nb0 = "+nb0+"\n");
			while((!b.equals(a)) && (i < n)) {
				nb1++;
				b = st.nextToken();	
				i++;
			}
			//System.out.println("nb1 = "+nb0+"\n");

			max = Math.max(max, Math.min(nb0, nb1));
					
		}
		System.out.println(2*max);
	}
}
