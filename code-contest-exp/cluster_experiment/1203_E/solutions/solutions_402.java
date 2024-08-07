                    import java.util.*;
                    import java.io.*;
                    public class Main{
                        public static void main(String[] args){
                    		        Reader sc= new Reader(System.in);
                            int fc=0,prev=0,temp;
                            int n=sc.nextInt();
                            int arr[]=new int[n];
                            int count[] = new int[150001];
                            for(int i=0;i<n;i++){
                            	arr[i]=sc.nextInt();
                            	count[arr[i]]++;
                    }
                    int j=0;
                    for(int i=1;i<=150000;i++){
                        for(int k=0;k<count[i];k++){
                            arr[j++]=i;
                        }
                    }
                    for(int i=0;i<n;i++){
                    	            temp =arr[i];
                                if((temp-1)>0 && (temp-1)>prev){
                                    prev=temp-1;
                                    fc++;
                                }
                                else if(temp>prev){
                                    prev=temp;
                                    fc++;
                                }
                                  else if((temp+1)>prev){
                                    prev=temp+1;
                                    fc++;
                                }
                    }
                            System.out.println(fc);
                        }
                    }
                    
                    class Reader {
                		private InputStream stream;
                		private byte[] buf = new byte[1024];
                		private int curChar;
                		private int numChars;
                		private SpaceCharFilter filter;
                		
                		public Reader(InputStream stream) {
                			this.stream = stream;
                		}
                		
                		public int read() {
                			if (numChars == -1) {
                				throw new InputMismatchException();
                			}
                			if (curChar >= numChars) {
                				curChar = 0;
                				try {
                					numChars = stream.read(buf);
                				} catch (IOException e) {
                					throw new InputMismatchException();
                				}
                				if (numChars <= 0) {
                					return -1;
                				}
                			}
                			return buf[curChar++];
                		}
                		
                		public int nextInt() {
                			int c = read();
                			while (isSpaceChar(c)) {
                				c = read();
                			}
                			int sgn = 1;
                			if (c == '-') {
                				sgn = -1;
                				c = read();
                			}
                			int res = 0;
                			do {
                				if (c < '0' || c > '9') {
                					throw new InputMismatchException();
                				}
                				res *= 10;
                				res += c - '0';
                				c = read();
                			} while (!isSpaceChar(c));
                			return res * sgn;
                		}
                		
                		public String readString() {
                			int c = read();
                			while (isSpaceChar(c)) {
                				c = read();
                			}
                			StringBuilder res = new StringBuilder();
                			do {
                				res.appendCodePoint(c);
                				c = read();
                			} while (!isSpaceChar(c));
                			return res.toString();
                		}
                		
                		public double nextDouble() {
                			int c = read();
                			while (isSpaceChar(c)) {
                				c = read();
                			}
                			int sgn = 1;
                			if (c == '-') {
                				sgn = -1;
                				c = read();
                			}
                			double res = 0;
                			while (!isSpaceChar(c) && c != '.') {
                				if (c == 'e' || c == 'E') {
                					return res * Math.pow(10, nextInt());
                				}
                				if (c < '0' || c > '9') {
                					throw new InputMismatchException();
                				}
                				res *= 10;
                				res += c - '0';
                				c = read();
                			}
                			if (c == '.') {
                				c = read();
                				double m = 1;
                				while (!isSpaceChar(c)) {
                					if (c == 'e' || c == 'E') {
                						return res * Math.pow(10, nextInt());
                					}
                					if (c < '0' || c > '9') {
                						throw new InputMismatchException();
                					}
                					m /= 10;
                					res += (c - '0') * m;
                					c = read();
                				}
                			}
                			return res * sgn;
                		}
                		
                		public long nextLong() {
                			int c = read();
                			while (isSpaceChar(c)) {
                				c = read();
                			}
                			int sgn = 1;
                			if (c == '-') {
                				sgn = -1;
                				c = read();
                			}
                			long res = 0;
                			do {
                				if (c < '0' || c > '9') {
                					throw new InputMismatchException();
                				}
                				res *= 10;
                				res += c - '0';
                				c = read();
                			} while (!isSpaceChar(c));
                			return res * sgn;
                		}
                		
                		public boolean isSpaceChar(int c) {
                			if (filter != null) {
                				return filter.isSpaceChar(c);
                			}
                			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
                		}
                		
                		public String next() {
                			return readString();
                		}
                		
                		public interface SpaceCharFilter {
                			public boolean isSpaceChar(int ch);
                		}
                	}