import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class JavaApplication15 {
   
    public static void main(String[] args) {
        int n,i,c=0;
        Integer t[];
        Scanner sc = new Scanner(System.in);
        Map <Integer,Integer> s = new HashMap <Integer,Integer>();
        n = sc.nextInt();
        t = new Integer [n];
        for(i=0;i<n;i++)
            t[i] = sc.nextInt();
        Arrays.sort(t);
        for(i=0;i<n;i++){
           if(!s.containsKey(t[i])){
               s.put(t[i], t[i]);
               c++;
            }
           else if(!s.containsKey(t[i] + 1)){
                s.put(t[i] + 1, t[i] + 1);
                c++;
            }
           else if(t[i] > 1 && !s.containsKey(t[i] - 1)){
              s.put(t[i] - 1, t[i] - 1); 
              c++;
            }
        }
        System.out.println(c);
    }
}
