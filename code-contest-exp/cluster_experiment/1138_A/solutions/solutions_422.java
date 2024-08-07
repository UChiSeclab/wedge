//package CodeForces_Prac;
import java.util.*;
import java.io.*;
public class Sushi_2 {
    static class FastReader{
        BufferedReader br;
        StringTokenizer st;
        
        public FastReader(){
            br=new BufferedReader(new InputStreamReader(System.in));
        }

        String next(){
            while(st==null || !st.hasMoreElements()){
                try {
                    st=new StringTokenizer(br.readLine());
                } catch (Exception e) {
                
                    e.printStackTrace();
                }

            }
            return st.nextToken();
        }

        int nextInt(){
            return Integer.parseInt(next());
        }

        String nextLine(){
            String str="";
            try {
                str=br.readLine();
            } catch (Exception e) {
                  e.printStackTrace();
            }
            return str;
        }

        long nextLong(){
            return Long.parseLong(next());
        }

    }


    public static void main(String [] args){
         FastReader in = new FastReader ();
         int n = in.nextInt();
         int a[] = new int[n];
         for(int i=0; i<n;i++){
             a[i] = in.nextInt();
         }
         int aux[] = new int[n];
         int c = 0;
         aux[0] = 1;
         for(int i=1;i<n;i++){
            if(a[i] == a[i-1]){
                aux[c]++;
            }else{
                c++;
                aux[c] = 1;
            }
         }
        int ans = 0;
        for(int i = 1;i<n;i++){
            ans = Math.max(ans,Math.min(aux[i],aux[i-1]));
        }
        System.out.println(ans * 2);
               
    }
    
}
