
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;

public class RatingCompression {

    static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);

    static int cnt[];

    public static void main(String[] args) throws IOException {
        int  t = ri();
        while(t--> 0) {
            int n = ri();
            boolean[] ans = new boolean[n];
            cnt = new int[n];
            int a[] = ria();
            int count = 0;
            for(int i = 0; i<n; i++){
                if(cnt[i]>0){
                    count++;
                }
            }
            ans[0] = count == n;
            ans[n-1] = cnt[0] > 0;
            int l = 0, r= n-1;
            for(int i = n-1; i>0; i--) {
                if(!ans[n-1]) break;
                ans[i] = true;
                int next= n - i - 1;
                if(--cnt[next] == 0 && (a[l] == next || a[r] == next) && cnt[next+1]>0) {
                    if(a[l] == next) l++;
                    if(a[r] == next)r--;
                    continue;
                }
                break;
            }
            for(boolean x: ans){
                if(x) w('1');
                else w('0');
            }
            lb();
        }
        closeOutStream();
    }

    private static boolean printSmaller(int a[], int k) {
        Deque<Integer> q = new LinkedList<>();
        int i=0;
        for(i= 0; i<k; i++){
            while(!q.isEmpty() && a[q.peekLast()] > a[i])
                q.pollLast();
            q.addLast(i);
        }
        HashSet<Integer> set = new HashSet<>();
        int maxElements = a.length - k +1;
        int thisEle;
        for(; i<a.length; ++i){
//            w(a[q.peekFirst()] +" ");
            thisEle = a[q.peekFirst()];
            if(set.contains(thisEle) || thisEle > maxElements){
                return false;
            }else {
                set.add(thisEle);
            }
            while(!q.isEmpty() && q.peekFirst() <= i-k)
                q.pollFirst();
            while(!q.isEmpty() && a[i] < a[q.peekLast()])
                q.pollLast();
            q.addLast(i);
        }
        thisEle = a[q.peekFirst()];
        if(set.contains(thisEle) || thisEle > maxElements){
            return false;
        }
        return true;
    }

    private static boolean verify(int a[], int k) {
        int l =0, r = 0;
        HashSet<Integer> set = new HashSet<>();
        while(r-l +1 <k){
            set.add(a[r++]);
        }
        for(; r<a.length; l++, r++) {
            set.add(a[r]);
            if(set.size()<k){
                return false;
            }
            set.remove(a[l]);
        }
        return true;
    }

    static int ri() throws IOException {
        return Integer.parseInt(r.readLine());
    }

    static int[] ria() throws IOException {
        String s []= r.readLine().split(" ");
        int x[] = new int[s.length];
        for(int i=0; i<s.length; i++){
            x[i] = Integer.parseInt(s[i])-1;
            cnt[x[i]]++;
        }
        return x;
    }

    static void append(String s){
        out.print(s);
    }

    static void wl(Object s){
        out.println(s);
    }

    static void w(Object c){
        out.print(c);
    }

    static void lb() {
        out.println();
    }

    static void closeOutStream(){
        out.close();
    }
}
