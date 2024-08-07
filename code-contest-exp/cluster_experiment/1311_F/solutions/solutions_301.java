import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
	static class TaskAdapter implements Runnable {
		@Override
		public void run() {
			long startTime = System.currentTimeMillis();
			InputStream inputStream = System.in;
			OutputStream outputStream = System.out;
			FastReader in = new FastReader(inputStream);
			Output out = new Output(outputStream);
			FMovingPoints solver = new FMovingPoints();
			solver.solve(1, in, out);
			out.close();
			System.err.println(System.currentTimeMillis()-startTime+"ms");
		}
	}

	public static void main(String[] args) throws Exception {
		Thread thread = new Thread(null, new TaskAdapter(), "", 1<<28);
		thread.start();
		thread.join();
	}

	static class FMovingPoints {
		private final int iinf = 1_000_000_000;

		public FMovingPoints() {
		}

		public static Pair<Long, Long> add(Pair<Long, Long> a, Pair<Long, Long> b) {
			return new Pair<>(a.a+b.a, a.b+b.b);
		}

		public static Pair<Long, Long> subtract(Pair<Long, Long> a, Pair<Long, Long> b) {
			return new Pair<>(a.a-b.a, a.b-b.b);
		}

		public void solve(int kase, InputReader in, Output pw) {
			int n = in.nextInt();
			int[][] arr = new int[n][2];
			for(int i = 0; i<2; i++) {
				for(int j = 0; j<n; j++) {
					arr[j][i] = in.nextInt();
				}
			}
			Arrays.sort(arr, Comparator.comparingInt(o -> o[1]));
			{
				int prev = -iinf, ind = -1;
				for(int i = 0; i<n; i++) {
					if(arr[i][1]!=prev) {
						prev = arr[i][1];
						arr[i][1] = ++ind;
					}else {
						arr[i][1] = ind;
					}
				}
			}
			Arrays.sort(arr, Comparator.comparingInt(o -> o[0]));
			TFenwickTree<Pair<Long, Long>> ft;
			{
				Pair<Long, Long>[] tmp = new Pair[n];
				for(int i = 0; i<n; i++) {
					tmp[i] = new Pair<>(0L, 0L);
				}
				ft = new TFenwickTree<>(tmp, new Pair<>(0L, 0L), FMovingPoints::add, FMovingPoints::subtract);
			}
			for(int i = 0; i<n; i++) {
				ft.set(arr[i][1], add(ft.get(arr[i][1]), new Pair<>((long) arr[i][0]-arr[0][0], 1L)));
			}
			long sum = 0, ans = 0;
			for(int i = 0; i<n; i++) {
				ft.set(arr[i][1], subtract(ft.get(arr[i][1]), new Pair<>((long) arr[i][0]-arr[0][0], 1L)));
				Utilities.Debug.dbg(ft.arr);
				var p = ft.query(arr[i][1], n-1);
				ans += p.a-(arr[i][0]-arr[0][0])*p.b;
			}
			pw.println(ans);
		}

	}

	static class Pair<T1, T2> implements Comparable<Pair<T1, T2>> {
		public T1 a;
		public T2 b;

		public Pair(Pair<T1, T2> p) {
			this(p.a, p.b);
		}

		public Pair(T1 a, T2 b) {
			this.a = a;
			this.b = b;
		}

		public String toString() {
			return a+" "+b;
		}

		public int hashCode() {
			return Objects.hash(a, b);
		}

		public boolean equals(Object o) {
			if(o instanceof Pair) {
				Pair p = (Pair) o;
				return a.equals(p.a)&&b.equals(p.b);
			}
			return false;
		}

		public int compareTo(Pair<T1, T2> p) {
			int cmp = ((Comparable<T1>) a).compareTo(p.a);
			if(cmp==0) {
				return ((Comparable<T2>) b).compareTo(p.b);
			}
			return cmp;
		}

	}

	static class Output implements Closeable, Flushable {
		public StringBuilder sb;
		public OutputStream os;
		public int BUFFER_SIZE;
		public String lineSeparator;

		public Output(OutputStream os) {
			this(os, 1<<16);
		}

		public Output(OutputStream os, int bs) {
			BUFFER_SIZE = bs;
			sb = new StringBuilder(BUFFER_SIZE);
			this.os = new BufferedOutputStream(os, 1<<17);
			lineSeparator = System.lineSeparator();
		}

		public void println(long l) {
			println(String.valueOf(l));
		}

		public void println(String s) {
			sb.append(s);
			println();
		}

		public void println() {
			sb.append(lineSeparator);
		}

		private void flushToBuffer() {
			try {
				os.write(sb.toString().getBytes());
			}catch(IOException e) {
				e.printStackTrace();
			}
			sb = new StringBuilder(BUFFER_SIZE);
		}

		public void flush() {
			try {
				flushToBuffer();
				os.flush();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}

		public void close() {
			flush();
			try {
				os.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}

	}

	static class FastReader implements InputReader {
		final private int BUFFER_SIZE = 1<<16;
		private DataInputStream din;
		private byte[] buffer;
		private int bufferPointer;
		private int bytesRead;

		public FastReader(InputStream is) {
			din = new DataInputStream(is);
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		public int nextInt() {
			int ret = 0;
			byte c = skipToDigit();
			boolean neg = (c=='-');
			if(neg) {
				c = read();
			}
			do {
				ret = ret*10+c-'0';
			} while((c = read())>='0'&&c<='9');
			if(neg) {
				return -ret;
			}
			return ret;
		}

		private boolean isDigit(byte b) {
			return b>='0'&&b<='9';
		}

		private byte skipToDigit() {
			byte ret;
			while(!isDigit(ret = read())&&ret!='-') ;
			return ret;
		}

		private void fillBuffer() {
			try {
				bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			}catch(IOException e) {
				e.printStackTrace();
				throw new InputMismatchException();
			}
			if(bytesRead==-1) {
				buffer[0] = -1;
			}
		}

		private byte read() {
			if(bytesRead==-1) {
				throw new InputMismatchException();
			}else if(bufferPointer==bytesRead) {
				fillBuffer();
			}
			return buffer[bufferPointer++];
		}

	}

	static class Utilities {
		public static class Debug {
			public static final boolean LOCAL = System.getProperty("ONLINE_JUDGE")==null;

			private static <T> String ts(T t) {
				if(t==null) {
					return "null";
				}
				try {
					return ts((Iterable) t);
				}catch(ClassCastException e) {
					if(t instanceof int[]) {
						String s = Arrays.toString((int[]) t);
						return "{"+s.substring(1, s.length()-1)+"}";
					}else if(t instanceof long[]) {
						String s = Arrays.toString((long[]) t);
						return "{"+s.substring(1, s.length()-1)+"}";
					}else if(t instanceof char[]) {
						String s = Arrays.toString((char[]) t);
						return "{"+s.substring(1, s.length()-1)+"}";
					}else if(t instanceof double[]) {
						String s = Arrays.toString((double[]) t);
						return "{"+s.substring(1, s.length()-1)+"}";
					}else if(t instanceof boolean[]) {
						String s = Arrays.toString((boolean[]) t);
						return "{"+s.substring(1, s.length()-1)+"}";
					}
					try {
						return ts((Object[]) t);
					}catch(ClassCastException e1) {
						return t.toString();
					}
				}
			}

			private static <T> String ts(T[] arr) {
				StringBuilder ret = new StringBuilder();
				ret.append("{");
				boolean first = true;
				for(T t: arr) {
					if(!first) {
						ret.append(", ");
					}
					first = false;
					ret.append(ts(t));
				}
				ret.append("}");
				return ret.toString();
			}

			private static <T> String ts(Iterable<T> iter) {
				StringBuilder ret = new StringBuilder();
				ret.append("{");
				boolean first = true;
				for(T t: iter) {
					if(!first) {
						ret.append(", ");
					}
					first = false;
					ret.append(ts(t));
				}
				ret.append("}");
				return ret.toString();
			}

			public static void dbg(Object... o) {
				if(LOCAL) {
					System.err.print("Line #"+Thread.currentThread().getStackTrace()[2].getLineNumber()+": [");
					for(int i = 0; i<o.length; i++) {
						if(i!=0) {
							System.err.print(", ");
						}
						System.err.print(ts(o[i]));
					}
					System.err.println("]");
				}
			}

		}

	}

	static class TFenwickTree<T> {
		int n;
		T initialValue;
		public T[] arr;
		public T[] value;
		BinaryOperator<T> operation;
		BinaryOperator<T> undo;

		public TFenwickTree(T[] arr, T initialValue, BinaryOperator<T> operation, BinaryOperator<T> undo) {
			n = arr.length;
			this.initialValue = initialValue;
			this.arr = (T[]) new Object[n];
			value = (T[]) new Object[n+1];
			Arrays.fill(value, initialValue);
			Arrays.fill(this.arr, initialValue);
			this.operation = operation;
			this.undo = undo;
			for(int i = 0; i<n; i++) {
				set(i, arr[i]);
			}
		}

		public void set(int ind, T val) {
			T old = arr[ind];
			arr[ind] = val;
			ind++;
			while(ind<=n) {
				value[ind] = operation.apply(undo.apply(value[ind], old), val);
				ind += ind&-ind;
			}
		}

		private T psum(int ind) {
			T ret = initialValue;
			while(ind>0) {
				ret = operation.apply(ret, value[ind]);
				ind -= ind&-ind;
			}
			return ret;
		}

		public T query(int l, int r) {
			return undo.apply(psum(r+1), psum(l));
		}

		public T get(int ind) {
			return arr[ind];
		}

	}

	static interface InputReader {
		int nextInt();

	}
}

