import java.util.Scanner;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 * @author Fantast
 */
public class Main {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		Scanner in = new Scanner(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		TaskE solver = new TaskE();
		solver.solve(1, in, out);
		out.close();
	}
}

class TaskE {
    int n;
    char[] s;
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        s = in.next().toCharArray();
        n = in.nextInt();

        for (int i = 0; i < 101; ++i) {
            for (int j = 0; j < 2; ++j) {
                for (int k = 0; k < 101; ++k) {
                    mxs[i][j][k] = -1;
                    mns[i][j][k] = -1;
                }
            }
        }

        out.println(
                Math.max(
                        Math.abs(mxdist(0, 1, n)),
                        Math.abs(mndist(0, 1, n))
                ));
    }

    int mxs[][][] = new int[101][2][101];
    public int mxdist(int ind, int dir, int tochange) {
        if (tochange > s.length - ind || tochange < 0) {
            return Integer.MIN_VALUE / 2;
        }

        if (ind == s.length) {
            return 0;
        }

        int di = (dir+1)/2;
        if (mxs[ind][di][tochange] != -1) {
            return mxs[ind][di][tochange];
        }

        int res;
        if (s[ind] == 'T') {
            res = Math.max(
                    mxdist(ind + 1, -dir, tochange),
                    dir + mxdist(ind + 1, dir, tochange - 1));
        } else {
            res = Math.max(
                    dir + mxdist(ind + 1, dir, tochange),
                    mxdist(ind + 1, -dir, tochange - 1));
        }

        return mxs[ind][di][tochange] = res;
    }

    int mns[][][] = new int[101][2][101];
    public int mndist(int ind, int dir, int tochange) {
        if (tochange > s.length - ind || tochange < 0) {
            return Integer.MAX_VALUE / 2;
        }

        if (ind == s.length) {
            return 0;
        }

        int di = (dir+1)/2;
        if (mns[ind][di][tochange] != -1) {
            return mns[ind][di][tochange];
        }

        int res;
        if (s[ind] == 'T') {
            res = Math.min(
                    mndist(ind + 1, -dir, tochange),
                    dir + mndist(ind + 1, dir, tochange - 1));
        } else {
            res = Math.min(
                    dir + mndist(ind + 1, dir, tochange),
                    mndist(ind + 1, -dir, tochange - 1));
        }

        return mns[ind][di][tochange] = res;
    }
}

