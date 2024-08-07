
import java.util.*;
import java.io.*;
public class B {
    static PrintWriter ww = new PrintWriter(System.out);
    static int[] nextS(int arr[]){
        Stack<Integer> s = new Stack<>();
        int n = arr.length;
        int ns[] = new int[n];
        Arrays.fill(ns , n);
        for(int i = 0 ; i < n ; i ++){
            while(!s.isEmpty() && arr[i] < arr[s.peek()]){
                ns[s.pop()] = i;
            }
            s.add(i);
        }
        return ns;
    }
    static int[] prevS(int arr[]){
        Stack<Integer> s = new Stack<>();
        int n = arr.length;
        int ps[] = new int[n];
        Arrays.fill(ps , -1);
        for(int i = n - 1 ; i >= 0 ; i --){
            while(!s.isEmpty() && arr[i] < arr[s.peek()]){
                ps[s.pop()] = i;
            }
            s.add(i);
        }
        return ps;
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int tt = sc.nextInt();
        while(tt -- >  0){
            int n = sc.nextInt();
            int arr[] = new int[n];
            for(int i = 0 ; i < n ; i ++){
                arr[i] = sc.nextInt();
            }
            int ns[] = nextS(arr);
            int ps[] = prevS(arr);
            int c[] = new int[n + 1];
            for(int i = 0 ; i < n ; i ++){
//                int co = ns[i] - ps[i] - 1;
//                if(co == )
                c[arr[i]] = Math.max(c[arr[i]] , ns[i] - ps[i] - 1);
            }
            int ans[] = new int[n + 1];
            for(int i = 1 ; i <= n ; i ++){
                if(c[i] == 0)break;
                if(c[i] >= n - i  +1){
                    ans[n - i + 1] = 1;
                }
                
            }
            for(int i = 1 ; i <= n ; i ++)ww.print(ans[i]);
            ww.println();
        }
        ww.close();
    }    
}