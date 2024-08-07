import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
public class Main
{
	static Scanner scan = new Scanner(System.in);
	static int n;
	static LinkedList<Integer> a = new LinkedList<Integer>();
	public static void main(String args[])
	{
		n = scan.nextInt();
		int last = 0, cnt = 1;
		for(int i=0;i<n;i++)
		{
			int x = scan.nextInt();
			if(x == last) cnt++;
			else
			{
				a.add(cnt);
				cnt = 1;
				last = x;
			}
		}
		a.add(cnt);
		ListIterator<Integer> it = a.listIterator();
		it.next();
		int ans = 0;
		while(it.hasNext())
		{
			it.previous();
			ans = Math.max(ans, Math.min(it.next(),it.next()));
		}
		System.out.println(ans*2);
	}	
}