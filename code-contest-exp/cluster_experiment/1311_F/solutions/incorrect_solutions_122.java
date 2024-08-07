import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int SumD = 0;

		Scanner in = new Scanner(System.in);
		int n = in.nextInt();

		int[] P = new int[n];
		int[] V = new int[n];

		for (int i = 0; i < n; i++) {
			P[i] = in.nextInt();
		}

		for (int i = 0; i < n; i++) {
			V[i] = in.nextInt();
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {

				if(P[i] < P[j]){
					if(V[j] >= V[i]){
						SumD += P[j]-P[i];
					}
				}else{
					if(V[j] <= V[i]){
						SumD += P[i]-P[j];
					}
				}
			}
		}

		System.out.println(SumD);

	}
}