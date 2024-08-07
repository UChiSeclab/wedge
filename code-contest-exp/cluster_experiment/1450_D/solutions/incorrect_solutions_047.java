import java.io.*;
import java.util.*;
public class global implements Runnable 
{
	private boolean console=false;

	public void solve() 
	{
		int i; int n=in.ni(); int a[]=new int[n]; ArrayList<Integer> pos[]=new ArrayList[n];  char ans[]=new char[n];
		TreeSet<Integer> done=new TreeSet();
		for(i=0;i<n;i++) pos[i]=new ArrayList();
		for(i=0;i<n;i++)
		{
			a[i]=in.ni()-1; pos[a[i]].add(i);
		}
		for(i=0;i<n;i++)
		{
			if(pos[i].size()==0) break;
			int size=n-i; boolean poss=false;
			for(int ind:pos[i])
			{
				int low,high;
				if(done.lower(ind)==null) low=-1;
				else low=done.lower(ind);
				if(done.higher(ind)==null) high=n;
				else high=done.higher(ind);
				low++; high--; 
				if(high-low+1>=size)
				{
					poss=true; break;
				}
			}
			for(int ind:pos[i]) done.add(ind);
			if(poss) ans[n-i-1]='1';
			else ans[n-i-1]='0';
			if(pos[i].size()>1) 
			{
				i++; break;
			}
		}
		for(;i<n;i++) ans[n-i-1]='0';
		out.println(ans);	
	}
	@Override
	public void run() {
		try {  init();  } 
		catch (FileNotFoundException e) {  e.printStackTrace();   }

		int t= in.ni();

		while (t-->0) {
			solve();
			out.flush(); }
	}
	private FastInput in;    private PrintWriter out;
	public static void main(String[] args) throws Exception {	 new global().run();	 }

	private void init() throws FileNotFoundException {
		InputStream inputStream = System.in; 	 OutputStream outputStream = System.out;
		try {  if (!console && System.getProperty("user.name").equals("sachan")) {
			outputStream = new FileOutputStream("/home/sachan/Desktop/output.txt");
			inputStream = new FileInputStream("/home/sachan/Desktop/input.txt");   	}
		}	catch (Exception ignored) {	}
		out = new PrintWriter(outputStream); 	 in = new FastInput(inputStream);
	}
	static class FastInput { InputStream obj;
	public FastInput(InputStream obj) {	this.obj = obj;	}
	byte inbuffer[] = new byte[1024];  int lenbuffer = 0, ptrbuffer = 0;
	int readByte() { if (lenbuffer == -1) throw new InputMismatchException();
	if (ptrbuffer >= lenbuffer) { ptrbuffer = 0;
	try { lenbuffer = obj.read(inbuffer);  }
	catch (IOException e) { throw new InputMismatchException(); } }    
	if (lenbuffer <= 0) return -1;return inbuffer[ptrbuffer++]; }

	String ns() { int b = skip();StringBuilder sb = new StringBuilder();
	while (!(isSpaceChar(b)))	  { sb.appendCodePoint(b);b = readByte(); }return sb.toString();}

	int ni() { int num = 0, b;boolean minus = false;
	while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-')) ;
	if (b == '-') { minus = true;b = readByte(); }
	while (true) { if (b >= '0' && b <= '9') { num = num * 10 + (b - '0'); } else {
		return minus ? -num : num; }b = readByte(); }}

	long nl() { long num = 0;int b;boolean minus = false;
	while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-')) ;
	if (b == '-') { minus = true;b = readByte(); }
	while (true) { if (b >= '0' && b <= '9') { num = num * 10L + (b - '0'); } else {
		return minus ? -num : num; }b = readByte(); } }

	boolean isSpaceChar(int c) { return (!(c >= 33 && c <= 126)); }
	int skip() { int b;while ((b = readByte()) != -1 && isSpaceChar(b)) ;return b; }
	float nf() {return Float.parseFloat(ns());}
	double nd() {return Double.parseDouble(ns());}
	char nc() {return (char) skip();}
	}
}