import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Boxers {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        ArrayList<Integer> list = new ArrayList<Integer>();
        st = new StringTokenizer(br.readLine());
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < n; i++) {
            list.add(Integer.parseInt(st.nextToken()));
        }
        Collections.sort(list);
        for (int i = 0; i < n; i++) {
            int s = set.size();
            if (list.get(i) - 1 > 0) {
                set.add(list.get(i) - 1);
            }
            if (set.size() == s) {
                set.add(list.get(i));
            }
            if (set.size() == s) {
                set.add(list.get(i) + 1);
            }
        }
        System.out.print(set.size());
    }
}
