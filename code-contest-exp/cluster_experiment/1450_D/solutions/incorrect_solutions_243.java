//////package round_global_12;


import java.io.*;
import java.util.*;

public class D {
    static class InputReader {
        BufferedReader reader;
        StringTokenizer tokenizer;
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
        String next() { // reads in the next string
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
        public int nextInt() { return Integer.parseInt(next()); } // reads in the next int
        public long nextLong() { return Long.parseLong(next()); } // reads in the next long
        public double nextDouble() { return Double.parseDouble(next()); } // reads in the next double
    }
    static InputReader r = new InputReader(System.in);
    static PrintWriter pw = new PrintWriter(System.out);
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args){
        int a=r.nextInt();
        for (int u=0;u<a;u++){
            int b=r.nextInt();
            int[] arr=new int[b];
            int[] count=new int[b+1];
            for (int i=0;i<b;i++){
                arr[i]=r.nextInt();
                count[arr[i]]++;
            }
            int mindouble=arr.length+1;
            int minmissing=arr.length+1;
            for (int i=1;i<count.length;i++){
                if (count[i]>1){
                    mindouble=i;
                    break;
                }

            }
            for (int i=1;i<count.length;i++){
                if (count[i]==0){
                    minmissing=i;
                    break;
                }
            }
            int pos=0;
            for (int i=1;i<arr.length;i++){
                if (arr[i]>arr[i-1]){
                    pos=i;
                }
                else{
                    break;
                }
            }
            int pos2=arr.length-1;
            for (int i=arr.length-2;i>pos;i--){
                if (arr[i]>arr[i+1]){
                    pos2=i;
                }
                else{break;}
            }
            int min=1000000000;
            for (int i=pos+1;i<pos2;i++){
                min=Math.min(arr[i],min);
            }
            int left=-1;
            int right=arr.length-2;
            for (int i=pos;i>=0;i--){
                if (arr[i]<min){
                    left=i;
                    break;
                }
            }
            for (int i=pos2;i<arr.length;i++){
                if (arr[i]<min){
                    right=i;
                    break;
                }
            }

            if (minmissing==arr.length+1){
                pw.print("1");
            }
            else{
                pw.print("0");
            }
            int val=Math.max(Math.max(arr.length-minmissing+1,arr.length-mindouble),right-left-2);
            for (int i=1;i<val;i++){
                pw.print("0");
            }
            for (int i=Math.max(1,val);i<arr.length;i++){
                pw.print("1");
            }
            pw.println();




        }
        pw.close();
    }

}
