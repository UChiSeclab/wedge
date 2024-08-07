import java.io.*;
import java.util.*;
     
    public class Solution {
    	 
    public static void main(String[] args) throws Exception
    { 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        int n = Integer.parseInt(br.readLine());
        List<Point> points = new ArrayList();
        StringTokenizer st = new StringTokenizer(br.readLine());
        long[] x = new long[n];
        long[] vel = new long[n];
        
        for(int i=0;i<n;i++){
             x[i] = Long.parseLong(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<n;i++){
             vel[i] = Long.parseLong(st.nextToken());
        }
        
        //HashMap<Long,Integer> velVal = getMap(vel);
        
        for(int i=0;i<n;i++){
             Point point = new Point(x[i],vel[i]);
             points.add(point);
        }
        
               
        Collections.sort(points,(p1,p2)->{
             if(p1.x< p2.x) return -1;
             else if(p1.x == p2.x) return 0;
              return 1;
        });
        
        Map<Long,Integer> map = getMap(points);
        
     //  points.stream().forEach(p->System.out.println(p.x+" "+p.vel));
          
        long ans = 0;
        int negX = 0;
        int count = 0;
        for(int i=0;i<points.size();i++){
             if(points.get(i).vel < 0){
                  negX+=points.get(i).x;
                  count++;
             }
             else ans+= (count*points.get(i).x - negX);  
        }
        
        Collections.sort(points,(p1,p2)->{
            if(p1.vel< p2.vel) return -1;
            else if(p1.vel == p2.vel) {
            	if(p1.x < p2.x)return -1;
            	else if(p1.x > p2.x) return 1;
            	return 0;
            }
             return 1;
       });
       //points.stream().forEach(p->System.out.println(p.x+" "+p.vel));

       
            
       long[][] ft = new long[n+1][2];
       
       int ind = -1;
      for(int i=0;i<points.size();i++) {
    	  if(points.get(i).vel >=0) {
    		  ind = i;
    		  break;
    	  }
    	  long[] res = BIT(ft,map,points.get(i).x);
    	  long c = res[1];
    	  long values = res[0];
    	  ans+= (c*points.get(i).x-values);
    	  //System.out.println("ans =" +ans);
    	  
      }
      
      ft = new long[n+1][2];
      for(int i = (ind==-1) ? points.size() : ind; i< points.size(); i++) {
    	  long[] res = BIT(ft,map,points.get(i).x);
    	  long c = res[1];
    	  long values = res[0];
    	  ans+= (c*points.get(i).x-values); 
    	  //System.out.println("ans =" +ans);
      }
        out.println(ans);
        out.close();
    }
    
    
    private static  Map<Long,Integer> getMap(List<Point> points){
         Map<Long,Integer> map = new HashMap();
         
         int pos=1,neg = 1;
         for(int i=0;i<points.size();i++) {
        	 if(points.get(i).vel < 0) {
        		 map.put(points.get(i).x, neg);
        		 neg++;
        	 }
        	 else {
        		 map.put(points.get(i).x, pos);
        		 pos++; 
        	 }
         }
         
         return map;
    }
    
    public static long[] BIT(long[][] ft, Map<Long,Integer> map,long val){
	    int v = map.get(val);
	    long[] result = new long[2];
	    result[0]+=ft[v][0];
	    result[1]+=ft[v][1];
	    
	    while(v > 0){
	      v -= ((~v) + 1)&v;
	        result[0]+=ft[v][0];
		    result[1]+=ft[v][1];
	    }
	    
	    v = map.get(val);
	    
	    while(v < ft.length){
	    	ft[v][0]+=val;
	       ft[v][1]++;
	       v += ((~v) + 1)&v;
	    }
	    return result;
	}
}

class Point{
     long x;
     long vel;
     
     Point(long x, long vel){
          this.x= x;
          this.vel = vel;
     }
}