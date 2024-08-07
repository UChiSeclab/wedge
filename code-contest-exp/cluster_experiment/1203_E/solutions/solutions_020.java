import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// 1203E Boxers
public class Boxers{
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            br.readLine();
            String[] lst = br.readLine().split(" ");
            List<Integer> lst1 = new ArrayList<>();
            for(String s:lst){
                lst1.add(new Integer(s));
            }
            lst1.sort(Integer::compareTo);
            int[] lst2 = new int[150010];
            int sum = 0;
            for(int i = 0; i < lst1.size(); i ++){
                Integer i1 = lst1.get(i);
                if(i1 - 1 > 0 && lst2[i1-1] == 0){
                    lst2[i1-1] = i1-1;
                    sum ++;
                }else if(i1 > 0 && lst2[i1] == 0){
                    lst2[i1] = i1;
                    sum++;
                }else if(i1  + 1 > 0 && lst2[i1+1] == 0){
                    lst2[i1+1] = i1 + 1;
                    sum++;
                }
            }
            System.out.println(sum);



        }catch(IOException io){

        }
    }
}
