
import java.util.HashMap;
import java.util.Scanner;

public class p1536C {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = Integer.parseInt(in.nextLine());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            in.nextLine();

            String s = in.nextLine();
            HashMap<String, Integer> prefixes = new HashMap<>();
            int kCount = 0, dCount = 0;

            for (char c: s.toCharArray()){
                if (c == 'K')
                    kCount++;
                else
                    dCount++;
                int kC = kCount;
                int dC = dCount;

                if (kC == 0)
                    dC = 1;
                else if (dC == 0)
                    kC = 1;
                else{
                    int g = gcd(kC, dC);
                    kC/=g;
                    dC/=g;
                }

                String k = kC + ":" +dC;
                if (!prefixes.containsKey(k))
                    prefixes.put(k, 1);
                else
                    prefixes.put(k, prefixes.get(k)+1);
                sb.append(prefixes.get(k)).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static int gcd(int a, int b) {
        return a==0?b:gcd(b%a, a);
    }
}
