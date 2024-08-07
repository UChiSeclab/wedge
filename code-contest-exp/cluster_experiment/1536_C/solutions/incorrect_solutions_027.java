//package pack;
import java.io.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.math.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Math.sqrt;
import static java.lang.Math.floor;







public class topcoder {
	 public static class TreeNode {
	     int val;
	   TreeNode left;
	    TreeNode right;
	    TreeNode() {}
	    TreeNode(int val) { this.val = val; }
	    TreeNode(int val, TreeNode left, TreeNode right) {
	        this.val = val;
	        this.left = left;
	        this.right = right;
	   }
	    
	   static int depth(TreeNode root) {
	    	
		   if(root == null)return 0;
		   int a = 1+ depth(root.left);
		   int b = 1+ depth(root.right);
		   
		   return Math.max(a,b);
	    }

	   
	   static HashSet<Integer>set = new HashSet<>();
	   static void deepestLeaves(TreeNode root, int cur_depth, int depth) {
		   
		   if(root == null)return;
		   if(cur_depth == depth)set.add(root.val);
		   
		  deepestLeaves(root.left,cur_depth+1,depth);
		   deepestLeaves(root.right,cur_depth+1,depth);
		   
		   
		   
	   }
	   
	   public static void print(TreeNode root) {
		   
		   if(root == null)return;
		   
		   System.out.print(root.val+" ");
		   System.out.println("er");
		   print(root.left);
		   print(root.right);
	   }
	   public static HashSet<Integer>original(TreeNode root){
		   int d = depth(root);
		   deepestLeaves(root,0,d);
		   return set;
	   }
	   static HashSet<Integer>set1 = new HashSet<>();
	   static void leaves(TreeNode root) {
		   
		   if(root == null)return;
		    
		   if(root.left == null && root.right == null)set1.add(root.val);
		   
		   leaves(root.left);
		   leaves(root.right);
		   
		   
	   }
	  public static boolean check(HashSet<Integer>s, HashSet<Integer>s1) {
		  
		  if(s.size() != s1.size())return false;
		  
		  for(int a : s) {
			  if(!s1.contains(a))return false;
		  }
		  
		  return true;
	  }
	 static  TreeNode subTree;
	   public static void smallest_subTree(TreeNode root) {
		   
		   if(root == null)return;
		   
		   smallest_subTree(root.left);
		   smallest_subTree(root.right);
		   
		   
		   set1 = new HashSet<>();
		   leaves(root);
		   boolean smallest = check(set,set1);
		   
		  if(smallest) {
			  subTree = root;
			  return;
		   
	   }
	 }
	   
	   public static TreeNode answer(TreeNode root) {
		   smallest_subTree(root);
		   
		   return subTree;
	   }
	 }
	static class pair{
		 
		 long first;
		 long second;
		  public pair(long first, long second) {
			  this.first = first;
			  this.second = second;
		  }
	}


	static class Compare{
		
		 static void compare(ArrayList<pair>arr, long n) {
			 
			 Collections.sort(arr,new Comparator<pair>() {
				 public int compare(pair p1, pair p2) {
					 return (int) (p1.first-p2.first);
				 }
			 });
		
	}
	}
	
