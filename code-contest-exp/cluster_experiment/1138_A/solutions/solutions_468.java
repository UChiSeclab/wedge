//https://codeforces.com/problemset/problem/1138/A
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Problem_1138_A {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int n = scanner.nextInt();
        int [] array = new int[n];
        int maxOne = Integer.MIN_VALUE;
        int maxTwo = Integer.MIN_VALUE;
        int countOne = 0;
        int countTwo = 0;
        int ans = Integer.MIN_VALUE;
        ArrayList<Integer> list = new ArrayList<>();

        for (int i=0; i<n; i++){
            array[i] = scanner.nextInt();
            if (array[i] == 2){
                countTwo++;
                maxTwo = Math.max(maxTwo, countTwo);
                list.add(countOne);
                countOne = 0;
            }else {
                countOne++;
                maxOne = Math.max(maxOne, countOne);
                list.add(countTwo);
                countTwo = 0;
            }
        }
        if (countOne !=0)
            list.add(countOne);
        if (countTwo != 0)
            list.add(countTwo);

        list.removeAll(Collections.singleton(0));

        for (int i=0; i<list.size()-1; i++){
            ans = Math.max(ans, Math.min(list.get(i), list.get(i+1)));
        }

//        System.out.println(list);

        System.out.println(ans * 2);




        scanner.close();
    }
}
