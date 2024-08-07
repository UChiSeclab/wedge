import java.util.* ;
public class sol
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in) ;
        int n = in.nextInt() ;
        int a[] = new int[n] ;
        for(int i=0 ; i<n ; i++)
        {
            a[i] = in.nextInt() ;
            if(a[i]==2)
            a[i]=-1 ;
        }
        HashMap<Integer , Integer> map = new HashMap<>() ;
        int cs=0 , max_len = Integer.MIN_VALUE ;
        for(int i=0 ; i<n ; i++)
        {
            cs+=a[i] ;
            if(cs==0)
            max_len=i+1 ;
            else{
                if(!map.containsKey(cs))
                map.put(cs, i) ;
                else
                max_len = (int)Math.max(max_len , i-map.get(cs)) ;
            }
        }
        System.out.println(max_len) ;
    }
}