import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
public class Boxers {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		HashSet<Integer> set = new HashSet<>();
		Integer[] arr = new Integer[n];
		for(int i = 0; i < n; i++) {
			arr[i] = sc.nextInt();
		}
		Collections.sort(Arrays.asList(arr));
		Integer[] changes = new Integer[n];
		boolean doAdd = true;
		int hold = -1;
		if(arr[0] == 1) {
			changes[0] = 1;
		}else {
			changes[0] = arr[0] - 1;
		}
		set.add(changes[0]);
		for(int i = 1; i < n; i++) {
			if(changes[i-1] == -1) {
				if(hold == arr[i]) {
					changes[i] = arr[i] + 1;
				}else if(arr[i-1] == arr[i]) {
					doAdd = false;
					changes[i] = -1;
				}else {
					changes[i] = arr[i] - 1;
				}
			}
			else if(arr[i]-1 > changes[i-1]) {
				changes[i] = arr[i] - 1;
			}else if(arr[i] == changes[i-1]){
				changes[i] = arr[i] + 1;
			}else if(arr[i] -1 == changes[i-1]) {
				changes[i] = arr[i];
			}
			else {
				changes[i] = -1;
				hold = changes[i-1];
				doAdd = false;
			}
			if(doAdd) {
				set.add(changes[i]);
			}
			doAdd = true;
		}
		System.out.println(set.size());
	}
	
}
