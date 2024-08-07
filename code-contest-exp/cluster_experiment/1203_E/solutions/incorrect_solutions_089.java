
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] A = new int[n];
        int[] B = new int[150002];
        for(int i=0;i<n;i++) {
            A[i] = in.nextInt();
            if(A[i]!=1 && B[A[i]]!=1) {
                B[A[i]] =1;
                B[A[i]+1] = 1;
                B[A[i]-1] = 1;
            }
            else {
                B[1] = 1;
                B[2] = 1;
            }
        }
        int count=0;
        for(int i=0;i<150002;i++) {
            count+=B[i];
        }
        
        System.out.println(count);
        
        
        
        
        
        
    }
    
    
    
    
    
    
    
    
    
}
