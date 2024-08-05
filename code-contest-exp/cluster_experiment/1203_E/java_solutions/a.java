import java.io.*;
import java.util.*;
import java.math.*;
public class a {
	public static void main(String arg[])throws Exception {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		OutputStream out=new BufferedOutputStream(System.out);
		int n=Integer.parseInt(br.readLine());
		List<Integer> a=new ArrayList<Integer>();
		String s[]=br.readLine().split("\\ ");
		for(int i=0;i<n;i++) {
			a.add(Integer.parseInt(s[i]));
		}
		Collections.sort(a);
		int ans=0;
		Map<Integer,Integer> map=new HashMap<>();
		for(int i=0;i<n;i++) {
			if(a.get(i)-1>0 && !map.containsKey(a.get(i)-1)) {
				ans++;
				map.put(a.get(i)-1, 1);
			}
			else if(!map.containsKey(a.get(i))) {
				ans++;
				map.put(a.get(i),1);
			}
			else if(a.get(i)+1<=150001 && !map.containsKey(a.get(i)+1)) {
				ans++;
				map.put(a.get(i)+1, 1);
			}
		}
		out.write((ans+"").getBytes());
				
		out.flush();
	}
}
