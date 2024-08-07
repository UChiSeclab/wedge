
import java.util.*;

public class Boxers {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n= in.nextInt();
        Integer[] a= new Integer[n];
        for(int i=0;i<n;i++)
            a[i]=in.nextInt();
        //Set<Integer> set = new HashSet<>();
        Arrays.sort(a);
        int last=150003;
        int count=0;
        for(int i=n-1;i>=0;i--){
            if(a[i]+1<last){
                last=a[i]+1;
                count++;
            }else{
                if(a[i] < last){
                    last=a[i];
                    count++;
                }
                else if(a[i]-1 < last && a[i]-1!=0){
                    last=a[i]-1;
                    count++;
                }
            }

        }
        System.out.println(count);
        //System.out.println(set.size());


    }
}
