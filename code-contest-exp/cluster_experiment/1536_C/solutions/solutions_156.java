import java.io.*;
import java.util.*;

public class DilucKaeya {
	public static void main(String[] args) throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int testNum = Integer.parseInt(read.readLine());

		StringBuilder ans = new StringBuilder();
		for (int t = 0; t < testNum; t++) {
			read.readLine();
			String str = read.readLine();

			int dNum = 0;
			int kNum = 0;
			HashMap<Integer, HashMap<Integer, Integer>> prevRatios = new HashMap<>();
			int[] maxPrefChunks = new int[str.length()];
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == 'D') {
					dNum++;
				} else if (str.charAt(i) == 'K') {
					kNum++;
				}
				int gcd = gcd(dNum, kNum);
				int dRatio = dNum / gcd;
				int kRatio = kNum / gcd;
				if (!prevRatios.containsKey(dRatio)) {
					prevRatios.put(dRatio, new HashMap<>());
				}
				prevRatios.get(dRatio).put(
					kRatio, prevRatios.get(dRatio).getOrDefault(kRatio, 0) + 1
				);
				maxPrefChunks[i] = prevRatios.get(dRatio).get(kRatio);
			}

			for (int i = 0; i < str.length() - 1; i++) {
				ans.append(maxPrefChunks[i]).append(' ');
			}
			ans.append(maxPrefChunks[str.length() - 1]).append('\n');
		}
		System.out.print(ans);
	}

	private static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}
}
