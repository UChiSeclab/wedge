/*package whatever //do not write package name here */

import java.util.*;
import java.lang.*;
import java.io.*;

public class Boxers {
	public static void main (String[] args) throws IOException{
		//code
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int sz = Integer.parseInt(br.readLine());
		String[] inp = br.readLine().trim().split("\\s+");
		int v;
		HashMap<Integer, Integer> hm = new HashMap<>();
		for(int g = 0; g < sz; g++){
		    v = Integer.parseInt(inp[g]);
		    if(hm.containsKey(v)){
		    	hm.put(v, hm.get(v) + 1);
		    }
		    else hm.put(v, 1);
		}
		if(sz == 1) {
			System.out.println(1);
			return;
		}
		
		HashSet<Integer> hs = new HashSet<Integer>();
		int curr = 0;
		for(Integer x: hm.keySet()){
		    int val = hm.get(x);
	        if(x == 1){
	        	hs.add(1);
	        	curr++;
	        	if(val != 1) {
	        		hs.add(2);
	        		curr++;
	        	}
	        	continue;
	        }
            if(!hs.contains(x - 1)) {
                hs.add(x - 1);
                curr++; 
                val--;
            }
            if((!hs.contains(x)) && (val > 0)) {
                hs.add(x);
                curr++;
                val--;
            }
            if((!hs.contains(x + 1)) && (val > 0)) {
                hs.add(x + 1);
                curr++;
                val--;
            }
		}	
		System.out.println(curr);
	}
}

