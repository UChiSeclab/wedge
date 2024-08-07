import java.util.*;

public class yes1 {
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		ArrayList<Integer> al = new ArrayList<>();
		for(int i=0;i<n;i++) {
			int k = input.nextInt();
			al.add(k);
		}
		Collections.sort(al, Collections.reverseOrder());
		HashSet<Integer> hm = new HashSet<>();
		int curr = Integer.MAX_VALUE;
		for(int i=0;i<al.size();i++) {
			if(al.get(i)+1<curr && !hm.contains(al.get(i)+1)) {
				hm.add(al.get(i)+1);
				curr = al.get(i)+1;
			}
			else if(al.get(i)<curr &&!hm.contains(al.get(i))) {
				hm.add(al.get(i));
				curr = al.get(i);
			}
			else if(al.get(i)-1>0 && al.get(i)-1<curr &&!hm.contains(al.get(i)-1)) {
				hm.add(al.get(i)-1);
				curr = al.get(i)-1;
			}
		}
		System.out.println(hm.size());
	}
}
