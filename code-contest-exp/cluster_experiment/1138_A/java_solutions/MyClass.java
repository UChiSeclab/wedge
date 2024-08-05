
import java.util.*;
public class MyClass {
    public static void main(String args[]) {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();
        ArrayList<Integer> res=new ArrayList<>();
        int prev=-1, streak=1;
        for(int i=0;i<n;i++) {
            int x=scan.nextInt();
            if(x==prev) streak++;
            else {
                res.add(streak);
                streak=1;
            }
            prev=x;
        }
        res.add(streak);
        int ans=0;
        for(int i=0;i<res.size()-1;i++) {
            int nxt=2*Math.min(res.get(i),res.get(i+1));
            ans=Math.max(ans,nxt);
        }
        System.out.println(ans);
    }
}