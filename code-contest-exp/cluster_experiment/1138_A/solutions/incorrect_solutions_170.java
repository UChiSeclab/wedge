import java.util.* ;
public class SushiForTwo {
    
    public static void main(String ar[])
    {
       Scanner sc= new Scanner(System.in) ;
       int n = sc.nextInt() ;
       int arr[] = new int[n] ;
       for(int i = 0 ; i < n ; i++)
       {
           arr[i] = sc.nextInt() ;
       }
       int preCount = 1 ;
       int currentCount = 1 ;
       int ans = 2 ;
       for(int i = 0 ; i < n - 1; i++)
       {
           if(arr[i] == arr[i + 1])
               currentCount++ ;
           else
           {
               preCount = currentCount ;
               currentCount = 1 ;
           }
           if(preCount == currentCount)
               ans = Math.max(ans , 2 * currentCount) ;
       }
       System.out.println(ans) ;
    }
  
       
   
}
