import java.io.*;
import java.math.*;
import java.util.*;

public class CODEFORCES
{
	private InputStream is;
	private PrintWriter out;

	public static int[] suffixArray(CharSequence S)
	{
		int n = S.length();
		Integer[] order = new Integer[n];
		for (int i = 0; i < n; i++)
			order[i] = n - 1 - i;
		Arrays.sort(order, (a, b) -> Character.compare(S.charAt(a), S.charAt(b)));
		int[] sa = new int[n];
		int[] classes = new int[n];
		for (int i = 0; i < n; i++)
		{
			sa[i] = order[i];
			classes[i] = S.charAt(i);
		}
		for (int len = 1; len < n; len <<= 1)
		{
			int[] c = classes.clone();
			for (int i = 0; i < n; i++)
				classes[sa[i]] = i > 0 && c[sa[i - 1]] == c[sa[i]] && sa[i - 1] + len < n
						&& c[sa[i - 1] + (len >> 1)] == c[sa[i] + (len >> 1)] ? classes[sa[i - 1]] : i;
			int[] cnt = new int[n];
			for (int i = 0; i < n; i++)
				cnt[i] = i;
			int[] s = sa.clone();
			for (int i = 0; i < n; i++)
			{
				int s1 = s[i] - len;
				if (s1 >= 0)
					sa[cnt[classes[s1]]++] = s1;
			}
		}
		return sa;
	}

	public int[] lcp(int[] sa, CharSequence s)
	{
		int n = sa.length;
		int[] rank = new int[n];
		for (int i = 0; i < n; i++)
			rank[sa[i]] = i;
		int[] lcp = new int[n - 1];
		for (int i = 0, h = 0; i < n; i++)
		{
			if (rank[i] < n - 1)
			{
				for (int j = sa[rank[i] + 1]; Math.max(i, j) + h < s.length()
						&& s.charAt(i + h) == s.charAt(j + h); ++h)
					;
				lcp[rank[i]] = h;
				if (h > 0)
					--h;
			}
		}
		return lcp;
	}

	int[] logTable;
	int[][] rmq;
	int[] a;

	public void SparseTable()
	{
		int n = a.length;
		logTable = new int[n + 1];
		for (int i = 2; i <= n; i++)
			logTable[i] = logTable[i >> 1] + 1;
		rmq = new int[logTable[n] + 1][n];
		for (int i = 0; i < n; ++i)
			rmq[0][i] = i;
		for (int k = 1; (1 << k) < n; ++k)
		{
			for (int i = 0; i + (1 << k) <= n; i++)
			{
				int x = rmq[k - 1][i];
				int y = rmq[k - 1][i + (1 << k - 1)];
				rmq[k][i] = a[x] < a[y] ? x : y;
			}
		}
	}

	public int minPos(int i, int j)
	{
		int k = logTable[j - i];
		int x = rmq[k][i];
		int y = rmq[k][j - (1 << k) + 1];
		return a[x] < a[y] ? x : y;
	}

	int arr[];

	void update(int l, int r, int c, int x, int y, int val)
	{
		if (arr[c] != 0 && l != r)
		{
			arr[2 * c + 1] = arr[c];
			arr[2 * c + 2] = arr[c];
			arr[c] = 0;
		}
		if (l > r || x > y || l > y || x > r)
			return;
		if (x <= l && y >= r)
		{
			arr[c] = val;
			return;
		}
		int mid = l + r >> 1;
		update(l, mid, 2 * c + 1, x, y, val);
		update(mid + 1, r, 2 * c + 2, x, y, val);
	}

	int val(int l, int r, int c, int ind)
	{
		if (arr[c] != 0 && l != r)
		{
			arr[2 * c + 1] = arr[c];
			arr[2 * c + 2] = arr[c];
			arr[c] = 0;
		}
		if (l == r)
			return arr[c];
		int mid = l + r >> 1;
		if (mid >= ind)
			return val(l, mid, 2 * c + 1, ind);
		else
			return val(mid + 1, r, 2 * c + 2, ind);
	}

	void print(String a, int b, int len)
	{
		for (int i = b; i < b + len; i++)
			out.print(a.charAt(i));
		out.println();
	}

