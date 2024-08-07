import java.util.*;
public class p1536C {
    public static void main(String[] args) {
        Scanner scn=new Scanner(System.in);
        int t=scn.nextInt();
        for(int i1=0;i1<t;i1++){
            int n=scn.nextInt();
            String s=scn.next();
            int[] arr1=new int[n+1];
            int[] arr2=new int[n+1];
            int[] ans=new int[n+1];
            for(int i=0;i<n;i++){
                if(s.charAt(i)=='D'){
                    arr1[i+1]=arr1[i]+1;
                    arr2[i+1]=arr2[i];
                }
                else{
                    arr2[i+1]=arr2[i]+1;
                    arr1[i+1]=arr1[i];
                }
                ans[i+1]=gcd1(arr1[i+1],arr2[i+1]);
            }
            for(int i=1;i<n+1;i++){
                System.out.print(ans[i]+" ");
            }
            System.out.println();
        }
    }
    public static int gcd1(int a,int b){
        if(a>=b){
            while(b>0){
                int m=a%b;
                a=b;
                b=m;
            }
            return(a);
        }
        else{
            return(gcd1(b,a));
        }
    }
}
