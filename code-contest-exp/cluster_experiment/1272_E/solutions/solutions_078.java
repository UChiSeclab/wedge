import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class NearestOppositeParity {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

	static int n, a[];
	static int[] ans;
	static List<List<Integer>> g;
	
	static void bfs(List<Integer> start, List<Integer> end) {
		int[] d = new int[n];
		Arrays.fill(d, Integer.MAX_VALUE);
		
		Queue<Integer> q = new LinkedList<>();
		for (int i: start) {
			d[i] = 0;
			q.add(i);
		}
		
		while (!q.isEmpty()) {
			int v = q.poll();
			for (int u: g.get(v)) {
				if (d[u] == Integer.MAX_VALUE) {
					d[u] = d[v] + 1;
					q.add(u);
				}
			}
		}
		
		for (int i: end) {
			if (d[i] != Integer.MAX_VALUE) {
				ans[i] = d[i];
			}
		}
	}
	
	static void process() throws Exception {
		g = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			g.add(new LinkedList<>());
		}
		List<Integer> odd = new ArrayList<>();
		List<Integer> even = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			if ((a[i] & 1) == 1) {
				odd.add(i);
			} else {
				even.add(i);
			}
		}
		for (int i = 0; i < n; i++) {
			int lf = i - a[i];
			int rf = i + a[i];
			if (lf >= 0) {
				g.get(lf).add(i);
			}
			if (rf < n) {
				g.get(rf).add(i);
			}
		}
		
		ans = new int[n];
		Arrays.fill(ans, -1);
		
		bfs(odd, even);
		bfs(even, odd);
		
		for (int i: ans) {
			writer.write(String.format("%d ", i));
		}
		writer.newLine();
	}

	public static void main(String[] args) throws Exception {
		//		writer = new BufferedWriter(new FileWriter("output.txt"));
		String data[], input;
		n = Integer.parseInt(reader.readLine());
		a = Arrays.asList(reader.readLine().split(" ")).stream().mapToInt(Integer::parseInt).toArray();
		process();
		writer.close();
	}

}