	void solve()
	{
		String ai = ns();
		int k = ni();
		long n = ai.length();
		if (k > (n * (n + 1)) >> 1)
		{
			out.println("No such line.");
			return;
		}
		if (n == 1)
		{
			out.println(ai);
			return;
		}
		int sa[] = suffixArray(ai);
		this.a = lcp(sa, ai);
		SparseTable();
		arr = new int[4 * ((int) n)];
		int ni = (int) n;
		for (int i = 0; i < n; i++)
		{
			int che = val(0, ni - 1, 0, i), ri = ni - sa[i];
			//out.println(che + " " + ri + " " + k);
			for (int j = che + 1; j <= ri; j++)
			{
				int l = i, r = ni - 2;
				int pos = -1;
				while (l <= r)
				{
					int mid = l + r >> 1;
					int get = minPos(i, mid);
					//out.println(get + " " + a[get] + " " + j + " " + mid);
					if (a[get] >= j)
					{
						pos = mid;
						l = mid + 1;
					} else
						r = mid - 1;
				}
				//out.println(pos);
				if (pos != -1)
				{
					if (pos - i + 2 >= k)
					{
						print(ai, sa[i], j);
						return;
					} else
					{
						k -= pos - i + 2;
						update(0, ni - 1, 0, i + 1, pos + 1, j);
					}
				} else
				{
					if (k == 1)
					{
						print(ai, sa[i], j);
						return;
					} else
						k--;
				}
			}
		}
	}

	void soln() throws Exception
	{
		is = System.in;
		out = new PrintWriter(System.out);
		long s = System.currentTimeMillis();
		solve();
		out.flush();
		tr(System.currentTimeMillis() - s + "ms");
	}

	public static void main(String[] args) throws Exception
	{
		new CODEFORCES().soln();

	}

	// To Get Input
	// Some Buffer Methods
	private byte[] inbuf = new byte[1024];
	public int lenbuf = 0, ptrbuf = 0;

	private int readByte()
	{
		if (lenbuf == -1)
			throw new InputMismatchException();
		if (ptrbuf >= lenbuf)
		{
			ptrbuf = 0;
			try
			{
				lenbuf = is.read(inbuf);
			} catch (IOException e)
			{
				throw new InputMismatchException();
			}
			if (lenbuf <= 0)
				return -1;
		}
		return inbuf[ptrbuf++];
	}

	private boolean isSpaceChar(int c)
	{
		return !(c >= 33 && c <= 126);
	}

	private int skip()
	{
		int b;
		while ((b = readByte()) != -1 && isSpaceChar(b))
			;
		return b;
	}

	private double nd()
	{
		return Double.parseDouble(ns());
	}

	private char nc()
	{
		return (char) skip();
	}

	private String ns()
	{
		int b = skip();
		StringBuilder sb = new StringBuilder();
		while (!(isSpaceChar(b)))
		{ // when nextLine, (isSpaceChar(b) && b != '
			// ')
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}

	private char[] ns(int n)
	{
		char[] buf = new char[n];
		int b = skip(), p = 0;
		while (p < n && !(isSpaceChar(b)))
		{
			buf[p++] = (char) b;
			b = readByte();
		}
		return n == p ? buf : Arrays.copyOf(buf, p);
	}

	private char[][] nm(int n, int m)
	{
		char[][] map = new char[n][];
		for (int i = 0; i < n; i++)
			map[i] = ns(m);
		return map;
	}

	private int[] na(int n)
	{
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = ni();
		return a;
	}

	private int ni()
	{
		int num = 0, b;
		boolean minus = false;
		while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-')
		{
			minus = true;
			b = readByte();
		}

		while (true)
		{
			if (b >= '0' && b <= '9')
			{
				num = num * 10 + (b - '0');
			} else
			{
				return minus ? -num : num;
			}
			b = readByte();
		}
	}

	private long nl()
	{
		long num = 0;
		int b;
		boolean minus = false;
		while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-')
		{
			minus = true;
			b = readByte();
		}

		while (true)
		{
			if (b >= '0' && b <= '9')
			{
				num = num * 10 + (b - '0');
			} else
			{
				return minus ? -num : num;
			}
			b = readByte();
		}
	}

	private boolean oj = System.getProperty("ONLINE_JUDGE") != null;

	private void tr(Object... o)
	{
		if (!oj)
			System.out.println(Arrays.deepToString(o));
	}
}