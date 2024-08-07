import java.util.*;
import java.io.*;

public class E {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		Integer[] sort = new Integer[n];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < n; i++)
			sort[i] = Integer.parseInt(st.nextToken());
		Arrays.sort(sort);
		
		int[] array = new int[n];
		for(int i = 0; i < n; i++)
			array[i] = sort[i].intValue();
		
		int count = 1, curr;
		if(array[0] == 1)
			curr = array[0];
		else
			curr = array[0] - 1;
		
		for(int i = 1; i < n; i++) {
			if(array[i] - 1 > curr) {
				count++;
				curr = array[i] - 1;
			} else if(array[i] > curr) {
				count++;
				curr = array[i];
			} else if(array[i] + 1 > curr) {
				count++;
				curr = array[i] + 1;
			}
		}
		
		System.out.println(count);
	}
}
