import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class JavaApplication2 {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        int n=input.nextInt();
        int [] a=new int[n];
        for(int i=0;i<n;i++)
            a[i]=input.nextInt();
        int r=0;
        int finr=0;
        int n1=0,n2=0;
        
        for(int i=0;i<n;){
            
            if(a[i]==1){
                n1=0;
                while(a[i]==1){
                    n1++;
                    
                    i++;
                    if(i==n)
                        break;
                }
            }
            else{
                n2=0;
                while(a[i]==2){
                    n2++;
                    
                    i++;
                    if(i==n)
                        break;
                }
            }
            if(n1<n2)
                r=n1*2;
            else
                r=n2*2;
            if(finr<r)
                finr=r;
        }
        System.out.println(finr);
    }
    public static int min(ArrayList <Integer> a){
        int min=a.get(0);
        for(int i=1;i<a.size();i++)
            if(a.get(i)<min)
                min=a.get(i);
        return min;
    }
    
}
