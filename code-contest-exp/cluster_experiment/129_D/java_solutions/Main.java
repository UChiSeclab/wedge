import java.util.Arrays;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 * @author Artem Gilmudinov
 */
public class Main {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		Reader in = new Reader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		TaskD solver = new TaskD();
		solver.solve(1, in, out);
		out.close();
	}
}

class TaskD {
    public void solve(int testNumber, Reader in, PrintWriter out) {
        String s = in.rl();
        int k = in.ni();
        int n = s.length();
        long cur = 0;
        ArrayList<Integer> pos = new ArrayList<>();
        long[] to = new long[27];
        for(int i = 0; i < n; i++) {
            to[s.charAt(i) - 'a'] += n - i;
        }
        long temp = 0;
        for(int i = 0; i < 26; i++) {
            if(temp < k && temp + to[i] >= k) {
                for(int j = 0; j < n; j++) {
                    if(s.charAt(j) - 'a' == i) {
                        pos.add(j);
                    }
                }
                cur = temp;
                out.print((char)('a' + i));
                break;
            }
            temp += to[i];
        }
        while(true) {
            Arrays.fill(to, 0);
            for(int i = 0; i < pos.size(); i++) {
                if(pos.get(i) < n - 1) {
                    to[s.charAt(pos.get(i) + 1) - 'a' + 1] += n - pos.get(i) - 1;
                }
            }
            to[0] = pos.size();
            temp = cur;
            int res = 0;
            for(int i = 0; i < 27; i++) {
                if(temp < k && temp + to[i] >= k) {
                    res = i;
                    break;
                }
                temp += to[i];
            }
            if(res == 0) {
                break;
            }
            ArrayList<Integer> list = new ArrayList<>();
            for(int i = 0; i < pos.size(); i++) {
                if(pos.get(i) < n - 1) {
                    if(s.charAt(pos.get(i) + 1) - 'a' + 1 == res) {
                        list.add(pos.get(i) + 1);
                    }
                }
            }
            out.print((char)('a' + res - 1));
            cur = temp;
            pos = list;
        }
    }
}

class Reader {
    private BufferedReader in;
    private StringTokenizer st = new StringTokenizer("");
    private String delim = " ";

    public Reader(InputStream in) {
        this.in = new BufferedReader(new InputStreamReader(in));
    }

    public String next() {
        if (!st.hasMoreTokens()) {
            st = new StringTokenizer(rl());
        }
        return st.nextToken(delim);
    }

    public String rl() {
        try {
            return in.readLine();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int ni() {
        return Integer.parseInt(next());
    }

}

