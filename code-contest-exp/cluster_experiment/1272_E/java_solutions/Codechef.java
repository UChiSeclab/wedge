    import java.util.*;
    import java.lang.*;
    import java.io.*;
     
    /* Name of the class has to be "Main" only if the class is public. */
    public class Codechef
    {
    	static int []a;
    	static int []dp;
    	static int n;
    	
    	public static void re(int i){
    
    	if(dp[i]!=0)return;
		int l = i-a[i],r = i+a[i];
		if(l<0 && r>=a.length){
			dp[i]=-1;
			return;
		}
		if((l>=0 && a[l]%2!=a[i]%2)||(r<n && a[r]%2!=a[i]%2))
			dp[i]=1;
		else {
			dp[i] = Integer.MAX_VALUE;
			if(l>=0)
			{
				re(l);				
				dp[i] = Math.min(dp[l]+1, dp[i]);
			}
			if(dp[i]!=1 && r<n)
			{
				re(r);
				//System.out.println(dp[r]);
//				if(dp[r]!=-1){
//					if(dp[i]!=0)
						dp[i] = Math.min(dp[r]+1, dp[i]);
//					else dp[i] =dp[r]+1;
//				}
			}
			else
				dp[i]=-1;
				
		}
    	}
    	
        public static void main(String args[]) throws Exception {
        	Scanner sc = new Scanner(System.in);
        	n = sc.nextInt();
        	a = new int[n];
        	for(int i=0;i<n;i++){
        		a[i] = sc.nextInt();
        	}
        	
        	dp  = new int[n];
        	for(int i = 0;i<n;i++){
        		if(dp[i]==0){
        			re(i);
        		}
        	}
        	for(int i:dp){
        	    System.out.print(i+" ");
        	}
        	System.out.println();
        }
    }