import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



/**
 * Pick questions that you love and solve them with love, try to get AC in one go with cleanest code
 * Just one thing to keep in mind these questions should be out of your reach, which make you step out of comfort zone
 * Try to pick them from different topics
 * CP is a sport, enjoy it. Don't make it pressure cooker or job
 * ****Use pen and paper************* check few examples analyze them, some not given in sample also analyze then code
 * Use pen and paper. Solve on paper then code.
 * If there is some reasoning e.g. sequence/paths, try printing first 100 elements or 100 answers using brute and observe.
 * *********Read question with extreme caution. Mistake is happening here which costs time, WA and easy problem not getting solved.*********
 * Sometimes we make question complex due to misunderstanding.
 * Prefix sum and suffix sum is highly usable concept, look for it.
 * Think of cleanest approach. If too many if else are coming then its indication of WA.
 * Solve 1-2 more questions than you solved during contest.
 */
public class Codeforces {

	static int max = (int)1e7;
	public static void main(String args[]) throws IOException {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int a[] = new int[n];
		for (int i=0;i<n;i++) {
			a[i] = in.nextInt();
		}
		
		int d0[] = get(a, 0);
		int d1[] = get(a, 1);
		for (int i=0;i<n;i++) {
			if (a[i]%2 == 0) {
				if (d0[i]>=max) System.out.print(-1 + " ");
				else System.out.print(d0[i] + " ");
			} else {
				if (d1[i]>=max) System.out.print(-1 + " ");
				else System.out.print(d1[i] + " ");
			}
		}
		in.close();
	}
	
	private static int[] get(int[] a, int par) {
		int n = a.length;
		
		int from[] = new int[2*n], to[] = new int[2*n];
		int p = 0;
		for (int i=0;i<n;i++) {
			if (a[i]%2 == par) {
				if (i+a[i]<n) {
					from[p] = i + a[i];
					to[p] = i;
					p++;
				}
				if (i-a[i]>=0) {
					from[p] = i-a[i];
					to[p] = i;
					p++;
				}
			}
		}
		
		int g[][] = pack(from, to, n);

		int d[] = new int[n];
		Arrays.fill(d, max);
		Queue<Integer> qu = new ArrayDeque<Integer>();
		for (int i=0;i<n;i++) {
			if (a[i]%2 != par) {
				qu.add(i);
				d[i] = 0;
			}
		}
		
		while (!qu.isEmpty()) {
			int u = qu.poll();
			for (int v:g[u]) {
				if (d[v]>d[u]+1) {
					d[v] = d[u]+1;
					qu.add(v);
				}
			}
		}
		return d;
	}

	private static int[][] pack(int[] from, int[] to, int n) {
		int g[][] = new int[n][];
		int cnt[] = new int[n];
		Arrays.fill(cnt, 0);
		
		for (int i=0;i<from.length;i++) cnt[from[i]]++;
		for (int i=0;i<n;i++) g[i] = new int[cnt[i]];
		
		for (int i=0;i<from.length;i++) {
			g[from[i]][--cnt[from[i]]] = to[i];
		}
		return g;
	}

	static class Pair {
		Integer x;
		Integer y;
		private Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == null || !(o instanceof Pair))
				return false;
			Pair cor = (Pair)o;
			return x.equals(cor.x) && y.equals(cor.y);
		}
		
		@Override
		public int hashCode() {
			int result = 17;
			return result;
		}
		
		static class PairComparatorX implements Comparator<Pair> {

			@Override
			public int compare(Pair o1, Pair o2) {
				return o1.x.compareTo(o2.x);
			}
			
		}

		static class PairComparatorY implements Comparator<Pair> {

			@Override
			public int compare(Pair o1, Pair o2) {
				return o1.y.compareTo(o2.y);
			}
			
		}

	}
	
	private static class Reader {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte[] buffer;
		private int bufferPointer, bytesRead;
		StringTokenizer st;
		BufferedReader br;

//		public static void main(String args[]) throws IOException {
//			Reader in = new Reader();
//			int n = in.nextInt();
//			String s[] = new String[n];
//			for (int i=0;i<n;i++) s[i] = in.next();
//			for (int i=0;i<n;i++) System.out.println(s[i]);
//			
//		}
		public Reader() {
			din = new DataInputStream(System.in);
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		public Reader(String file_name) throws IOException {
			din = new DataInputStream(new FileInputStream(file_name));
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		public String readLine() throws IOException {
			byte[] buf = new byte[64]; // line length
			int cnt = 0, c;
			while ((c = read()) != -1) {
				if (c == '\n')
					break;
				buf[cnt++] = (byte) c;
			}
			return new String(buf, 0, cnt);
		}

		public int nextInt() throws IOException {
			int ret = 0;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');

			if (neg)
				return -ret;
			return ret;
		}

		public long nextLong() throws IOException {
			long ret = 0;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}

		public double nextDouble() throws IOException {
			double ret = 0, div = 1;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();

			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');

			if (c == '.') {
				while ((c = read()) >= '0' && c <= '9') {
					ret += (c - '0') / (div *= 10);
				}
			}

			if (neg)
				return -ret;
			return ret;
		}

		private void fillBuffer() throws IOException {
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		public void close() throws IOException {
			if (din == null)
				return;
			din.close();
		}

	}

}