import java.util.*;
import java.lang.*;
import java.io.*;
public class Main{
    static class FastReader{
        BufferedReader br;
        StringTokenizer st;
        public FastReader(){
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        String next(){
            while(st == null || !st.hasMoreElements()){
                try{
                    st = new StringTokenizer(br.readLine());
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
        int nextInt(){
            return Integer.parseInt(next());
        }
        long nextLong(){
            return Long.parseLong(next());
        }
        String nextLine(){
            String sr = "";
            try{
                sr = br.readLine();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            return sr;
        }
    }
    public static void main(String args[]) throws Exception{
        FastReader fr = new FastReader();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = fr.nextInt();
        int a[] = new int[n];
        for(int i = 0;i<n;i++){
            a[i] = fr.nextInt();
        }
        int o = 0;
        int t = 0;
        int ans = 0;
        for(int i = 0;i<n;i++){
           if(a[i] == 1)
           {
               o++;
               ans = Math.max(ans,Math.min(o,t));
               if(((i+1)<n) && a[i+1]!=1)
               t = 0;
           }
           else{
                t++;
               ans = Math.max(ans,Math.min(o,t));
              if(((i+1)<n) && a[i+1]!=2)
               o = 0;
           }
        }
        out.write(2*ans+"");
        out.flush();
            } 
}