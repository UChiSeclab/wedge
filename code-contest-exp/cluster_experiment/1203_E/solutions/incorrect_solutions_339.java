import java.util.*;

public class Main{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
        int input[] = new int[n];
        int seen[] = new int[150005];
    	int ans = 0;
        for(int i=0;i<n;i++)
        {
            input[i] = sc.nextInt();
        }
        Arrays.sort(input);
        
        for(int i=0;i<n;i++)
        {
            if(seen[input[i]]==0)
            {
                seen[input[i]]++;
                ans++;
               
            }
            else
           {
                if(input[i]-1>0 && seen[input[i]-1]==0)
            {                
                    seen[input[i]-1]++;
                    ans++;
                
            }
             else if(input[i]+1<=150001 && seen[input[i]+1]==0)
            {
                    seen[input[i]+1]++;
                    ans++;
              
            }
           }
        }
       System.out.println(ans);
	}
}