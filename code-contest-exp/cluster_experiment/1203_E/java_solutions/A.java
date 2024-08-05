
import java.io.PrintWriter;
import java.util.Scanner;

public class A {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PrintWriter wr = new PrintWriter(System.out);
		int n = sc.nextInt();
		int freq[] = new int[150002];
		for (int i = 0; i < n; i++) {
			freq[sc.nextInt()]++;
		}
		int max = 0;
		for (int i = 1; i < 150001; i++) {

			if (i == 1) {
				if (freq[i] == 1)
					max++;
				if (freq[i] >= 2) {
					max++;
					freq[i + 1]++;
				}
			} else {

				if (freq[i] == 1) {
					if (freq[i - 1] == 0) {
						freq[i]--;
					}
					max++;

				} else if (freq[i] == 2) {
					if (freq[i - 1] == 0) {
						freq[i]--;
						max++;
					} else {
						freq[i + 1]++;
					}
					max++;
				} else if (freq[i] >= 3) {
					if (freq[i - 1] == 0) {
						freq[i]--;
						max++;
					}
					freq[i + 1]++;
					freq[i]--;
					max++;
				}
			}

		}
		wr.println(max);

		wr.flush();
		wr.close();
	}

}
