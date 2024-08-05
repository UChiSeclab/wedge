import java.util.Scanner;

public class CF545A {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }
        int[] x = new int[3];
        boolean ok = true;
        int max = 2;
        int cur = arr[0];
        x[cur]++;
        int index = 1;
        int loop = 0;
        while (index < N) {
            x[arr[index]]++;
            if (cur != arr[index]) {
                max = Math.max(max, Math.min(x[1], x[2]) * 2);
                if (x[arr[index]] > 1) {
                    x[cur] = 0;
                }
            }
            cur = arr[index++];
            if (index == N) {
                index = 0;
                loop++;
            }
            if (loop > 1) {
                break;
            }
        }

        System.out.println(max);
    }
}

