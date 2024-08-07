import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(tokenizer.nextToken());    // number of boxers
        tokenizer = new StringTokenizer(in.readLine());

        PriorityQueue<Integer> q = new PriorityQueue<>();
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            q.add(Integer.parseInt(tokenizer.nextToken()));
        }
        for (int i = 0; i < n; i++) {
            int current = q.poll();
            if (set.contains(current-1) || current == 1) {
                if (!set.contains(current)) {
                    set.add(current);
                } else if (!set.contains(current+1)) {
                    set.add(current + 1);
                }
            } else if (current > 1){
                set.add(current-1);
            }
        }
        System.out.println(set.toString());
        System.out.println(set.size());
    }
}
