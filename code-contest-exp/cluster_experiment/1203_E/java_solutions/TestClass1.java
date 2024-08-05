import java.io.*;
import java.util.*;

public class TestClass1 {
static long gcd(long a, long b) { return b==0 ? a : gcd(b, a%b);}

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int l = Integer.parseInt(br.readLine());
        int[] a = new int[l];
        String[] s = br.readLine().split(" ");
        TreeSet<Integer> hs = new TreeSet<>();
        TreeMap<Integer, Integer> hm = new TreeMap<>();
        
        for(int i=0; i<l; i++){
            a[i] = Integer.parseInt(s[i]);
            if(!hs.contains(a[i])){
                hs.add(a[i]);
                hm.put(a[i],1);
            }else{
                hm.compute(a[i], (y,z)->z+1);
            }
        }
        
        Arrays.sort(a);
        hm.forEach((x,y)->{
            for(int i=1; i<y; i++){
                if(!hs.contains(x-1) && x-1!=0)
                hs.add(x-1);
                else if(!hs.contains(x+1))
                hs.add(x+1);
            }
        });
        
        System.out.println(hs.size());
        // hs.forEach(x->System.out.println(x));
    }
      
    
}

