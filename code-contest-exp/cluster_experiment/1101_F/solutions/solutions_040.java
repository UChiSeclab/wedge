import java.io.*;
import java.util.*;

public class B{

	static long INF=(long)1e18;
	public static void main(String[] args) throws IOException {
		Scanner sc=new Scanner();
		PrintWriter out=new PrintWriter(System.out);
		int n=sc.nextInt(),m=sc.nextInt();
		int []a=new int [n];
		for(int i=0;i<n;i++)
			a[i]=sc.nextInt();
		long ans=1;
		int []indices=new int [m],S=new int [m],F=new int [m],C=new int [m],R=new int [m];
		for(int i=0;i<m;i++) {
			indices[i]=i;
			S[i]=sc.nextInt()-1;
			F[i]=sc.nextInt()-1;
			C[i]=sc.nextInt();
			R[i]=sc.nextInt();
		}
		Random rand =new Random();
		for(int i=0;i<m;i++) {
			int r=rand.nextInt(m);
			
//			System.out.println(r);
			int tmp=indices[r];
			indices[r]=indices[i];
			indices[i]=tmp;
		}
				for(int j=0;j<m;j++) {
//			int s=sc.nextInt()-1,f=sc.nextInt()-1,c=sc.nextInt(),r=sc.nextInt();
			int idx=indices[j];
			int s=S[idx],f=F[idx],c=C[idx],r=R[idx];
			long start=ans;
				boolean ok=true;
				long fuel=start;
				int refuel=r;
				for(int i=s+1;i<=f && ok;i++) {
					long needed=(a[i]-a[i-1])*1l*c;
					fuel-=needed;
					if(fuel<0) {
						fuel=start-needed;
						if(refuel--==0 || fuel<0)
							ok=false;
					}
				}
				if(ok) {
					continue;
				}
				
				
			
			long lo=ans,hi=INF,curr=INF;
			while(lo<=hi) {
				 start=lo+hi>>1;
				 ok=true;
				 fuel=start;
				 refuel=r;
				for(int i=s+1;i<=f && ok;i++) {
					long needed=(a[i]-a[i-1])*1l*c;
					fuel-=needed;
					if(fuel<0) {
						fuel=start-needed;
						if(refuel--==0 || fuel<0)
							ok=false;
					}
				}
				if(ok) {
					curr=start;
					hi=start-1;
				}
				else
					lo=start+1;
				
			}
			ans=Math.max(ans, curr);
		
		}
		out.println(ans);
		out.close();

	}
	static class pair{
		int l,r,idx;
		pair(int a,int b,int c){
			l=a;
			r=b;
			idx=c;
		}
	}
	static class Scanner
	{
		BufferedReader br;
		StringTokenizer st;
		Scanner(){
			br=new BufferedReader(new InputStreamReader(System.in));
		}
		Scanner(String fileName) throws FileNotFoundException{
			br=new BufferedReader(new FileReader(fileName));
		}
		String next() throws IOException {
			while(st==null || !st.hasMoreTokens())
				st=new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		String nextLine() throws IOException {
			return br.readLine();
		}
		int nextInt() throws IOException{
			return Integer.parseInt(next());
		}
		long nextLong()  throws NumberFormatException, IOException {
			return Long.parseLong(next());
		}
		double nextDouble() throws NumberFormatException, IOException {
			return Double.parseDouble(next());
		}
	}
}