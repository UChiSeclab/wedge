
import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		int t=Integer.parseInt(br.readLine());
		while(t--!=0) {
			int n=Integer.parseInt(br.readLine());
			String s[]=br.readLine().split(" ");
			int a[]=new int[n];
			for(int i=0;i<n;i++) a[i]=Integer.parseInt(s[i]);
			HashSet<Integer> hss=new HashSet<>();
			ArrayList<Integer> aa=new ArrayList<>();
			for(int i=0;i<n;i++) {
				aa.add(a[i]);
				hss.add(a[i]);
			}
			ArrayList<ArrayList<Integer>> arr=new ArrayList<ArrayList<Integer>>();
			arr.add(aa);
			String  ans="";
			if(hss.contains(n) && hss.size()==n) ans+="1";
			else ans+="0";
			for(int i=1;i<n-1;i++) {
				ArrayList<Integer> ar=new ArrayList<>();
				for(int j=i;j<n;j++) {
					ar.add(Math.min(a[j], arr.get(i-1).get(j-i)));
				}
				arr.add(ar);
				HashSet<Integer> hs=new HashSet<>(ar);
				if(hs.size()==ar.size()) ans+="1";
				else ans+="0";
			}
			if(arr.get(0).contains(1)) ans+="1";
			else ans+="0";
			System.out.println(ans);
		}
	}
		
	
	public static  boolean isIntegerValue(BigDecimal bd) {
		  return bd.stripTrailingZeros().scale() <= 0;
	}
	public static BigDecimal sqrt(BigDecimal value) {
	    BigDecimal x = new BigDecimal(Math.sqrt(value.doubleValue()));
	    return x.add(new BigDecimal(value.subtract(x.multiply(x)).doubleValue() / (x.doubleValue() * 2.0)));
	}
	
	
}
