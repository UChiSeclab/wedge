import java.util.*;
import java.io.*;
 
public class Es5
{
    static IO io = new IO();
    public static void main(String[] args)
    {
		int n = io.getInt();
		Integer[] a = new Integer[n];
		for(int i=0; i<n; i++)
			a[i] = io.getInt();
		Arrays.sort(a);
		if(a[0]>1)
			a[0]--;
		int count = 1;
		int last = a[0];
		for(int i=1; i<n; i++){
			if(a[i]-1 > last)
			{
				a[i]--;
				count++;
				last = a[i];
			}
			else if(a[i] > last){
				count++;
				last = a[i];
			}
			else if(a[i]+1 > last){
				a[i]++;
				count++;
				last = a[i];
			}
		}
		io.println(count);
		
        io.close();
    }
	/*
	public static int solve(int pos, int prev)
	{
		if(pos == n)
			return 0;
		return 0;
	}*/
}
 
 
 
class IO extends PrintWriter {
	public IO() {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(System.in));
    }
 
    public IO(String fileName) {
        super(new BufferedOutputStream(System.out));
        try{
            r = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            this.println("File Not Found");
        }
    }
 
    public boolean hasMoreTokens() {
        return peekToken() != null;
    }
 
    public int getInt() {
        return Integer.parseInt(nextToken());
    }
 
    public double getDouble() {
        return Double.parseDouble(nextToken());
    }
 
    public long getLong() {
        return Long.parseLong(nextToken());
    }
 
    public String getWord() {
        return nextToken();
    }
 
	public String getLine(){
        try{
            st = null;
            return r.readLine();
        }
        catch(IOException ex){}
        return null;
    }
	
 
    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;
 
    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        return token;
    }
 
    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}