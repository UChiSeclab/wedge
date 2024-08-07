import java.util.*;
import java.io.*;
import java.math.*;
public class Solution{
    public static void main(String[] args)throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int[] b = new int[150003];
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<n;i++) b[Integer.parseInt(st.nextToken())]++;
        int[] c = new int[150003];
        for(int i=1;i<150001;i++){
            //out.println(i+" "+b[i]+" "+c[i]);
            if(i>1&&b[i]>0&&c[i-1]==0){
                //out.println(i+" "+(i-1));
                c[i-1]++;
                b[i]--;
            }
            if(b[i]>1&&c[i+1]==0){
                //out.println(i+" "+(i+1));
                c[i+1]++;
                b[i]--;
            }else if(b[i]==1&&c[i+1]==0&&c[i]>0){
                c[i+1]++;
                b[i]--;
            }
            c[i] += b[i];
            //if(b[i]!=0) out.println(i+" "+(i));
            b[i] = 0;
        }
        int sum = 0;
        for(int i=1;i<=150001;i++) if(c[i]!=0) sum++;
        out.println(sum);
        out.flush(); 
    }
    
}