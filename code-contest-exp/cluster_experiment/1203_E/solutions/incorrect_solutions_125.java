import java.util.Scanner;
import java.util.TreeSet;

public class Boxers 
{
	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		
		int amountBoxers = scan.nextInt();
		TreeSet<Integer> boxTeam = new TreeSet<Integer>();
		
		for(int i = 0; i < amountBoxers; ++i)
		{
			int nextBoxer = scan.nextInt();
			
			if(!boxTeam.contains(nextBoxer))
			{
				boxTeam.add(nextBoxer);
				continue;
			}
			if(!boxTeam.contains(nextBoxer+1))
			{
				boxTeam.add(++nextBoxer);
				continue;
			}
			if((nextBoxer-1) != 0 && (!boxTeam.contains(nextBoxer-1)))
			{
				boxTeam.add(--nextBoxer);
				continue;
			}
		}
		System.out.println(boxTeam.size());
		scan.close();
	}
}