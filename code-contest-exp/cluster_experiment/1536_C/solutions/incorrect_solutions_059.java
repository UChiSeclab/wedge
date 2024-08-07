import java.io.*;
import java.util.*;
public class Main {
	
	public static int gcd(int a,int b)
	{
		return a==0?b:gcd(b%a,a);
	}
	public static void main(String[] args) throws  java.lang.Exception{
		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
		int t;
		t=Integer.parseInt(br.readLine());
		while(t-->0)
		{
			int n,i,d=0,k=0;
			n=Integer.parseInt(br.readLine());
			char str[]=br.readLine().toCharArray();
			Map<String,Integer> map=new HashMap<>();
			for(i=0;i<str.length;i++)
			{
				if(str[i]=='D')
					d++;
				else
					k++;
				int gcd=gcd(d,k),a=d/gcd,b=k/gcd;
				String st=a+" "+b;
				int p = map.containsKey(st) ? map.get(st) + 1 : 1;
				map.put(st, p);
				bw.write(p+" ");
			}
			bw.write("\n");
			System.out.println();
			bw.flush();
		}
	}
}