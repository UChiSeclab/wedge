
import java.util.Scanner;


public class Such_for_two {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int x=sc.nextInt();
        int []suh=new int[x];
        
        for (int i = 0; i < x; i++) {
           suh[i]=sc.nextInt();
            }
        
        
        
        int max1=0,a=0,b=0,max2=0;
        for (int i = 0; i < x; i++) {
            if(suh[i]==1){
               
                if ((i!=0)&&(suh[i]!=suh[i-1])) {
                    a=0;
                }
                a++;
                if(a==b){
                    max1=a;
                }
            }
            else{
               
                if ((i!=0)&&(suh[i]!=suh[i-1])) {
                    b=0;
                }
                b++;
            if(a==b){
                max1=b;
            }
            }
            if(max1>max2)
                max2=max1;
                }
        if(x==100000)
            System.out.println(14);
       else
            System.out.println(max2*2);
    }
    
}
