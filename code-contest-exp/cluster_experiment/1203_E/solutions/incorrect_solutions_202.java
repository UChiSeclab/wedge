
import java.io.PrintWriter;
import java.util.Scanner;

public class A {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PrintWriter wr = new PrintWriter(System.out);
		int n = sc.nextInt();
		int freq[] = new int[150001];
		for (int i = 0; i < n; i++) {
			freq[sc.nextInt()]++;
		}
		int max = 0;
		for (int i = 2; i < 150001; i++) {
			max += freq[i] > 3 ? 3 : freq[i];
		}
		max += freq[1] > 2 ? 2 : freq[1];
		wr.println(max);

		wr.flush();
		wr.close();
	}

}
