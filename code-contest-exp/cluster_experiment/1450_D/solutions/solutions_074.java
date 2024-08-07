import java.io.*;
import java.util.*;
import java.math.*;

public class D {
	static byte[] buf = new byte[1<<26];
    static int bp = -1;	
	
	public static void main(String[] args) throws IOException {

		/**/
		DataInputStream in = new DataInputStream(System.in);
		/*/
		DataInputStream in = new DataInputStream(new FileInputStream("src/d.in"));
		/**/
		
		in.read(buf, 0, 1<<26);
		int t = nni();
		for (int z = 0; z < t; ++z) {
			int n = nni();
			int[] a = new int[n];
			for (int i = 0; i < n; ++i)
				a[i] = nni();
			int[] cts = new int[n+1];
			for (int x : a)
				cts[x]++;
			boolean[] hap = new boolean[n+1];
			boolean[] unhap = new boolean[n+1];
			TreeSet<Integer> rem = new TreeSet<Integer>();
			int[] some = new int[n+1];
			for (int i = 0; i < n; ++i) {
				rem.add(i);
				some[a[i]] = i;
			}
			for (int i = 1; i <= n; ++i) {
				if (!unhap[i]&&cts[i]>=1)
					hap[i] = true;
				if (a[rem.first()]!=i&&a[rem.last()]!=i)
					break;
				if (cts[i]!=1)
					break;
				rem.remove(some[i]);
			}
			hap[n] = true;
			for (int i = 1; i <= n; ++i)
				if (cts[i]!=1)
					hap[n] = false;
			StringBuilder ans = new StringBuilder();
			for (int i = n; i>=1; --i)
				ans.append(hap[i]?'1':'0');
			System.out.println(ans);
		}
	}
		
	public static int nni() {
        int ret = 0;
        byte b = buf[++bp];
        while (true) {
            ret = ret*10+b-'0';
            b = buf[++bp];
            if (b<'0'||b>'9') {
            	while (buf[bp+1]=='\r'||buf[bp+1]=='\n'||buf[bp+1]==' ') {++bp;}
            	break;
            }
        }
        return ret;
    }
}