//package codeforces;
import java.util.Scanner;
import java.util.ArrayList;
public class ex5 {
public static void main(String[] args) {
	Scanner scan = new Scanner(System.in);
	int n = scan.nextInt();
	int[] arr = new int[n];
for (int i = 0; i < arr.length; i++) {
	arr[i]=scan.nextInt();
}
	
	ArrayList<Integer> list = new ArrayList<Integer>();
	int c1 = 0;
	int c2 = 0;
	
	for (int i = 0; i < n; i++) {
		
		if (arr[i] == 1) {
			c1++;
			if (c2 != 0) {
				list.add(c2);
				c2 = 0;
			}

		} else {
			c2++;
			if (c1 != 0) {
				list.add(c1);
				c1 = 0;
			}
		}
	}
	

	
	if (arr[n - 1] == 1) {
	list.add(c1);
	} else {
	
		list.add(c2);
	}
	

	
	int ele = arr[0];
	int ans = 0;
	
	for (int i = 1; i < list.size(); i++) {
		int temp1 = list.get(i - 1);
		
		int temp2 = list.get(i);
		int temp = Math.min(temp1, temp2);
		if(temp > ans) {
			ans = temp;
		}
	}
	System.out.println(ans*2);
}
}
