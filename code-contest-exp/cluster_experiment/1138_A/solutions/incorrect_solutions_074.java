import java.util.Scanner;
import java.util.Arrays;
import static java.lang.Math.*;
public class Main{
    public static void main(String[]args){
     Scanner x = new Scanner (System.in);
                int y = x.nextInt();
                int r=0,k=0,u=0,o=0,m=0;
                int [] z = new int [y];
                for(int i=0;i<y;i++){
                    int a = x.nextInt();
                    if(i+1==y){if(a==1){r++;}else{k++;}}
                    if(u>0&&o>0&&(a!=m||i+1==y)){if(a==1){u=0;z[i]=min(r,k)*2;r=0;}else{o=0;z[i]=min(r,k)*2;k=0;}}
                    else{z[i]=0;}
                    if(a==1){u++;r++;m=a;}
                    else {o++;k++;m=a;}
                }
                Arrays.sort(z);
                System.out.println(z[y-1]);
        
    }
}