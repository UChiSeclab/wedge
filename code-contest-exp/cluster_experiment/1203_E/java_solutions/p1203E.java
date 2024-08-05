import java.io.*;
import java.util.*;
import java.math.*;

public class p1203E {

	public boolean[] definite;
	public int[] cnt;

	public void cascade(int i, int dir) {
		boolean still = true;
		boolean lastlegs = false;
		i += dir;

		
		while(i > 0) {
			still = false;
			boolean nonehere = true;
			if(cnt[i] > 0) {
				nonehere = false;
			}

			if(lastlegs && nonehere) {
				return;
			}

			if(cnt[i] >= 3) {
				definite[i] = true;
				definite[i - 1] = true;
				definite[i + 1] = true;
				cnt[i] = 0;
				still = true;
			}

			if(cnt[i] == 2 && definite[i - 1]) {
				definite[i] = true;
				definite[i + 1] = true;
				cnt[i] = 0;
				still = true;
			}
			if(cnt[i] == 2 && definite[i + 1]) {
				definite[i - 1] = true;
				definite[i] = true;
				cnt[i] = 0;
				still = true;
			}

			if(cnt[i] == 1 && definite[i - 1] && definite[i]) {
				definite[i + 1] = true;
				cnt[i] = 0;
				still = true;
			}
			if(cnt[i] == 1 && definite[i - 1] && definite[i + 1]) {
				definite[i] = true;
				cnt[i] = 0;
				still = true;
			}
			if(cnt[i] == 1 && definite[i] && definite[i + 1]) {
				definite[i - 1] = true;
				cnt[i] = 0;
				still = true;
			}

			if(still)
				lastlegs = false;
			else
				lastlegs = true;

			i += dir;
		}
			

	}

	public void realMain() throws Exception {

		BufferedReader fin = new BufferedReader(new InputStreamReader(System.in), 1000000);

		String in = fin.readLine();

		String[] ar = in.split(" ");

		int n = Integer.parseInt(ar[0]);

		cnt = new int[150002];

		for(int i = 0; i < n; i++) {
			int ret = 0;
			boolean dig = false;
			for (int ch = 0; (ch = fin.read()) != -1; ) {
        			if (ch >= '0' && ch <= '9') {
            				dig = true;
           				ret = ret * 10 + ch - '0';
        			} else if (dig) break;
    			}

			cnt[ret]++;
			
		}

		definite = new boolean[150002];

		for(int i = 1; i < 150002; i++) {
			if(cnt[i] >= 3) {
				definite[i] = true;
				definite[i - 1] = true;
				definite[i + 1] = true;
				cnt[i] = 0;
				cascade(i, -1);
				cascade(i, 1);
			}
		}

		definite[0] = true;

		for(int i = 1; i < 150002; i++) {
			if(cnt[i] == 2 && definite[i - 1]) {
				definite[i] = true;
				definite[i + 1] = true;
				cnt[i] = 0;
				cascade(i, 1);
			}
			if(cnt[i] == 2 && definite[i + 1]) {
				definite[i - 1] = true;
				definite[i] = true;
				cnt[i] = 0;
				cascade(i, -1);
			}
		}

		for(int i = 1; i < 150002; i++) {
			if(cnt[i] == 1 && definite[i - 1] && definite[i]) {
				definite[i + 1] = true;
				cnt[i] = 0;
				cascade(i, 1);
			}
			if(cnt[i] == 1 && definite[i - 1] && definite[i + 1]) {
				definite[i] = true;
				cnt[i] = 0;
				
			}
			if(cnt[i] == 1 && definite[i] && definite[i + 1]) {
				definite[i - 1] = true;
				cnt[i] = 0;
				cascade(i, -1);
			}
		}

		if(cnt[1] == 2) {
			definite[1] = true;
			definite[2] = true;
			cnt[1] = 0;
			cascade(1, 1);
		}

		if(cnt[1] == 1 && definite[2]) {
			definite[1] = true;
			cnt[1] = 0;
		}

		int ret = 0;

		for(int i = 1; i < 150002; i++) {
			if(definite[i]) {
				ret++;
			}
			ret += cnt[i];
		}

		System.out.println(ret);			



	}


	public static void main(String[] args) throws Exception {
		p1203E a = new p1203E();
		a.realMain();
	}
}