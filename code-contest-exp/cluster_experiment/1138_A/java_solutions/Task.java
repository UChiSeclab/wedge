import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task
{
	public static void solve()
	{
		int n = scanner.nextInt();
		List<Integer> list = new ArrayList<>();
		int prev = -1;
		for(int i=0;i<n;i++) {
			int val = scanner.nextInt();
			if(val == prev) {
				int newCount = list.get(list.size()-1) + 1;
				list.remove(list.size()-1);
				list.add(newCount);
			} else {
				list.add(1);
			}
			prev = val;
		}
		int max = 0;
		for(int i=1;i<list.size();i++) {
			int min = Math.min(list.get(i-1), list.get(i));
			max = Math.max(max, min);
		}
		System.out.println(2*max);
	}

	public static void main(String[] args) throws Exception
	{
		try
		{
			scanner = new Scanner(new BufferedInputStream(System.in));
			systemOut = new BufferedOutputStream(System.out);
			solve();
		}
		finally
		{
			scanner.close();
			systemOut.close();
		}
	}

	private static Scanner scanner = null;

	private static BufferedOutputStream systemOut = null;

	public static void out(String str)
	{
		try
		{
			systemOut.write(str.getBytes("utf-8"));
		}
		catch(Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public static void outln(String str)
	{
		out(str);
		out("\n");
	}
}
