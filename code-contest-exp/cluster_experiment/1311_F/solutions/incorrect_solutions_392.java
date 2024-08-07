import java.util.*;
import java.lang.*;
import java.io.*;

public class F {
	public static void main (String[] args) throws java.lang.Exception {
		new Solution();
	}
}
class Solution {
	Scanner scanner;

	public Solution() {
		scanner = new Scanner(System.in);

		while (scanner.hasNext()) {
			run_case();
		}
	}

	private void run_case() {
		String line = scanner.nextLine();
		int[] pos = strToIntArray(scanner.nextLine());
		int[] speed = strToIntArray(scanner.nextLine());

		List<Rock> list = new ArrayList<>();
		for (int i=0; i<pos.length; i++) {
			list.add(new Rock(pos[i], speed[i]));
		}
		Collections.sort(list, new ComparatorRock());

		long res = 0;

		int inf = (int)1e8 + 5;

		HashMap<Integer, Integer> cnt = new HashMap<>();
		SegmentTreeRSQ st_cnt = new SegmentTreeRSQ(-inf, inf);
		SegmentTreeRSQ st_pos = new SegmentTreeRSQ(-inf, inf);

		for (int i=list.size()-1; i>=0; i--) {
			int cur_v = list.get(i).v;
			int larger_cnt = (int)st_cnt.query(cur_v, inf);
			long larger_sum = st_pos.query(cur_v, inf);
			// list.add(0, res);

			cnt.putIfAbsent(cur_v, 0);
			cnt.put(cur_v, cnt.get(cur_v) + 1);

			st_cnt.update(cur_v, cnt.get(cur_v));
			st_pos.update(cur_v, st_pos.query(cur_v, cur_v) + list.get(i).pos);

			res += larger_sum - larger_cnt * list.get(i).pos;
		}

		// for (int i=0; i<list.size(); i++) {
		// 	for (int j=i+1; j<list.size(); j++) {
		// 		if (list.get(j).v >= list.get(i).v) {
		// 			res += list.get(j).pos - list.get(i).pos;
		// 		}
		// 	}
		// }

        System.out.println(res);

        return;
	}


	private int[] strToIntArray(String str) {
	    String[] vals = str.split("\\s+");
	    int sz = vals.length;
	    int[] res = new int[sz];
	    for (int i=0; i<sz; i++) {
	        res[i] = Integer.parseInt(vals[i]);
	    }
	    return res;
	}
}

class Rock {
	public int pos, v;
	public Rock(int pos, int v) {
		this.pos = pos;
		this.v = v;
	}
}

class ComparatorRock implements Comparator<Rock> {
    public int compare(Rock a, Rock b) {
        return a.pos - b.pos;
    }
}

class SegmentTreeRSQ {
	SegmentTreeRSQNode root = null;
    int[] nums;
	int inf = Integer.MAX_VALUE;

	public SegmentTreeRSQ(int start, int end) {
		root = new SegmentTreeRSQNode(start, end);
	}

	public void update(int i, long val) {
		if (root == null || i < root.start || i > root.end) return;
        update(root, i, val);
    }
    public void update(SegmentTreeRSQNode root, int i, long val) {
		if (root == null) return;
        if (root.start == i && root.end == i) root.sum = val;
        else {
            int mid = root.mid();
			// split
			// if (root.left == null) root.left = new SegmentTreeRSQNode(root.start, mid);
            // if (root.right == null) root.right = new SegmentTreeRSQNode(mid+1, root.end);
			// visit
            if (i <= mid) {
				if (root.left == null) root.left = new SegmentTreeRSQNode(root.start, mid);
				update(root.left, i, val);
            } else {
				if (root.right == null) root.right = new SegmentTreeRSQNode(mid+1, root.end);
				update(root.right, i, val);
			}
			// merge
			long sum_l = root.left != null ? root.left.sum : 0;
			long sum_r = root.right != null ? root.right.sum : 0;
			root.sum = sum_l + sum_r;
        }
    }

	public long query(int start, int end) {
        if (root == null) return 0;
        else return query(root, start, end);
    }
	public long query(SegmentTreeRSQNode root, int start, int end) {
		if (root == null) return 0;
        if (start > end) {
			return 0;
        } else if (root.start == start && root.end == end){
			return root.sum;
        } else {
            int mid = root.mid();
            if (end <= mid) { // in left
                return query(root.left, start, end);
            } else if (start >= mid+1) { // in right
                return query(root.right, start, end);
            } else {
				return query(root.left, start, mid) + query(root.right, mid+1, end);
            }
        }
    }
}

class SegmentTreeRSQNode {
	int inf = Integer.MAX_VALUE;
    public int start, end, min, max;
	public long sum;
    public SegmentTreeRSQNode left, right;
    public SegmentTreeRSQNode(int start, int end) {
        this.left = null;
        this.right = null;
        this.start = start;
        this.end = end;

        this.min = inf;
		this.max = -inf;
		this.sum = 0;
    }
	public int mid() {return this.start + (this.end - this.start) / 2;}
}
