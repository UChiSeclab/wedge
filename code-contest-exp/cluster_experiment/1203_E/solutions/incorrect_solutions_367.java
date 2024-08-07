import java.util.Scanner;
public class HelloWorld{

     public static void main(String []args){
        Scanner obj= new Scanner(System.in);
        int n= obj.nextInt();
        int[] list= new int[n];
        for(int i=0;i<n;i++){
            list[i]= obj.nextInt();
        }
        int result=0;
        for(int i=0;i<n;i++){
            int current=0;
            int cu= list[i];
            for(int f=0;f<n;f++){
                if(Math.abs(cu-list[f])<=1){
                  current++;
                } 
            }
            if(current>result) result=current;
        }
	System.out.println(result);
     }
}
