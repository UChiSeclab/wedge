import java.util.Scanner;

/**
 *
 * @author Akhilesh
 */
public class SushiForTwo1138A {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        int n = scan.nextInt();
        
        int arr[] = new int[n];
        
        for(int i = 0; i < n; i++){
            arr[i] = scan.nextInt();
        }
        
        int prev1 = 0, prev2 = 0;
        int i = 0;
        int k;
        
        while(arr[i] == arr[0]){
            prev1++;
            i++;
        }
        
        k = i;
        
        while(k < n && arr[k] == arr[i]){
            prev2++;
            k++;
        }
        
        int max = Math.min(prev1, prev2);
        
        while(k < n){
            i = k;
            int temp = 0;
            while(k < n && arr[k] == arr[i]){
                temp++;
                k++;
            }
            
            max = Math.max(max, Math.min(prev2, temp));
            prev2 = temp;
        }
        
        System.out.println(max*2);
    }
}