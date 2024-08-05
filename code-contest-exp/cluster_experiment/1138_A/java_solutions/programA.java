import java.util.*;
import java.io.*;
import java.lang.*;

public class programA {

	public static void main(String[] args)throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int arr[] = new int[n];
		for(int i = 0; i<n;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
	}
			int curr = 0;
  			int temp[] = new int[n];
  			 for (int i = 0, j; i < n; i = j) {
  	            j = i;
  	            while (j < n && arr[i] == arr[j]) {
  	                j++;
  	            }
  	            temp[curr++] = j - i;
  	        }
  	        int ans = 0 ;
  	        for (int i = 1; i < curr; i++) {
  	            ans = Math.max(ans, Math.min(temp[i], temp[i - 1]));
  	        }
  	        System.out.println(ans * 2);
	}

}
