import java.util.*;

public class E1272 {
    private static int[][] steps;
    private static boolean[] used;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        steps = new int[n][2];
        used = new boolean[n];
        for (int i = 0; i < n; i++) {
            steps[i][0] = Integer.MAX_VALUE;
            steps[i][1] = Integer.MAX_VALUE;
        }
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        process(a, n);
    }

    private static void process(int[] a, int n) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        boolean[] even = new boolean[n];
        for (int i = 0; i < n; i++) {
            even[i] = a[i] % 2 == 0;
            if (i + a[i] < n) {
                graph.computeIfAbsent(i, unused -> new ArrayList<>()).add(i + a[i]);
            }
            if (i - a[i] >= 0) {
                graph.computeIfAbsent(i, unused -> new ArrayList<>()).add(i - a[i]);
            }
        }
//        System.out.println(graph);
        for (int i = 0; i < n; i++) {
            Arrays.fill(used, false);
            discover(i, graph, even);
        }

        for (int i = 0; i < n; i++) {
            System.out.print((steps[i][even[i] ? 1 : 0] == Integer.MAX_VALUE ? -1 : steps[i][even[i] ? 1 : 0]) + " ");
        }
    }

    private static void discover(int idx, Map<Integer, List<Integer>> graph, boolean[] even) {
        if (steps[idx][0] != Integer.MAX_VALUE || steps[idx][1] != Integer.MAX_VALUE) return;
        if (used[idx]) return;
        used[idx] = true;
        List<Integer> niegbors = graph.getOrDefault(idx, Collections.emptyList());
        steps[idx][even[idx] ? 0 : 1] = 0;
        for (int neigh : niegbors) {
            discover(neigh, graph, even);
            int ne_steps = steps[neigh][!even[idx] ? 0 : 1];
            if (ne_steps != Integer.MAX_VALUE) {
                steps[idx][!even[idx] ? 0 : 1] = Math.min(steps[idx][!even[idx] ? 0 : 1], ne_steps + 1);
            }
        }
    }


}
