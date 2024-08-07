import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeMap;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;
import java.io.InputStream;
public class Main {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		FMovingPoints solver = new FMovingPoints();
		solver.solve(1, in, out);
		out.close();
	}

	static class FMovingPoints {
		int n;
		PrintWriter out;
		InputReader in;
		final Comparator<Entity> com = new Comparator<Entity>() {
			public int compare(Entity x, Entity y) {
				int xx = Integer.compare(x.a, y.a);
				return xx;
			}
		};

		public void solve(int testNumber, InputReader in, PrintWriter out) {
			int t, i, j, tt, k;
			this.out = out;
			this.in = in;
			n = in.nextInt();
			Entity en[] = new Entity[n];
			TreeSet<Long> ts = new TreeSet<>();
			for (i = 0; i < n; i++) {
				en[i] = new Entity(in.nextInt(), 0);
			}
			for (i = 0; i < n; i++) {
				en[i].b = in.nextInt();
				ts.add(en[i].b);
			}
			Arrays.sort(en, com);
			TreeMap<Long, Integer> tm = new TreeMap<>();
			int cc = 0;
			for (Long it : ts) {
				tm.put(it, cc);
				cc++;
			}
			SegmenTree sg = new SegmenTree();
			SegmenTree count = new SegmenTree();
			sg.build(cc);
			count.build(cc);
			long ans = 0;
			long ss = 0;
			for (i = 0; i < n; i++) {
				ans += (i - count.query(tm.get(en[i].b) + 1, cc)) * en[i].a - (ss - sg.query(tm.get(en[i].b) + 1, cc));
				count.updateTreeNode(tm.get(en[i].b), 1);
				sg.updateTreeNode(tm.get(en[i].b), en[i].a);
				ss += en[i].a;
			}
			pn(ans);
		}

		void pn(long a) {
			out.println(a);
		}

		class SegmenTree {
			int n;
			long[] tree;

			void build(int x) {
				n = x;
				tree = new long[2 * n + 1];
			}

			void updateTreeNode(int p, long value) {
				tree[p + n] += value;
				p = p + n;
				for (int i = p; i > 1; i >>= 1)
					tree[i >> 1] = tree[i] + tree[i ^ 1];
			}

			long query(int l, int r) {
				long res = 0;
				for (l += n, r += n; l < r;
				     l >>= 1, r >>= 1) {
					if ((l & 1) > 0)
						res += tree[l++];

					if ((r & 1) > 0)
						res += tree[--r];
				}

				return res;
			}

		}

		class Entity {
			int a;
			long b;

			Entity(int p, int q) {
				a = p;
				b = q;
			}

		}

	}

	static class InputReader {
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public InputReader(InputStream stream) {
			this.stream = stream;
		}

		public int read() {
			if (numChars == -1) {
				throw new UnknownError();
			}
			if (curChar >= numChars) {
				curChar = 0;
				try {
					numChars = stream.read(buf);
				} catch (IOException e) {
					throw new UnknownError();
				}
				if (numChars <= 0) {
					return -1;
				}
			}
			return buf[curChar++];
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public String next() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			StringBuffer res = new StringBuffer();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
		}

		private boolean isSpaceChar(int c) {
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

	}
}

