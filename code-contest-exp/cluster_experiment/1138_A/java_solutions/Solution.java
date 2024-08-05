import java.util.Scanner;

public class Solution{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        byte[] sushi = new byte[n];
        for(int i = 0;i < n;i++){
            sushi[i] = input.nextByte();
        }
        input.close();
        int max = 1 , maxFinal = 1;
        for(int i = 1;i < n;i++){
            if(sushi[i] == sushi[i-1]){
                max++;
            } else {
                if(check(sushi, i, max, sushi[i])){
                    if(max >= maxFinal){
                        maxFinal = max;
                    }
                }
                max = 1;
            }
        }
        System.out.println(maxFinal*2);

    }
    public static boolean check(byte[] sushi , int t , int max , byte wich){
        int equalMax = 0 , checkNum = 1;
        if(wich == 1){
            checkNum = 2;
        }

        for(int i = t;i < sushi.length;i++){
            if(sushi[i] == wich){
                equalMax++;
            }
            if(sushi[i] == checkNum || i+1 == sushi.length || equalMax == max)
                break;
        }
        if(max == equalMax){
            return true;
        } else {
            return false;
        }
    }
}