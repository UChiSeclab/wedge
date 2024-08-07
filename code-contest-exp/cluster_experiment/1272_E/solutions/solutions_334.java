
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<Integer> values = new ArrayList<>();
        for(int i=0; i<n; i++) {
            values.add(sc.nextInt());
        }
        int[] best = new int[n];
        Queue<Integer> q = new LinkedList<>();
        Map<Integer, Set<Integer>> edges = new TreeMap<>();
        for(int i=0; i<n; i++) {
            if(i - values.get(i) >= 0) {
                if(edges.get(i-values.get(i)) == null) edges.put(i-values.get(i), new HashSet<>());
                edges.get(i-values.get(i)).add(i);
            }
            if(i + values.get(i) < n) {
                if(edges.get(i+values.get(i)) == null) edges.put(i+values.get(i), new HashSet<>());
                edges.get(i+values.get(i)).add(i);
            }
            if(i - values.get(i) >= 0 && ((values.get(i)%2) != (values.get(i - values.get(i))%2)) ) {
                best[i] = 1;
                q.add(i);
                continue;
            }
            if(i + values.get(i) < n && ((values.get(i)%2) != (values.get(i + values.get(i))%2))) {
                best[i] = 1;
                q.add(i);
                continue;
            }
        }
        while(!q.isEmpty()) {
            int curr = q.remove();
            if(edges.get(curr) == null) continue;
            for(int i : edges.get(curr)) {
                if(best[i] == 0) {
                    q.add(i);
                    best[i] = best[curr] + 1;
                }
            }
        }
        for(int i=0; i<n; i++) {
            if(best[i] == 0) System.out.print("-1 ");
            else System.out.print(best[i] + " ");
        }

    }

}
