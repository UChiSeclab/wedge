import java.util.*;

public class Main{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n =sc.nextInt();
        int[] arr=new int[n];
        for(int i=0;i<n;i++)
            arr[i]=sc.nextInt();
        HashSet<Integer> set =new HashSet<>();
        int count=0;
        for(int i=0;i<n;i++)
        {
            if(!set.contains(arr[i])){
                set.add(arr[i]);
                count++;
            }
            else if(arr[i]-1 !=0 && !set.contains(arr[i]-1)){
                    set.add(arr[i]-1);
                    count++;
                }
            else if(!set.contains(arr[i]+1)){
                    set.add(arr[i]+1);
                    count++;
            }
        }
        System.out.println(count);
    }
}