import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

public class P579E {

    public static void main(String[] args) throws IOException {

        BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(bi.readLine());

        int n = Integer.parseInt(st.nextToken());

        int[] a = new int[n];

        st = new StringTokenizer(bi.readLine());

        for(int i=0; i<n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        int last = Integer.MAX_VALUE;
        int ret = 0;

        shuffle(a);
        Arrays.sort(a);

        for (int i = n - 1; i >= 0; i--) {
            if(a[i] + 1 < last) {
                ret++;
                last = a[i] + 1;
            } else if (a[i] < last) {
                ret++;
                last = a[i];
            } else if (a[i] - 1 < last && a[i] != 1) {
                ret++;
                last = a[i] - 1;
            }
        }

        System.out.println(ret);

    }

    static void shuffle(int[] a) {
        Random r = new Random();
        for (int i = 0; i < a.length; i++) {
            int j = r.nextInt(a.length);
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }
}
