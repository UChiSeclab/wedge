/* IMPORTANT: Multiple classes and nested static classes are supported */

/*
 * uncomment this if you want to read input.
//imports for BufferedReader
import java.io.BufferedReader;
import java.io.InputStreamReader;

//import for Scanner and other utility classes
import java.util.*;
*/

// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail
import java.util.*;
public class Main {
    public static void main(String args[] ) throws Exception {
        /* Sample code to perform I/O:
         * Use either of these methods for input

        //BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();                // Reading input from STDIN
        System.out.println("Hi, " + name + ".");    // Writing output to STDOUT

        //Scanner
        Scanner s = new Scanner(System.in);
        String name = s.nextLine();                 // Reading input from STDIN
        System.out.println("Hi, " + name + ".");    // Writing output to STDOUT

        */

        // Write your code here
        
        Scanner s=new Scanner(System.in);
        TreeMap<Integer,Integer> map=new TreeMap<>();
        int N=s.nextInt();
        for(int i=0;i<N;i++){
            int temp=s.nextInt();
            if(map.containsKey(temp))
               map.put(temp,map.get(temp)+1);
            else
               map.put(temp,1);
            
        }
        
        HashSet<Integer> set =new HashSet<Integer>();
        
        for(Integer temp: map.keySet()){
            int val=map.get(temp);
            if(temp==1){
                if(val==1)
                  set.add(1);
                else{
                    set.add(1);
                    set.add(2);
                }  
                continue;
            }
            
                if(!set.contains(temp-1)){
                    set.add(temp-1); val--;
                }
                if(!set.contains(temp) && val>0){
                    set.add(temp);  val--;
                }
                if(!set.contains(temp+1) && val>0){
                    set.add(temp+1); val--;
                }
                
            }
            
            
        
        
        System.out.println( set.size());
    }
}
