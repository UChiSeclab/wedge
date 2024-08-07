import java.io.*;
import java.util.*;
import java.lang.*;

public class Rextester{
    public static void main(String[] args)throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        StringBuffer sb = new StringBuffer();
        while(t-->0){
            int n = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] array = new int[n];
            int[] count = new int[n];
            for(int i=0;i<n;i++){
                array[i]=Integer.parseInt(st.nextToken())-1;
                count[array[i]]++;
            }
            int x = 0;
            for(int i=0;i<n;i++){
                if(count[i]==1){
                    x++;
                }
            }
            boolean[] ans = new boolean[n];
            if(x==n){
                ans[0]=true;
            }
            if(count[0]>0){
                ans[n-1]=true;
            }
            int l=0,r=n-1;
            for(int i=n-1;i>0;--i){
                if(!ans[n-1]){
                    break;
                }
                ans[i]=true;
                int next= n-i-1;
                if((array[l]==next||array[r]==next)&&count[next+1]>0&&--count[next]==0){
                    if(array[l]==next){
                        l++;
                    }
                    else{
                        r--;
                    }
                    continue;
                }
                break;
            }
            for(boolean z:ans){
                sb.append(z?1:0);
            }
            sb.append("\n");
        }
        br.close();
        System.out.println(sb);
    }
}
            
                    
                      
                   