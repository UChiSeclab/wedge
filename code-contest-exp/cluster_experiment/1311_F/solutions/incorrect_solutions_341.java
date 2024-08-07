import java.util.*;

public class MovingPoints {

    static class Pair {
        int first;
        int second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] coords = new int[n];
        int[] vels = new int[n];


        for (int i = 0; i < n; ++i) {
            coords[i] = scanner.nextInt();
        }
        for (int i = 0; i < n; ++i) {
            vels[i] = scanner.nextInt();
        }

        Pair[] vec = new Pair[n];

        for(int i = 0; i < n; i++) {
            vec[i] = new Pair(coords[i], vels[i]);
        }

        Arrays.sort(coords);
        Arrays.sort(vec, (o1, o2) -> {
            if(o1.second == o2.second)
                return (o2.first - o1.first);
            return (o2.second - o1.second);
        });

        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < n; i++) {
            sum += coords[i] * (2 * i + 1 - n);
            map.put(coords[i], i);
        }

        for(int i = 0; i < n; i++) {
            sum -= vec[i].first * (map.get(vec[i].first) - i);
        }

        System.out.println(sum);
    }
}