	public static HashMap<Integer,Integer>sortByValue(HashMap<Integer,Integer>hm){
	
		
		  List<Map.Entry<Integer,Integer>>list = new LinkedList<Map.Entry<Integer,Integer>>(hm.entrySet());

     Collections.sort(list,new Comparator<Map.Entry<Integer,Integer>>(){
	  public int compare(Map.Entry<Integer,Integer>o1,
	    Map.Entry<Integer,Integer>o2)
	  {
      return (o1.getValue()).compareTo(o2.getValue());
	  }});
     
   HashMap<Integer,Integer>temp = new LinkedHashMap<Integer,Integer>(); 
	for(Map.Entry<Integer,Integer>aa : list) {
     temp.put(aa.getKey(), aa.getValue());
	}
	
	return temp;
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
static class pairr implements  Comparable<pairr>{
	static Long value;
	Long index;
	
	   public pairr(Long value, Long index) {
		    this.value = value;
		    this.index = index;
	   }
	   public int  compareTo(pairr o) {
		   return  (int)(value-o.value);
	  }
}



	 static class Key<K1, K2>
	{
	    public K1 key1;
	    public K2 key2;
	 
	    public Key(K1 key1, K2 key2)
	    {
	        this.key1 = key1;
	        this.key2 = key2;
	    }
	 
	    @Override
	    public boolean equals(Object o)
	    {
	        if (this == o) {
	            return true;
	        }
	 
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }
	 
	        Key key = (Key) o;
	        if (key1 != null ? !key1.equals(key.key1) : key.key1 != null) {
	            return false;
	        }
	 
	        if (key2 != null ? !key2.equals(key.key2) : key.key2 != null) {
	            return false;
	        }
	 
	        return true;
	    }
	 
	    @Override
	    public int hashCode()
	    {
	        int result = key1 != null ? key1.hashCode() : 0;
	        result = 31 * result + (key2 != null ? key2.hashCode() : 0);
	        return result;
	    }
	 
	    @Override
	    public String toString() {
	        return "[" + key1 + ", " + key2 + "]";
	    }
	}
	
	
	 public static int sumOfDigits (long n) {
		 
		 int sum = 0;
		 
		 while(n > 0) {
			 sum += n%10;
			 n /= 10;
		 }
		 
		 return sum;
	 
	 }
	 
	 
	 public static long binary_search(int s, int e, long num, long []ar) {
		 
		 if(s > e) {
			 return -1;
		 }	 
			 
		 
		     int mid = (s+e)/2;
		     
		      if(s == e && ar[s] >= num) {
		    	  return ar[s];
		      }else if(s == e && ar[s] < num) {
		    	  return -1;
		      }else if(ar[mid] < num) {
		    	  return binary_search(mid+1,e,num,ar);
		      }else if(ar[mid] >= num) {
		    	    return binary_search(s,mid,num,ar);
		      }
		      
		      return -1;
		 
	 }
	 
	 
	 public static int index_search(int s, int e, long num, long []ar) {
		 
		 if(s > e) {
			 return -1;
		 }	 
			 
		 
		     int mid = (s+e)/2;
		     
		      if(s == e && ar[s] >= num) {
		    	  return s;
		      }else if(s == e && ar[s] < num) {
		    	  return -1;
		      }else if(ar[mid] < num) {
		    	  return index_search(mid+1,e,num,ar);
		      }else if(ar[mid] >= num) {
		    	    return index_search(s,mid,num,ar);
		      }
		      
		      return -1;
		 
	 }
		     public static void swap(int []ar, int i, int j) {
		    	 
		    	 
		    	 for(int k= j; k >= i; k--) {
		    		 int temp = ar[k];
		    		 ar[k] = ar[k+1];
		    		 ar[k+1] = temp;
		    	 }
		     }
		     
		     public static boolean digit_exists(long n) {
		    	 
		    	 while(n > 0) {
		    		 if(n%10 == 9)
		    			 return true;
		    		 n = n/10;
		    	 }
		    	 
		    	 return false;
		     }
		     public static int log(int n) {
		    	 
		    	 int c = 0;
		    	 while(n > 0) {
		    		 c++;
		    		 n /=2;
		    	 }
		    	 
		    	 return c;
		     }
			  public static int findOr(int[]bits){
	    	        int or=0;
	    	            for(int i=0;i<32;i++){
	    	                or=or<<1;
	    	                if(bits[i]>0)
	    	                    or=or+1;
	    	            }
	    	        return or;
	    	    }
			  
