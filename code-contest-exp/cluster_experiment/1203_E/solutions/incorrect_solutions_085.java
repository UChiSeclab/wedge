import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        int n = in.nextInt();
//        int mx =150001;
        int mx = 10;
        int[] num = new int[mx+1];
        for (int i=0;i<n;i++){
            num[in.nextInt()]++;
        }
        for(int i=mx-1;i>=1;i--){
            if(num[i]>0 && num[i+1]==0){
                num[i]--;
                num[i+1]++;
            }
        }
        for(int i=2;i<=mx;i++){
            if(num[i]>0 && num[i-1]==0){
                num[i-1]++;
                num[i]--;
            }
        }
        int c=0;
        for(int i=0;i<=mx;i++){
            if(num[i]>0)
                c++;
        }
        System.out.println(c);
    }
}
