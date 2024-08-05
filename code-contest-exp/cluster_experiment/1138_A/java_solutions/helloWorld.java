import java.util.*;

public class helloWorld 
{
	public static void main(String[] args) 
	{		
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int ans = 0;
	
		int x = 0;
		int last = 0, cnt = 0;
		for(int i = 0; i < n; i++) {
			int a = in.nextInt();
			if(a == last)
				cnt++;
			else {
				ans = Math.max(ans, Math.min(x, cnt) );
				x = cnt;
				last = a;
				cnt = 1;
			}
		}
		ans = Math.max(ans, Math.min(x, cnt) );
		
		System.out.println(ans*2);

		in.close();
	}
}