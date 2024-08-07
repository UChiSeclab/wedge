import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<Integer> boxers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            boxers.add(sc.nextInt());
        }
        Collections.sort(boxers);
        int soFarLargest = 0;
        HashSet<Integer> out = new HashSet<>();
        for (int i = 0; i < boxers.size(); i++) {
            int tmp = boxers.get(i);
            if (boxers.get(i).equals(soFarLargest)) {
                out.add(tmp + 1);
                soFarLargest = tmp + 1;
            } else if (tmp - 1 > soFarLargest) {
                out.add(tmp - 1);
                soFarLargest = tmp - 1;
            } else if (out.contains(tmp)) {
                
            } else {
                out.add(tmp);
                soFarLargest = tmp;
            }
        }
        System.out.println(out.size());
    }
}