			  static void simpleSieve(int limit, Vector<Integer> prime)
			    {
			        // Create a boolean array "mark[0..n-1]" and initialize
			        // all entries of it as true. A value in mark[p] will
			        // finally be false if 'p' is Not a prime, else true.
			        boolean mark[] = new boolean[limit+1];
			         
			        for (int i = 0; i < mark.length; i++)
			            mark[i] = true;
			      
			        for (int p=2; p*p<limit; p++)
			        {
			            // If p is not changed, then it is a prime
			            if (mark[p] == true)
			            {
			                // Update all multiples of p
			                for (int i=p*p; i<limit; i+=p)
			                    mark[i] = false;
			            }
			        }
			      
			        // Print all prime numbers and store them in prime
			        for (int p=2; p<limit; p++)
			        {
			            if (mark[p] == true)
			            {
			                prime.add(p);
			             
			            }
			        }
			    }
			      
			    // Prints all prime numbers smaller than 'n'
			public    static void segmentedSieve(int n, ArrayList<Integer>l)
			    {
			        // Compute all primes smaller than or equal
			        // to square root of n using simple sieve
			        int limit = (int) (floor(sqrt(n))+1);
			        Vector<Integer> prime = new Vector<>(); 
			  
			        simpleSieve(limit, prime);
			      
			        // Divide the range [0..n-1] in different segments
			        // We have chosen segment size as sqrt(n).
			        int low  = limit;
			        int high = 2*limit;
			      
			        // While all segments of range [0..n-1] are not processed,
			        // process one segment at a time
			        while (low < n)
			        {
			            if (high >= n)
			                high = n;
			 
			            // To mark primes in current range. A value in mark[i]
			            // will finally be false if 'i-low' is Not a prime,
			            // else true.
			            boolean mark[] = new boolean[limit+1];
			             
			            for (int i = 0; i < mark.length; i++)
			                mark[i] = true;
			      
			            // Use the found primes by simpleSieve() to find
			            // primes in current range
			            for (int i = 0; i < prime.size(); i++)
			            {
			                // Find the minimum number in [low..high] that is
			                // a multiple of prime.get(i) (divisible by prime.get(i))
			                // For example, if low is 31 and prime.get(i) is 3,
			                // we start with 33.
			                int loLim = (int) (floor(low/prime.get(i)) * prime.get(i));
			                if (loLim < low)
			                    loLim += prime.get(i);
			      
			                /*  Mark multiples of prime.get(i) in [low..high]:
			                    We are marking j - low for j, i.e. each number
			                    in range [low, high] is mapped to [0, high-low]
			                    so if range is [50, 100]  marking 50 corresponds
			                    to marking 0, marking 51 corresponds to 1 and
			                    so on. In this way we need to allocate space only
			                    for range  */
			                for (int j=loLim; j<high; j+=prime.get(i))
			                    mark[j-low] = false;
			            }
			      
			            // Numbers which are not marked as false are prime
			            for (int i = low; i<high; i++)
			                if (mark[i - low] == true)
			                   l.add(i);
			      
			            // Update low and high for next segment
			            low  = low + limit;
			            high = high + limit;
			        }
			    }
			    
			    public static int find_indexNum(long k) {
			    	
			    	long k1 = k;
			    	int power = 0;
			    	
			    	while(k > 0) {
			    		power++;
			    		k /=2 ;
			    	}
			    	
			    	long check = (long)Math.pow(2, power-1);
			    	if(k1 == check) {
			    		return power;
			    	}
			//   System.out.println(power);
			    	long f = (long)Math.pow(2, power-1);
			    	long rem = k1-f;
			    	return find_indexNum(rem);
			    }
			    
			 public static void sortPair(ArrayList<pair>l, int n) {
				  n = l.size();
				  
				  Compare obj = new Compare();
				  obj.compare(l, n);
			 }
               
            
			 
				   
		public static void shuffle(int []array, int num,int t_index,  boolean []vis, int m ) {	  
			 
		
		for(int i = 0; i < m; i++) {
			if(vis[i] == false) {
				
				
				int temp = array[i];
		          if(i < t_index) {
		        	  vis[i] = true;
		          }
				array[i] = num;
				array[t_index] = temp;
			//	System.out.println(array[t_index]+" "+array[i]);
				break;
			}
		}
		}
		
