import java.io.*;
import java.util.*;

/*CODE BY SHIKHAR TYAGI*/

public class E {
    public static void main(String args[]) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        PriorityQueue<Integer> q = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i < n; ++i) {
            q.add(scn.nextInt());
        }
        int last = Integer.MAX_VALUE;
        int cnt = 0;
        while(!q.isEmpty()){
            int no = q.poll();
            if(no+1<last){
                cnt++;
                last = no+1;
            } else if(no < last){
                cnt++;
                last = no;
            } else if(no-1 != 0 && no-1 < last){
                cnt++;
                last = no-1;
            } else {
                // skip this person
            }
        }
        System.out.println(cnt);

    }

    public static int bin(ArrayList<Integer> ar, int target) {
        int h = ar.size() - 1;
        int l = 0;
        int ans = -1;
        while (l < h) {
            int m = l + (h - l) / 2;
            if (ar.get(m) > target) {
                h = m - 1;
            } else {
                l = m;
                ans = m;
            }
        }
        return ans;
    }

    public static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(String s) {
            try {
                br = new BufferedReader(new FileReader(s));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String nextToken() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(nextToken());
        }

        long nextLong() {
            return Long.parseLong(nextToken());
        }

        double nextDouble() {
            return Double.parseDouble(nextToken());
        }
    }
}

class graph {
    HashMap<Integer, HashSet<Integer>> list;

    graph() {
        list = new HashMap<>();
    }

    public HashMap<Integer, HashSet<Integer>> getList() {
        return list;
    }

    public void addEdge(int u, int v) {
        if (!list.containsKey(u)) {
            list.put(u, new HashSet<>());
        }
        if (!list.containsKey(v)) {
            list.put(v, new HashSet<>());
        }

        list.get(u).add(v);
        list.get(v).add(u);
    }


    public void dfs() {
        HashMap<Integer, Boolean> map = new HashMap<>();
        for (int no : list.keySet()) {
            map.put(no, false);
        }
        for (int no : list.keySet()) {
            if (!map.get(no)) {
                dfsHelper(no, map);
            }
        }
    }

    public void dfsHelper(int no, HashMap<Integer, Boolean> map) {
        map.put(no, true);
        for (int n : list.get(no)) {
            if (!map.get(n)) {
                dfsHelper(n, map);
            }
        }
    }
}
