import java.io.*;
import java.util.*;

/*CODE BY SHIKHAR TYAGI*/

public class E {
    public static void main(String args[]) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int a[] = new int[n];
        ArrayList<Integer> ar = new ArrayList<>();
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            a[i] = scn.nextInt();
            if(map.containsKey(a[i])){
                int val = map.get(a[i]);
                val++;
                map.put(a[i],val);
            } else {
                map.put(a[i],1);
            }
        }
        Arrays.sort(a);
        for (int i = 0; i < n; ++i) {
            if (i == 0) {
                ar.add(a[i]);
            } else if (a[i] == ar.get(ar.size() - 1)) {
                continue;
            } else {
                ar.add(a[i]);
            }
        }
        int ans = 0;
        boolean taken[] = new boolean[150001];
        for (int no : ar) {
            int cnt = get(no, taken);
            ans += Math.min(cnt,map.get(no));
        }
        System.out.println(ans);
    }

    private static int get(int no, boolean[] taken) {
        if (no == 1) {
            taken[no] = true;
            taken[no + 1] = true;
            return 2;
        }
        int cnt = 0;
        if (taken[no]) {
            if (no + 1 < taken.length && !taken[no + 1]) {
                taken[no + 1] = true;
                cnt++;
            }
        } else {
            if (!taken[no - 1]) {
                taken[no - 1] = true;
                taken[no + 1] = true;
                taken[no] = true;
                cnt += 3;
            } else {
                taken[no + 1] = true;
                taken[no] = true;
                cnt += 2;
            }
        }
        return cnt;

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
