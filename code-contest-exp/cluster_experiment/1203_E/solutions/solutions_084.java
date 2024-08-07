import java.util.*;
import java.io.*;
public class boxer {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Integer[] a=new Integer[n];
        HashSet<Integer> set = new HashSet<>();
        for(int i=0;i<n;i++)
        {
            a[i] = sc.nextInt();
        }
        Arrays.sort(a);
        for(int i=0;i<n;i++){
            int box = a[i];
            if(!set.contains(box-1)&&(box-1)!=0){
                set.add(box-1);
            }else if(!set.contains(box)){
                set.add(box);
            }else if(!set.contains(box+1)){
                set.add(box+1);
            }
        }
        System.out.println(set.size());
    }
}