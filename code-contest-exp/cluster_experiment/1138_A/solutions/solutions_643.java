import javax.print.DocFlavor;
import javax.print.attribute.HashAttributeSet;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.attribute.FileAttribute;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.*;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class Main {


    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    public static void main(String[] args) {
        FastReader sc = new FastReader();
      int n=sc.nextInt();
      int[] arr=new int[n];
      for(int i=0;i<n;i++){
          arr[i]=sc.nextInt();
      }
      int last=arr[0];
      int cnt1=0;
      int cnt2=0;int ans=0;
      for(int i=0;i<n;i++){
          if(arr[i]!=last){
              cnt2=cnt1;
              cnt1=0;
              last=arr[i];
          }
          cnt1++;
          ans=Math.max(ans,Math.min(cnt1,cnt2));
      }
      System.out.println(2*ans);


    }}

