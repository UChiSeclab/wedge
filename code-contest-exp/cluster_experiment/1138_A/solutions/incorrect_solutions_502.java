import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String n = in.nextLine();
        String s = in.nextLine().replaceAll(" ", "");
        String r1 = s.replaceAll("^(.*?)((1)\\3*(2)\\4*)(.*?)$", "$2");
        String r2 = s.replaceAll("^(.*?)((2)\\3*(1)\\4*)(.*?)$", "$2");
        int l1 = (int) r1.chars().filter(i -> i == '1').count();
        int l2 = (int) r1.chars().filter(i -> i == '2').count();
        int m1 = (int) r2.chars().filter(i -> i == '1').count();
        int m2 = (int) r2.chars().filter(i -> i == '2').count();
        int min1 = Math.min(l1, l2);
        int min2 = Math.min(m1, m2);
        System.out.println(2 * Math.max(min1, min2));
    }
}
