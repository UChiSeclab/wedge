import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class E {
    static int[] arr;
    static int[] out;
    static int n;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        arr = new int[n];
        out = new int[n];
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        for (int a = 0; a < n; a++)
            arr[a] = Integer.parseInt(tokenizer.nextToken());
        for (int a = 0; a < n; a++)
            recursion(a);
        for (int x : out)
            System.out.print(x + " ");
        br.close();
    }

    public static void recursion(int cur) {
        if (out[cur] == -4) {
            out[cur] = -1;
            return;
        }
        if(cur > 0)
            return;
        int d = -1;
        if (cur + arr[cur] < n) {
            if ((arr[cur + arr[cur]] & 1) != (arr[cur] & 1)) {
                out[cur] = 1;
                return;
            }
            if (out[cur + arr[cur]] == 0 | out[cur + arr[cur]] == -4) {
                out[cur] = -4;
                recursion(cur + arr[cur]);
            }
            if(out[cur + arr[cur]] != -1) {
                d = out[cur + arr[cur]] + 1;
            }
        }
        if (cur - arr[cur] >= 0) {
            if ((arr[cur - arr[cur]] & 1) != (arr[cur] & 1)) {
                out[cur] = 1;
                return;
            }
            if (out[cur - arr[cur]] == 0| out[cur - arr[cur]] == -4) {
                out[cur] = -4;
                recursion(cur - arr[cur]);
            }
            if(out[cur - arr[cur]] != -1) {
                if (d == -1)
                    d = out[cur - arr[cur]] + 1;
                else
                    d = Math.min(out[cur - arr[cur]] + 1, d);
            }
        }
        out[cur] = d;
    }
}