		public static void rotate(int []arr,int j, int times, int m) {
			
		
			
			 if(j == 0) {
				 int temp1 = arr[0];
				 arr[0] = arr[times];
				 arr[times] = temp1;
				 
			 }else {
			 int temp = arr[j];
			 int z = arr[0];
				
			 arr[0] = arr[times];
			
			 arr[j] = z;
			 arr[times] =  temp;
			    
			
			
		}

		}
		
		public static void recur(int i,int A, int B,int []dp,int []metal, int n, boolean took,int ind) {
			
		  if(i-A <= 0 && i-B <= 0)return;
		    int count = 0;
		    
		    for(int j = 1; j <= n; j++) {
		    	if(dp[j] >= metal[j]) {
		    		count++;
		    	}
		    }
		    
		    if(count == n)return;
			if(i-A >= 0 && i-B >= 0 && dp[i] > 0 && dp[i] > metal[i]) {
			dp[i]--;
		
			dp[i-A]++;
		
			dp[i-B]++;
			}
			
			
			if(ind == 6) {
		//	System.out.println(Arrays.toString(dp));
			}
			recur(i-A,A,B,dp,metal,n,took,ind);
			recur(i-B,A,B,dp,metal,n,took,ind);
		}
		
		
		public static boolean isPrime(int n) { 
	        if (n <= 1) return false; 
	        if (n <= 3) return true; 
	        if (n % 2 == 0 || n % 3 == 0) return false; 
	        for (int i = 5; i * i <= n; i = i + 6) 
	            if (n % i == 0 || n % (i + 2) == 0) 
	            return false; 
	        return true; 
	    }
		
		  public static boolean[] getSieve(int n) {
		        boolean[] isPrime = new boolean[n+1];
		        for (int i = 2; i <= n; i++) isPrime[i] = true;
		        for (int i = 2; i*i <= n; i++) if (isPrime[i]) 
		                for (int j = i; i*j <= n; j++) isPrime[i*j] = false;
		        return isPrime;
		    }
		  

		   static int gcd(int a, int b) {
			   
			   if(a == 0)
				   return b;
			   
			   return gcd(b%a,a);
		   }
		    
		    
		
	
			
	    public static void dfs(LinkedList<Integer>[]list, HashMap<Integer,Integer>map, int parent, int n) {
	    	
	    	Stack<Integer>st = new Stack<>();
	   
	    	
	    }
	   public static boolean pos(int n) {
		
		   
		   int i = 1;
		   boolean pos = false;
		   
		   while(i*i <= n) {
			   if(i*i*2 == n || i*i*4 == n) {
				   pos = true;
				   break;
			   }
			   i++;
		   }
		   if(pos)return true;
		    return false;
		   
	   }
	   static  long count = 0;
	    public static void pairs (int []ar, int s, int e) {
	    	
	    	    if(e <= s)return;
	       //   System.out.println(ar[s]+" "+ar[e]+" "+s+" "+e);
	    	     if(ar[e]-ar[s] == e-s) {
		    	    	count++;
		    	    	//System.out.println("sdf");
	    	     }
	    	    	
            pairs(ar,s+1,e);
            pairs(ar,s,e-1);
	    		 
	    }	
	  
		
	    public static long ways(long n) {
	    	
	    	return (n*(n-1))/2;
	    }
	    
	  
	    
	    static boolean isPrime(long n) {
	    	
	    	if(n <= 1)return false;
	    	if(n <= 3)return true;
	    	
	    	if(n%2 == 0 || n%3 == 0)return false;
	    	
	    	
	    	for(int i = 5; i*i <= n; i+= 6) {
	    		if(n%i == 0 || n%(i+2) == 0)
	    			return false;
	    	}
	    	
	    	return true;
	    }
	    
	    static long nextPrime(long n) {
	    	
	    	boolean found = false;
	    	long prime = n;
	    	
	    	while(!found) {
	    		prime++;
	    		if(isPrime(prime))
	    			found = true;
	    	}
	    	
	    	return prime;
	    }
	    
