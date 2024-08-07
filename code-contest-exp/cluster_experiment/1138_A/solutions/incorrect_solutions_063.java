
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ProblemA {
	
	public static void main(String [] args){
		
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		List<Integer>nums = new ArrayList<Integer>();
		List<Integer>counts1 = new ArrayList<Integer>();
		List<Integer>counts2 = new ArrayList<Integer>();
		
		int count1 = 0, count2 = 0;
		
		for(int i =0; i < a; i++) {
			nums.add(scanner.nextInt());
		}
		
		for(int i =0; i < a; i++) {
			if(nums.get(i) == 1) {
				count1++;
			}else if(count1 > 0){
				counts1.add(count1);
				count1 = 0;
			}
		}
		
		if(count1 > 0) {
			counts1.add(count1);
		}
		
		for(int i =0; i < a; i++) {
			if(nums.get(i) == 2) {
				count2++;
			}else if(count2 > 0){
				counts2.add(count2);
				count2 = 0;
			}
		}
		
		if(count2 > 0) {
			counts2.add(count2);
		}
		
		Collections.sort(counts2);
		Collections.sort(counts1);
		
		

		int res = 0;
		
		if(counts2.get(counts2.size() - 1) >= counts1.get(counts1.size() - 1)) {
			
			for(int i = counts2.size() - 1; i >= 0; i--) {
				
				for(int j = counts1.size() - 1; j >= 0; j--) {
					if(counts2.get(i) >= counts1.get(j)) {
						
						res = Math.min(counts2.get(i), counts1.get(j)) * 2;
						break;
					}
				}
			}
			
		}else {
			
			for(int i = counts1.size() - 1; i >= 0; i--) {
			
			for(int j = counts2.size() - 1; j >= 0 ; j--) {
				if(counts1.get(i) >= counts2.get(j)) {
					
					res = Math.min(counts1.get(i), counts2.get(j)) * 2;
					break;
				}
			}
		}
			
		}
		
		

		
		System.out.println(res);
	}
}
