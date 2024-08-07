        ////////////////////////////////////////////////////
        //                                                //
        //  For her who keeps the fire kindling in me...  //
        //                                                //
        ////////////////////////////////////////////////////


import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader;
import java.util.Scanner; 
import java.util.StringTokenizer;
import java.lang.Math;  


public class Main{
	public static void main(String args[]){
		FastReader s = new FastReader();
		int n, i, c, x, l, r;
        long type1, type2, max, flag;

        n = s.nextInt();
        long arr[] = new long[n];

        for(i=0; i<n; i++)
            arr[i] = s.nextLong();

        flag = arr[0];
        l=0; r=0; type1=0; type2=0;
        type1=0; type2=0; max=0;
        /*if(flag==1)
            type2=-1;
        else
            type1=-1;*/
        while(r<n){
            if(arr[l]==arr[r]){
                if(flag==1)
                    type1++;
                else
                    type2++;
                r++;
            }else{
                if(type1>0 && type2>0 && (Math.min(type1, type2)*2)>max)
                    max = (Math.min(type1, type2)*2);
                if(flag==1){
                    flag=2; type2=0;
                }else{
                    flag=1; type1=0;
                }
                l=r;
            }
        }
        if(type1>0 && type2>0 && (Math.min(type1, type2)*2)>max)
            max = (Math.min(type1, type2)*2);

        System.out.println(""+max);
	}
}

//========================================### FAST IO ###=========================================//
class FastReader 
{ 
    BufferedReader br; 
    StringTokenizer st; 

    public FastReader() 
    { 
        br = new BufferedReader(new
                 InputStreamReader(System.in)); 
    } 

    String next() 
    { 
        while (st == null || !st.hasMoreElements()) 
        { 
            try
            { 
                st = new StringTokenizer(br.readLine()); 
            } 
            catch (IOException  e) 
            { 
                e.printStackTrace(); 
            } 
        } 
        return st.nextToken(); 
    } 

    int nextInt() 
    { 
        return Integer.parseInt(next()); 
    } 

    long nextLong() 
    { 
        return Long.parseLong(next()); 
    } 

    double nextDouble() 
    { 
        return Double.parseDouble(next()); 
    } 

    String nextLine() 
    { 
        String str = ""; 
        try
        { 
            str = br.readLine(); 
        } 
        catch (IOException e) 
        { 
            e.printStackTrace(); 
        } 
        return str; 
    } 
}
        //====================================================================================================//