	    public static boolean isValid(int h, int m, int hour, int minute) {
			int a = flip(hour / 10);
			if (a == -1) return false;
			int b = flip(hour % 10);
			if (b == -1) return false;
			int c = flip(minute / 10);
			if (c == -1) return false;
			int d = flip(minute % 10);
			if (d == -1) return false;
			if (10 * d + c >= h) return false;
			if (10 * b + a >= m) return false;
			return true;
		}
		
		public static int flip(int x) {
			if (x == 0) return 0;
			if (x == 1) return 1;
			if (x == 2) return 5;
			if (x == 5) return 2;
			if (x == 8) return 8;
			return -1;
		}
	    static long maximum(long a, long b, long c, long d) {
	    	
	    	long m = Math.max(a, b);
	    	long m1 = Math.max(c, d);
	    	return Math.max(m1, m1);
	    }
	    
   static long minimum(long a, long b, long  c,long  d) {
	    	
	    	long m = Math.min(a, b);
	    	 long m1 = Math.min(c, d);
	    	return Math.min(m, m1);
	    }
   static long ans = 0;
		public static void solve1(boolean [][]vis,long [][]mat, int r, int c, int r2, int c2, int r1, int c1, int r3, int c3) {
			
		 if(r > r1 || c > c1 || r > r2 || c > c2 || r1 > r3 || c1 > c3 || r3 < r2 || c3 < c2 ||
				 vis[r][c] || vis[r1][c1]|| vis[r2][c2] || vis[r3][c3])
			    return;
		 
		 vis[r][c] = true;
		 vis[r1][c1] = true;
		 vis[r2][c2] = true;
		 vis[r3][c3] = true;
		 long max = maximum(mat[r][c],mat[r1][c1],mat[r2][c2],mat[r3][c3]);
		 long min = minimum(mat[r][c],mat[r1][c1],mat[r2][c2],mat[r3][c3]);
		
		 long a = mat[r][c];
		 long b = mat[r1][c1];
		 long c4 = mat[r2][c2];
		 long d =mat[r3][c3];
		 long []p = {a,b,c4,d};
		 Arrays.sort(p);
		 long temp = (p[2]+p[3]-p[0]-p[1]);
	     if(r == r1 && r == r2 && r2 == r3 && r1 == r3)
	    	 temp /= 2;
		 System.out.println(Arrays.toString(p));
		 ans += temp;
		 
		 
		 
		  solve1(vis,mat,r+1,c,r2+1,c2,r1-1,c1,r3-1,c3); solve1(vis,mat,r,c+1,r2,c2-1,r1,c1+1,r3,c3-1); solve1
				 (vis,mat,r+1,c+1,r2+1,c2-1,r1-1,c1+1,r3-1,c3-1);
		 
			
		}
		
		
		
		
		
		
		
		
		
		
		
	    private static int solve(int[][] mat, int i, int c, int j, int c2, int k, int c1, int l, int c3) {
			// TODO Auto-generated method stub
			return 0;
		}
        
	    public static int dfs(int parent, LinkedList<Integer>[]list) {
	    	
	    	
	    	for(int i : list[parent]) {
	    		if(list[parent].size() == 0) {
	    			return 0;
	    		}else {
	    			return 1 + dfs(i,list);
	    		}
	    	}
	    	
	    	return 0;
	    	
	    }
	    
	    
	    public static long answer = Integer.MAX_VALUE;
	    
	    
	    public static void min_Time(int [][]dp, int i, HashSet<Integer>set, int min, int r, int c) {
	    	
	    	if(i > r) {
	    		answer = Math.min(answer, min);
	    		return;
	    	}
	    	if(min > answer)return;
	    
	    	    for(int j = i; j <= c; j++) {
	    	    	if(!set.contains(j)) {
	    	    		set.add(j);
	    	    		min += dp[i][j];
	    	    		min_Time(dp,i+1,set,min,r,c);
	    	    		min -= dp[i][j];
	    	    		set.remove(j);
	    	    	}
	    	    }
	    }
	    
	    
	    public static void dp(int [][]dp, int r, int c, int o, int z, long sum) {
	    	  
	    	  if(r > o) {
	    		  answer = Math.min(answer, sum);
	    	  }
	    	  if(r > o || c > z) {
	    		  return;
	    	  }
	    	  if(sum > answer)return;
	    	  sum += dp[r][c];
	    	  
	    	  dp(dp,r+1,c+1,o,z,sum);
	    	  sum -= dp[r][c];
	    	  dp(dp,r,c+1,o,z,sum);
	    	  
	    }
	    
