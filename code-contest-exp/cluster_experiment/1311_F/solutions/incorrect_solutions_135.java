import java.util.Scanner;

public class Moving_Points {
    public static void main(String args[]) {
        Scanner reader = new Scanner(System.in);

        int n = reader.nextInt();
        int[] x = new int[n];
        for (int i = 0; i < n; i++)
            x[i] = reader.nextInt();
        int[] v = new int[n];
        for (int i = 0; i < n; i++)
            v[i] = reader.nextInt();

        reader.close();

        int sum = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (v[i] * v[j] < 0 || v[i] == v[j] || (v[i] * v[j] > 0 && (x[i] < x[j] && v[i] < v[j]))) {
                    sum += Math.abs(x[i] - x[j]);
                } else if (v[i] * v[j] == 0) {
                    if ((v[i] == 0 && ((x[j] > x[i] && v[j] > 0) || (x[j] < x[i] && v[j] < 0)))
                    )
                        sum += Math.abs(x[i] - x[j]);
                }
            }
        }


        System.out.print(sum);
    }
}
