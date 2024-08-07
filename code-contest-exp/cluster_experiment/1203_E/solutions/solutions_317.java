import java.util.*;
import java.lang.*;
import java.io.*;

 // Check Examples

 // Compiler version JDK 11.0.2

 public class Dcoder
 {
	
	private static int getAnswer(int n, List<Integer> arr){
		int ans = 0;
		Collections.sort(arr);
		Map<Integer, Boolean> m = new HashMap<>();
		for (int i = 0;i < n;i++){
			int x = arr.get(i);
			int y = -1;
			if(x - 1 > 0 && m.get(x - 1) == null){
				x--;
			}
			if(m.get(x) == null || !m.get(x)){
				m.put(x, true);
				y = 0;
			}else{
				y = x - 1;
				int y2 = x + 1;
				if(m.get(y) != null && y2 < 150002 && m.get(y2) == null){
					y = y2;
				}else if(m.get(y) != null){
					y = -1;
				}
				//System.out.println(y);
			}
			if(y != -1){
				ans++;
				m.put(y, true);
			}
		}
		return ans;
	}
	
	private static void takeArrayIn(int n, Scanner sc, List<Integer> arr){
		for(int i = 0;i < n; i++){
			int x = sc.nextInt();
			arr.add(x);
		}
	}
	
	public static void main(String args[]) 
	{
		Scanner reader = new Scanner(System.in);
		int n = reader.nextInt();
		List<Integer> arr = new ArrayList<>();
		takeArrayIn(n, reader, arr);
		long result = getAnswer(n, arr);
		
		System.out.println(result);
	}
} 
