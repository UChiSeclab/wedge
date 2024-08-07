
import java.util.Scanner;
import java.util.Stack;

public class Problem_1138_A {

    public static void main(String[] args) {
        Scanner Input = new Scanner(System.in);
        Stack S1 = new Stack();
        Stack S2 = new Stack();
        int N = Input.nextInt();
        int C = 0, C2 = 0;
        int Size = 0, Size2 = 0;
        for (int I = 0; I < N; I++) {
            int Get = Input.nextInt();
            if (Get == 1 && C == 0) {
                S1.push(1);
            } else if (!S1.isEmpty() && Get == 2) {
                S1.pop();
                C++;
            } else {
                S1.removeAllElements();
            }
            Size = Math.max(Size, C);
            if (Get == 2 && C2 == 0) {
                S2.push(1);
            } else if (!S1.isEmpty() && Get == 2) {
                S2.pop();
                C2++;
            } else {
                S2.removeAllElements();
            }
            Size2 = Math.max(Size2, C2);

        }
        int Out = Math.max(Size, Size2);
        System.out.println(2 * Out);
    }

}
