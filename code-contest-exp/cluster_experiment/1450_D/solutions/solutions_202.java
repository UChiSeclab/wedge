import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        
        var sc = new Scanner(System.in);
        var pw = new PrintWriter(System.out);
        
        int T = Integer.parseInt(sc.next());
        for(int t = 0; t < T; t++){
            int n = Integer.parseInt(sc.next());
            var a = new int[n];
            var deque = new ArrayDeque<Integer>();
            var set = new HashSet<Integer>();
            for(int i = 0; i < n; i++){
                a[i] = Integer.parseInt(sc.next());
                deque.add(a[i]);
                set.add(a[i]);
            }
            var st = new SegmentTree(a, Integer.MAX_VALUE);
            int left = 0;
            int right = n;
            
            var ans = new int[n];
            if(set.size() == n){
                ans[0] = 1;
            }
            if(set.contains(1)){
                ans[n-1] = 1;
            }
            for(int i = 1; i < n-1; i++){
                if(deque.getFirst() == i){
                    deque.removeFirst();
                    left++;
                    if(st.getMin(left, right) == i+1){
                        ans[n-1-i] = 1;
                    }else{
                        break;
                    }
                }else if(deque.getLast() == i){
                    deque.removeLast();
                    right--;
                    if(st.getMin(left, right) == i+1){
                        ans[n-1-i] = 1;
                    }else{
                        break;
                    }
                }else{
                    break;
                }
            }
            for(int i = 0; i < n; i++){
                pw.print(ans[i]);
            }
            pw.println();
        }
        pw.flush();
    }
    
    static class SegmentTree {
        int n;
        int[] node;
        
        public SegmentTree(int size, int defaultValue) {
            n = 1;
            while(n < size) n <<= 1;
            node = new int[2*n-1];
            Arrays.fill(node, defaultValue);
        }
        public SegmentTree(int[] a, int defaultValue) {
            n = 1;
            while(n < a.length) n <<= 1;
            node = new int[2*n-1];
            System.arraycopy(a, 0, node, n-1, a.length);
            Arrays.fill(node, n-1+a.length, 2*n-1, defaultValue);
            for(int i = n-2; i >= 0; i--){
                node[i] = Math.min(node[i*2+1], node[i*2+2]);
            }
        }
        void update(int i, int x){
            i += n-1;
            node[i] = x;
            while(i > 0){
                i = (i-1)/2;
                node[i] = Math.min(node[i*2+1], node[i*2+2]);
            }
        }
        int getMin(int a, int b){
            return getMin(a, b, 0, 0, n);
        }
        int getMin(int a, int b, int k, int l, int r){
            if(r <= a || b <= l) return Integer.MAX_VALUE;
            if(a <= l && r <= b) return node[k];
            int vl = getMin(a, b, k*2+1, l, (l+r)/2);
            int vr = getMin(a, b, k*2+2, (l+r)/2, r);
            return Math.min(vl, vr);
        }
        int get(int i){
            return node[i+n-1];
        }
    }
}