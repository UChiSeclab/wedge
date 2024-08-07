import java.util.ArrayList;
import java.util.Scanner;
public class FG
{public static void main(String[] args){
    Scanner scn = new Scanner(System.in);
    long m = scn.nextLong();
    ArrayList<Long> list=new ArrayList<>();
    short k = scn.nextShort();
    for(long i=0;i<m;i++){
        int h=scn.nextInt();
        String y=Integer.toBinaryString(h);
        list.add((long) y.length());
    }
    int p =0;
    for(long i=0;i<list.size();i++){
        for(long j=i;j<list.size()-1;j++){
            if(Math.abs(list.get(Math.toIntExact(i))-list.get(Math.toIntExact((j + 1))))==k)
                p++;

        }
    }

    System.out.println(p);
}
}
