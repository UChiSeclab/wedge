import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author German le yo
 */
public class Main{
    
    static int[] answer;
    static int[] a;
    static int n;
    static int getStepsToParity(int i, HashSet<Integer> lasts, int recLimit, int recCurrent){
        if(recLimit != -1 && recCurrent == recLimit) return answer[i];
        if(answer[i] != 0) return answer[i];
        //System.out.printf("Now here with %d and set%s\n", i, lasts);
        
        lasts.add(i);
     
        int min = 0, tmp;
        for(int option: new int[]{i + a[i], i - a[i]}) if(option < n && option >= 0){
            // If found parity at distance 1 save it and return;
            if(((a[option] + a[i]) & 0x01) == 1) {
                lasts.remove(i);
                return answer[i] = 1;
            } else if(!lasts.contains(option)){
                tmp = getStepsToParity(option, lasts, recLimit, recCurrent + 1);
                if(tmp != 0 && (min == 0 || 1 + tmp < min)) min = 1 + tmp;
            }
        }
        
        lasts.remove(i);
        return answer[i] = min;
        
    }
    
    public static void main(String[] args){
        //* Console input
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        a = new int[n];
        for(int i = 0; i < n; i++) a[i] = scan.nextInt();
        scan.close();

        answer = new int[n];
        
        // Solve for answer = 1;
        for(int i = 0; i < n; i++) {
            getStepsToParity(i, new HashSet(), 1, 0);
        }
        // Solve for each case
        for(int i = 0; i < n; i++) {
            getStepsToParity(i, new HashSet(), -1, 0);
        }
        
        StringBuilder toPrint = new StringBuilder();
        for(int i = 0; i < n; i++){
            //if(i != 0)toPrint.append(' ');
            if(answer[i] == 0) toPrint.append("-1");
            else toPrint.append(answer[i]);
            toPrint.append(' ');
        }
        
        System.out.println(toPrint.toString());
    }
}

	 			   	  	 	 	   	 		 		