import java.util.* ;
public class Sushi_for_two
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
        int start=0 , end=0 ;
        int c=0 ;
        for(int i=0 ; i<n ; i++)
        {
            cs+=a[i] ;
            if(cs==0)
            {
                max_len=i+1 ;
                start=0 ; end=i ;
            }
            else{
                if(!map.containsKey(cs))
                map.put(cs, i) ;
                else
                {
                    max_len = (int)Math.max(max_len , i-map.get(cs)) ;
                    end=i ;
                    start=map.get(cs)+1 ;
                }
            }
        }
        //int length = end-start+1 ;
        boolean printed=false ;
        if(max_len==2)
        	{
        	    System.out.println("2") ;
        	    printed = true ;
        	}
        boolean flag=true ;
        for(int i=start+1 ; i<start+(max_len/2) ; i++)
        {
        	if(a[i]!=a[i-1])
        	{
        	    flag=false ;
        		if(!printed)
        		System.out.println("2");
        		break ;
        	}
        }
        if(flag)
        {
            if(max_len!=2)
            System.out.println(max_len) ;
        }
    }
}