	static    HashSet<ArrayList<Integer>>l = new HashSet<>();
	    
	    
	    public static void fourSum(Deque<Integer>ll, int i, int target, int []ar, int n) {
	    	
	    	if(ll.size() == 4) {
	    		int sum = 0;
	    		ArrayList<Integer>list = new ArrayList<>();
	    
	    		for(int a : ll) {
	    			sum += a;
	    			list.add(a);
	    		}
	    		
	    		if(sum == target) {
	    			
	    			Collections.sort(list);
	    			
	    		
	    			l.add(list);
	    		//	System.out.println(ll);
	    		}
	    		
	    		return;
	    	}
	    		
	    		   for(int j = i; j < n; j++) {
	    			       
	    			        ll.add(ar[j]);
	    				
	    				   fourSum(ll,j+1,target,ar,n);
	    				     ll.removeLast();
	    			   }
	    		   }
	    
	    	
	    
	     static int max_bottles(int cur, int exchange, int n){
	    	   if(cur == exchange){
		             cur = 0;
		             n++;
		           
		         }
	         if(n == 0)return 0;
	         
	      
	         
	         return 1+ max_bottles(cur+1,exchange,n-1);
	     }
	    
	     
	     
		    public static void fill(int [][]mat, List<Integer>ans, int row_start, int row_end, int col_start, int col_end) {
			    
		    	
		    	for(int i = col_start; i <= col_end; i++) {
		    		ans.add(mat[row_start][i]);
		    	}
		    	
		    	for(int i = row_start+1; i <= row_end; i++) {
		    		ans.add(mat[i][col_end]);
		    	}
		    	if(col_start == col_end)return;
		    	if(row_start == row_end)return;
		    	for(int i = col_end-1; i >= col_start; i--) {
		    		ans.add(mat[row_end][i]);
		    	}
		    	
		    	for(int i = row_end-1; i >= row_start+1; i--) {
		    		ans.add(mat[i][col_start]);
		    	}
		    }
		     
	
	    public static void create(int [][]mat, int j, int i, int k) {
	    	
	    	if(i < 1 || j >= mat.length)return;
	    	
	    	mat[j][i] = k;
	    	create(mat,j+1,i-1,k+1);
	    	
	    	
	    }
	    
	    public static long sum(int [][]mat, int x1, int y1, int x2, int y2) {
	    	
	    	
	    	   long sum = 0;
	    	   while(x1 <= x2) {
	    		   sum += mat[x1][y1];
	    		//   System.out.println(mat[x1][y1]);
	    		   x1++;
	    	   }
	    	   y1++;
	    	   while(y1 <= y2) {
	    		   sum += mat[x2][y1];
	    		   y1++;
	    	   }
	    	   
	    	   return sum;
	    }
	   
	     public static boolean allneg(int []ar, int n) {
	    	 
	    	 for(int i = 0; i < n; i++) {
	    		 if(ar[i] >= 0)return false;
	    	 }
	    	 
	    	 return true;
	     }
	     public static boolean allpos(int []ar, int n) {
	    	 
	    	 for(int i = 0; i < n; i++) {
	    		 if(ar[i] <= 0)return false;
	    	 }
	    	 
	    	 return true;
	     }
	
