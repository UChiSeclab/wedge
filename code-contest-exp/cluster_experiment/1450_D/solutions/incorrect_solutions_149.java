import java.util.*;
import java.io.*;
public class RatingCompression {
	static int findmin(int a[]) {
		int min = Integer.MAX_VALUE;
		for(int i = 0; i<a.length; i++) {
			min = Math.min(a[i], min);
		}
		return min;
	}
	static int[] kCompression(final int[] in, final int w) {
	    final int[] min_left = new int[in.length];
	    final int[] min_right = new int[in.length];

	    min_left[0] = in[0];
	    min_right[in.length - 1] = in[in.length - 1];

	    for (int i = 1; i < in.length; i++) {
	        min_left[i] = (i % w == 0) ? in[i] : Math.min(min_left[i - 1], in[i]);

	        final int j = in.length - i - 1;
	        min_right[j] = (j % w == 0) ? in[j] : Math.min(min_right[j + 1], in[j]);
	    }

	    final int[] sliding_min = new int[in.length - w + 1];
	    for (int i = 0, j = 0; i + w <= in.length; i++) {
	        sliding_min[j++] = Math.min(min_right[i], min_left[i + w - 1]);
	    }
	    return sliding_min;
	}
	
	static boolean permutation(int []arr) 
	{ 
		int n = arr.length;
	    Set<Integer> hash = new HashSet<Integer>();  
	  
	    int maxEle = 0; 
	    int minEle = Integer.MAX_VALUE;
	  
	    for (int i = 0; i < n; i++) { 
	  
	        // Insert all elements in the set 
	        hash.add(arr[i]); 
	  
	        // Calculating the max element 
	        maxEle = Math.max(maxEle, arr[i]); 
	        minEle = Math.min(minEle, arr[i]);
	    } 
	  
	    if (maxEle != n||minEle!=1) 
	        return false; 
	  
	    // Check if set size is equal to n 
	    if (hash.size() == n) 
	        return true; 
	  
	    return false; 
	}
	public static void main(String[] args)throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(bf.readLine());
		while(t-->0) {
			int n = Integer.parseInt(bf.readLine());
			String s = bf.readLine();
			String s1[] = s.split(" ");
			int a[] = new int[n];
			for(int i = 0; i<n; i++) {
				a[i] = Integer.parseInt(s1[i]);
			}
			StringBuffer result = new StringBuffer();
			if(permutation(a)) {
				result.append(1);
			}
			else result.append(0);
			for(int i = 2; i<n; i++) {
				if(permutation(kCompression(a,i))) {
					result.append(1);
				}
				else {
					result.append(0);
				}
			}
			if(findmin(a)==1) {
				result.append(1);
			}
			else result.append(0);
			System.out.println(result);
		}
	}
}