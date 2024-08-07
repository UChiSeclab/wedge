import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class VkCupD {
	public static void main(String[] args) {
		new VkCupD().run();
	}

	Scanner in;
	PrintWriter out;

	VkCupD() {
		in = new Scanner(System.in);
		out = new PrintWriter(System.out);
	}

	void run() {
		solve();
		out.close();
	}

	void solve() {
		int n = in.nextInt();
		int k = in.nextInt();
		int count[] = new int[10001];
		Arrays.fill(count, 0);
		long answer = 0;
		for (int i = 0; i < n; i++) {
			count[in.nextInt()]++;
		}
		int bit[] = new int[1 << 15];
		for (int i = 0; i < (1 << 15); i++) {
			bit[i] = Integer.bitCount(i);
		}
		for (int i = 0; i < count.length; i++) {
			for (int j = 0; j < count.length; j++) {
				if (bit[i ^ j] == k) {
					if (i != j)
						answer += (long)count[i] * count[j];
					else
						answer += (long)count[i] * (count[j] - 1);
				}
			}
		}
		out.print(answer / 2);
	}
}
