import java.io.*;
import java.util.*;
public class Boxers {
	private static StreamTokenizer st;
	private static int nextInt() throws IOException {
		st.nextToken();
		return(int)st.nval;
	}
	private static int[] mergeSort(int[] right, int[]left){
		if(right.length>1) {
			right = mergeSort(Arrays.copyOfRange(right,0,right.length/2),
				Arrays.copyOfRange(right,right.length/2,right.length));
		}
		if(left.length>1) {
			left = mergeSort(Arrays.copyOfRange(left,0,left.length/2),
				Arrays.copyOfRange(left,left.length/2,left.length));
		}
		int sortedArray[] = new int[right.length+left.length];
		int i = 0, j = 0;
		for(int k = 0; k < sortedArray.length; k++) {
			if(i == right.length) {
				sortedArray[k] = left[j];
				j++;
				continue;
			}else if(j == left.length) {
				sortedArray[k] = right[i];
				i++;
				continue;
			}
			if(right[i] < left[j]) {
				sortedArray[k] = right[i];
				i++;
			}else {
				sortedArray[k] = left[j];
				j++;
			}
		}
		return sortedArray;
	}
	public static void main(String[] args) throws IOException{
		st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
		int N = nextInt();
		int arr[] = new int[N];
		for(int i = 0; i < N; i++)arr[i] = nextInt();
		arr = mergeSort(Arrays.copyOfRange(arr,0,arr.length/2),
				Arrays.copyOfRange(arr,arr.length/2,arr.length));
		HashSet<Integer> hs = new HashSet<>();
		int count = 0;
		for(int i = 0; i < N; i++) {
			if(arr[i]==1) {
				if(!hs.contains(arr[i])) {
					hs.add(arr[i]);
					count++;
				}else if(!hs.contains(arr[i]+1)) {
					hs.add(arr[i]+1);
					count++;
				}
			}else {
				if(!hs.contains(arr[i]-1)) {
					hs.add(arr[i]-1);
					count++;
				}else if(!hs.contains(arr[i])) {
					hs.add(arr[i]);
					count++;
				}else if(!hs.contains(arr[i]+1)) {
					hs.add(arr[i]+1);
					count++;
				}
			}
		}
		System.out.println(count);
	}

}
