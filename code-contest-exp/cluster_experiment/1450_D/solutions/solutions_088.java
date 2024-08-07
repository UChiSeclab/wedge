import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Coding {

    private static BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) {
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void run() throws Exception {
        InputModule inp = new InputModule();
        OutputModule out = new OutputModule();
        int t = inp.cinInt();
        while (t > 0) {
            int n = inp.cinInt();
            int[] ar = inp.cinIntArray(n);
            int[] cnt = new int[n+1];
            for(int i=1;i<=n;++i) cnt[i] = 0;
            for(int i=0;i<n;++i) cnt[ar[i]]++;

            int[] lsmall = new int[n];
            int[] rsmall = new int[n];

            for(int i=0;i<n;++i) {
                lsmall[i] = -1;
                rsmall[i] = n;
            }
            Stack<Integer> st = new Stack<>();
            st.add(0);
            for(int i=1;i<n;++i) {
                while (!st.isEmpty() && (ar[st.peek()]>=ar[i])) st.pop();
                if (!st.isEmpty()) lsmall[i] = st.peek();
                st.add(i);
            }
            st.clear();
            st.add(n-1);
            for(int i=n-2;i>=0;--i) {
                while (!st.isEmpty() && (ar[st.peek()]>ar[i])) st.pop();
                if (!st.isEmpty()) rsmall[i] = st.peek();
                st.add(i);
            }

            //out.printIntArray(lsmall);
            //out.printIntArray(rsmall);

            int first = 1;
            for(int i=1;i<=n;++i) {
                if (cnt[i] == 0) break;
                first++;
            }
            //out.printInt(first);
            int[] ans = new int[n];
            for(int i=0;i<n;++i) ans[i] = 1;
            for(int i=1;i<=n-first+1;++i) ans[i-1] = 0;

            int[] fmax = new int[n+1];
            int[] smax = new int[n+1];
            for(int i=1;i<=n;++i) fmax[i] = smax[i] = -1;

            int ovMax = -1;
            for(int i=0;i<n;++i) {
                int m2 = rsmall[i]-lsmall[i]-1;
                if (m2>fmax[ar[i]]) {
                    smax[ar[i]] = fmax[ar[i]];
                    fmax[ar[i]] = m2;
                } else if (m2>smax[ar[i]]) {
                    smax[ar[i]] = m2;
                }
                if ((lsmall[i]==i-1)||(rsmall[i]==i+1)) continue;
                int maxm = m2-1;
                ovMax = Math.max(ovMax, maxm);
            }
            //System.out.println(ovMax);
            for(int i=1;i<=n;++i) {
                if ((fmax[i]==-1)||(smax[i]==-1)) continue;
                //System.out.println("here for " + i + " " + fmax[i] + " " + smax[i]);
                int maxm = (fmax[i]==smax[i])?fmax[i]:smax[i];
                ovMax = Math.max(ovMax, maxm);
            }


            if (ovMax != -1) {
                for (int i = 2; i <= ovMax; ++i) ans[i - 1] = 0;
            }

            for(int i=0;i<n;++i)writer.append(String.valueOf(ans[i]));
            writer.append("\n");
            writer.flush();
            t--;
        }
    }

    private static class InputModule {
        private int cinInt() throws Exception {
            return Integer.parseInt(bi.readLine().split(" ")[0].trim());
        }

        private long cinLong() throws Exception {
            return Long.parseLong(bi.readLine().split(" ")[0].trim());
        }

        private Double cinDouble() throws Exception {
            return Double.parseDouble(bi.readLine().split(" ")[0].trim());
        }

        private String cinString() throws Exception {
            return bi.readLine();
        }

        private int[] cinIntArray(int n) throws Exception {
            String input = bi.readLine();
            String[] values = input.split(" ");
            int[] ar = new int[n];
            for (int i = 0; i < n; ++i) {
                ar[i] = Integer.parseInt(values[i]);
            }
            return ar;
        }

        private int[] cinIntArray() throws Exception {
            String input = bi.readLine();
            String[] values = input.split(" ");
            int[] ar = new int[values.length];
            for (int i = 0; i < values.length; ++i) {
                ar[i] = Integer.parseInt(values[i]);
            }
            return ar;
        }

        private long[] cinLongArray(int n) throws Exception {
            String input = bi.readLine();
            String[] values = input.split(" ");
            long[] ar = new long[n];
            for (int i = 0; i < n; ++i) {
                ar[i] = Long.parseLong(values[i]);
            }
            return ar;
        }

        private String[] cinStringArray(int n) throws Exception {
            return bi.readLine().split(" ");
        }
    }

    private static class OutputModule {
        private void printInt(int ans) throws Exception {
            writer.append(ans + "\n");
            writer.flush();
        }

        private void printLong(long ans) throws Exception {
            writer.append(ans + "\n");
            writer.flush();
        }

        private void printDouble(Double ans) throws Exception {
            writer.append(String.format("%.10f", ans));
            writer.append("\n");
            writer.flush();
        }

        private void printString(String ans) throws Exception {
            writer.append(ans + "\n");
            writer.flush();
        }

        private void printIntArray(int[] ans) throws Exception {
            for (int i = 0; i < ans.length; ++i) {
                writer.append(ans[i] + " ");
            }
            writer.append("\n");
            writer.flush();
        }

        private void printLongArray(long[] ans) throws Exception {
            for (int i = 0; i < ans.length; ++i) {
                writer.append(ans[i] + " ");
            }
            writer.append("\n");
            writer.flush();
        }

        private void printIntMatrix(int[][] mat, int n, int m) throws Exception {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    writer.append(mat[i][j] + " ");
                }
                writer.append("\n");
            }
            writer.flush();
        }

        private void printLongMatrix(long[][] mat, int n, int m) throws Exception {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    writer.append(mat[i][j] + " ");
                }
                writer.append("\n");
            }
            writer.flush();
        }

        private void printPoint(Point p) throws Exception {
            writer.append(p.x + " " + p.y + "\n");
            writer.flush();
        }

        private void printPoints(List<Point> p) throws Exception {
            for (Point pp : p) {
                writer.append(pp.x + " " + pp.y + "\n");
            }
            writer.flush();
        }
    }
}