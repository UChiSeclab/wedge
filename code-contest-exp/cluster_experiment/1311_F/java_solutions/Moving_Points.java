import java.util.Scanner;

public class Moving_Points {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		for (int i=0;i<n;i++)
			a[i] = scan.nextInt();
		for (int i=0;i<n;i++)
			b[i] = scan.nextInt();
		int sum = 0;
		for (int i = 0; i < n; i++) 
			for (int j = i + 1; j < n; j++)
				sum += distanceOfTwoPoints(a[i], b[i], a[j], b[j]);
		System.out.println(sum);
	}

	private static int distanceOfTwoPoints(int pos1, int v1, int pos2, int v2) {

		if (pos1 < pos2 && v1 <= v2)
			return pos2 - pos1;
		else if (pos2 < pos1 && v2 <= v1)
			return pos1 - pos2;
		else return 0;
	}

}
