import java.util.Scanner;

public class SOlution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		int[] t = new int[n];
		for(int i = 0; i < n; i++) {
			t[i] = input.nextInt();
		}
		int max = 0;
		for(int i = 0; i < n - 1; i++) {
			int nextI = i+1;
			if(t[i] != t[i+1]) {
				int maxRight = 1, maxLeft = 1;
				for(int j = i + 2; j < n ; j++) {
					if(t[i+1] == t[j])
						maxRight++;
					else {
						nextI = j-1;
						break;
					}
					
				}
				for(int j = i - 1; j >= 0; j--) {
					if(t[i] == t[j])
						maxLeft++;
					else
						break;
				}
				int currentLength = (int) Math.min(maxRight, maxLeft) * 2;
				if(currentLength > max)
					max = currentLength;
			}
			i = nextI;
		}
		System.out.println(max);

	}

}
