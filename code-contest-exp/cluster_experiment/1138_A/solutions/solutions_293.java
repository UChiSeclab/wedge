import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class I_O {
    
    public static void main(String args[])throws IOException {
        
    	//CODE HERE	
    	int n = in.nextInt();
		int arr[] = new int[n];
		for(int i=0; i<n; i++) arr[i] = in.nextInt();
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		int one = 0, two = 0;
		for(int i=0; i<n; i++) {
			if(arr[i] == 1) {
				one++;
				if(two!=0) list.add(two);
				two = 0;
			}
			else {
				two++;
				if(one!=0) list.add(one);
				one = 0;
			}
		}
		if(one!=0) list.add(one);
		if(two!=0) list.add(two);
		int ans = 0;
		for(int i=0; i<list.size()-1; i++) {
			ans = Math.max(ans, Math.min(list.get(i), list.get(i+1)));
		}
		
		out.print(ans*2+"\n");
    	out.flush();
    	out.close();
    }

    // Fast I/O
    static BR in = new BR();
	static Reader br = new Reader();	
    static OutputWriter out = new OutputWriter(System.out);
    
    static class BR {
 		BufferedReader br; StringTokenizer st;
 		public BR() {
 			br = new BufferedReader(new InputStreamReader(System.in));
 		}
 		int nextInt() {
 			return Integer.parseInt(next());
 		}
 		long nextLong() {
 			return Long.parseLong(next());
 		} 
 		double nextDouble() {
 			return Double.parseDouble(next()); 
 		}
 		String nextLine() {
 			String str = "";
 			try {
 				str=br.readLine();
 				}
 			catch (IOException e) {
 				e.printStackTrace();
 				}
 			return str;
 		}
 		String next() {
 			while(st==null||!st.hasMoreElements()) {
 				try {
 					st=new StringTokenizer(br.readLine());
 					}
 				catch(IOException e) {
 					e.printStackTrace();
 					}
 				}
 			return st.nextToken();
 		}
 	}
    
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64];
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0)
                    writer.print(' ');
                writer.print(objects[i]);
            }
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }

    }

    //
}