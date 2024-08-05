import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



public class E {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n,k;
        String line = scanner.next();
         k = scanner.nextInt();
         n = line.length();
         long total = ((long)n) * ((long) (n+1)) /2;
        if (total < k) {
            System.out.println("No such line.");
        }
        else {
            List<Integer> lists = new ArrayList<Integer>();
            List<Integer> tmpList = new ArrayList<Integer>();
            List<Integer> tmp = null;
            for(int i = 0; i < line.length(); i ++) {
                lists.add(i);
            }
            
            while (k > 0) {
                int i = 'a';
                for (i = 'a'; i <= 'z' ; i ++) {
                    tmpList.clear();
                    long m = 0;
                    for (int j = 0; j < lists.size() && k > 0; j ++) {
                        int index = lists.get(j);
                        if (line.charAt(index) <= i) {
                            m += line.length() - index;
                            //k -= line.length() - index;
                                                        
                        }
//                      else if (line.charAt(index) == i) {
//                          m ++;
//                      } 
                    }
                    if (m >= k) {
                        break;                  
                    } 
                }               
                tmpList.clear();
                for (int j = 0; j < lists.size(); j ++) {
                    int index = lists.get(j);
                    if (line.charAt(index) < i) {
                        k -= line.length() - index;
                    }
//                  else {
//                  }
                    else if (line.charAt(index) == i ) {
                        if (index + 1 < line.length()) {
                            tmpList.add(index + 1);
//                          System.out.println("in : " + (index + 1));
                        }
                        k--;
                    }
                }
                System.out.print((char)i);
                //System.out.println((char)i + " " + k);
                tmp = tmpList;
                tmpList = lists;
                lists = tmp;
                //System.out.println(lists.get(0));
            }
            System.out.println();
        }
        
    }
}
