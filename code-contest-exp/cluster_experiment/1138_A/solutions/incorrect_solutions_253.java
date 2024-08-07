import java.util.Scanner;

public class SushiForTwo
{
  public static void main(String[] args)
  {
    Scanner sc = new Scanner(System.in);
    int size = sc.nextInt();
    int[] input = new int[size];
    int index = 1;
    input[0] = sc.nextInt();
    // array to check for segments of the same value
    int[] same = new int[size];
    same[0] = 1;
    int sameIndex = 0;
    while (index < size)
    {
      // check for subsegment of the same value
      input[index] = sc.nextInt();
      if (input[index] == input[index - 1])
      {
        same[sameIndex]++;
      }
      // if 2 numbers are different, move on to the next segment
      else
      {
        sameIndex++;
        same[sameIndex]++;
      }
      index++;
    }
    int max = Integer.MIN_VALUE;
    for (int i = 1; i < same.length && same[i] != 0; i++)
    {
      if (same[i] >= same[i - 1] && same[i - 1] > max)
      {
        max = same[i - 1];
      }
      if (i < same.length - 1 && same[i] >= same[i + 1] && same[i + 1] > max)
      {
        max = same[i + 1];
      }
    }
    System.out.println(max * 2);
  }
}