	     public static int max_pos(int []ar, int n) {
	    	
	    	 int min = Integer.MAX_VALUE;
	    	
	    	for(int i = 1; i < n; i++) {
	    		if(ar[i] > 0) {
	    			break;
	    		}
	    		int a = Math.abs(ar[i]-ar[i-1]);
	    		min = Math.min(min, a);
	    	}
	    	int c = 0;
	    	boolean zero = false;
	    	TreeSet<Integer>set = new TreeSet<>();
	    	int neg = 0;
	    	for(int i = 0; i < n; i++) {
	    		if(ar[i] <= 0) {neg++; if(ar[i] == 0) zero = true; continue;}
	    	    if(ar[i] <= min) {
	    	    	c = 1;
	    	    }
	    	}
	    	
	    	neg += c;
	    	return neg;
	    	
	    	
	    	
	    	
	     }
	
	      
	     static final int MAX = 10000000;
	     
	  // prefix[i] is going to store count
	  // of primes till i (including i).
	  static int prefix[] = new int[MAX + 1];
	   
	  static void buildPrefix() {
	       
	      // Create a boolean array "prime[0..n]". A
	      // value in prime[i] will finally be false
	      // if i is Not a prime, else true.
	      boolean prime[] = new boolean[MAX + 1];
	      Arrays.fill(prime, true);
	   
	      for (int p = 2; p * p <= MAX; p++) {
	   
	      // If prime[p] is not changed, then
	      // it is a prime
	      if (prime[p] == true) {
	   
	          // Update all multiples of p
	          for (int i = p * 2; i <= MAX; i += p)
	          prime[i] = false;
	      }
	      }
	   
	      // Build prefix array
	      prefix[0] = prefix[1] = 0;
	      for (int p = 2; p <= MAX; p++) {
	      prefix[p] = prefix[p - 1];
	      if (prime[p])
	          prefix[p]++;
	      }
	  }
	  
	  static int query(int L, int R)
	  {
	      return prefix[R] - prefix[L - 1];
	  }
	    
	   static void alter(int n) {	 
	    	 
 	  int ans = 0;
	  
	  
	  boolean []vis = new boolean[n+1];
	
	  
	  for(int i = 2; i <= n; i++){
		  boolean p = false;
		  if(vis[i] == false) {
		   for(int j = i; j <= n; j+=i) {
			       if(vis[j] == true) {
			    	   p = true;
			       }else {
			    	   vis[j] = true;
			       }
		   }
		   if(!p)ans++;
	  }
	  }
	  
	  System.out.println(ans);
  }
	     
	     public static void solveDK(int []dp, int i, int D, int K) {
	    	 
	    	 int d = D/K;
	    	 int ans = -1;
	    	 int ind = d+1;
	    	 
	    	 while(ind < i) {
	    		 int temp = i/ind;
	    		 
	    		 temp--;
	    		 if(dp[temp*ind] == temp) {
	    			 ans = dp[temp*ind]+1;
	    			 dp[i] = ans;
	    			 break;
	    		 }
	    		 ind = ind*2;
	    	 }
	    	 if(ans == -1)
	    	 dp[i] = 1;
	    	 
	    	
	     }
	     
	     
  public static void solveKD(int []dp, int i, int D, int K) {
	    	 
	    	 int d = K/D;
	    	 int ans = -1;
	    	 int ind = d+1;
	    	 
	    	 while(ind < i) {
	    		 int temp = i/ind;
	    		 
	    		 temp--;
	    		 if(dp[temp*ind] == temp) {
	    			 ans = dp[temp*ind]+1;
	    			 dp[i] = ans;
	    			 break;
	    		 }
	    		 ind = ind*2;
	    	 }
	    	 if(ans == -1)
	    	 dp[i] = 1;
	    	 
	    	  
	     }
  
  
		public static void main(String args[])throws IOException{
			 
	    	//	 System.setIn(new FileInputStream("Case.txt"));
	    		 BufferedReader ob = new BufferedReader(new InputStreamReader(System.in));
	    	  
	    
	    		  
	    		  
	    	      int t = Integer.parseInt(ob.readLine());
	    	   
	    	      
	    	  I:    while(t --> 0) {
	    	     int n = Integer.parseInt(ob.readLine());
	    	     String s = ob.readLine();
	    	     
	    	     
	    	     int []dp = new int[n+1];
	    	     int []countD = new int[n+1];
	    	     int []countK = new int[n+1];
	    	     
	    	    
	    	     
	    	     for(int i = 0; i < n; i++) {
	    	    	  
	    	    	 if(s.charAt(i) == 'D') {
	    	    		 countD[i+1] = countD[i]+1;
	    	    	 }else {
	    	    		 countD[i+1] = countD[i];
	    	    	 }
	    	    	 
	    	    	 if(s.charAt(i) == 'K') {
	    	    		 countK[i+1] = countK[i]+1;
	    	    	 }else {
	    	    		 countK[i+1] = countK[i];
	    	    	 }
	    	     }
	    	     
	    	      for(int i = 1;   i <= s.length(); i++) {
	    	    	  int D = countD[i];
	    	    	  int K = countK[i];
	    	    	  if(D == 0) {
	    	    		  dp[i] = K;
	    	    		  continue;
	    	    	  }
	    	    	  if(K == 0) {
	    	    		  dp[i] = D;
	    	    		  continue;
	    	    	  }
	    	    	  if(D%K == 0) {
	    	    		  solveDK(dp,i,D,K);
	    	    	  }else if(K%D == 0) {
	    	    		  solveKD(dp,i,D,K);
	    	    	  }else {
	    	    		  dp[i] = 1;
	    	    	  }
	    	      }
	    	       
	    	      for(int i = 1; i < dp.length; i++) {
	    	    	  System.out.print(dp[i]+" ");
	    	      }
	    	      System.out.println();
	    //	      System.out.println(Arrays.toString(dp));
	    	    	 
	    	     }
		}
	    	     
	    	     
	    	     
	               
	                  
	                  
	    		 

