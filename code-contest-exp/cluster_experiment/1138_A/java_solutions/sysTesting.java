import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class sysTesting {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int numSubm = in.nextInt();
        int counterFor2 = 0;
        int counterFor1 = 0;
        LinkedList<Integer> linkedList1 = new LinkedList<>();
        LinkedList<Integer> linkedList2 = new LinkedList<>();
        int prev = 0;
        int first1 = 0;
        int first2 = 0;
        for(int i = 0; numSubm > i;i++){
            int num = in.nextInt();
            if (num == 2){
                counterFor2++;
                if (prev != num && first2 != 0){
                    counterFor2 = 0;
                }
                linkedList2.add(counterFor2);
                first2++;
            }
            else if (num == 1){
                counterFor1++;
                if (prev != num && first1 != 0){
                    counterFor1 = 0;
                }

                linkedList1.add(counterFor1);
                first1++;
            }
            prev = num;
        }
        Collections.sort(linkedList1, Collections.reverseOrder());
        Collections.sort(linkedList2, Collections.reverseOrder());
        System.out.println(linkedList1.getFirst() >= linkedList2.getFirst() ? linkedList2.getFirst() * 2: linkedList1.getFirst() * 2);
    }
}
