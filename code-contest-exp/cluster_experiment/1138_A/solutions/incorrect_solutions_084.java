import java.util.ArrayList;
import java.util.Scanner;

public class A1138 {
    static int[] sushi;
    static ArrayList<Integer> consecutive = new ArrayList<>();

    public static void grabConsecutive() {
        int prevSushi = sushi[0];
        int count = 1;
        for (int i = 1; i < sushi.length; i++) {
            if (sushi[i] != prevSushi) {
                consecutive.add(count);
                count = 1;
            }
            else count++;
            prevSushi = sushi[i];
        }
        consecutive.add(count);
    }

    public static int getMax() {
        int maxConsec = 0;
        for (int i = 1; i < consecutive.size(); i++) {
            maxConsec = Math.max(maxConsec, Math.min(consecutive.get(i-1), consecutive.get(i)));
        }
        return 2 * maxConsec;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = Integer.parseInt(sc.nextLine());
        sushi = new int[size];
        for (int i = 0; i < size; i++) {
            sushi[i] = Integer.parseInt(sc.next());
        }

        if (size != 0) {
            grabConsecutive();
            System.out.println(getMax());
        }
        else {
            System.out.println(0);
        }
        System.out.println(consecutive);
    }
}
