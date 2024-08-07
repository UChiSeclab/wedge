import java.io.DataInputStream;
import java.io.IOException;

public class NearestOppositeParity {

	public static void main(String[] args) throws IOException {
		int n = readInt();
		int[] a = new int[n+1];
		for (int i = 1; i <= n; i++) {
			a[i] = readInt();
		}
		
		int[] dist = new int[n+1];
		boolean[] visited = new boolean[n+1];
		
		for (int i = 1; i <= n; i++) {
			if (!visited[i]) {
				dfs(a, n, dist, visited, i);
			}
		}
		
		for (int i = 1; i <= n; i++) {
			if (i > 1) {
				System.out.print(" ");
			}
			if (dist[i] == Integer.MAX_VALUE) {
				System.out.print(-1);
			} else {
				System.out.print(dist[i]);
			}
		}
		System.out.println();
	}
	
	private static void dfs(int[] a, int n, int[] dist, boolean[] visited, int curr) {
		visited[curr] = true;
		int left = Integer.MAX_VALUE;
		int right = Integer.MAX_VALUE;
		if (curr - a[curr] >= 1) {
			int next = curr - a[curr];
			if ((a[curr] + a[next]) % 2 != 0) {
				dist[curr] = 1;
				
//				return;
			}
			if (!visited[next]) {
				dfs(a, n, dist, visited, next);
			}
			if (dist[next] == Integer.MAX_VALUE) {
				left = Integer.MAX_VALUE;
			} else {
				left = dist[next] + 1;
			}
		} 
		if (curr + a[curr] <= n) {
			int next = curr + a[curr];
			if ((a[curr] + a[next]) % 2 != 0) {
				dist[curr] = 1;
//				return;
			}
			if (!visited[next]) {
				dfs(a, n, dist, visited, next);
			}
			if (dist[next] == Integer.MAX_VALUE) {
				right = Integer.MAX_VALUE;
			} else {
				right = dist[next] + 1;
			}
		}
		if (dist[curr] == 0) {
			dist[curr] = Math.min(left, right);
		}
	}

	final private static int BUFFER_SIZE = 1 << 16;
	private static DataInputStream din = new DataInputStream(System.in);
	private static byte[] buffer = new byte[BUFFER_SIZE];
	private static int bufferPointer = 0, bytesRead = 0;
	
	public static char readChar() throws IOException {
		byte c = Read();
		while (c <= ' ')
			c = Read();
		return (char) c;
	}

	public static int readInt() throws IOException {
		int ret = 0;
		byte c = Read();
		while (c <= ' ')
			c = Read();
		boolean neg = (c == '-');
		if (neg)
			c = Read();
		do {
			ret = ret * 10 + c - '0';
		} while ((c = Read()) >= '0' && c <= '9');

		if (neg)
			return -ret;
		return ret;
	}

	public static long readLong() throws IOException {
		long ret = 0;
		byte c = Read();
		while (c <= ' ')
			c = Read();
		boolean neg = (c == '-');
		if (neg)
			c = Read();
		do {
			ret = ret * 10 + c - '0';
		} while ((c = Read()) >= '0' && c <= '9');

		if (neg)
			return -ret;
		return ret;
	}

	public static String readLine() throws IOException {
		byte[] buf = new byte[64]; // line length
		int cnt = 0, c;
		while ((c = Read()) != -1) {
			if (c == '\n')
				break;
			buf[cnt++] = (byte) c;
		}
		return new String(buf, 0, cnt);
	}
	
	public static String readString() throws IOException{
		byte[] ret = new byte[10001];
        int idx = 0;
        byte c = Read();
        while (c <= ' ') {
            c = Read();
        }
        do {
            ret[idx++] = c;
            c = Read();
        } while (c != -1 && c != ' ' && c != '\n' && c != '\r');
        return new String(ret, 0, idx);
	}
	
	public static double readDouble() throws IOException {
		double ret = 0, div = 1;
		byte c = Read();
		while (c <= ' ')
			c = Read();
		boolean neg = (c == '-');
		if (neg)
			c = Read();

		do {
			ret = ret * 10 + c - '0';
		} while ((c = Read()) >= '0' && c <= '9');

		if (c == '.') {
			while ((c = Read()) >= '0' && c <= '9') {
				ret += (c - '0') / (div *= 10);
			}
		}

		if (neg)
			return -ret;
		return ret;
	}
	
	private static void fillBuffer() throws IOException {
		bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
		if (bytesRead == -1)
			buffer[0] = -1;
	}

	private static byte Read() throws IOException {
		if (bufferPointer == bytesRead)
			fillBuffer();
		return buffer[bufferPointer++];
	}
}
