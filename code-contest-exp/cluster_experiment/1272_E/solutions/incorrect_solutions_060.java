import java.util.*;

public class E1272 {
    private static int[] evenSteps;
    private static int[] oddSteps;
    private static boolean[] used;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        evenSteps = new int[n];
        oddSteps = new int[n];
        used = new boolean[n];
        Arrays.fill(oddSteps, Integer.MAX_VALUE);
        Arrays.fill(evenSteps, Integer.MAX_VALUE);
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
        for (int i = 0; i < n; i++) {
            Arrays.fill(used,false);

            System.out.print(findCloset(i, graph, even, !even[i]) + " ");
        }
    }

    private static int findCloset(int idx, Map<Integer, List<Integer>> graph, boolean[] even, boolean e) {
        if (e && evenSteps[idx] != Integer.MAX_VALUE) return evenSteps[idx];
        if (!e && oddSteps[idx] != Integer.MAX_VALUE) return oddSteps[idx];
        used[idx] = true;
        LinkedList<Integer> q = new LinkedList<>(graph.getOrDefault(idx, new ArrayList<>()));

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Integer integer = q.removeFirst();
                if (used[integer]) return -1;
                if (even[integer] == e) {
                    if (e) evenSteps[idx] = Math.min(evenSteps[idx], 1);
                    else oddSteps[idx] = Math.min(oddSteps[idx], 1);
                    continue;
                }
                int closet = findCloset(integer, graph, even, e);
                if (closet == -1) continue;
                if (e) evenSteps[idx] = Math.min(evenSteps[idx], closet + 1);
                else oddSteps[idx] = Math.min(oddSteps[idx], closet + 1);
            }
        }
        if (e && evenSteps[idx] == Integer.MAX_VALUE) evenSteps[idx] = -1;
        if (!e && oddSteps[idx] == Integer.MAX_VALUE) oddSteps[idx] = -1;
        if (e) return evenSteps[idx];
        return oddSteps[idx];
    }


}
