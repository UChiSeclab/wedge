// Don't place your source in a package

import java.math.BigInteger;
import java.util.*;
import java.lang.*;
import java.io.*;



// Please name your class Main
public class Main {
    static FastScanner fs=new FastScanner();
    static class FastScanner {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st=new StringTokenizer("");
        public String next() {
            while (!st.hasMoreElements())
                try {
                    st=new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return st.nextToken();
        }
        int Int() {
            return Integer.parseInt(next());
        }

        long Long() {
            return Long.parseLong(next());
        }

        String Str(){
            return next();
        }
    }


    public static void main (String[] args) throws java.lang.Exception {
        PrintWriter out = new PrintWriter(System.out);


        int T=1;
        for(int t=0;t<T;t++){
            int n=Int();
            int A[]=new int[n];
            for(int i=0;i<n;i++){
                A[i]=Int();
            }
            Solution sol=new Solution(out);
            sol.solution(A);
        }
        out.flush();

    }


    public static int Int(){
        return fs.Int();
    }
    public static long Long(){
        return fs.Long();
    }
    public static String Str(){
        return fs.Str();
    }

}






class Solution{
    PrintWriter out;
    public Solution(PrintWriter out){
        this.out=out;
    }



    public void solution(int A[]){
        sort(A);
        int cnt=1;
        int pre=A[0];
        if(pre>1)pre--;
        for(int i=1;i<A.length;i++){
            if(A[i]<pre)continue;
            if(A[i]-1>pre){
                pre=A[i]-1;
                cnt++;
            }
            else if(A[i]-1==pre){
                pre=A[i];
                cnt++;
            }
            else if(A[i]+1>pre){
                pre=A[i]+1;
                cnt++;
            }
        }

        out.println(cnt);
    }
    
    public void sort(int A[]){
        PriorityQueue<Integer>pq=new PriorityQueue<>();
        for(int i:A)pq.add(i);
        for(int i=0;i<A.length;i++)A[i]=pq.poll();
    }









}



/*
                             ;\
                            |' \
         _                  ; : ;
        / `-.              /: : |
       |  ,-.`-.          ,': : |
       \  :  `. `.       ,'-. : |
        \ ;    ;  `-.__,'    `-.|
         \ ;   ;  :::  ,::'`:.  `.
          \ `-. :  `    :.    `.  \
           \   \    ,   ;   ,:    (\
            \   :., :.    ,'o)): ` `-.
           ,/,' ;' ,::"'`.`---'   `.  `-._
         ,/  :  ; '"      `;'          ,--`.
        ;/   :; ;             ,:'     (   ,:)
          ,.,:.    ; ,:.,  ,-._ `.     \""'/
          '::'     `:'`  ,'(  \`._____.-'"'
             ;,   ;  `.  `. `._`-.  \\
             ;:.  ;:       `-._`-.\  \`.
              '`:. :        |' `. `\  ) \
      -hrr-      ` ;:       |    `--\__,'
                   '`      ,'
                        ,-'


                      free bug dog
*/





