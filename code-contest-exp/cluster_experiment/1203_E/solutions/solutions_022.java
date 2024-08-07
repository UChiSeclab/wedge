import java.io.*;
import java.util.*;
public class MyClass {
    
    public static void main(String args[]) {
      FastReader sc = new FastReader();     //For Fast IO
      func f = new func();                  //Call func for swap , permute and for sort.
      int n = sc.nextInt();
      int max[] = new int[n];
      HashSet<Integer> hs = new HashSet<>();
      for(int i=0;i<n;i++){
          int x =sc.nextInt();
          max[i]=x;
      }
      f.sort(max);
      if(max[0]!=1){
          max[0]--;
      }
      max[n-1]++;
      for(int i=1;i<n-1;i++){
          if((max[i]-max[i-1])==1){continue;}
          else if((max[i]-max[i-1]>1)){max[i]--;}
          else{max[i]++;}
      }
      for(int i=0;i<n;i++){
          hs.add(max[i]);
      }
      int m1 = hs.size();
      System.out.println(m1);
    }
}
class FastReader 
	{ 
		BufferedReader br; 
		StringTokenizer st; 
 
		public FastReader() 
		{ 
			br = new BufferedReader(new
					InputStreamReader(System.in)); 
		} 
 
		String next() 
		{ 
			while (st == null || !st.hasMoreElements()) 
			{ 
				try
				{ 
					st = new StringTokenizer(br.readLine()); 
				} 
				catch (IOException e) 
				{ 
					e.printStackTrace(); 
				} 
			} 
			return st.nextToken(); 
		} 
 
		int nextInt() 
		{ 
			return Integer.parseInt(next()); 
		} 
 
		long nextLong() 
		{ 
			return Long.parseLong(next()); 
		} 
 
		double nextDouble() 
		{ 
			return Double.parseDouble(next()); 
		} 
 
		String nextLine() 
		{ 
			String str = ""; 
			try
			{ 
				str = br.readLine(); 
			} 
			catch (IOException e) 
			{ 
				e.printStackTrace(); 
			} 
			return str; 
		} 
	}
class func{
    static ArrayList<String> al = new ArrayList<>();
        public static void sort(int[] arr) {
            int n = arr.length, mid, h, s, l, i, j, k;
            int[] res = new int[n];
            for (s = 1; s < n; s <<= 1) {
                for (l = 0; l < n - 1; l += (s << 1)) {
                    h = Math.min(l + (s << 1) - 1, n - 1);
                    mid = Math.min(l + s - 1, n - 1);
                    i = l;
                    j = mid + 1;
                    k = l;
                    while (i <= mid && j <= h) res[k++] = (arr[i] <= arr[j] ? arr[i++] : arr[j++]);
                    while (i <= mid) res[k++] = arr[i++];
                    while (j <= h) res[k++] = arr[j++];
                    for (k = l; k <= h; k++) arr[k] = res[k];
                }
            }
        }
        public static void permute(char a[] , int i , int n){
            if(i==n-1){
                String s = new String(a);
                al.add(s);                      // al stores all permutations of string. 
                return;
            }
            for(int j=i;j<n;j++){
                swap(a,i,j);
                permute(a,i+1,n);
                swap(a,i,j);
            }
        }
        public static void swap(char a[],int i, int j){
            char temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        public static void swap(int a[],int i, int j){
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        
 
}



	 	 
