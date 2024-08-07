// Don't place your source in a package
import javax.swing.*;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
import java.util.stream.Stream;



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
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        //BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        //reading /writing file
        //Scanner sc=new Scanner(new File("input.txt"));
        //PrintWriter pr=new PrintWriter("output.txt");


        int T=Int();
        for(int t=0;t<T;t++){
            int n=Int();
            String s=Str();

            Solution sol=new Solution(out);
            sol.solution(s);
        }
        out.close();

    }

    public static int[] Arr(int n){
        int A[]=new int[n];
        for(int i=0;i<n;i++)A[i]=Int();
        return A;
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
    int INF=Integer.MAX_VALUE;
    public Solution(PrintWriter out){
        this.out=out;
    }



    public void solution(String s){
        char A[]=s.toCharArray();
        int d=0,k=0;
        int n=A.length;
        int cnt[][]=new int[n][2];

        Map<String,Integer>f=new HashMap<>();
        for(int i=0;i<n;i++){
            if(A[i]=='D')d++;
            else k++;

            int g=gcd(d,k);
            if(d==0||k==0){
                out.print((i+1)+" ");
                continue;
            }
            int D=d/g;
            int K=k/g;
            String ratio=D+","+K;
            if(!f.containsKey(ratio)){
                f.put(ratio,1);
                out.print(f.get(ratio)+" ");
            }
            else{
                f.put(ratio,f.get(ratio)+1);
                out.print(f.get(ratio)+" ");
            }
        }
        out.println();

    }

    public int gcd(int num1, int num2) {
        if (num2 != 0){
            return gcd(num2, num1 % num2);
        } else{
            return num1;
        }
    }



}
