import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by marsov on 04.03.17.
 */
public class MainD {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        TaskD task = new TaskD();
        task.solve(in);
    }

    static class TaskD {
        int n, k;
        ArrayList<Integer> numbers = new ArrayList<>();
        void solve(Scanner in) {
            n = in.nextInt();
            k = in.nextInt();
            for (int i = 0; i < n; i++)
                numbers.add(in.nextInt());
            int pairs = 0;
                for (int i = 0; i < numbers.size(); i++)
                    for (int j = i+1; j < numbers.size(); j++)
                        if (k == difference(numbers.get(j),numbers.get(i))) {
                            pairs++;
                        }

            System.out.println(pairs);
        }

        static int binlog(int bits) {
            int log = 0;
            if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
            if( bits >= 256 ) { bits >>>= 8; log += 8; }
            if( bits >= 16  ) { bits >>>= 4; log += 4; }
            if( bits >= 4   ) { bits >>>= 2; log += 2; }
            return log + ( bits >>> 1 );
        }

        int difference(int num1, int num2) {
            int k = 0;
            while (num1 != 0 && num2 != 0) {
                if (num1 % 2 != num2 % 2)
                    k++;
                num1 >>= 1;
                num2 >>= 1;
            }

            if (num1 != 0) {
                k+=binlog(num1);
            } else {
                k+=binlog(num2);
            }
            return k;
        }

    }
}
