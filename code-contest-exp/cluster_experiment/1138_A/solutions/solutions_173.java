import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String n = in.nextLine();
        char[] ch = in.nextLine().replaceAll(" ", "").toCharArray();
        int count1 = 1, count2 = 1, max = 0;
        for (int i = 0; i < ch.length - 1; i++) {
            if (ch[i] == ch[i + 1]) {
                count1++;
            }
            if (i == ch.length - 2 || ch[i] != ch[i + 1]) {
                if (count2 >= count1 && count1 > max) {
                    max = count1;
                } else if (count1 > count2 && count2 > max) {
                    max = count2;
                }
                count2 = count1;
                count1 = 1;
            }
        }
        System.out.println(2 * max);
    }
}