import java.util.*;
import java.io.*;

public class boxers {
	public void run() throws Exception {
		Scanner file = new Scanner(System.in);
		int times= file.nextInt();
		int[]x = new int[times];
		for (int i= 0; i < times; i++) {
			x[i] = file.nextInt();
		}
		Arrays.sort(x);
		HashSet<Integer> cont = new HashSet();
		for (int i = 0; i < x.length; i++) {
			int a = x[i], b = x[i] + 1, c = x[i] - 1;
			if (c == 0) c = x[i];
			if (!cont.contains(a)) cont.add(a);
			else if (!cont.contains(b)) cont.add(b);
			else if (!cont.contains(c)) cont.add(c);
		}
		System.out.println(cont.size());
		//System.out.println(cont);
	}

	public static void main(String[] args) throws Exception {
		new boxers().run();
	}

}