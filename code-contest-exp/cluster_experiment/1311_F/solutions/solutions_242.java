import java.awt.*;
import java.util.*;
import java.io.*;

	public class mp {
	    public static void main(String[] args) throws Exception {
	        Scanner sc= new Scanner(System.in);
	        n= sc.nextInt();
	        prvi= new long[n+1];
	        prvi1= new int[n+2];
	        Point p[]=new Point[n];
	        for (int i= 0; i< n; i++)p[i]= new Point(sc.nextInt(),0);
	        for (int i= 0; i< n; i++)p[i].y= sc.nextInt();
	        Arrays.sort(p, new Comparator<Point>() {
	            public int compare(Point o1, Point o2) {
	                return o1.y-o2.y;
	            }
	        })
	        ;
	        int x= 0;
	        int prej= -1;
	        for (int i=0;i<n;i++){
	            if (i>0 && prej!=p[i].y)x++;
	            prej=p[i].y;
	            p[i].y=x;
	        }
	        Arrays.sort(p, new Comparator<Point>() {
	         
	            public int compare(Point o1, Point o2) {
	                return o1.x-o2.x;
	            }
	        });

	        long odg=0;
	        for (int i=n-1;i>=0;i--){

	            long sestevek=polni(p[i].y);
	            long sestevek1=polni1(p[i].y);
	            gor(p[i].y,p[i].x);
	            gor1(p[i].y);
	            odg+=Math.abs(sestevek-p[i].x*sestevek1);
	        }
	        System.out.println(odg);
	    }
	    static long prvi[];
	    static int prvi1[];
	    static int n;
	    static void gor(int i,int val){
	        for (i++;i<=n;i+=i&(-i)){
	        	prvi[i]+=val;
	        }
	    }
	    static void gor1(int i){
	        for (i++;i<=n;i+=i&(-i)){
	        	prvi1[i]+=1;
	        }
	    }
	    static long sestej(int i){
	        long res=0;
	        for (i++;i>0;i-=i&(-i)){
	            res+=prvi[i];
	        }
	        return res;
	    }
	    static long sestej1(int i){
	        long res=0;
	        for (i++;i>0;i-=i&(-i)){
	            res+=prvi1[i];
	        }
	        return res;
	    }
	    static long polni(int i){
	        return sestej(n-1)-sestej(i-1);
	    }
	    static long polni1(int i){
	        return sestej1(n-1)- sestej1(i-1);
	    }
	    static class beri {
	        BufferedReader br;
	        StringTokenizer st;

	        public beri() {
	            br = new BufferedReader(new InputStreamReader(System.in));
	        }
	        String naslj() {
	            while (st == null || !st.hasMoreElements()) {
	              try {
	                   st = new StringTokenizer(br.readLine());
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }	            }
	            return st.nextToken();
	        }
	        int nextInt() {
	            return Integer.parseInt(naslj());
	        }
	        long nextLong() {
	            return Long.parseLong(naslj());
	        }
	        double nextDouble() {
	            return Double.parseDouble(naslj());
	        }
	        String beriNaslj() {
	            String beri= "";
	            try {
	                beri= br.readLine();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            return beri;
        }
	   }
}