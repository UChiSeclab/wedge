import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long number = sc.nextLong();
        int[] type = new int[100001];
        for (int i = 0; i < number; i++)
            type[i] = sc.nextInt();

        int ans1 = 0,temp1 = 0;
        boolean flush1 = true;
        Stack<Integer> one = new Stack<>();
        for (int i = 0; i < number; i++)
        {
            if (type[i] == 1 && flush1)
            {
                one.push(type[i]);
            }
            else
            {
                if (type[i] == 2)
                {
                    if (one.empty()) {flush1=true;continue;}
                    one.pop();
                    temp1++;
                    flush1 = false;
                }
                if (type[i] == 1)
                {
                    while (!one.empty()) one.pop();
                    temp1=0;
                    flush1 = true;
                    one.push(type[i]);
                }
                if (temp1 > ans1) ans1 = temp1;
            }
        }


        int ans2 = 0,temp2=0;boolean flush2 = true;
        Stack<Integer> two = new Stack<>();
        for (int r = 0; r < number; r++)
        {
                if (type[r] == 2 && flush2)
                {
                    two.push(type[r]);
                }
                else
                {
                    if (type[r] == 1)
                    {
                        if (two.empty()) {flush2=true;continue;}
                        two.pop();
                        temp2++;
                        flush2 = false;
                    }
                    if (type[r] == 2)
                    {
                        while (!two.empty()) two.pop();
                        temp2=0;
                        flush2 = true;
                        two.push(type[r]);
                    }
                    if (temp2 > ans2) ans2 = temp2;
                }

        }

        int ans = ans1>ans2?ans1:ans2;
        System.out.println(ans*2);

    }

}
