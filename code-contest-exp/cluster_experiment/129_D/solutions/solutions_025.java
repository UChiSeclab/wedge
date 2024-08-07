import java.util.List;
import java.io.InputStreamReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.StringTokenizer;
import java.math.BigInteger;
import java.util.Collections;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 * @author aircube
 */
public class Main {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		FastInputReader in = new FastInputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		TaskB solver = new TaskB();
		solver.solve(1, in, out);
		out.close();
	}
}

class TaskB {
	public void solve(int testNumber, FastInputReader in, PrintWriter out) {
        String s = in.nextString() + (char)('a'-1);
        int k = in.nextInt();
        int n = s.length() - 1;
        if ((long)n * (n + 1) / 2 < k) {
            out.print("No such line.");
            return;
        }
        int[] p = StringUtils.suffixArray(s);
        long[] sum = new long[p.length + 1];
        for (int i = 0; i < p.length; ++i) {
            if (i > 0)
                sum[i] += sum[i - 1];
            if (i < p.length)
                sum[i] += (s.length() - p[i] - 1);
        }
//        out.println("Work string = " + s);
//        for (int r = 1; r <= n * (n + 1) / 2; ++r) {
//            out.println(solve(0,1, p.length-1,r,p,s,sum));
//        }
        solve(0, 1, s.length() - 1, k, p, s, sum);
        out.print(answer.toString());

	}

    StringBuilder answer=new StringBuilder();
    private void solve(int depth, int l, int r, int k, int[] p, String s, long[] sum) {
        if (k==0)return;
        if (s.charAt(p[l] + depth) == s.charAt(p[r] + depth)) {
            //StringBuilder tmp = new StringBuilder();
            answer.append(s.charAt(p[l] + depth));
            if (k <= r - l + 1) {
                return;
            } else {
                solve(depth + 1, l, r, k - (r - l + 1),p, s, sum);
            }
        } else {
            for (int i = l, j; i <= r; i = j) {
                for (j = i; j <= r && s.charAt(p[i] + depth) == s.charAt(p[j] + depth); ++j);
                long cur = sum[j - 1] - (i == 0 ? 0 : sum[i - 1]) - (long)(j - i) * depth;
                if (cur >= k) {
                    solve(depth, i, j - 1, k, p, s, sum);
                    return;
                } else {
                    k -= cur;
                }
            }
        }
    }
}

class FastInputReader {
    private StringTokenizer tokenizer;
    public BufferedReader reader;
    public FastInputReader(InputStream inputStream) {
        reader = new BufferedReader(new InputStreamReader(inputStream));
        tokenizer=null;
    }
    public String next() {
        try {
            while (tokenizer==null || !tokenizer.hasMoreTokens()){
                tokenizer=new StringTokenizer(reader.readLine());
            }
        } catch (IOException e) {
            return "-1";
        }
        return tokenizer.nextToken();
    }
    public String nextString() {
        return next();
    }
    public int nextInt(){
        return Integer.parseInt(next());
    }
    }

class StringUtils {
    public static int[] suffixArray(String s) {
        int[] tmp = new int[s.length()];
        for (int i = 0; i < tmp.length; ++i) {
            tmp[i] = s.charAt(i);
        }
        return SuffixArray(tmp);
    }

    public static int[] SuffixArray(int[] _a) {
        int[] a = _a.clone();
        int minAlpha = Integer.MAX_VALUE;
        int maxAlpha = Integer.MIN_VALUE;
        for (int x : a) {
            minAlpha = Math.min(minAlpha, x);
            maxAlpha = Math.max(maxAlpha, x);
        }
        int Alphabet = maxAlpha - minAlpha + 1;
        int[] count = new int[Alphabet];
        for (int i = 0; i < a.length; ++i)
            a[i] -= minAlpha;
        for (int x : a)
            count[x] ++;
        int classes = 0;
        int n = a.length;
        int[] perm = new int[n];
        int[] c = new int[n];
        int[] pointer = new int[n];
        int[] sum = new int[Alphabet];
        for (int i = 0; i < Alphabet; ++i) {
            if (i > 0)
                sum[i] += sum[i - 1];
            sum[i] += count[i];
        }
        int[] ptr = new int[Alphabet];
        int[] first = new int[Alphabet];
        for (int i = 0; i < Alphabet; ++i) {
            if (count[i] > 0) {
                first[i] = classes;
                pointer[classes] = ptr[i] = (i == 0 ? 0 : sum[i - 1]);
                ++classes;
            }
        }
        for (int i = 0; i < n; ++i){
            c[i] = first[a[i]];
            perm[ptr[a[i]] ++] = i;
        }
        int[] nextp = new int[n];
        int[] nextc = new int[n];
        for (int h = 1; h < n && classes != n; h *= 2) {
            for (int j : perm) {
                int u = (j - h);
                if (u < 0) u += n;
                nextp[pointer[c[u]] ++] = u;
            }
            classes = 0;
            for (int i = 0, j = 0; i < n; i = j) {
                for (j = i; j < n && c[nextp[j]] == c[nextp[i]] && c[(nextp[j] + h) % n] == c[(nextp[i] + h) % n]; ++j);
                for (int u = i; u < j; ++u)
                    nextc[nextp[u]] = classes;
                pointer[classes] = i;
                classes++;
            }
            System.arraycopy(nextc, 0, c, 0, nextc.length);
            System.arraycopy(nextp, 0, perm, 0, nextp.length);
        }
        return perm;
    }

}