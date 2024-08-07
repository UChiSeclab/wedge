
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class D {
    static long[] H;
    static long[] P;
    static int p = 31;
    static String s;

    public static long getHash(int i, int j) {
        return H[j + 1] - P[j - i + 1] * H[i];
    }

    public static void main(String[] args) throws NumberFormatException,
            IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        s = in.readLine();
        int n = s.length();
        int k = Integer.parseInt(in.readLine()) - 1;
        if (k >= ((long) n * (n + 1)) / 2) {
            System.out.println("No such line.");
            return;
        }
        H = new long[n + 1];
        P = new long[n + 1];
        P[0] = 1;
        for (int i = 1; i <= n; i++) {
            H[i] = p * H[i - 1] + s.charAt(i - 1);
            P[i] = p * P[i - 1];
        }
        PriorityQueue<sub> Q = new PriorityQueue<sub>();
        for (int i = 0; i < s.length(); i++)
            Q.add(new sub(i, i));
        while (k > 0) {
            sub temp = Q.poll();
            k--;
            temp.end++;
            if (temp.end == n)
                continue;
            Q.add(temp);
        }
        sub temp = Q.poll();
        System.out.println(s.substring(temp.start, temp.end + 1));
    }
}

class sub implements Comparable<sub> {
    int start;
    int end;

    public sub(int i, int j) {
        start = i;
        end = j;
    }

    public int compareTo(sub o) {
        int l1 = end - start + 1;
        int l2 = o.end - o.start + 1;
        if (l1 <= l2
                && D.getHash(start, end) == D
                        .getHash(o.start, o.start + l1 - 1))
            return -1;

        if (l2 <= l1
                && D.getHash(o.start, o.end) == D
                        .getHash(start, start + l2 - 1))
            return 1;
        int lo = 0;
        int hi = Math.min(l1, l2) - 1;
        while (lo != hi) {
            int mid = (lo + hi) / 2;
            if (D.getHash(start, start + mid) == D.getHash(o.start, o.start
                    + mid))
                lo = mid + 1;
            else
                hi = mid;
        }
        return D.s.charAt(start + lo) - D.s.charAt(o.start + lo);
    }
}