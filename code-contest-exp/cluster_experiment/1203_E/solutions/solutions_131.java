import java.util.*;
import java.io.*;

public class boxers {
	public void run() throws Exception {
		Scanner file = new Scanner(System.in);
		int times= file.nextInt();
		ArrayList<Integer> x= new ArrayList();
		for (int i = 0; i < times; i++) x.add(file.nextInt());
		Collections.sort(x);
		HashSet<Integer> cont = new HashSet();
		for (int i = 0; i < x.size(); i++) {
			int a = x.get(i), b = x.get(i) + 1, c = x.get(i) - 1;
			if (c == 0) c = x.get(i);
			if (!cont.contains(c)) cont.add(c);
			else if (!cont.contains(a)) cont.add(a);
			else if (!cont.contains(b)) cont.add(b);
		}
		System.out.println(cont.size());
		//System.out.println(cont);
	}

	public static void main(String[] args) throws Exception {
		new boxers().run();
	}

}