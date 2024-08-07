import java.awt.List;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.stream.Collectors;
 
 
public class wef {
	public static class FastReader {
		BufferedReader br;
		StringTokenizer st;
		//it reads the data about the specified point and divide the data about it ,it is quite fast
		//than using direct 
 
		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}
 
		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (Exception r) {
					r.printStackTrace();
				}
			}
			return st.nextToken();
		}
 
		int nextInt() {
			return Integer.parseInt(next());//converts string to integer
		}
 
		double nextDouble() {
			return Double.parseDouble(next());
		}
 
		long nextLong() {
			return Long.parseLong(next());
		}
 
		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (Exception r) {
				r.printStackTrace();
			}
			return str;
		}
	}
	static class Pair implements Comparable<Pair>{
	    int x;int y;
	    Pair(int x,int y){
	        this.x=x;
	        this.y=y;
	      //  this.i=i;
	    }
		@Override
		public int compareTo(Pair o) {
			// TODO Auto-generated method stub
			return y-o.y;
			
		}
	}
	
	public static PrintWriter out = new PrintWriter (new BufferedOutputStream(System.out));
    
	 static int lowerBound(ArrayList<Integer> list, int length, long val) {
        int low = 0;
        int high = length;
        while (low < high) {
            final int mid = (low + high) / 2;
            if (val <= list.get(mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
	  class ArrayListComparator<Integer extends Comparable<Integer>> implements Comparator<ArrayList<Integer>> {
 
		  public int compare1(ArrayList<Integer> o1, ArrayList<Integer> o2) {
		    for (int i = 0; i < Math.min(o1.size(), o2.size()); i++) {
		      int c = o1.get(i).compareTo(o2.get(i));
		      if (c != 0) {
		        return c;
		      }
		    }
		    return 0;
		  }
 
		@Override
		public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
			// TODO Auto-generated method stub
			return 0;
		}
 
		}
	  public static String frac(int numerator, int denominator) {
	        long a=numerator,b=denominator;
	        if(a%b==0) return String.valueOf(a/b);
	        TreeMap<Long,Integer> map=new TreeMap<>();
	        StringBuilder res=new StringBuilder();
	        if((a>0&&b<0)||(a<0&&b>0)) res.append("-");
	        a=Math.abs(a);
	        b=Math.abs(b);
	        res.append(a/b+".");
	        a=(a%b)*10;
	        while(!map.containsKey(a)){
	            map.put(a,res.length());
	            res.append(String.valueOf(a/b));
	            a=(a%b)*10;
	            if(a==0) return res.toString();
	        }
	        System.out.println(map.keySet());
	        System.out.println(map.values());
	        return res.insert(map.get(a),"(").append(")").toString();
	    }
 
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
FastReader in=new FastReader();
ArrayList<Integer>list=new ArrayList<Integer>();
ArrayList<Integer>back=new ArrayList<Integer>();
Map<Integer,Integer> map=new HashMap<Integer,Integer>();
HashSet<Integer>set=new HashSet<Integer>();
 
int n=in.nextInt();
Integer a[]=new Integer[n];
for(int i=0;i<n;i++)
	a[i]=in.nextInt();
Arrays.sort(a);
for(int i=0;i<n;i++)
{
	if(a[i]==1)
	{
		if(set.contains(1))
			set.add(2);
		else
			set.add(1);
	}
	else
	{
		if(set.contains(a[i]-1)&&set.contains(a[i])&&set.contains(a[i]+1))
			continue;
		else if(set.contains(a[i]-1)&&set.contains(a[i]))
			set.add(a[i]+1);
		else if(set.contains(a[i]-1))
			set.add(a[i]);
		else
			set.add(a[i]-1);
			
	}
}
System.out.println(set.size());
 
 
 
 
	}
		}