import java.util.Scanner;
public class JavaApplication5 {
    static Scanner in=new  Scanner(System.in);
    public static void main(String[] args) {
        int n=in.nextInt(),arr[]=new int[n],accomulator[]=new int[1500070],result=0;
        for (int i = 0; i < n; i++) {
            arr[i]=in.nextInt();
            accomulator[arr[i]]++;
        }
        for (int i = 1; i <=150001; i++) {
        if (accomulator[i-1]>0){
            result++;
        }else{
        if((accomulator[i]>0)){
        result++;accomulator[i]--;}
        else{
        if(accomulator[i+1]>0){
            result++;accomulator[i+1]--;
        }}}}
        System.out.println(result);
    }
}