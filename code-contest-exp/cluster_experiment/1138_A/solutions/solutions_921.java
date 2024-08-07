import java.util.*;
public class sol
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
         int n = sc.nextInt();
		    int next =0,tuna =0,last =0,eel =0,ans =0;
		    for(int i=0;i<n;i++)
		    {
		        next = sc.nextInt();
		        if(next == 1)
		        {
		            if(last == 1)
		            {
		                tuna += 1;
		            }
		            else
		            {
		                tuna = 1;
		            }
		        }
		        else
		        {
		            if(last == 2)
		            {
		                eel += 1;
		            }
		            else
		            {
		                eel = 1;
		            }
		        }
		        last = next;
		        ans = Math.max(ans,Math.min(tuna,eel));
		    }
		    System.out.println(ans*2);
		
    }
}