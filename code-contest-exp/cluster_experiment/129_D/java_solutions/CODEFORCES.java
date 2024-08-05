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
	
	void solve()
	{
		/*int n = ni();
		long d = nl();
		long a[] = new long[n];
		for (int i = 0; i < n; i++)
			a[i] = ni();
		long min = a[0];
		long ans = 0;
		for (int i = 1; i < n; i++)
		{
			if (min > a[i] - ans * d)
			{
				long hh = (min - a[i] + ans * d) / d;
				long mod = (min - a[i] + ans * d) % d;
				min = min - mod;
				if ((hh & 1) == 1)
					min -= d;
				else if (mod == 0)
					min -= 2 * d;
				continue;
			}

			long tmp = (a[i] - ans * d - min) / d;
			tmp >>= 1;
			tmp++;
			ans += tmp;
			min = a[i] - ans * d;
		}
		out.println(ans);*/
		String a = ns();
		long n = a.length();
		int k = ni();
		if(k>(n*(n+1))>>1)
			out.println("No such line.");
		else {
			int sa[] = suffixArray(a);
			for(int i=0;i<n;i++) {
				if(k>n-sa[i])
					k-=n-sa[i];
				else {
					for(int j=sa[i];j<sa[i]+k;j++)
						out.print(a.charAt(j));
					break;
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