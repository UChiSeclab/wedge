import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
	public static int answer = 0;
	public static int n = 0;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextLine()) {
			n++;
			String l1 = sc.nextLine();
			String l2 = sc.nextLine();
			
			int num = Integer.parseInt(l1);
			
			Scanner scan = new Scanner(l2);
			
			int[] arr = new int[num];
			List<Integer> arrCnt = new ArrayList();
			int sameCnt = 1;
			int prev = 0;
			int maxCnt = 1;
			
			int idx = 0;
			while(scan.hasNextInt()) {
				int next = scan.nextInt();
				arr[idx] = next;

				if(idx == 0) prev = next;
				else {
					if(prev == next) {
						sameCnt++;
					}else {
						if(sameCnt > maxCnt) maxCnt = sameCnt;
						
						arrCnt.add(sameCnt);
						sameCnt = 1;
					}
					if(idx == num - 1) arrCnt.add(sameCnt);
				}
				prev = next;
				idx++;
			}
				
			
			int max = 1;
			for (int i = 0; i < arrCnt.size() - 1; i++) {
				int left = arrCnt.get(i);
				int right = arrCnt.get(i + 1);
				
				int min = Math.min(left, right);
				if(max < min) {
					max = min;
				}
			}
			
			answer = max;
			System.out.println(answer * 2);
		}
	}

	
}