import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Main {
	
	public static void main(String[] args) {						
	
		Scanner scan = new Scanner(System.in);
	
		int i , n = scan.nextInt();
		Set<Integer> finalSet = new HashSet<>();		
		TreeMap<Integer , Integer> map = new TreeMap<>();
		for (i = 0;i < n;i ++) {
			int value = scan.nextInt();
			map.put(value , map.getOrDefault(value , 0) + 1);			
		}
		List<Integer> list = new ArrayList<>(map.keySet());
		Collections.sort(list);		
		for (i = 0;i < list.size();i ++) {
			int value = list.get(i);			
			// move left
			if (map.get(value) > 0) {
				if (value - 1 > 0 && !finalSet.contains(value - 1)) {
					finalSet.add(value - 1);
					map.put(value , map.get(value) - 1);					
				}
			}
			// keep			
			if (map.get(value) > 0) {
				if (!finalSet.contains(value)) {
					finalSet.add(value);
					map.put(value , map.get(value) - 1);
				}				
			}
			// move right			
			if (map.get(value) > 0) {				
				if (!finalSet.contains(value + 1)) {
					finalSet.add(value + 1);
					map.put(value , map.get(value) - 1);					
				}
			}			
		}		
		System.out.println(finalSet.size());
		
	}
	
}


