    /*package whatever //do not write package name here */
    
    import java.io.*;
    import java.util.*;
    
    
    public class Solution {
        
       private static List<List<Integer>> adjl = new ArrayList();;
       
       public static void main(String[] args) throws Exception{
           BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
           PrintWriter out = new PrintWriter(System.out);
           
              int n = Integer.parseInt(br.readLine());
              StringTokenizer st = new StringTokenizer(br.readLine()) ; 
              int[] a = new int[n];
              
             for(int i=0;i<n;i++) a[i] = Integer.parseInt(st.nextToken());

             for(int i=0;i<=n;i++) adjl.add(new ArrayList());
             
             int[] result = new int[n];
             Arrays.fill(result,-1);
             
             List<Integer> odd = new ArrayList();
             List<Integer> even = new ArrayList();
             
             for(int i=0;i<n;i++){
                 if(i-a[i] >= 0){
                     if( (a[i] &1) == (a[i-a[i]] & 1)) adjl.get(i-a[i]).add(i);
                     else{
                         result[i] = 1;
                         if(a[i] %2 == 0)even.add(i);
                         else odd.add(i);
                     }
                 } 
                 if(i+a[i] < n) {
                     if((a[i] &1) == (a[i+a[i]] & 1)) adjl.get(i+a[i]).add(i);
                     else{
                        result[i] = 1;
                         if(a[i] %2 == 0)even.add(i);
                         else odd.add(i); 
                     }
                 }
             }
             
             
             findCount(even,result,a);
             findCount(odd,result,a);
             
            for(int i=0;i<n;i++) out.print(result[i]+" ");
            out.close();
       }
       
       private static void findCount(List<Integer> queue, int[] result,int[] a){
           
           int count = 1;
          
           while(queue.size() > 0){
               count++;
              int size = queue.size();
              List<Integer> q2 = new ArrayList();
              for(int i=0;i<size;i++){
               int u = queue.get(i);
                for(int v : adjl.get(u)){
                    if(result[v] > 0 ) continue;
                    result[v] = count;
                    q2.add(v);
                }
              }
              queue = q2;
           }
       }
       
    }