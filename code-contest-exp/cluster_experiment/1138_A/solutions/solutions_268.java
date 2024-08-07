
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] sushi = new int[n];
        for (int i = 0; i < n; i++) {
            sushi[i] = in.nextInt();
        }

        int curr = sushi[0];
        int counter = 1;
        List<Integer> subSum = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (sushi[i] == curr) counter++;
            else {
                subSum.add(counter);
                counter = 1;
                curr = sushi[i];
            }
        }
        subSum.add(counter);

        int max = 1;
        for (int i = 0; i < subSum.size() - 1; i++) {
            max = Math.max(max, Math.min(subSum.get(i), subSum.get(i + 1)));
        }
        System.out.println(max * 2);

    }


}