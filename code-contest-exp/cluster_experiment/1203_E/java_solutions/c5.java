import java.util.*;
import java.lang.*;
public class c5{

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		ArrayList<Integer> list = new ArrayList<>();
 
            for (int i = 0; i < n; i++) {
                list.add(sc.nextInt());
            }
 
            Collections.shuffle(list);
            Collections.sort(list);

		HashSet<Integer> set = new HashSet<>();
		


for (int no :list ) {
		
		if ( (no-1 ) > 0){
		if(!set.contains(no-1)){
			set.add(no-1);
			continue;
		}

	}
	if(!set.contains(no)){
		set.add(no);
		continue;
	}
	if(!set.contains(no+1)){
		set.add(no+1);
		continue;
	}

	
		}

		//System.out.println(set);
		System.out.println(Math.min(set.size(),n));

	}
}