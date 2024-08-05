// package Div2_724;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ProblemC {
    public static void main(String[] args)throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        StringBuilder print=new StringBuilder();
        int test=Integer.parseInt(br.readLine());
        while(test--!=0){
            int n=Integer.parseInt(br.readLine());
            char c[]=br.readLine().toCharArray();
            int sumd=0,sumk=0;
            HashMap<Pair<Integer,Integer>,Integer> map=new HashMap<>();
            for(int i=0;i<n;i++){
                if(c[i]=='D'){
                    sumd++;
                }
                else{
                    sumk++;
                }
                if(sumd==0||sumk==0){
                    print.append(i+1).append(" ");
                }
                else{
                    int g=gcd(sumd,sumk);
                    int d=sumd/g;
                    int k=sumk/g;
                    Pair<Integer,Integer> curr=new Pair<>(d,k);
                    if(map.containsKey(curr)){
                        int val=map.get(curr);
                        print.append(val+1).append(" ");
                        map.put(curr,val+1);
                    }
                    else{
                        print.append(1).append(" ");
                        map.put(curr,1);
                    }
                }
            }
            print.append("\n");
        }
        System.out.println(print.toString());
    }
    public static int gcd(int a,int b){
        if(b==0){
            return a;
        }
        return gcd(b,a%b);
    }
}
