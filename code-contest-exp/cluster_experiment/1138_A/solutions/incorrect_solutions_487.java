import java.util.*;
import java.io.*;

public class Main {

	static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	public static void main(String[] args) throws IOException{
		
		int n = Integer.valueOf(bf.readLine());
		st = new StringTokenizer(bf.readLine());
		
		int values[] = new int[n];
		values[0] = Integer.valueOf(st.nextToken());
		int max = 0, temp1 = 1, temp2 = 0, t;
		
		for(int i = 1; i < n; ++i) {
			values[i] = Integer.valueOf(st.nextToken());
			if(values[i] == values[i-1]) {
				temp1++;
			}else {
				if(temp2 != 0) {
					t = temp1+temp2;
					t = (t % 2 == 0) ? t : t-1;
					max = Math.max(t, max);
				}
				temp2 = temp1;
				temp1 = 1;
			}
		}
		t = temp1+temp2;
		t = (t % 2 == 0) ? t : t-1;
		max = Math.max(t, max);
		System.out.println(max);
		
	}

}
