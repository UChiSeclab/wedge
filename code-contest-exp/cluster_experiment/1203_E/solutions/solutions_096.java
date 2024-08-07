import java.util.*;
public class Main {
    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int arr[] = new int[150005];
        HashSet<Integer> curr = new HashSet<>();
        HashSet<Integer> set = new HashSet<>();
        for(int i = 0; i < n; i++){
            int k = s.nextInt();
            arr[k]++;
            curr.add(k);
        }
        if(arr[1] == 1){
            set.add(1);
        }
        else if(arr[1] > 1){
            set.add(1);
            set.add(2);
        }
        for(int i = 2; i < arr.length; i++){
            if(arr[i] >= 3){
                set.add(i - 1);
                set.add(i);
                set.add(i + 1);
            }
            else if(arr[i] == 2){
                if(!set.contains(i - 1)){
                    set.add(i - 1);
                     if(set.contains(i)){
                           set.add(i + 1);
                       }
                       else{
                           set.add(i);
                       }
                    
                }
                else{
                   set.add(i);
                   set.add(i + 1);
                }
            }
            else if(arr[i] == 1){
                   if(!set.contains(i - 1)){
                       set.add(i - 1);
                   }
                   else{
                       if(set.contains(i)){
                           set.add(i + 1);
                       }
                       else{
                           set.add(i);
                       }
                   }
                
            }
            
        
        }
       
        
        
        System.out.println(set.size());
    }
}