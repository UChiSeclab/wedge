import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class P579E {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] a = new int[n];
        for(int i=0; i<n; i++) {
            a[i] = sc.nextInt();
        }

        HashSet<Integer> known = new HashSet<>();

//        Arrays.sort(a);
//
//        int team = 0;
//
//        for(int i=n-1; i>=0; i--) {
//            if(i == n-1) {
//                a[i]++;
//                team++;
//            } else {
//                if(a[i+1] == (a[i] + 1)) {
//                    team++;
//                } else if(a[i+1] == a[i]) {
//                    a[i]--;
//                }
//            }
//        }
//
//        System.out.println(team);

        for(int i=n-1; i>=0; i--) {
            if (!known.contains(a[i] + 1)) {
                known.add(a[i] + 1);
            } else if (!known.contains(a[i])) {
                known.add(a[i]);
            } else if (!known.contains(a[i] - 1) && a[i] != 1) {
                known.add(a[i] - 1);
            }
        }

        System.out.println(known.size());
    }
}
