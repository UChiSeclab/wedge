import java.io.*;
import java.util.*;
import java.math.*;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class MovingPoints {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskA solver = new TaskA();
        solver.solve(1, in, out);
        out.close();
    }
    static class TaskA {
        long mod = (long)(1000000007);
        public void solve(int testNumber, InputReader in, PrintWriter out) throws IOException {
            while(testNumber-->0){
            	int n = in.nextInt();
            	Combine a[] = new Combine[n];
            	TreeSet<Integer> s = new TreeSet<>();
            	for(int i=0;i<n;i++)
            		a[i] = new Combine(in.nextInt() , 0);
            	for(int i=0;i<n;i++){
            		a[i].delete = in.nextInt();
            		s.add(a[i].delete);
            	}
            	Arrays.sort(a , new Sort2());
            	HashMap<Integer , Integer> index = new HashMap<>();
            	int b = 1;
            	for(int i:s)
            		index.put(i , b++);
            	long count[] = new long[b+1];
            	long sum[] = new long[b+1];
            	long ans = 0;
            	for(int i=0;i<n;i++){
            		long getCount = get(count , index.get(a[i].delete));
            		long getSum = get(sum , index.get(a[i].delete));
            		ans += a[i].value*getCount - getSum;
            		update(count , index.get(a[i].delete) , 1);
            		update(sum , index.get(a[i].delete) , a[i].value);
            	}
            	out.println(ans);
            }
        }
        public void update(long a[] , int index , long value){
        	for(int i=index;i<a.length;i += i&(-i))
        		a[i] += value;
        }
        public long get(long a[] , int index){
        	long res = 0;
        	for(int i=index;i>0;i-=i&(-i))
        		res += a[i];
        	return res;
        }
        public int[] nextGreater(int a[] , int n){
        	int ng[] = new int[n+1];
            Deque<Combine> s = new LinkedList<>();
            for(int i=n;i>0;i--){
                int k = i;
                Combine c = new Combine(a[k] , k);
                while(s.size()>0 && s.peekLast().value <= c.value){
                    Combine d = s.pollLast();
                    ng[d.delete] = c.delete;
                }
                s.addLast(c);
            }
            return ng;
        }
        public void sieve(int a[]){
            a[0] = a[1] = 1;
            int i;
            for(i=2;i*i<=a.length;i++){
                if(a[i] != 0)
                    continue;
                a[i] = i;
                for(int k = (i)*(i);k<a.length;k+=i){
                    if(a[k] != 0)
                        continue;
                    a[k] = i;
                }
            }
        }
        public int [][] matrixExpo(int c[][] , int n){
            int a[][] = new int[c.length][c[0].length];
            int b[][] = new int[a.length][a[0].length];
            for(int i=0;i<c.length;i++)
                for(int j=0;j<c[0].length;j++)
                    a[i][j] = c[i][j];
            for(int i=0;i<a.length;i++)
                b[i][i] = 1;
            while(n!=1){
                if(n%2 == 1){
                    b = matrixMultiply(a , a);
                    n--;
                }
                a = matrixMultiply(a , a);
                n/=2;
            }
            return matrixMultiply(a , b);
        }
        public int [][] matrixMultiply(int a[][] , int b[][]){
            int r1 = a.length;
            int c1 = a[0].length;
            int c2 = b[0].length;
            int c[][] = new int[r1][c2];
            for(int i=0;i<r1;i++){
                for(int j=0;j<c2;j++){
                    for(int k=0;k<c1;k++)
                        c[i][j] += a[i][k]*b[k][j];
                }
            }
            return c;
        }
        public long nCrPFermet(int n , int r , long p){
            if(r==0)
                return 1l;
            long fact[] = new long[n+1];
            fact[0] = 1;
            for(int i=1;i<=n;i++)
                fact[i] = (i*fact[i-1])%p;
            long modInverseR = pow(fact[r] , p-2 , 1l , p);
            long modInverseNR = pow(fact[n-r] , p-2 , 1l , p);
            long w = (((fact[n]*modInverseR)%p)*modInverseNR)%p;
            return w;
        }
        public long pow(long a , long b , long res , long mod){
            if(b==0)
                return res;
            if(b==1)
                return (res*a)%mod;
            if(b%2==1){
                res *= a;
                res %= mod;
                b--;
            }
            // System.out.println(a + " " + b + " " + res);
            return pow((a*a)%mod , b/2 , res , mod);
        }
        public long pow(long a , long b , long res){
            if(b == 0)
                return res;
            if(b==1)
                return res*a;
            if(b%2==1){
                res *= a;
                b--;
            }
            return pow(a*a , b/2 , res);
        }
        public void swap(int a[] , int p1 , int p2){
            int x = a[p1];
            a[p1] = a[p2];
            a[p2] = x;
        }
        public void sortedArrayToBST(TreeSet<Integer> a , int start, int end) { 
            if (start > end) {
                return;
            }
            int mid = (start + end) / 2;
            a.add(mid);
            sortedArrayToBST(a, start, mid - 1);
            sortedArrayToBST(a, mid + 1, end); 
        }
        class Combine{
            int value;
            int delete;
            Combine(int val , int delete){
                this.value = val;
                this.delete = delete;
            }
        }
        class Sort2 implements Comparator<Combine>{
            public int compare(Combine a , Combine b){
                if(a.value > b.value)
                    return 1;
                else if(a.value == b.value && a.delete>b.delete)
                    return 1;
                else if(a.value == b.value && a.delete == b.delete)
                    return 0;
                return -1;
            }
        }
        public int lowerLastBound(ArrayList<Integer> a , int x){
            int l = 0;
            int r = a.size()-1;
            if(a.get(l)>=x)
                return -1;
            if(a.get(r)<x)
                return r;
            int mid = -1;
            while(l<=r){
                mid = (l+r)/2;
                if(a.get(mid) == x && a.get(mid-1)<x)
                    return mid-1;
                else if(a.get(mid)>=x)
                    r = mid-1;
                else if(a.get(mid)<x && a.get(mid+1)>=x)
                    return mid;
                else if(a.get(mid)<x && a.get(mid+1)<x)
                    l = mid+1;
            }
            return mid;
        }
        public int upperFirstBound(ArrayList<Integer> a , Integer x){
            int l = 0;
            int r = a.size()-1;
            if(a.get(l)>x)
                return l;
            if(a.get(r)<=x)
                return r+1;
            int mid = -1;
            while(l<=r){
                mid = (l+r)/2;
                if(a.get(mid) == x && a.get(mid+1)>x)
                    return mid+1;
                else if(a.get(mid)<=x)
                    l = mid+1;
                else if(a.get(mid)>x && a.get(mid-1)<=x)
                    return mid;
                else if(a.get(mid)>x && a.get(mid-1)>x)
                    r = mid-1;
            }
            return mid;
        }
        public int lowerLastBound(int a[] , int x){
            int l = 0;
            int r = a.length-1;
            if(a[l]>=x)
                return -1;
            if(a[r]<x)
                return r;
            int mid = -1;
            while(l<=r){
                mid = (l+r)/2;
                if(a[mid] == x && a[mid-1]<x)
                    return mid-1;
                else if(a[mid]>=x)
                    r = mid-1;
                else if(a[mid]<x && a[mid+1]>=x)
                    return mid;
                else if(a[mid]<x && a[mid+1]<x)
                    l = mid+1;
            }
            return mid;
        }
        public int upperFirstBound(long a[] , long x){
            int l = 0;
            int r = a.length-1;
            if(a[l]>x)
                return l;
            if(a[r]<=x)
                return r+1;
            int mid = -1;
            while(l<=r){
                mid = (l+r)/2;
                if(a[mid] == x && a[mid+1]>x)
                    return mid+1;
                else if(a[mid]<=x)
                    l = mid+1;
                else if(a[mid]>x && a[mid-1]<=x)
                    return mid;
                else if(a[mid]>x && a[mid-1]>x)
                    r = mid-1;
            }
            return mid;
        }
        public long log(float number , int base){
            return (long) Math.floor((Math.log(number) / Math.log(base)));
        }
        public long gcd(long a , long b){
            if(a<b){
                long c = b;
                b = a;
                a = c;
            }
            if(b == 0)
                return a;
            return gcd(b , a%b);
        }
        public void print2d(long a[][] , PrintWriter out){
            for(int i=0;i<a.length;i++){
                for(int j=0;j<a[i].length;j++)
                    out.print(a[i][j] + " ");
                out.println();
            }
            out.println();
        }
        public void print1d(int a[] , PrintWriter out){
            for(int i=0;i<a.length;i++)
                out.print(a[i] + " ");
            out.println();
            out.println();
        }
        class Node{
        	int index;
        	Node right;
        	Node left;
        	public Node(int index){
        		this.index = index;
        		right = null;
        		left = null;
        	}
        }
        class AVLTree{
            Node root;
            public AVLTree(){
                this.root = null;
            }
            public int height(Node n){
                return (n == null ? 0 : n.height);
            }
            public int getBalance(Node n){
                return (n == null ? 0 : height(n.left) - height(n.right));
            }
            public Node rotateRight(Node a){
                Node b = a.left;
                Node br = b.right;

                a.lSum -= b.lSum;
                a.lCount -= b.lCount;
                
                b.rSum += a.rSum;
                b.rCount += a.rCount;
                
                b.right = a;
                a.left = br;
                
                a.height = 1 + Math.max(height(a.left) , height(a.right));
                b.height = 1 + Math.max(height(b.left) , height(b.right));
                return b;
            }
            public Node rotateLeft(Node a){
                Node b = a.right;
                Node bl = b.left;
                
                a.rSum -= b.rSum;
                a.rCount -= b.rCount;
                
                b.lSum += a.lSum;
                b.lCount += a.lCount;
                
                b.left = a;
                a.right = bl;
                
                a.height = 1 + Math.max(height(a.left) , height(a.right));
                b.height = 1 + Math.max(height(b.left) , height(b.right));
                return b;
            }
            public Node insert(Node root , long value){
                if(root == null){
                    return new Node(value);
                }
                if(value<=root.value){
                    root.lCount++;
                    root.lSum += value;
                    root.left =  insert(root.left , value);
                }
                if(value>root.value){
                    root.rCount++;
                    root.rSum += value;
                    root.right = insert(root.right , value);
                }
                // updating the height of the root
                root.height = 1 + Math.max(height(root.left) , height(root.right));
                int balance = getBalance(root);

                //ll
                if(balance>1 && value<=root.left.value)
                    return rotateRight(root);
                //rr
                if(balance<-1 && value>root.right.value)
                    return rotateLeft(root);
                //lr
                if(balance>1 && value>root.left.value){
                    root.left = rotateLeft(root.left);
                    return rotateRight(root);
                }
                //rl
                if(balance<-1 && value<=root.right.value){
                    root.right = rotateRight(root.right);
                    return rotateLeft(root);
                }
                return root;
            }
            public void insertElement(long value){
                this.root = insert(root , value);
            }
            public int getElementLessThanK(long k){
                int count = 0;
                Node temp = root;
                while(temp!=null){
                    if(temp.value == k){
                        if(temp.left == null || temp.left.value<k){
                            count += temp.lCount;
                            return count-1;
                        }
                        else
                            temp = temp.left;
                    }
                    else if(temp.value>k){
                        temp = temp.left;
                    }
                    else{
                        count += temp.lCount;
                        temp = temp.right;
                    }
                }
                return count;
            }
            public void inorder(Node root , PrintWriter out){
                Node temp = root;
                if(temp!=null){
                    inorder(temp.left , out);
                    out.println(temp.value + " " + temp.lCount + " " + temp.rCount);
                    inorder(temp.right , out);
                }
            }
            class Node{
	            long value;
	            long lCount , rCount;
	            long lSum , rSum;
	            Node left , right;
	            int height;
	            public Node(long value){
	                this.value = value;
	                left = null;
	                right = null;
	                lCount = 1;
	                rCount = 1;
	                lSum = value;
	                rSum = value;
	                height = 1;
	            }
	        }
        }
        
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

    }
}
