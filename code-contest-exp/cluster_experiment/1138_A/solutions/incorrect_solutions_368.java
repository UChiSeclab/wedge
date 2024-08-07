
import java.util.Scanner;



public class JavaApplication170 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        int sn = 0;
        int to = 0;
        int saveto = 0;
        int savesn = 0;

        while(n != 0){
            n--;
            int x = sc.nextInt();
            if(x == 1){
                to++;
                saveto = Math.max(saveto, to);
            }else{
                to = 0;
            }
            
            if(x == 2){
                sn++;
                savesn = Math.max(savesn, sn);
            }else{
                sn = 0;
            }
        }
        System.out.println(Math.min(saveto, savesn)*2);
        

        
        

    }
    
}
 
