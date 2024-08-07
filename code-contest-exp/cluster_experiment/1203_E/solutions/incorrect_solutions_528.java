
import java.io.*;
import java.util.*;
public class Boxers {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int number = sc.nextInt();
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		for(int i = 0; i < number; i++)  {
			pq.add(sc.nextInt());
		}
		int max = pq.poll();
		int count = 1;
		while(!pq.isEmpty())  {
			int x = pq.poll();
				if(x-1>max){
					count++;
			        max=x-1;
				}
				else if(x-1==max){
			        count++;
			        max=x;
			    }
				else if(x==max){
			        count++;
			        max=x+1;
			   }
			}
		System.out.println(count);
		}

	}

		
	