	    static class Scanner {
			StringTokenizer st;
			BufferedReader br;
	 
			public Scanner(InputStream s) {
				br = new BufferedReader(new InputStreamReader(s));
			}
	 
			public Scanner(FileReader r) {
				br = new BufferedReader(r);
			}

		public long[] nextlongArray(int n) throws IOException {
			long[] a = new long[n];
			for (int i = 0; i < n; i++)
				a[i] = nextLong();
			return a;
		}
 
		public Long[] nextLongArray(int n) throws IOException {
			Long[] a = new Long[n];
			for (int i = 0; i < n; i++)
				a[i] = nextLong();
			return a;
		}
 
		public int[] nextIntArray(int n) throws IOException {
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = nextInt();
			return a;
		}
 
		public Integer[] nextIntegerArray(int n) throws IOException {
			Integer[] a = new Integer[n];
			for (int i = 0; i < n; i++)
				a[i] = nextInt();
			return a;
		}
		
		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
 
		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
 
		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}
 
		public String nextLine() throws IOException {
			return br.readLine();
		}
	    
	    }
	    
	    
	    

		static class pairrr implements Comparable<pairrr> {
			int x;
			int y;
	 
			public pairrr(int x, int y) {
				this.x = x;
				this.y = y;
			}
	 
			public String toString() {
				return x + " " + y;
			}
	 
			public boolean equals(Object o) {
				if (o instanceof pair) {
					pairrr p = (pairrr) o;
					return p.x == x && p.y == y;
				}
				return false;
			}
	 
			public int hashCode() {
				return new Integer(x).hashCode() * 31 + new Integer(y).hashCode();
			}
	 
			public int compareTo(pairrr other) {
				if (this.x == other.x) {
					return Long.compare(this.y, other.y);
				}
				return Long.compare(this.x, other.x);
			}
		}
	 
	 
		static class tuble implements Comparable<tuble> {
			int x;
			int y;
			int z;
	 
			public tuble(int x, int y, int z) {
				this.x = x;
				this.y = y;
				this.z = z;
			}
	 
			public String toString() {
				return x + " " + y + " " + z;
			}
	 
			public int compareTo(tuble other) {
				if (this.x == other.x) {
					if (this.y == other.y) {
						return this.z - other.z;
					}
					return this.y - other.y;
				} else {
					return this.x - other.x;
				}
			}
		}
	    }
	    

	 

	    
	    		    		
	    		    		
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			
	    		    			