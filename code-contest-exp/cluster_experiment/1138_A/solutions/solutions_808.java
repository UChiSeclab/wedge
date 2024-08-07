import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CodeForces
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

		int n = input.nextInt();
		ArrayList<Integer> list = new ArrayList<>();

		int before = input.nextInt();
		int streak = 1;
		for (int i = 1; i < n; i++)
		{
			int z = input.nextInt();
			if (z == before)
			{
				streak++;
			} else
			{
				list.add(streak);
				before = z;
				streak = 1;
			}
		}
		list.add(streak);

		int max = 0;
		for (int i = 0; i < list.size() - 1; i++)
		{
			if (max < Math.min(list.get(i), list.get(i + 1)))
			{
				max = Math.min(list.get(i), list.get(i + 1));
			}
		}
		System.out.println(max * 2);
	}
}