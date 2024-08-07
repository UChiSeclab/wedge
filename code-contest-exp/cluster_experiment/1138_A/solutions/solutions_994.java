import java.util.*;

public class Sushi42 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int t[] = new int[N];
		t[0] = in.nextInt();
		boolean front = t[0] == 1; //true = 1, false = 2
		int num[] = {0,0}; //num[0] = 1, num[1] = 2
		num[t[0]-1] += 1;
		int max = 0;
		for(int i = 1; i < N; i++) {
			t[i] = in.nextInt();
			if((front && t[i] == 1 && num[1]>0) || (!front && t[i] == 2 && num[0]>0)) {
				max = Math.max(max,Math.min(num[0], num[1])*2);
				num[t[i]-1] = 1;
				front = !front;
			}else {
				num[t[i]-1]++;
			}
		}
		System.out.println(Math.max(max,Math.min(num[0], num[1])*2));
		in.close();
	}

}
