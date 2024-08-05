import java.util.*;
public class boxer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n =sc.nextInt();
        List<Integer> list = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for(int i=0;i<n;i++){
            list.add(sc.nextInt());
        }
        Collections.sort(list);
        for(int i=0;i<n;i++){
            int box = list.get(i);
            if(!list.contains(box-1)&&(box-1)!=0){
                list2.add(box-1);
            }else if(!list.contains(box)){
                list2.add(box);
            }else if(!list.contains(box+1)){
                list2.add(box+1);
            }
        }
        System.out.println(list.size());
    }
}