import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
public class ShushiForTwo {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int a[]=new int[n];
        ArrayList<Integer> ar=new ArrayList<>();
        
        int x=0;
        int y=0;
        for(int i=0;i<n;i++)
        {
            a[i]=sc.nextInt();
        }
        
        for(int i=0;i<n;i++)
        {
            y=0;
            //System.out.print(x+" "+y);
            while(i!=n && a[i]==2)
            {
                x++;
                i++;
            }
            while(i!=n && a[i]==1)
            {
                y++;
                i++;
            }
            int s=Math.min(x, y);
           // System.out.print(s+" ");
           ar.add(s);
            x=0;
            while(i!=n && a[i]==2)
            {
                x++;
                i++;
            }
            int r=Math.min(x, y);
           // System.out.print(r+"  ");
           ar.add(r);
           i--;
        }
       
       Collections.sort(ar,Collections.reverseOrder());
        System.out.println(ar.get(0)*2);
    /* for(int i=0;i<ar.size();i++)
        {
            System.out.println(ar.get(i));
        }
      */ 
    }
}
