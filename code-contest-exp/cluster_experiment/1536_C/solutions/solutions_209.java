import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            String str = br.readLine();
            int []d=new int[n];
            int []k=new int[n];

            StringBuilder sb=new StringBuilder();

            if(str.charAt(0)=='D')d[0]=1;
            else k[0]=1;

            for(int i=1;i<n;i++){
                if(str.charAt(i)=='D'){
                    d[i]=d[i-1]+1;
                }else{
                    d[i]=d[i-1];
                }

                if(str.charAt(i)=='K'){
                    k[i]=k[i-1]+1;
                }else{
                    k[i]=k[i-1];
                }
            }
            HashMap<String,Integer> map=new HashMap<>();

            for(int i=0;i<n;i++){
                int gcd=gcd(d[i], k[i]);
                d[i]=d[i]/gcd;
                k[i]=k[i]/gcd;
            }
            
            sb.append(1+" ");
            if(d[0]==0){
                map.put("0#1", 1);
            }else{
                map.put("1#0", 1);
            }
            for(int i=1;i<n;i++){
                String s=d[i]+"#"+k[i];
                map.put(s, map.getOrDefault(s, 0)+1);
                sb.append(map.get(s)+" ");
            }

            // HashMap<Double,Integer> map=new HashMap<>();
            // sb.append(1+" ");
            // if(d[0]==0){
            //     map.put(0.0,1);
            // }else{
            //     map.put(Integer.MAX_VALUE*1.0,1);
            // }

            // for(int i=1;i<n;i++){
            //     double ratio=0.0;
            //     if(k[i]==0){
            //         ratio=Integer.MAX_VALUE*1.0;
            //     }else{
            //         ratio=(d[i]*1.0)/(k[i]*1.0);
            //     }
            //     map.put(ratio,map.getOrDefault(ratio, 0)+1);
            //     sb.append(map.get(ratio)+" ");
            // }

            System.out.println(sb.toString());
        }
    }
    public static int gcd(int a,int b){
        if(a==0)return b;

        return gcd(b%a,a);
    }
}
