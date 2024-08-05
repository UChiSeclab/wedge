
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Round624F {
	
	public static class node{
		int pos;
		int neg;
		long negsum;
		long possum;
		public node(int pos, int neg, long possum, long negsum) {
			this.pos = pos;
			this.neg = neg;
			this.negsum = negsum;
			this.possum = possum;
		}
	}
	
	public static node merge(node l, node r) {
		return new node(l.pos + r.pos, l.neg + r.neg, l.possum + r.possum, l.negsum + r.negsum);
	}

	public static void Update(node[] tree, int start, int end, int index, int i, int type, long speed) {
		if(start > end || i < start || i > end) {
			return;
		}
		if(start == end) {
			tree[index] = new node(type == 1? 1:0, type == -1?1:0, type == 1 ? speed : 0, type == -1? speed : 0);
			return;
		}
		int mid = (start + end)>>1;
		Update(tree, start, mid, index<<1, i, type, speed);
		Update(tree, mid+1, end, index<<1|1, i, type, speed);
		tree[index] = merge(tree[index<<1], tree[index<<1|1]);
	}
	
	public static node Query(node[] tree, int start, int end, int index, int i, int j) {
		if(start > end || j < start || i > end) {
			return new node(0,0,0,0);
		}
		if(i <= start && j >= end) {
			return tree[index];
		}
		int mid = (start + end)>>1;
		node left = Query(tree, start, mid, index<<1, i, j);
		node right = Query(tree, mid + 1, end, index<<1|1, i , j);
		return merge(left, right);
	}
	
	public static class Pair implements Comparable<Pair>{
		int x;
		int v;
		public Pair(int x, int v) {
			this.x = x;
			this.v = v;
		}
		
		public int compareTo(Pair x) {
			return this.x - x.x;
		}
	}
	
	public static class Triple{
		int start;
		int end;
		int idx;
		public Triple(int start, int end, int idx) {
			this.start = start;
			this.end = end;
			this.idx = idx;
		}
	}
	
	public static void solve() {
		int n = s.nextInt();
		Pair[] points = new Pair[n];
		Pair[] reverse = new Pair[n];
		int[] xs = new int[n];
		for(int i = 0; i < n; i++) {
			xs[i] = s.nextInt();
		}
		int[] vs = new int[n];
		for(int i = 0; i < n; i++) {
			vs[i] = s.nextInt();
		}
		for(int i = 0; i < n; i++) {
			int x = xs[i], v = vs[i];
			points[i] = new Pair(x, v);
			reverse[i] = new Pair(Math.abs(v), Math.abs(v)==v?1:-1);
		}
		Arrays.sort(points);
		Arrays.sort(reverse);
		HashMap<Integer,Triple> helper = new HashMap<>();
		for(int i = 0; i < n; i++) {
			if(helper.containsKey(reverse[i].x)) {
				helper.get(reverse[i].x).end = i;
			}else {
				helper.put(reverse[i].x, new Triple(i, i, i));
			}
		}
		node[] tree = new node[4 * n + 1];
		Arrays.fill(tree, new node(0,0,0,0));
		long ans = 0;
		for(int i = 0; i < points.length; i++) {
			int v = points[i].v;
			int id = helper.get(Math.abs(v)).idx;
			helper.get(Math.abs(v)).idx++;
			int mid = helper.get(Math.abs(v)).end;
			node left = Query(tree, 0, n - 1, 1, 0, mid);
			node right = Query(tree, 0, n - 1, 1, mid + 1, n - 1);
			long val = points[i].x;
			if(Math.abs(v) == v) {
				ans += - left.possum + left.pos * val;
				ans += - left.negsum + left.neg * val;
				ans += - right.negsum + right.neg * val;
			}else {
				ans += - right.negsum + right.neg * val;
			}
			Update(tree, 0, n - 1, 1, id, v < 0 ? -1 : 1, val);
		}
		out.println(ans);
	}

	public static void main(String[] args) {
		out = new PrintWriter(new BufferedOutputStream(System.out));
		s = new FastReader();
		solve();
		out.close();
	}

	public static FastReader s;
	public static PrintWriter out;

	public static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
		}
	}

	
}
