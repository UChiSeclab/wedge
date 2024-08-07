import java.util.*;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int str[] = new int[n];
        for(int i=0;i<n;i++)
            str[i] = sc.nextInt();
        int temp1 =1,temp2=0,ans=0;
        for(int i =0;i<(n-1);i++){
            if(str[i]==str[i+1]){
                temp1++;
            }else{
                if(2*Math.min(temp1,temp2)>ans){
                    ans =2*Math.min(temp1,temp2);
                }
                temp2 = temp1;
                temp1=1;
            }
        }
        if(2*Math.min(temp1,temp2)>ans){
                    ans =2*Math.min(temp1,temp2);
                }
        System.out.println(ans);
}
}