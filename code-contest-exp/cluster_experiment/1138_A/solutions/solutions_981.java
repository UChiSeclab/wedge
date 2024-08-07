//package Div2_542;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Sushi{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        String input[] = br.readLine().trim().split(" ");
        ArrayList<Integer> list = new ArrayList<>();
        int k = Integer.parseInt(input[0]);
        int sum=0;
        for(int i=0;i<n;i++){
            int current = Integer.parseInt(input[i]);
            if(current==k){
                sum++;
            }else{
                k=current;
                list.add(sum);
                sum=1;
            }
            if(i==n-1){
                list.add(sum);
            }
        }
        int max = 0;
        for(int i=0;i<list.size()-1;i++){
            int x = Math.min(list.get(i),list.get(i+1));
            x*=2;
            max = Math.max(x,max);
        }
        System.out.println(max);
    }
}

