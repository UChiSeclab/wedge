import java.util.*;
public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int n = sc.nextInt();
        HashSet<Integer> set = new HashSet<>();
        for(int i = 0; i < n; i++){
            int a = sc.nextInt();
            if(!set.contains(a)){
                set.add(a);
            }
            else if(!set.contains(a+1)){
                set.add(a+1);
            }
            else if(!set.contains(a-1) && a - 1 != 0){
                set.add(a-1);
            }
        }
        System.out.println(set.size());
	// write your code here
    }
}
