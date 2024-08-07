import java.io.*;
    import java.util.*;
    public class Solution2 {
    	public static void main(String args[]) throws Exception {
    		InputStreamReader is = new InputStreamReader(System.in);
    		BufferedReader br = new BufferedReader(is);
    		int n = Integer.parseInt(br.readLine());
    		Set<Integer> set =  new HashSet<>();
    		int[] arr = new int[n+1];
    		int i=0;
    		for(String s : br.readLine().split("\\s+"))
    		{
    		arr[ Integer.parseInt(s)]++;
    		    
    		}
    		//Arrays.sort(arr);
    		for(i=0;i<n;i++)
    		{
    		    
    		    if(arr[i]==0)
    		        continue;
    		    else if(arr[i]>0)
    		    {
    		        while(arr[i]-->0)
    		        {
    		            if(i>1)
    		            {
    		                if(set.add(i-1)==false)
    		                {
    		                   if(set.add(i)==false)
    			                {
    			                set.add(i+1);
    			                } 
    		                }
    		            }
    		            else if(i==1)
    		            {
    		                if(set.add(i)==false)
    			                {
    			                set.add(i+1);
    			                } 
    		            }
    		        }
    		    }      
    		      
    		}
    		
    		System.out.println(set.size());
    	}
    }