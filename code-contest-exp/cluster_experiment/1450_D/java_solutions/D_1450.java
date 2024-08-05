import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class D_1450{
    static PrintWriter pw = new PrintWriter(System.out);

    public static void main(String arg[]) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        StringTokenizer token = new StringTokenizer(" ");


        int t = 0;
        int n = 0;
        int i = 0;
        int j=0;
        int x=0;

        int arr[];

        t = Integer.parseInt(br.readLine());

        for(i=0;i<=t-1;i++) {
            n = Integer.parseInt(br.readLine());

            arr=new int[n];
            token=new StringTokenizer(br.readLine());

            for(j=0;j<=n-1;j++) {
                x = Integer.parseInt(token.nextToken());
                arr[j]=x;
            }
            getResult(arr);

        }
        pw.close();

    }

    private static void getResult(int arr[]){

        int len=arr.length;
        int i=0;
        int k=0;
        int B[];
        int countB=0;

        for(i=0;i<=len-1;i++){
            k=i+1;

           if(k==1){
                if(isPermutationArray(arr))
                    pw.print('1');
                else
                    pw.print('0');

            }
            else {
                B = new int[len - k + 1];

                getCompressedArray(arr, B, k);

            if(isPermutationArray(B))
                pw.print('1');
            else
                pw.print('0');

            }


        }
        pw.println();
    }

    private static void getCompressedArray(int arr[],int B[],int k){
        int min1=arr[0];
        int min2=Integer.MAX_VALUE;
        int i=0;
        int len=arr.length;
        int count=0;
        int countB=0;

        for(i=1;i<=k-1;i++){
            if(arr[i]<min1)
                min1 = arr[i];

            if(arr[i]>min1 && arr[i]<min2)
                min2=arr[i];
        }
        B[countB]=min1;
        countB++;

        if(min1==arr[count]){
            min1=min2;
        }
        count++;


        while(count<=len-k){

            min2=arr[count+1];


            if(arr[count+k-1]<min1)
                min1 = arr[count+k-1];

            if(arr[count+k-1]>min1 && arr[count+k-1]<min2)
                min2=arr[count+k-1];


            B[countB]=min1;
            countB++;

            if(min1==arr[count]) {
                min1 = min2;
            }

            count++;

        }



    }

    private static boolean isPermutationArray(int arr[]){
        int i=0;
        int len=arr.length;
        HashSet<Integer> hashSet=new HashSet<>();

        for(i=0;i<=len-1;i++){
            if(arr[i]<1 || arr[i]>len)
                return false;
            hashSet.add(arr[i]);
        }
        if(hashSet.size()!=len)
            return false;

        return true;
    }

}