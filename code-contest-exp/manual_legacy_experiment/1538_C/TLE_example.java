import java.util.Scanner;

public class BOOMBAAM_MAXE_EXPRESS {
    public static void main(String[] args) {
        Scanner list = new Scanner(System.in);
        int T=list.nextInt();
        for(int i=0;i<T;i++){
            int n=list.nextInt();
            int l= list.nextInt();
            long r=list.nextLong();
            int b=0;
            int[]hi=new int[n];
            for(int k=0;k<n;k++){
                hi[k]=list.nextInt();


            }
            for(int j=0;j<n;j++){
                if(j>r){
                    continue;
                }
                for(int h=j+1;h<n;h++){
                    if (hi[h]+hi[j]<=r && l<=hi[h]+hi[j]){
                        b++;
                    }

                }

            }
            System.out.println(b);

        }





    }
}