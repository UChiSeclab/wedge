import java.util.*;
public class cf{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		ArrayList<Integer> arr = new ArrayList<>();
		ArrayList<Integer> two = new ArrayList<>();

		int x = 0;
		int current = 0;
		int count = 0;
		for (int i = 0; i < n; i++) {
			x = input.nextInt();
			if (x == 1 && current == 0){
				current = 1;
			} 
			else if (current == 0)
			{
				current = 2;
			}
			if (current != x){
				two.add(count);
				count = 1;
				current = x;
			}
			else{
				count++;
			}
		}

		two.add(count);

		// for (int i = 0; i < two.size(); i++) {
		// 	System.out.println(two.get(i));			
		// }		

		int res = 0;
		int base = 0;
		int base2 = 0;
		if (two.size()/2 == 1) {
			for(int i = 0; i < two.size()/2; i++ ){
				base = Math.max(res, Math.min(two.get(i), two.get(i+1)));
			}
			System.out.println(base);
		}
		else{
			for(int i = 0; i < two.size()/2; i++ ){
				base = Math.max(res, Math.min(two.get(i), two.get(i+1)));
				base2 = Math.max(res, Math.min(two.get(i+1), two.get(i+2)));
			}
			if (base > base2) {
				System.out.println(base*2);
			}
			else{
				System.out.println(base2*2);
			}
		}
	}
}


