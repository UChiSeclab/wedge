
import java.util.*;
import java.io.*;
 
public class Solve{
    public static void main(String[] args) throws Exception{
        Scanner sc=new Scanner(System.in);
        PrintWriter out =new PrintWriter(System.out);
        int t=sc.nextInt();
        while(t-->0){
            int n=sc.nextInt();
            char[] c=sc.next().toCharArray();
            HashMap<String,Integer> mp=new HashMap<>();
            int d=0;
            int k=0;
            int[] ans=new int[n];
            for(int i=0;i<n;i++){
                if(c[i]=='D')d++;
                if(c[i]=='K')k++;
                int m= gcd(d,k);
                String s=d/m+":"+k/m;
            
                if(mp.containsKey(s)){mp.put(s,mp.get(s)+1);}
                else mp.put(s,1);
                
                ans[i]=mp.get(s);
            }
            for(int i=0;i<n;i++)out.print(ans[i]+" ");
            out.println();
            
        }out.close();
    }
    static int gcd(int a,int b){
        if(b==0)return a;
        return gcd(b,a%b);
    }
    
}