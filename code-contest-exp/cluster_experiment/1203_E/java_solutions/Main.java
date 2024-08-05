import java.io.BufferedInputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static void main(String args[]) {
		Scanner scanner = new Scanner(new BufferedInputStream(System.in));
		int n = scanner.nextInt();
		int[] arr = new int[n];
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = scanner.nextInt();
			if (!set.contains(arr[i])) {
				set.add(arr[i]);
			} else if (!set.contains(arr[i] - 1)) {
				set.add(arr[i] - 1);
			} else if (!set.contains(arr[i] + 1)) {
				set.add(arr[i] + 1);
			}
		}
		set.remove(0);
		scanner.close();
		System.out.println(set.size());
	}
}
