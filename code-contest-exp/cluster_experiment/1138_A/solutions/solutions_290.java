import java.util.*;

public class Sushi{

	public static boolean check(int[] arr, int len){

		SQueue que1 = new SQueue();
		SQueue que2 = new SQueue();
		for (int i=0;i<(len/2);i++){
			que1.enqueue(arr[i]);
		}
		for (int i=(len/2);i<len;i++){
			que2.enqueue(arr[i]);
		}
		if ((que1.is1() && que2.is2()) || (que1.is2() && que2.is1())){
			return true;
		}
		int i = len;
		while (i<arr.length){
			que2.enqueue(arr[i]);
			que1.enqueue(que2.dequeue());
			que1.dequeue();
			if ((que1.is1() && que2.is2()) || (que1.is2() && que2.is1())){
				return true;
			}
			i++;
		}
		return false;
	}

	public static int func(int[] arr, int[] l){
		int low = 0; int high = l.length - 1;
		int ans = 2;
		while (low <= high){
			int mid = (low+high)/2;
			if (check(arr,l[mid])){
				ans = Math.max(ans,l[mid]);
				low = mid+1;
			}
			else{
				high = mid-1;
			}
		}
		return ans;
	}

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		
		int n = in.nextInt();
		int[] arr = new int[n];
		for (int i=0;i<n;i++){
			arr[i] = in.nextInt();
		}
		int[] l = new int[(int)(n/2)];
		for (int i=1; i<=(int)(n/2); i++){
			l[i-1] = 2*i;
		}
		System.out.println(func(arr,l));
	}
}

class SQueue{
	LinkedList<Integer> list;
	private int size = 0;
	private int sum = 0;
	public SQueue(){
		this.list = new LinkedList<>();
	}
	public void enqueue(int x){
		this.list.add(x);
		this.size++;
		this.sum += x;
	}
	public int dequeue(){
		if (this.size>0){
			int val = this.list.pop();
			this.size--;
			this.sum -= val;
			return val;
		}
		return -1;
	}
	public int right(){
		return this.list.peekLast();
	}
	public int left(){
		return this.list.peek();
	}
	public int size(){
		return this.size;
	}
	public int sum(){
		return this.sum;
	}
	public boolean is2(){
		if (this.sum == this.size * 2){
			return true;
		}
		return false;
	}
	public boolean is1(){
		if (this.sum == this.size){
			return true;
		}
		return false;
	}
	public String toString(){
		return this.list.toString();
	}
}