
import java.util.ArrayList;
import java.util.Scanner;
public class FG
{public static void main(String[] args){
    Scanner scn = new Scanner(System.in);
    int m = scn.nextInt();
    ArrayList<Integer> list=new ArrayList<>();
    int k = scn.nextInt();
    for(int i=0;i<m;i++){
        int h=scn.nextInt();
        String y=Integer.toBinaryString(h);
        list.add(y.length());
    }
    int p =0;
    for(int i=0;i<list.size();i++){
        for(int j=i;j<list.size()-1;j++){
            if(Math.abs(list.get(i)-list.get(j+1))==k)
                p++;

        }
    }
    System.out.println(p);
}
}