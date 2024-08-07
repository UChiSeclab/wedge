import java.util.*;
import java.io.*;
public class Main{
   static class FastReader{
        BufferedReader br;
        StringTokenizer st;
        public FastReader(){
            br = new BufferedReader(new
            InputStreamReader(System.in));
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
          return  Integer.parseInt(next());
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
    public static void main(String args[])throws Exception{
         FastReader ft = new FastReader();
         BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
         int n = ft.nextInt();
         int a[] = new int[n];
         for(int i = 0;i < n;i++)
         a[i] =  ft.nextInt();
         int ans = 0;
         for(int i = 0;i < n;i++){
             int o = 0;
             int t = 0;
             for(int j = i;j < n;j++){
                 if(a[j]==1)
                 o++;
                 else
                 t++;
                 if(o == t){
                 ans = Math.max(ans,(j-i+1));
                 break;}
             }
         }
         System.out.println(ans);
        }}