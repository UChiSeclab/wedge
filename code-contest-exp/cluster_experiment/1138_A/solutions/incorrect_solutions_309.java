import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br;

    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
            int tc= cinI();
    
           String[] arr = cinA();
           int[] a= new int[tc];
           
           arrinit(arr,a);
           int [] d = new int[2];
            d[0]=a[0]==1?1:-1;
            d[1]=a[1]==2?-1:1;
         
            int[] x = new int[tc];
            x[0]=1;
            int max=1;
           for(int j=1;j<tc;j++){
               if(a[j]==a[j-1]){
               x[j]=x[j-1]+1;
               d[a[j]-1]=j;
               if(x[j]%2==0)
               max=Math.max(x[j],max);
               continue;
           }
              x[j]=j-d[a[j]-1];
                             if(x[j]%2==0)
              max=Math.max(x[j],max);
              d[a[j]-1]=j;
           
           }
           System.out.println(max);
           
        //   for(int u=0;u<tc;u++){
        //       System.out.print(x[u]+ " ");
        //   }
           
        
        
    }
    

  
    
    public static void arrinit(String[] a,long[] b)throws Exception{
        for(int i=0;i<a.length;i++){
            b[i]=Long.parseLong(a[i]);
        }
    }
    public static void arrinit(String[] a,int[] b)throws Exception{
        for(int i=0;i<a.length;i++){
            b[i]=Integer.parseInt(a[i]);
        }
    }


    static double findRoots(int a, int b, int c) {
        // If a is 0, then equation is not
        //quadratic, but linear


        int d = b * b - 4 * a * c;
        double sqrt_val = Math.sqrt(Math.abs(d));


        // System.out.println("Roots are real and different \n");

        return Math.max((double) (-b + sqrt_val) / (2 * a),
                (double) (-b - sqrt_val) / (2 * a));


    }

    public static String cin() throws Exception {
        return br.readLine();
    }

    public static String[] cinA() throws Exception {
        return br.readLine().split(" ");
    }
  public static String[] cinA(int x) throws Exception{
      return br.readLine().split("");
  }

    public static String ToString(Long x) {
        return Long.toBinaryString(x);
    }

    public static void cout(String s) {
        System.out.println(s);
    }

    public static Integer cinI() throws Exception {
        return Integer.parseInt(br.readLine());
    }

    public static int getI(String s) throws Exception {
        return Integer.parseInt(s);
    }

    public static long getL(String s) throws Exception {
        return Long.parseLong(s);
    }

    public static int max(int a, int b) {
        return Math.max(a, b);
    }

    public static void coutI(int x) {
        System.out.println(String.valueOf(x));
    }

    public static void coutI(long x) {
        System.out.println(String.valueOf(x));
    }

    public static Long cinL() throws Exception {
        return Long.parseLong(br.readLine());
    }

    public static void arrInit(String[] arr, int[] arr1) throws Exception {
        for (int i = 0; i < arr.length; i++) {
            arr1[i] = getI(arr[i]);
        }

    }
}

class range {
    int l;
    int r;
    int diff;

    public range(int l, int r, int diff) {
        this.l = l;
        this.r = r;
        this.diff = diff;
    }
}

class sortRange implements Comparator<range> {


    @Override
    public int compare(range range, range t1) {
        if (range.diff != t1.diff)
            return t1.diff - range.diff;
        return range.l - t1.l;
    }
}