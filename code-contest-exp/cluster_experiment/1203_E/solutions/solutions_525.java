
import java.io.*;
import java.util.*;
public class Boxers {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int number = sc.nextInt();
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Collections.reverseOrder());
		for(int i = 0; i < number; i++)  {
			pq.add(sc.nextInt());
		}
        int max = pq.poll()+1;
        int count = 1;
        while (!pq.isEmpty() && max>1){
            int x = pq.poll();
            if(x+1<max){
                max = x+1;
            }else if(x+1==max){
                max = x;
            }else if(x==max){
                max = x-1;
            }else{
                continue;
            }
            count++;
        }
		System.out.println(count);
		}

	}

		
	


